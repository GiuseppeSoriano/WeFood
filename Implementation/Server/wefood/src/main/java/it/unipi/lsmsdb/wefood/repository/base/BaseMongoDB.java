package it.unipi.lsmsdb.wefood.repository.base;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

import org.bson.conversions.Bson;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Aggregates;
import org.json.JSONArray;
import org.json.JSONObject;


public abstract class BaseMongoDB {
    
    // private static final String MONGODB_HOST = "10.1.1.25:27017,10.1.1.24:27017"; // ,10.1.1.23:27017";
    private static final String MONGODB_HOST = "localhost:27017";
    private static final String MONGODB_DATABASE = "WeFood";
    private static final String mongoString = String.format("mongodb://%s/%s", MONGODB_HOST, MONGODB_DATABASE);

    private static volatile MongoClient client;

    // Singleton pattern for MongoClient
    public static MongoClient getMongoClient() {
        if (client == null) {
            synchronized (BaseMongoDB.class) {
                if (client == null) {
                    try {
                        client = MongoClients.create(mongoString);
                        System.out.println("MongoDB client created successfully");
                    } catch (MongoException e) {
                        System.err.println("Something went wrong while creating the MongoDB client. Error: " + e.getMessage());
                        throw e;
                    }
                }
            }
        }
        System.out.println("MongoDB client returned successfully");
        return client;
    }

    public static void closeMongoClient() {
        if (client != null) {
            try {
                client.close();
                client = null;  // Reset the client after closing
            } catch (MongoException e) {
                System.err.println("Something went wrong while closing the MongoDB client. Error: " + e.getMessage());
            }
        }
    }


