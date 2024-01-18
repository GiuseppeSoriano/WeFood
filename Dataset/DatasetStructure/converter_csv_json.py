import json 
import pandas as pd

df = pd.read_csv('recipes.csv')

# print to json file
df.to_json('recipes/IMAGE_recipes.json', orient='records', lines=False, indent=4)