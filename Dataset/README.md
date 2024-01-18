# Datasets Employed
To populate the databases with a substantial volume of realistic data, datasets sourced from Kaggle were employed.

## 1. [Calories per 100 grams in Food Items](https://www.kaggle.com/datasets/kkhandekar/calories-in-food-items-per-100-grams)

File: `calories.csv`

- FoodCategory
- FoodItem
- per100grams
- Cals_per100grams
- KJ_per100grams


## 2. [Recipes and Interactions](https://www.kaggle.com/datasets/shuyangli94/food-com-recipes-and-user-interactions?select=PP_recipes.csv)

File: `PP_users.csv`

- u: User ID mapped to contiguous integer sequence from 0;
- techniques: Cooking techniques encountered by user;
- items: Recipes interacted with, in order;
- n_items: Number of recipes reviewed;
- ratings: Ratings given to each recipe encountered by this user;
- n_ratings: Number of ratings in total.


File: `RAW_interactions.csv`

- user_id: User ID;
- recipe_id: Recipe ID;
- date: Date of interaction;
- rating: Rating given;
- review: Review text.


File: `RAW_recipes.csv`

- name: Recipe name;
- id: Recipe ID;
- minutes: Minutes to prepare recipe;
- contributor_id: User ID who submitted this recipe;
- submitted: Date recipe was submitted;
- tags: Food.com tags for recipe;
- nutrition: Nutrition information (calories (#), total fat (PDV), sugar (PDV) , sodium (PDV) , protein (PDV) , saturated fat (PDV) , and carbohydrates (PDV));
- n_steps: Number of steps in recipe:
- steps: Text for recipe steps, in order;
- description: User-provided description;
- ingredients: List of ingredient names;
- n_ingredients: Number of ingredients.


File: `ingr_map.pkl`

- raw_ingr
- raw_words
- processed
- len_proc
- replaced
- count
- id


## 3. [Recipes and Reviews](https://www.kaggle.com/datasets/irkaal/foodcom-recipes-and-reviews?select=reviews.csv)

File: `recipes.csv`

- RecipeId: ID;
- Name: Name;
- AuthorId: Author ID;
- AuthorName: Author name;
- CookTime: Cook time;
- PrepTime: Prep time;
- TotalTime: Total time;
- DatePublished: Date published;
- Description: Description;
- Images: Image URLs;
- RecipeCategory: Recipe category;
- Keywords: Keywords;
- RecipeIngredientQuantities: Ingredient Quantities;
- RecipeIngredientParts: Ingredient Parts;
- AggregatedRating: Rating;
- ReviewCount: Number of Reviews;
- Calories: Calories;
- FatContent: Fat;
- SaturatedFatContent: Saturated Fat;
- CholesterolContent: Cholesterol;
- SodiumContent: Sodium;
- CarbohydrateContent: Carbohydrate;
- FiberContent: Fiber;
- SugarContent: Sugar;
- ProteinContent: Protein;
- RecipeServings: Servings;
- RecipeYield: Yield;
- RecipeInstructions: Instructions.


File: `reviews.csv`

- ReviewId
- RecipeId
- AuthorId
- AuthorName
- Rating
- Review
- DateSubmitted
- DateModified

