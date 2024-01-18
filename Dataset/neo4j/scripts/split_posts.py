import pandas as pd

posts_data = pd.read_json('../data/posts_database.json')
posts_size = len(posts_data)

# split the file in 20 files dividing equally the posts
for i in range(20):
    posts_data.iloc[int(posts_size*i/20):int(posts_size*(i+1)/20)].to_json('../data/posts/posts_database_'+str(i)+'.json')

sum = 0
# print size of each file
for i in range(20):
    print(i, len(pd.read_json('../data/posts/posts_database_'+str(i)+'.json')))
    sum += len(pd.read_json('../data/posts/posts_database_'+str(i)+'.json'))
    
print(sum)
print(posts_size)