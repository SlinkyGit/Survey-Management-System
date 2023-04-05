# Survey Management System
Created a survey management system where users can create, modify, load, save, take, and display a survey. A survey consists of various kinds of question types (M.C., T/F, etc.). A test is a variation of a survey but with answer choices and a correct answer(s).

Incorporated a mix of Abstraction, Inheritance, Serialization, and the Strategy Design Pattern (to name a few).

Starting with an Abstract Question class, created child-classes that all inherited from the Question class. Within the Question class, there are a variety of methods that all range in functionality (Getters, Setters, etc.).

There are 2 methods that are abstract, and they are used later: one for modifying the question and another for getting the user’s response.

Some questions extended another form of question, for example Short Answer extending Essay.

I had a Survey class, which was responsible for managing a List of Question objects; list of Questions. Aside from this, I had methods that would help display the questions in the Survey, add a specific type of question to the Survey (interacted with Question’s child classes), and get the user’s responses (to name a few).

I also had a Survey-Helper class, which was responsible for managing the Survey itself. For example, creating a new survey, displaying one, loading and saving one, all was done in this class. In addition to this, I had specific question type helpers in order to “communicate” with the user and add their specified questions, choices, and responses to the Survey.

A survey can successfully be created, displayed, loaded (from ‘surveys’ folder), saved (to ‘surveys’ folder), taken (with answers being displayed after taking) and modifying every question/response type.

The “Main Driver” consisted of a variety of switch-cases, which allowed for the user to access different menus and have different outcomes upon choosing a number from the specified menu.

I created a Test class which was simply an extension of my Survey class. In this method, I had the usual getters and setters, as well as helper functions to add specific question-type questions to a test (“Strategy”).

I also implemented a grade function that would calculate the number of points per question, and iterate through the questions and add a score. Through use of instanceof, I was able to check if the question was either an essay/short answer, and then grade respectively.

In my SurveyHelper class, I added duplicate methods for my Test class, to ensure that these methods would correspond to a test. In addition to this, I added methods for tabulating and grading answers given to the test. 

It would then tally up which responses were given more than once and what they were. This was done by use of for-each loops, in addition to the respective method calls.
