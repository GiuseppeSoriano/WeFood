package it.unipi.lsmsdb.wefood.repository.base;

import com.mongodb.MongoException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.ArrayList;
import java.util.List;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.BsonValue;
import org.bson.Document;

import org.bson.conversions.Bson;
import com.mongodb.client.model.BsonField;
import com.mongodb.client.model.Aggregates;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;



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
            return null;
        } 
        return documents;
    }
    
    // If query succeeds, returns a list of documents containing the result of the find
    // If query fails, returns null

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

    // AGGREGATE

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

    // If query succeeds, returns a list of documents containing the result of the aggregation
    // If query fails, returns null
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

    // INSERT

    // If query succeeds, returns a list of documents containing the inserted ID
    // If query fails, returns null
    public static List<Document>  insertOne(String collectionName, String query) {
        Document query_insert = Document.parse(query);
        List<Document> documents = new ArrayList<>(); 


        try {
            MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
            InsertOneResult result = collection.insertOne(query_insert);
            // Ottieni l'ID del documento inserito
            BsonValue insertedId = result.getInsertedId();

            // Converti l'ID del documento in un oggetto Document
            documents.add(new Document("_id", insertedId));
            
            return documents;
        }
        catch(MongoException e) {
            //Something went wrong
            System.err.println("Something went wrong while querying the MongoDB database. Error: " + e.getMessage());
            return null;
        }
    }

    // UPDATE

    public static List<Document> updateOne(String collectionName, String query) {
        // Possible operations: $set, $unset, $push, $pull
        String set_regex = "\\$set:";
        String unset_regex = "\\$unset:";
        String push_regex = "\\$push:";
        String pull_regex = "\\$pull:";

        Matcher set_matcher = Pattern.compile(set_regex).matcher(query);
        Matcher unset_matcher = Pattern.compile(unset_regex).matcher(query);
        Matcher push_matcher = Pattern.compile(push_regex).matcher(query);
        Matcher pull_matcher = Pattern.compile(pull_regex).matcher(query);


        Document filter = null;
        Document update = null;
        Document[] tmp = null;
        if (set_matcher.find()) {
            tmp = extractDocument(query, set_regex);
        }
        else if (unset_matcher.find()) {
            tmp = extractDocument(query, unset_regex);
        }
        else if (push_matcher.find()) {
            tmp = extractDocument(query, push_regex);
        }
        else if (pull_matcher.find()) {
            tmp = extractDocument(query, pull_regex);
        }
        else {
            throw new IllegalArgumentException("Invalid MongoDB update operation: " + query);
        }
        
        filter = tmp[0];
        update = tmp[1];
        
        if(filter == null || update == null){
            throw new IllegalArgumentException("Invalid MongoDB update operation: " + query);
        }

        List<Document> documents = new ArrayList<>(); 

        try {
            MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
            UpdateResult result = collection.updateOne(filter, update);
            
            // Converti l'ID del documento in un oggetto Document
            documents.add(new Document("result", result.toString()));
            
            return documents;
        }
        catch(MongoException e) {
            //Something went wrong
            System.err.println("Something went wrong while querying the MongoDB database. Error: " + e.getMessage());
            return null;
        }

    }

    private static Document[] extractDocument(String query, String operation) {
        Pattern filterPattern = Pattern.compile("\\{(.*?)\\}", Pattern.DOTALL);
        Pattern updatePattern = Pattern.compile("\\{\\s*" + operation +  " \\{[\\s\\S]*?}}", Pattern.DOTALL);

        Matcher filterMatcher = filterPattern.matcher(query);
        Matcher updateMatcher = updatePattern.matcher(query);

        if (filterMatcher.find()) {
            System.out.println("filter = \"" + filterMatcher.group() + "\"");
        }

        if (updateMatcher.find()) {
            System.out.println("update = \"" + updateMatcher.group() + "\"");
        }

        String filterString = filterMatcher.group(1).trim();
        String updateString = updateMatcher.group(1).trim();

        Document [] documents = {Document.parse(filterString), Document.parse(updateString)};

        return documents;
    }

    // DELETE

    // If query succeeds, returns a list of documents containing the deleted count
    // If query fails, returns null
    public static List<Document> deleteOne(String collectionName, String query) {
        Document query_insert = Document.parse(query);
        List<Document> documents = new ArrayList<>(); 

        try {
            MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
            DeleteResult result = collection.deleteOne(query_insert);
            // Ottieni l'ID del documento inserito
            long deletedCount = result.getDeletedCount();

            // Converti l'ID del documento in un oggetto Document
            documents.add(new Document("deleted_count", deletedCount));
            
            return documents;
        }
        catch(MongoException e) {
            //Something went wrong
            System.err.println("Something went wrong while querying the MongoDB database. Error: " + e.getMessage());
            return null;
        }
    }
    

    public static List<Document> executeOnMongo(String mongosh_string){
        // String[] operations = {"find", "aggregate", "insertOne", "updateOne", "deleteOne"};
       
        List<Document> result;

        // Getting the collection name from the mongosh_string 
        // db.collectionName.FIRSToperation(document).SECONDoperation(document).THIRDoperation(document)
        // SECOND can only be sort
        // THIRD can only be limit
        String sort = "sort";
        String limit = "limit";

        String collectionName = new String(mongosh_string.split("\\.")[1]);

        // Getting the operation from the mongosh_string
        String operationWithDocument = new String(mongosh_string.split("\\.")[2]);
        
        String operation = new String(operationWithDocument.split("\\(")[0]);
        String operationDoc = mongosh_string.substring(mongosh_string.indexOf("(")+1, mongosh_string.indexOf(")"));

        System.out.println("Collection: " + collectionName);
        System.out.println("Operation: " + operation);
        System.out.println("OperationDoc: " + operationDoc);

        switch (operation) {
            case "find": // find

                // Now we check if mongosh_string contains sort and limit
                String sortDoc;
                String limitDoc;

                // Regex for extracting the document inside SECOND
                String sortRegex = sort+"\\((.+?)\\)";
                sortDoc = extractPattern(mongosh_string, sortRegex);

                // Regex for extracting the document inside THIRD
                String limitRegex = limit+"\\((\\d+)\\)";
                limitDoc = extractPattern(mongosh_string, limitRegex);

                System.out.println("sortDoc: " + sortDoc);
                System.out.println("limitDoc: " + limitDoc);

                return find(collectionName, operationDoc, sortDoc, limitDoc);
        
            case "aggregate": // aggregate
               
                return aggregate(collectionName, operationDoc);
            
            case "insertOne": // insertOne
                
                return insertOne(collectionName, operationDoc);
            
            case "updateOne": // updateOne
                
                return updateOne(collectionName, operationDoc);
                            
            case "deleteOne": // deleteOne

                return deleteOne(collectionName, operationDoc);
                
            default:
                throw new IllegalArgumentException("Invalid MongoDB operation: " + operation);
        }

    }

    private static String extractPattern(String input, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(1).trim();
        }
        return null;
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
            List<Document> result =executeOnMongo(mongosh_string);

            for(Document doc : result){
                System.out.println(doc.toJson());
            }

        } finally {
            closeMongoClient(); // Ensure client is closed
        }
        
    }

}

