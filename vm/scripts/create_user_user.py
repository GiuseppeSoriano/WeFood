from neo4j import GraphDatabase
import pandas as pd

# Connessione al Database Neo4j
uri = "bolt://localhost:7687"
user = "neo4j"
password = "password"

class Neo4jConnection:
    def __init__(self, uri, user, password):
        self.__uri = uri
        self.__user = user
        self.__password = password
        self.__driver = None
        try:
            self.__driver = GraphDatabase.driver(self.__uri, auth=(self.__user, self.__password))
        except Exception as e:
            print("Failed to create the driver:", e)
        
    def close(self):
        if self.__driver is not None:
            self.__driver.close()
        
    def query(self, query, parameters=None, db=None):
        assert self.__driver is not None, "Driver not initialized!"
        session = None
        response = None
        try: 
            session = self.__driver.session(database=db) if db is not None else self.__driver.session() 
            response = list(session.run(query, parameters))
        except Exception as e:
            print("Query failed:", e)
        finally: 
            if session is not None:
                session.close()
        return response
    
conn = Neo4jConnection(uri, user, password)

for i in range(20):

    posts_data = pd.read_json('../data/posts/posts_database_'+str(i)+'.json')

    for index, row in posts_data.iterrows():
        if str(row["comments"]) == "nan":
            continue

        comments = row["comments"]
        author = row["idUser"]["$oid"]
        
        for comment in comments:
            user = comment["idUser"]["$oid"]
            
            if user == author:
                continue
            
            # Crea una relazione tra Ricetta e Ingrediente
            conn.query("""
                MATCH (u1:User {_id: $user}), (u2:User {_id: $author})
                MERGE (u1)-[:FOLLOWS]->(u2)
                """, {"user": user, "author": author})

conn.close()
