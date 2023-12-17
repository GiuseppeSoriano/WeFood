
# Introduction
WeFood is a Social Network where users can share their recipes and
provide feedbacks about other users' recipes through comments and star rankings. 

# Requirements


## Functional Requirements
The actors that are involved in WeFood are:
1. Unregistered Users;
2. Registered Users;
3. Administrator.

1. Unregistered Users:
    1.1. Browse *recent* recipes published by influencers (?)
    1.2. Registration

2. Registered Users:
    2.1. Log In
    2.2. Log Out
    2.3. Follow a User
    2.4. Unfollow a User
    2.5. Upload a Post
    2.6. Modify an uploaded Post
    2.7. Delete an uploaded Post
    2.8. Comment a Post
    2.9. Evaluate by a star ranking a Post
    2.10. Browse most recent friends' Posts
    2.11. Browse most recent top rated Posts
    2.12. Browse most recent Posts by ingredients 
    2.13a. Browse most recent Posts by calories 
    2.13. View his/her own profile
    2.14. View other Users' profiles
    2.15. View his/her Friends
    2.16. View his/her Followers
    2.17. View his/her Followed Users
    2.18. Modify his/her own profile (e.g. change username and password)
    2.19. Delete his/her own profile
    2.20. Search for a User by username
    2.21. View Total Calories of a Recipe
    2.22. View Calories of an Ingredient
    2.23. View Steps of a Recipe 
    2.24. View Recipe of a Post
    2.25. View Ingredients of a Recipe


    


3. Administrator:
    3.1 Log In
    3.2 Log Out
    3.3. Ban a User
    3.4. Delete a Post
    3.5. Delete a Comment
    3.6. See statistics about the usage of WeFood

A User can follow a user and can be followed by other users.
A User has a friend when he/she follows another user and the other user follows him/her back.

## Non-Functional Requirements
The non-functional requirements for WeFood are as follows:

1. Performance: the system must be able to handle a request in less than 1.5 seconds.

2. Availability: the system must be available 24/7.

3. Security: the system must be secure and protect users' data by hashing passwords.

4. Reliability: the system must be reliable and not lose data (e.g. by doing a daily backup).

5. Usability: the system must be easy to use and intuitive. Each user should be able to use the system without any training and in about 15 minutes.

6. Java: the back-end must be written in Java.

# Dataset and Web Scraping

See Data...


# UML Use Case Diagram


# UML Class Diagram

The UML Class Diagram is shown in the following figure:



Details about the UML Class Diagram:

Admin:
- username: String
- password: String (hashed)

RegisteredUser:
- username: String
- password: String (hashed)
- name: String
- surname: String
- posts: List<Post>

Post:
- description: String
- timestamp: Date
- comments: List<Comment>
- starRankings: List<StarRanking>
- recipe: Recipe

Comment:
- user: RegisteredUser
- text: String
- timestamp: Date

StarRanking:
- user: RegisteredUser
- vote: Double
- timestamp: Date (?)

Recipe:
- name: String
- image: String_URL
- steps: List<String> (or String)
- ingredients: Map<Ingredient, Double>

Ingredient:
- name: String
- calories: Double