    public List<Document> query(String collectionName, String query) throws MongoException {
        List<Document> result = new ArrayList<>();
        try {
            MongoCursor<Document> cursor = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName).find().iterator();
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                result.add(doc);
            }
        } catch (MongoException e) {
            //Something went wrong
            System.err.println("Something went wrong while querying the MongoDB database. Error: " + e.getMessage());
        }
        return result;
    }

    


    public List<Document> query(String collectionName, Document query) {
        List<Document> documents = new ArrayList<>();

        try {
            MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
            try (MongoCursor<Document> cursor = collection.find(query).iterator()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    documents.add(document);
                }
            }
        } catch(MongoException e) {
            //Something went wrong
        } /*finally {
            mongoClient.close();
        }
        */
        return documents;
    }

    // FIND 
    
    public static List<Document> execute_find(String collectionName, Document query_find, Document query_sort, long query_limit) {
        List<Document> documents = new ArrayList<>(); 

        // Se query_sort come stringa è null, passo quella di default
        // Se query_limit è null, passo quella di default

        try {
            MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
            try (MongoCursor<Document> cursor = collection.find(query_find).sort(query_sort).limit((int) query_limit).iterator()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    documents.add(document);
                }
            }
        } catch(MongoException e) {
            //Something went wrong
            System.err.println("Something went wrong while querying the MongoDB database. Error: " + e.getMessage());
        } 
        return documents;
    }
    
    public static List<Document> find(String collectionName, String find, String sort, String limit) {
        Document query_find = Document.parse(find);
        if(sort == null){
            sort = "{}";
        }
        Document query_sort = Document.parse(sort);
        if(limit == null){
            limit = "0";
        }
        long query_limit = Long.parseLong(limit);

        System.out.println("Query Find: " + query_find.toJson());
        System.out.println("Query Sort: " + query_sort.toJson());
        System.out.println("Query Limit: " + query_limit);

        return execute_find(collectionName, query_find, query_sort, query_limit);
    }    

    // AGGREGATIONS

    private static Bson handleGroupOperation(JSONObject groupJson) {
        // Rimuovi '_id' se è null e crea accumulatori di campo
        List<BsonField> fieldAccumulators = new ArrayList<>();
        for (String key : groupJson.keySet()) {
            if (!"_id".equals(key) || !groupJson.isNull(key)) {
                fieldAccumulators.add(new BsonField(key, new Document(groupJson.getJSONObject(key).toMap())));
            }
        }

        return Aggregates.group(groupJson.isNull("_id") ? null : groupJson.get("_id"), fieldAccumulators.toArray(new BsonField[0]));
    }

    public static List<Bson> translateAggregations(String queryString) {
        JSONArray queryArray = new JSONArray(queryString);
        List<Bson> bsonList = new ArrayList<>();

        for (int i = 0; i < queryArray.length(); i++) {
            JSONObject queryPart = queryArray.getJSONObject(i);
            String key = queryPart.keys().next();

            switch (key) {
                case "$match":
                    bsonList.add(Aggregates.match(new Document(queryPart.getJSONObject(key).toMap())));
                    break;
                case "$sort":
                    bsonList.add(Aggregates.sort(new Document(queryPart.getJSONObject(key).toMap())));
                    break;
                case "$limit":
                    // Estrai direttamente il valore intero per $limit
                    bsonList.add(Aggregates.limit(queryPart.getInt(key)));
                    break;
                case "$group":
                    bsonList.add(handleGroupOperation(queryPart.getJSONObject(key)));
                    break;
                case "$project":
                    bsonList.add(Aggregates.project(new Document(queryPart.getJSONObject(key).toMap())));
                    break;
                // Aggiungi altri casi per altri tipi di operazioni di aggregazione
            }
        }
        return bsonList;
    }


    public static List<Document> aggregate(String collectionName, String query) {
        List<Document> documents = new ArrayList<>();

        try {
            MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
            List<Bson> bsonList = translateAggregations(query);
            try (MongoCursor<Document> cursor = collection.aggregate(bsonList).iterator()) {
                while (cursor.hasNext()) {
                    Document document = cursor.next();
                    documents.add(document);
                }
            }
        } catch(MongoException e) {
            //Something went wrong
            System.err.println("Something went wrong while querying the MongoDB database. Error: " + e.getMessage());
        } 
        return documents;
    }


    // anche queste dovrebbero ritornare una lista di document o no?

    // INSERT

    public boolean insertOne(String collectionName, Document document) {
        
        return true;
    }

    // UPDATE

    public boolean updateOne(String collectionName, Document document) {
        
        return true;
    }

    // DELETE

    public boolean deleteOne(String collectionName, Document document) {
        
        return true;
    }
    

    // public List<Document>
    public static void executeOnMongo(String mongosh_string){
        // String[] operations = {"find", "aggregate", "insertOne", "updateOne", "deleteOne"};

        // Getting the collection name from the mongosh_string 
        // db.collectionName.FIRSToperation(document).SECONDoperation(document).THIRDoperation(document)
        // SECOND can only be sort
        // THIRD can only be limit
        String second = "sort";
        String third = "limit";

        String collectionName = new String(mongosh_string.split("\\.")[1]);

        // String[] risultato = inputString.split("\\(|\\)");

        // Getting the operation from the mongosh_string
        String operationWithDocument = new String(mongosh_string.split("\\.")[2]);
        
        String operation = new String(operationWithDocument.split("\\(")[0]);
        String operationDoc = new String(operationWithDocument.split("\\(")[1].split("\\)")[0]);

        System.out.println("Collection: " + collectionName);
        System.out.println("Operation: " + operation);
        System.out.println("OperationDoc: " + operationDoc);

        switch (operation) {
            case "find": // find

                // Now we check if mongosh_string contains sort and limit
                String sortDoc;
                String limitDoc;
                if(mongosh_string.split("\\.").length > 3){
                    
                    if(mongosh_string.split("\\.").length == 5){
                        // We have SECOND and THIRD operation
                        sortDoc = new String(mongosh_string.split("\\.")[3].split("\\(")[1].split("\\)")[0]);
                        limitDoc = new String(mongosh_string.split("\\.")[4].split("\\(")[1].split("\\)")[0]);
                    }
                    else{
                        // We have only SECOND operation
                        if(mongosh_string.split("\\.")[3].contains(second)){
                            sortDoc = new String(mongosh_string.split("\\.")[3].split("\\(")[1].split("\\)")[0]);
                            limitDoc = null;
                        }
                        else if(mongosh_string.split("\\.")[3].contains(third)){
                            sortDoc = null;
                            limitDoc = new String(mongosh_string.split("\\.")[3].split("\\(")[1].split("\\)")[0]);
                        }
                        else{
                            throw new IllegalArgumentException("Invalid SECOND operation. Expected 'sort' or 'limit', instead received: " + mongosh_string.split("\\.")[3].split("\\(")[0]);
                        }
                    }
                }
                else{
                    // No SECOND and THIRD operation

                    sortDoc = null;
                    limitDoc = null;
                }

                System.out.println("sortDoc: " + sortDoc);
                System.out.println("limitDoc: " + limitDoc);

                List<Document> result = find(collectionName, operationDoc, sortDoc, limitDoc);

                for(Document doc : result){
                    System.out.println(doc.toJson());
                }
                
                // return find(collectionName, operationDoc, sortDoc, limitDoc);
                break;
        
            case "aggregate": // aggregate
                result = aggregate(collectionName, operationDoc);

                System.out.println();
                for(Document doc : result){
                    System.out.println(doc.toJson());
                }
                // return aggregate(collectionName, operationDoc);
                break;
            
            case "insertOne": // insertOne
                
                // return insertOne(collectionName, operationDoc);
                break;
            
            case "updateOne": // updateOne
                
                // return updateOne(collectionName, operationDoc);
                break;
            
            case "deleteOne": // deleteOne

                // return deleteOne(collectionName, operationDoc);
                break;

            default:
                throw new IllegalArgumentException("Invalid MongoDB operation: " + operation);
        }

    }


    // TEST MAIN
    public static void main(String[] args) {
        try {
            getMongoClient(); // Ensure client is created
            String mongosh_string = "db.Post.aggregate([\n" +
                    "    {\n" +
                    "        $match: {\n" +
                    "            username: \"cody_cisneros_28\",\n" +
                    "        }\n" +
                    "    },\n" +
                    "    {\n" +
                    "        $sort: {\n" +
                    "            avgStarRanking: -1\n" +
                    "        }\n" +
                    "    },\n" +
                    "    {\n" +
                    "        $limit: 1\n" +
                    "    },\n" +
                    "    {\n" +
                    "        $project: {\n" +
                    "            _id: 0,\n" +
                    "            post: {\n" +
                    "                _id: \"$_id\",\n" +
                    "                description: \"$description\",\n" +
                    "                timestamp: \"$timestamp\",\n" +
                    "                recipe: \"$recipe\",\n" +
                    "                avgStarRanking: \"$avgStarRanking\",\n" +
                    "                starRankings: \"$starRankings\",\n" +
                    "                comments: \"$comments\"\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "])";
            executeOnMongo(mongosh_string);
        } finally {
            closeMongoClient(); // Ensure client is closed
        }
    }

}