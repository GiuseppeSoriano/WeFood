
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
    2.13. View his/her own profile
    2.14. View other Users' profiles
    2.15. View his/her Friends
    2.16. View his/her Followers
    2.17. View his/her Followed Users
    2.18. Modify his/her own profile (e.g. change username and password)
    2.19. Delete his/her own profile
    2.20. Search for a User by username

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

- Show most active users
- Show most followed users
- Show post with most comments
- Show post with the highest/lowest star ranking
- Show most/less used ingredients
- Show most/less used ingredients by a user
- To add others...


#### Suggestions

- Suggest users to follow (based on the user's friends)
- Suggest users to follow (based on common ingredients)
- Suggest users to follow (based on friends' followings)
- Suggest most popular combination of ingredients
- Suggest new set of ingredients based on friends’ usage
- Suggest most followed users


# UML Use Case Diagram


Aggiungere tutti attributi anche dettagliati e per ogni attributo bisogna specificare il tipo in Java (es. String, int, ecc...).





# UML Class Diagram





# Load Estimation

# DataBase

## Document DB

### Collections

### Queries

CRUD
Statistics Queries

## Graph DB

### Queries

CRUD
Statistics Queries

# Redundancies

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

