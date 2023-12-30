import pandas as pd 

posts_data = pd.read_json('posts_database.json')

num_ingredients = 0

for index, row in posts_data.iterrows():
    recipe = row["recipe"]
    
    # Itera attraverso gli ingredienti nella ricetta
    for ingredient in recipe["ingredients"]:
        num_ingredients += 1
        
print(num_ingredients)