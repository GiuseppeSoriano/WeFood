package it.unipi.lsmsdb.wefood.repository.base;

import com.mongodb.MongoException;
import com.mongodb.client.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BsonString;
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
import com.mongodb.client.model.Updates;



public abstract class BaseMongoDB {
    
    // private static final String MONGODB_HOST = "localhost:27017";
    private static final String MONGODB_HOST = "10.1.1.25:27017,10.1.1.24:27017,10.1.1.23:27017";
    private static final String MONGODB_DATABASE = "WeFood";
    private static final String WRITE_CONCERN = "1";
    private static final String WTIMEOUT = "5000";
    private static final String READ_PREFERENCE = "nearest";
    private static final String mongoString = String.format("mongodb://%s/%s?w=%s&wtimeout=%s&readPreference=%s", MONGODB_HOST, MONGODB_DATABASE, WRITE_CONCERN, WTIMEOUT, READ_PREFERENCE);

    private static final String SET_REGEX = "\\$set:";
    private static final String UNSET_REGEX = "\\$unset:";
    private static final String PUSH_REGEX = "\\$push:";
    private static final String PULL_REGEX = "\\$pull:";


    private static volatile MongoClient client;

    private static enum QueryType {
        UNSET_COMMENT(
                "db.Ingredient.updateOne({\n" +
                        "   \"name\": \"Pippo\"\n" +
                        "}," +
                        "{$unset: {\"comments\": \"\" }}" +
                        ")"
        ),
        SET_COMMENT(
                "db.Ingredient.updateOne({\n" +
                        "   \"name\": \"Pippo\"\n" +
                        "}," +
                        "{$set: {\"comments\": [{\"author\": \"pippo\"}] }}" +
                        ")"
        ),
        PUSH_COMMENT(
                "db.Ingredient.updateOne({\n" +
                        "   \"name\": \"Pippo\"\n" +
                        "}," +
                        "{$push: {\"comments\": {\"author\": \"pluto\"} }}" +
                        ")"
        ),
        PULL_COMMENT(
                "db.Ingredient.updateOne({\n" +
                        "   \"name\": \"Pippo\"\n" +
                        "}," +
                        "{$pull: {\"comments\": {\"author\": \"pippo\"} }}" +
                        ")"
        ),
        FIND(
                "db.Post.find({ timestamp: { $gte: 1389712493188 } }).sort({avgStarRanking:-1}).limit(10)"
        ),
        AGGREGATE(
                "db.Ingredient.aggregate([\n" +
                        "   {$match: {\"name\": \"Pippo\"}},\n" +
                        "   {$sort: {\"name\": 1}},\n" +
                        "   {$limit: 1}\n" +
                        "])"
        ),
        INSERT_ONE(
                "db.Ingredient.insertOne({\n" +
                        "   \"name\": \"Pippo\"\n" +
                        "})"
        ),
        DELETE_ONE(
                "db.Ingredient.deleteOne({\n" +
                        "   \"name\": \"Pippo\"\n" +
                        "})"
        );

        private final String query;

        QueryType(String query) {
            this.query = query;
        }

        public String getQuery() {
            return query;
        }
    };


    // Singleton pattern for MongoClient
    public static void openMongoClient() {
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

    // FIND
    // If query succeeds, returns a list of documents containing the result of the find
    // If query fails, throws an exception. Here excpetions that can be thrown:
    // - MongoException: high level exception used to deal with errors linked to database operations
    // - IllegalArgumentException: In document.parse, if the query isn't a propper json file
    // - IllegalStateException: Thrown if MongoCollection or MongoClient aren't able to perform operations

    private static List<Document> find(String collectionName, String find, String project, String sort, String limit)
            throws MongoException, IllegalArgumentException, IllegalStateException {

        Document query_find = Document.parse(find);
        Document query_project = (project==null) ? null : Document.parse(project);                  // If project is null, null is passed
        Document query_sort = (sort==null) ? Document.parse("{}") : Document.parse(sort);      // If sort is null, default value is passed
        long query_limit = (limit==null) ? 0 : Long.parseLong(limit);                               // If limit is null, default value is passed

        return execute_find(collectionName, query_find, query_project, query_sort, query_limit);
    }

    private static List<Document> execute_find(String collectionName, Document query_find, Document query_project, Document query_sort, long query_limit)
            throws MongoException, IllegalArgumentException, IllegalStateException {

        List<Document> documents = new ArrayList<>(); 

        MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
        FindIterable<Document> findIterable = collection.find(query_find).sort(query_sort).limit((int) query_limit);
        if (query_project != null) {
            findIterable = findIterable.projection(query_project);
        }
        MongoCursor<Document> cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            documents.add(document);
        }
        return documents;
    }
    

