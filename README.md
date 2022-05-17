# JokeOverflow
This repository corresponds to a project of an android app in a study context.
## I. Introduction
### A. Description
This is an android app which references jokes from the community. 
### B. Purpose
The aim of this app is to bring people together and make them laugh.
### C. Intended audience
Everyone, the expected audience should be the quite young.
### D. Project scope
Get a maximum of knowledge in android development and in versioning.

## II. User stories
- As a visitor I want to be able to read jokes.
- As a visitor I want to be able to register/login.
- As a visitor I want to be able to check the users leaderboard.
- As a visitor I want to be able to check the best jokes.
- As a visitor I want to be able to search for jokes.
 
- As a user I want to be able to do everything that the visitor can do.
- As a user I want to create/edit/delete jokes.
- As a user I want to be able to rate jokes.
- As a user I want to be able to respond jokes.
- As a user I want to be able to edit my account.
- As a user I want to be able to save prefered tags and categories.
## III. Requirements
#### A.  Account system
User is able to create his own account. It allows score saving, posting jokes, participating to the referencing
 thanks to the grading system.
#### B. Grading system
Each post can be either up-voted or down-voted, those two statistics are used to build the post referencing.  
A post grade is visible on condition that the user already voted for this specific post.
#### C. Leaderboard
Users can grind the leaderboard depending on the number of posts and the grading system.
#### D. Search system
The users can find specific jokes by filtering by tag (keyword).

## IV. User overview

![](https://cdn.discordapp.com/attachments/667443778478407680/955113753270157373/unknown.png)

## V. Achievements

Youtube video : https://www.youtube.com/watch?v=rShYLxcEMco

#### A. Successes
##### a. Pages
Our application is functional and has many features :

- A Home page showing displaying all the jokes posted by the users.
- A Leaderboard page displaying all the users by their score (number of jokes posted).
- A "Best Jokes" page displaying all jokes in descending order of their score.
- A Profile page displaying all the informations of the user : profile picture, his fullname and username, age, mail address and score. There are also two buttons : Logout and "Get some inspirations" showing a random joke by clicking the button. 
- A "Add a Joke" page allowing the user to enter his joke and its category.
- A Login/Register page allowing the user to connect or create an account.
- A Search page allowing the user to search a joke by its category.

##### b. Cards
The joke's cards are designed in a way so the user can see the profile picture of the writter, the date of posting, the joke and the marking system. By clicking on the "flame" or the "snow flask", you can increase or decrease the score of the joke.

##### c. Languages
This app is allowed in English and French.

##### d. Style
There are two themes : Day and Night.

- Day : white as main color and blue as secondary.
- Night : black as main color and yellow as secondary.
#### B. Difficulties

We had difficulties on few points :

- A user can vote as much as he wants to rate a joke. He can decide the score of a joke and can increase without limit the score of his own joke
- On the home page, the last joke in the list is not displayed correctly.
- Difficulties to round profile pictures.
