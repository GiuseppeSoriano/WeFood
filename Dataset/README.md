# Information about the DataSets


## IngredientsAndCalories

### Calories
FoodCategory
FoodItem
per100grams
Cals_per100grams
KJ_per100grams


## RecipesAndInteractions

### PP_Users
u: User ID mapped to contiguous integer sequence from 0
techniques: Cooking techniques encountered by user
items: Recipes interacted with, in order
n_items: Number of recipes reviewed
ratings: Ratings given to each recipe encountered by this user
n_ratings: Number of ratings in total

### RAW_Interactions
user_id
recipe_id
date
rating
review: Here we have problems because I saw that there are some reviews that contain line breaks, and the csv format is not able to handle them.

### RAW_Recipes
name
id
minutes
contributor_id
submitted
tags
nutrition
n_steps
steps
description
ingredients
n_ingredients

### Ingr_Map
raw_ingr
raw_words
processed
len_proc
replaced
count
id


## RecipesAndReviews (Really well done)
In the DataSet RecipesAndReviews, we need to consider the parquet files because the csv ones are not able to proper memorize the Review column. Indeed, I've seen that in that column there are reviews that do contain line breaks, and the csv format is not able to handle them.

### Recipes
RecipeId
Name
AuthorId
AuthorName
CookTime
PrepTime
TotalTime
DatePublished
Description
Image URLs
RecipeCategory
Keywords
RecipeIngredientQuantities
RecipeIngredientParts
AggregatedRating
Number of Reviews
Calories
FatContent
SaturatedFatContent
CholesterolContent
SodiumContent
CarbohydrateContent
FiberContent
SugarContent
ProteinContent
RecipeServings
RecipeYield
RecipeInstructions

### Reviews
ReviewId
RecipeId
AuthorId
AuthorName
Rating
Review
DateSubmitted
DateModified