(# Load Estimation)

# DataBase


## Document DB

Entities:
- User
- Post
- Comment
- StarRanking
- Recipe

Collections:
- User
- Post


### Collections

Structure of the collections:

User
{
    _id: #,
    type: enum {Admin, RegisteredUser},
    username: String,
    password: String,
    name: String, # Not applicable for Admin
    surname: String, # Not applicable for Admin
    posts: [{
                _idPost: #,
                name: String, [R]
                image: String_URL [R]
    }, ...]
}

Post
{
    _id: #,
    _idUser: #, 
    username: String, [R]
    description: String,
    timestamp: Timestamp,
    recipe: {
                name: String,
                image: String_URL,
                steps: [String, ...],
                totalCalories: Double, [R]
                ingredients: [{
                                name: String,
                                quantity: Double, 
                }, ...]
    },
    avgStarRanking: Double, [R]
    starRankings: [{
                        _idUser: #,
                        username: String, [R]
                        vote: Double,
                        (timestamp: Timestamp)
    }, ...],
    comments: [{
                _idUser: #,
                username: String, [R]
                text: String,
                timestamp: Timestamp
    }, ...]
}



## Key-Value DB

Entities:
- Ingredient

Bucket Ingredients:

EntityName:Attribute=Value
    Ingredient:Calories=X

e.g.
    Pasta:Calories=100


## Graph DB

Entities:
- User:
    - (PK) == _id (PK of User in Document DB)
    - username [R]
  
- Recipe:
    - (PK) == _idPost (PK of Post in Document DB)
    - name [R]


- Ingredient:
    - (PK) == nameOfIngredient (PK of Ingredient in Key-Value DB)
    

Relationships:
- User --> :FOLLOWS --> User

- User --> :USED --> Ingredient
           (times: int) [R]
Here times keeps track of the fact that the relationship with the ingredient still exists in other recipes after the deletion of a recipe. If times = 0, even then the relationship can be deleted.

- Ingredient --> :USED_WITH --> Ingredient  
                 (times: int) [R]
[This relationship is bidirectional]

- Recipe --> :CONTAINS --> Ingredient


# Statistics and Queries

## CRUD operations

### Create

- Create a new user 
- Create a new post
- Create a new recipe
- Create a new comment
- Create a new star ranking
- Create a new following relationship
- Create a new ingredient
- Create a new user-ingredient relationship
- Create a new ingredient-ingredient relationship

### Read

- Show users
- Shows posts
- Show comments
- Show star ranking
- Show ingredients
- Show friends
- Show followers
- Show followings
- Show calories of an ingredient
- Show steps of a Recipe 
- Show recipe of a Post
- Show ingredients of a Recipe


### Update

- Update user's information
- Update post
- Update recipe
- Update comment
- Update star ranking
- Update ingredient
- Update user-ingredient relationship
- Update ingredient-ingredient relationship

### Delete

- Delete user
- Delete post
- Delete comment
- Delete star ranking
- Delete following relationship
- Delete ingredient
- Delete user-ingredient relationship
- Delete ingredient-ingredient relationship

### Query

#### Analytics

- Show most active users (da vedere)
- Show most followed users
- Show post with most comments
- Show post with the highest/lowest star ranking
- Show most/less used ingredients
- Show most/less used ingredients by a user
- Show total amount of calories of a recipe
- To add others...


#### Suggestions

- Suggest users to follow (based on the user's friends)
- Suggest users to follow (based on common ingredients)
- Suggest users to follow (based on friends' followings)
- Suggest most popular combination of ingredients
- Suggest new set of ingredients based on friends’ usage
- Suggest most followed users


# Redundancies


Where is the Redundancy | Reason | Original Value
-----------------------|--------|----------------

User{
    posts: [{
                name,
                image
    }]
}
Reason: to avoid joins 
Oringinal Value: Post{
                       recipe:{
                                name,
                                image
                       }
                     }

--- 

Post{
    username
}
Reason: to avoid joins
Original Value: User{
                        username
                    }

---

Post{
    recipe:{
                totalCalories
           }
}
Reason: to avoid computing the total calories of a recipe every time that a post is shown
Original Value: It is possibile to compute the total calories of a recipe by summing the calories of each ingredient of the recipe  (retrieving the calories of each ingredient from the Key-Value DB)

---

Post{
    avgStarRanking
}
Reason: to avoid computing the average star ranking of a post every time that a post is shown
Original Value: It is possibile to compute the average star ranking of a post by summing the star rankings of each user and dividing by the number of users that voted for the post

---

Post{
    starRankings: [{
                        username,
    }]
}
Reason: to avoid joins
Original Value: User{
                        username
                    }

---

Post{
    comments: [{
                    username,
    }]
}
Reason: to avoid joins
Original Value: User{
                        username
                    }

---

GraphDB: The redundancies inside the GraphDB are necessary to avoid joins with the DocumentDB. In this way we can show partial information.

User:
    - username
Reason: to avoid joins with the DocumentDB
Original Value: User{
                        username
                    }

---

Recipe:
    - name

Reason: to avoid joins with the DocumentDB
Original Value: Post{
                        recipe:{
                                    name
                               }
                    }

---

:USED:
    - times

Reason: to avoid computing the number of times that an ingredient is used every time that is needed.
Original Value: it is possible to compute the number of times that an ingredient is used by counting the number of times that a user has used an ingredient in his/her recipes (retrieving the information from the DocumentDB).

---

:USED_WITH:
    - times

Reason: to avoid computing the number of times that an ingredient is used with another ingredient every time that is needed.
Original Value: it is possible to compute the number of times that an ingredient is used with another ingredient by counting the number of times that a user has used an ingredient with another ingredient in his/her recipes (retrieving the information from the DocumentDB).





# Document DataBase Index Design

# Graph DataBase Indexes and Constraints Design

# System Architecture

## General description

## Frameworks and components

# Implementation

# PERFORMANCE TEST

# CONSISTENCY, AVAILABILITY AND PARTITION TOLERANCE

# USER MANUAL

# BIBLIOGRAPHY

