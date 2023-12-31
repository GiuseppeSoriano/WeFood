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

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.client.model.Updates;

import static com.mongodb.client.model.Updates.set;


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
        Bson updateOperation = new Document();

        if (set_matcher.find()) {
            tmp = extractFilterAndUpdate(query);
            filter = tmp[0];
            update = tmp[1];
            for (String key : update.keySet()) {
                updateOperation = Updates.combine(updateOperation, Updates.set(key, update.get(key)));
            }
        }
        else if (unset_matcher.find()) {
            tmp = extractFilterAndUpdate(query);
            filter = tmp[0];
            update = tmp[1];
            for (String key : update.keySet()) {
                updateOperation = Updates.combine(updateOperation, Updates.unset(key));
            }
        }
        else if (push_matcher.find()) {
            tmp = extractFilterAndUpdate(query);
            filter = tmp[0];
            update = tmp[1];
            for (String key : update.keySet()) {
                Document value = update.get(key, Document.class);
                updateOperation = Updates.combine(updateOperation, Updates.push(key, value));
            }
        }
        else if (pull_matcher.find()) {
            tmp = extractFilterAndUpdate(query);
            filter = tmp[0];
            update = tmp[1];
            for (String key : update.keySet()) {
                Document value = update.get(key, Document.class);
                updateOperation = Updates.combine(updateOperation, Updates.pull(key, value));
            }
        }
        else {
            throw new IllegalArgumentException("Invalid MongoDB update operation" + query);
        }

        List<Document> documents = new ArrayList<>(); 

        try {
            MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
            UpdateResult result = collection.updateOne(filter, updateOperation);
            
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
        Pattern filterPattern = Pattern.compile("\\{([^}]+)\\}", Pattern.DOTALL);
        Pattern updatePattern = Pattern.compile(operation + "\\s*([^)]+?)\\)", Pattern.DOTALL);

        Matcher filterMatcher = filterPattern.matcher(query);
        Matcher updateMatcher = updatePattern.matcher(query);

        String filterString = "";
        String updateString = "";

        if (filterMatcher.find()) {
            filterString = filterMatcher.group();
            System.out.println("filter = \"" + filterString + "\"");
        }

        if (updateMatcher.find()) {
            updateString = updateMatcher.group();
            updateString = updateString.replaceFirst(operation+"\\s*", "");
            System.out.println("update = \"" + updateString + "\"");
        }

        Document doc1 = Document.parse(filterString);
        System.out.println("doc1 = " + doc1.toString());

        Document doc2 = Document.parse(updateString);
        System.out.println("doc2 = " + doc2.toString());

        Document[] documents = {Document.parse(filterString), Document.parse(updateString)};

        return documents;
    }

    public static Document[] extractFilterAndUpdate(String query) {
        String[] result = new String[2];
        result[0] = "";
        result[1] = "";
        int braceCount = 0;
        int startOuter = -1;
        int startInner = -1;

        for (int i = 0; i < query.length(); i++) {
            if (query.charAt(i) == '{') {
                braceCount++;
                if (braceCount == 1 && startOuter == -1) {
                    startOuter = i; // Inizio della prima sottostringa
                } else if (braceCount == 2 && startInner == -1) {
                    startInner = i; // Inizio della sottostringa interna
                }
            } else if (query.charAt(i) == '}') {
                if (braceCount == 2) {
                    // Fine della sottostringa interna
                    result[1] = query.substring(startInner, i + 1);
                    startInner = -1; // Reimposta l'inizio per la prossima sottostringa interna
                }
                braceCount--;
                if (braceCount == 0 && startOuter != -1) {
                    // Fine della sottostringa esterna
                    if (result[0].isEmpty()) {
                        result[0] = query.substring(startOuter, i + 1);
                        startOuter = -1; // Reimposta l'inizio per la prossima sottostringa esterna
                    }
                }
            }
        }

        return new Document[]{Document.parse(result[0]), Document.parse(result[1])};
    }




    // DELETE

    // If query succeeds, returns a list of documents containing the deleted count
    // If query fails, returns null
    public static List<Document> deleteOne(String collectionName, String query) {
        Document query_delete = Document.parse(query);
        List<Document> documents = new ArrayList<>(); 

        try {
            MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
            DeleteResult result = collection.deleteOne(query_delete);
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
            String mongosh_string = "db.Ingredient.updateOne({\n" +
                    "   \"name\": \"Pippo\"\n" +
                    "}," +
                    "{$unset: {\"comments\": \"\" }}" +
                    ")";

            List<Document> result =executeOnMongo(mongosh_string);

            System.out.println();
            System.out.println("Result: ");
            for(Document doc : result){
                System.out.println(doc.toJson());
            }

        } finally {
            closeMongoClient(); // Ensure client is closed
        }
    }

}

