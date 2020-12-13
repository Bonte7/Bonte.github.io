<b><p style="text-align:center;">ePortfolio / My Journal Application</p></b>

**Code Review**

  The original version of this application was designed to be a weight tracker with a basic notification system to remind users to log their weight. Each weight log in the app
also holds data on when the log was added, the weight, and a description. Below is a video describing its original functionality and a code review of the project.

<iframe width="560" height="315" src="https://www.youtube.com/embed/0DCGfCQTNR8" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

  Based on the code review, I decided to take this project into a different direction. I've worked towards converting it to a full journal logging application as most of the functionality was already there. Not only that, but I wanted to implement a remote database to make the application more scalable and, in the future, bring this Android application over to iOS with the same database implementation. 


<b><p style="text-align:center;">Artifacts</p></b>

**Software Design and Engineering**

  To start, I felt that the application needed a few UI enhancements. I've also added in a few new buttons and features to the user interface as placeholders for future code that will be implemented into the project. The first enhancement is in the login activity of the application. Here, I've adjusted the login options for a user. I've added a sign in with Google button and a sign in with Facebook button. While these are not yet functional, they will serve as UI placeholders for the Firebase OAuth functionality that will be added during my new database implementation later.

<img src="https://drive.google.com/file/d/18EUvSg4j0ldldMCZDE-WZmXhIiSmhfgA/view?usp=sharing" width="500" alt="login screen"/>

  The next UI design update was in the MainActivity file where each data card with the note title and creation date is displayed. This was a more minor change as the card was given rounded edges and more padding. I noticed the cards seemed a bit too close together in the previous version and giving them smoothed edges and padding makes differentiating each card in the list view a bit easier. In additional to interface updates, I’ve also allowed the Title of the note card to read any character or string that is entered. This was done by unrestricting the XML file of this activities editText value. Previously this was implemented to only allow integers or double values.
  
<img src="https://drive.google.com/file/d/1D32PCtSIcS-W8Uo3yPluiQ07irACAPSK/view?usp=sharing" width="500" alt="card views"/>

  Next, I've made a few changes to the structure of my code. I've removed the notification button and functionality since I am transitioning the functionality towards a journaling application. A notification system for this type of app doesn’t seem beneficial to the end user. I’ve also gone ahead and restructured and refactored all variable names in the code to fit the final Journal app objective.
