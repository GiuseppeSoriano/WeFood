# 9. User Manual
This guide outlines the general approach to navigating and utilizing the features of our social network application. After this introduction, you'll find comprehensive instructions on interacting with the user interface, tailored for registered users, as well as an overview for non-registered users on accessing limited functionalities. A table presents all possible commands that users can input into the interface along with their corresponding outcomes.

For non-registered users, browsing recent posts sorted by upload date is permitted. However, all other operations, except for registration, are restricted until users complete the registration process. Upon registration, users are greeted with an empty personal profile page, zero followers/followed, and zero posts. The interface displays a personal shell prompting users to insert commands. The subsequent table details various commands and their corresponding results.

| Command | Operation |
| --- | --- |
| `login` | To login |
| `logout` | To logout |
| `findIngredientByName` | To find an ingredient by name |
| `findIngredientsUsedWithIngredient` | To show suggestions about ingredients based on ingredient combinations |
| `findNewIngredientsBasedOnFriendsUsage` | To show suggestions about ingredients based on friends |
| `findUsersToFollowBasedOnUserFriends` | To show suggestions on new followers based on friends |
| `findMostFollowedUsers` | To show suggestions about the most followed users |
| `findUsersByIngredientUsage` | To show suggestions about users based on ingredients usage |
| `findMostUsedIngredientByUser` | To find the most used ingredient |
| `findMostLeastUsedIngredient` | To show an overview about ingredient usage |
| `uploadPost` | To upload a post |
| `modifyPost` | To modify a post |
| `deletePost` | To delete a post |
| `browseMostRecentTopRatedPosts` | To browse the most recent and top rated posts |
| `browseMostRecentTopRatedPostByIngredients` | To browse the most recent and top rated posts by ingredients |
| `browseMostRecentPostsByCalories` | To browse the most recent posts by calories |
| `findPostByRecipeName` | To find a post by recipe name |
| `averageTotalCaloriesByUser` | Statistics about calories |
| `findRecipeByIngredients` | To find a recipe by ingredients |
| `modifyPersonalInformation` | To modify personal informations |
| `deleteUser` | To delete your personal profile |
| `followUser` | To follow a user |
| `unfollowUser` | To unfollow a user |
| `findFriends` | To find your friends |
| `findFollowers` | To find your followers |
| `findFollowed` | To find your followed users |
| `exit` | To exit from the site |

After entering a command, the user will receive on-screen guidance to complete the operation. Accuracy in providing information is crucial; any inaccuracies will result in a failed operation, necessitating a restart. Notably, there is no back button available, meaning completed operations cannot be reversed. A pop-up message informs users of the success or failure of an operation, redirecting them to the main shell.

For post browsing, a folder is dynamically created/deleted upon issuing the relevant command, and the folder's path is displayed on the screen.

The admin of the social network is pre-registered, and credentials are communicated through various channels (e.g., voice, messages). The admin interacts with a personal shell using specific commands. Similar to regular users, the admin lacks a back button, and once an operation is correctly completed, it cannot be reversed.

The following table outlines admin-specific commands:

| Command | Operation |
| --- | --- |
| `login` | To login |
| `logout` | To logout |
| `createIngredient` | To create a new ingredient |
| `banUser` | To ban a user from the site |
| `unbanUser` | To unban a user |
| `findIngredient` | To find information about a specific ingredient |
| `getAllIngredients` | To retrieve all information about ingredients |
| `findIngredientsUsedWithIngredient` | To find combinations of ingredients from a starting ingredient |
| `mostPopularCombinationOfIngredients` | Statistics about the most popular combinations of ingredients |
| `exit` | To close the application |

This detailed guide aims to provide users, whether regular or admin, with a clear understanding of the social network's functionalities and the corresponding commands to interact effectively.

+ Mettere screen