    // AGGREGATE

    // If query succeeds, returns a list of documents containing the result of the aggregation
    // If query fails, throws an exception. Here excpetions that can be thrown:
    // - MongoException: high level exception used to deal with errors linked to database operations
    // - IllegalArgumentException: In document.parse, if the query isn't a propper json file
    // - IllegalStateException: Thrown if MongoCollection or MongoClient aren't able to perform operations
    private static List<Document> aggregate(String collectionName, String query) 
            throws MongoException, IllegalArgumentException, IllegalStateException {

        List<Document> documents = new ArrayList<>();

        MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
        List<Bson> bsonList = translateAggregations(query);
        MongoCursor<Document> cursor = collection.aggregate(bsonList).iterator();
        while (cursor.hasNext()) {
            Document document = cursor.next();
            documents.add(document);
        }

        return documents;
    }

    //mongosh_string = "db.Post.aggregate([{$match: {$expr:{ $eq:[{$toString:\"$_id\"}, '658572b7d312a33aeb784cfc']}} }])";
    // If you want to match by object id, you need to convert the object id to string following the above syntax
    // in order to avoid errors in converting to json array
    private static List<Bson> translateAggregations(String queryString) throws IllegalArgumentException {
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
            }
        }
        return bsonList;
    }

    /**
     * Handles the "$group" stage of a MongoDB aggregation pipeline.
     *
     * @param groupJson JSONObject representing the group operation in the aggregation pipeline.
     * @return Bson representation of the group stage.
     */
    private static Bson handleGroupOperation(JSONObject groupJson) throws IllegalArgumentException {
        List<BsonField> fieldAccumulators = new ArrayList<>();
        for (String key : groupJson.keySet()) {
            if (!"_id".equals(key)) {
                // Retrieves the accumulator operation for each field in the group stage.
                JSONObject accumulatorObject = groupJson.getJSONObject(key);
                // Converts the JSON object to a BsonValue and creates a BsonField, then adds it to the list.
                fieldAccumulators.add(new BsonField(key, convertToBsonValue(accumulatorObject)));
            }
        }
        // Creates and returns the group stage Bson object using the _id field and the list of accumulators.
        return Aggregates.group(groupJson.isNull("_id") ? null : groupJson.get("_id"), fieldAccumulators);
    }

    /**
     * Converts a JSONObject to a BsonValue.
     *
     * @param json JSONObject to be converted.
     * @return BsonValue representation of the JSONObject.
     */
    private static Bson convertToBsonValue(JSONObject json) {
        Document doc = new Document();
        for (String key : json.keySet()) {
            Object value = json.get(key);
            if (value instanceof JSONObject) {
                // Recursively converts nested JSONObjects to Documents.
                doc.append(key, convertToDocument((JSONObject) value));
            } else if (value instanceof JSONArray) {
                // Converts JSONArray to a List of objects.
                doc.append(key, convertToArray((JSONArray) value));
            } else {
                // Directly adds the value if it's not a JSONObject or JSONArray.
                doc.append(key, value);
            }
        }
        // Converts the Document to a BsonDocument.
        return new Document(doc).toBsonDocument();
    }

    /**
     * Converts a JSONObject to a Document.
     *
     * @param json JSONObject to be converted.
     * @return Document representation of the JSONObject.
     */
    private static Document convertToDocument(JSONObject json) {
        Document doc = new Document();
        for (String key : json.keySet()) {
            Object value = json.get(key);
            if (value instanceof JSONObject) {
                // Recursively converts nested JSONObjects to Documents.
                doc.append(key, convertToDocument((JSONObject) value));
            } else if (value instanceof JSONArray) {
                // Converts JSONArray to a List of objects.
                doc.append(key, convertToArray((JSONArray) value));
            } else {
                // Directly adds the value if it's not a JSONObject or JSONArray.
                doc.append(key, value);
            }
        }
        return doc;
    }

    /**
     * Converts a JSONArray to a List of objects.
     *
     * @param array JSONArray to be converted.
     * @return List of objects representing the JSONArray.
     */
    private static List<Object> convertToArray(JSONArray array) {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONObject) {
                // Recursively converts nested JSONObjects to Documents.
                list.add(convertToDocument((JSONObject) value));
            } else if (value instanceof JSONArray) {
                // Recursively converts nested JSONArrays to Lists.
                list.add(convertToArray((JSONArray) value));
            } else {
                // Adds the value directly if it's not a JSONObject or JSONArray.
                list.add(value);
            }
        }
        return list;
    }

    // INSERT

    // If query succeeds, returns a list of documents containing the inserted ID
    // If query fails, throws an exception. Here excpetions that can be thrown:
    // - MongoException: high level exception used to deal with errors linked to database operations
    // - IllegalArgumentException: In document.parse, if the query isn't a propper json file
    // - IllegalStateException: Thrown if MongoCollection or MongoClient aren't able to perform operations
    private static List<Document>  insertOne(String collectionName, String query) 
            throws MongoException, IllegalArgumentException, IllegalStateException {
        Document query_insert = Document.parse(query);
        List<Document> documents = new ArrayList<>();

        MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
        InsertOneResult result = collection.insertOne(query_insert);
        
        // Ottieni l'ID del documento inserito
//        BsonValue insertedId = result.getInsertedId();
        String insertedId = result.getInsertedId().asObjectId().getValue().toString();      // DA PROVARE
        // Converti l'ID del documento in un oggetto Document
        documents.add(new Document("_id", insertedId));
            
        return documents;
    }

    // UPDATE

    private static List<Document> updateOne(String collectionName, String query) 
            throws MongoException, IllegalArgumentException, IllegalStateException{
        // Possible operations: $set, $unset, $push, $pull

        Document filter = null;
        Document update = null;
        Document[] tmp = null;
        Bson updateOperation = new Document();
        List<Document> documents = new ArrayList<>(); 

        Matcher set_matcher = Pattern.compile(SET_REGEX).matcher(query);
        Matcher unset_matcher = Pattern.compile(UNSET_REGEX).matcher(query);
        Matcher push_matcher = Pattern.compile(PUSH_REGEX).matcher(query);
        Matcher pull_matcher = Pattern.compile(PULL_REGEX).matcher(query);

        tmp = extractFilterAndUpdate(query);
        filter = tmp[0];
        update = tmp[1];

        if (set_matcher.find()) {
            for (String key : update.keySet()) {
                updateOperation = Updates.combine(updateOperation, Updates.set(key, update.get(key)));
            }
        }
        else if (unset_matcher.find()) {
            for (String key : update.keySet()) {
                updateOperation = Updates.combine(updateOperation, Updates.unset(key));
            }
        }
        else if (push_matcher.find()) {
            for (String key : update.keySet()) {
                Document value = update.get(key, Document.class);
                updateOperation = Updates.combine(updateOperation, Updates.push(key, value));
            }
        }
        else if (pull_matcher.find()) {
            for (String key : update.keySet()) {
                Document value = update.get(key, Document.class);
                updateOperation = Updates.combine(updateOperation, Updates.pull(key, value));
            }
        }

        MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
        UpdateResult result = collection.updateOne(filter, updateOperation);
        
        // Converti l'ID del documento in un oggetto Document
        documents.add(new Document("result", result.getMatchedCount()));
        
        return documents;
    }

    private static Document[] extractFilterAndUpdate(String query) throws IllegalArgumentException {
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
    // If query fails, throws an exception. Here excpetions that can be thrown:
    // - MongoException: high level exception used to deal with errors linked to database operations
    // - IllegalArgumentException: In document.parse, if the query isn't a propper json file
    // - IllegalStateException: Thrown if MongoCollection or MongoClient aren't able to perform operations
    private static List<Document> deleteOne(String collectionName, String query)
            throws MongoException, IllegalArgumentException, IllegalStateException {
        Document query_delete = Document.parse(query);
        List<Document> documents = new ArrayList<>(); 

        MongoCollection<Document> collection = client.getDatabase(MONGODB_DATABASE).getCollection(collectionName);
        DeleteResult result = collection.deleteOne(query_delete);
        // Get how many documents were deleted
        long deletedCount = result.getDeletedCount();
        // Create document with the deleted count to be returned
        documents.add(new Document("deleted_count", deletedCount));
            
        return documents;
    }

    // If query fails, throws an exception. Here excpetions that can be thrown:
    // - MongoException: high level exception used to deal with errors linked to database operations
    // - IllegalArgumentException: In document.parse, if the query isn't a propper json file
    // - IllegalStateException: Thrown if MongoCollection or MongoClient aren't able to perform operations

    public static List<Document> executeQuery(String mongosh_string) 
        throws IllegalArgumentException, IllegalStateException, MongoException {
        // OPERATIONS IMPLEMENTED = {"find", "aggregate", "insertOne", "updateOne", "deleteOne"};
       
        // Getting the collection name from the mongosh_string
        String collectionName = new String(mongosh_string.split("\\.")[1]);

        // Getting the operation from the mongosh_string
        String operationWithDocument = new String(mongosh_string.split("\\.")[2]);
        
        String operation = new String(operationWithDocument.split("\\(")[0]);
        
        String operationDoc = mongosh_string.substring(mongosh_string.indexOf("(")+1);
        for (int i = 0, counter = 0; i < operationDoc.length(); i++) {
            if (operationDoc.charAt(i) == '(') {
                counter++;
            }
            else if(operationDoc.charAt(i) == ')') {
                if(counter == 0){
                    operationDoc = operationDoc.substring(0, i);
                    break;
                }
                counter--;
            }
        }

        System.out.println("Operation: " + operation);
        System.out.println("OperationDoc: " + operationDoc);

        switch (operation) {
            case "find": // find
                String findDoc = operationDoc;
                String projectDoc = null;
                // Regex for extracting the project from operationDoc
                String[] operationDocParts = operationDoc.split("},\\s*\\{");

                if(operationDocParts.length > 1){
                    // We do have the project inside the operationDoc
                    findDoc = operationDocParts[0] + "}";
                    projectDoc = "{" + operationDocParts[1];
                }
                System.out.println("findDoc: "+ findDoc);
                System.out.println("projectDoc: "+ projectDoc);

                // Regex for extracting the document inside sort
                String sortDoc = extractPattern(mongosh_string, "sort\\((.+?)\\)");
                // Regex for extracting the document inside limit
                String limitDoc = extractPattern(mongosh_string, "limit\\((\\d+)\\)");

                System.out.println("sortDoc: " + sortDoc);
                System.out.println("limitDoc: " + limitDoc);

                return find(collectionName, findDoc, projectDoc, sortDoc, limitDoc);
        
            case "aggregate": // aggregate

                return aggregate(collectionName, operationDoc);
            
            case "insertOne": // insertOne

                return insertOne(collectionName, operationDoc);
            
            case "updateOne": // updateOne
                
                return updateOne(collectionName, operationDoc);
                            
            case "deleteOne": // deleteOne

                return deleteOne(collectionName, operationDoc);
                
            default:
                throw new IllegalArgumentException("Invalid MongoDB operation: " + mongosh_string);
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
            openMongoClient(); // Ensure client is created
            String query = "db.User.find({username: \"cody_cisneros_28\"}, {username:1, posts:1})";

            List<Document> provas = executeQuery(query);

            for(Document prova: provas){
                System.out.println(prova.toJson());
            }

        } finally {
           closeMongoClient(); // Ensure client is closed
        }
    }

}



