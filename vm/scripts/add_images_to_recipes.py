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

# Caricamento dei dati
    
for i in range(20):
    posts_data = pd.read_json('../data/posts/posts_database_'+str(i)+'.json')
    
    for index, row in posts_data.iterrows():
        recipe = row["recipe"]
        recipe_id = row["_id"]["$oid"]
        
        # Verifica se l'immagine esiste nel post
        if 'image' in recipe and recipe['image'] is not None:
            image_url = recipe['image']
            # Aggiorna il nodo Ricetta con l'URL dell'immagine
            conn.query("""
                MATCH (r:Recipe {_id: $recipe_id})
                SET r.image = $image_url
            """, {"recipe_id": recipe_id, "image_url": image_url})
            
    print("Immagini aggiunte ai nodi Ricetta " + str(i))


conn.close()