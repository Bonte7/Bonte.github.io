<p style="text-align:center;"><b>ePortfolio / My Journal Application</b></p>

**Code Review**

The original version of this application was designed to be a weight tracker with a basic notification system to remind users to log their weight. Each weight log in the app
also holds data on when the log was added, the weight, and a description. Below is a video describing its original functionality and a code review of the project.

<iframe width="560" height="315" src="https://www.youtube.com/embed/0DCGfCQTNR8" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

Based on the code review, I decided to take this project into a different direction. I've worked towards converting it to a full journal logging application as most of the functionality was already there. Not only that, but I wanted to implement a remote database to make the application more scalable and, in the future, bring this Android application over to iOS with the same database implementation. 


<p style="text-align:center;"><b>Artifacts</b></p>

**Software Design and Engineering**

To start, I felt that the application needed a few UI enhancements. I've also added in a few new buttons and features to the user interface as placeholders for future code that will be implemented into the project. The first enhancement is in the login activity of the application. Here, I've adjusted the login options for a user. I've added a sign in with Google button and a sign in with Facebook button. While these are not yet functional, they will serve as UI placeholders for the Firebase OAuth functionality that will be added during my new database implementation later.

<img style="text-align:center;" src="https://i.postimg.cc/P5JW3j0G/Login-screen.png" width="300" alt="login screen"/>

The next UI design update was in the MainActivity file where each data card with the note title and creation date is displayed. This was a more minor change as the card was given rounded edges and more padding. I noticed the cards seemed a bit too close together in the previous version and giving them smoothed edges and padding makes differentiating each card in the list view a bit easier. In additional to interface updates, I’ve also allowed the Title of the note card to read any character or string that is entered. This was done by unrestricting the XML file of this activities editText value. Previously this was implemented to only allow integers or double values.
  
<img style="text-align:center;" src="https://i.postimg.cc/HxwXcs8M/Card-Views.png" width="300" alt="card views"/>

Next, I've made a few changes to the structure of my code. I've removed the notification button and functionality since I am transitioning the functionality towards a journaling application. A notification system for this type of app doesn’t seem beneficial to the end user. I’ve also gone ahead and restructured and refactored all variable names in the code to fit the final Journal app objective.
  
<p style="text-align:center;"><b>Algorithms and Data Structure</b></p>
 
The structure of my new application design is more so built around database functionality, giving the user the ability to create, save, update, and delete information from a real-time database. Due to this, my application needs a database built out entirely before algorithms can be deeply tested and investigated. While I do plan to implement a searching/filtering feature to the app, I'm afraid that can't be done until the database implementation is finished and Firebase is extremely robust and feature rich. I have still implemented a few hashing algorithms to store and structure data in a specific way. 
  
My artifact upgrade was these hashing algorithms to ensure user data is structured clearly. To start, in my "RegisterActivity.java" file, I've added a hashing algorithm using a Hash Map algorithm.
  
<img style="text-align:center;" src="https://i.postimg.cc/0y1vZkzj/hash-algo.png" width="300" alt="card views"/>
 
Here, the Hash Map is created and takes in Strings, one from the user and the other is randomly generated. Then, a key is assigned to each userObj reference which will ultimately add a user ID and username to the database when this map is called. Once the code is executed, the user data is placed in the Firestore database like so:
 
<img style="text-align:center;" src="https://i.postimg.cc/hPTvK3xJ/firestore-users.png" width="300" alt="card views"/>

You can see that each user is assigned a unique user ID and then given the username they type in on the create account activity screen. This algorithm and data structure are important since, together, they will allow me to differentiate each user and ensure that each user only sees their information in the app unlike the previous version of this application. I've also used this same hash map algorithm to layout data when new journal entries are added.
