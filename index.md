<p style="text-align:center;"><b>Self Assessment</b></p>

Throughout this project and the CS Program at SNHU, I've learned quite a lot. This Capstone class has really made me realize how much I've absorbed from my previous courses and expereince as it all sort of came together to help me achieve my goal of creating new features and updating existing functionality in my Capstone application artifacts. Throughout the CS Program I've learned database implementation, best practices, the development process, several different programming languages, and most importantly, how to think critically as a developer. 

In creating this ePortoflio and working through each artificat upgrade in my Journal application, I've had the opportunity to showcase some of my stegrnths and abilities that I have found while in the CS Program. First, my knowledge of code structure and design helped in creating and updating a new user interface to make sure the app is simple and easy to use. I've also completely restructured the files in the application to make it easier for another developer to follow and understand what each file and class is meant to accomplish. This will make future collaboration easier to start once I upload the project to Github. 

Throughout the CS Program, I have learned about several different database styles such as Mongo DB, MySQL, and SQLite just to name a few. I wanted to challenge myself to learn something new that my classes have not taught, so I chose to implement Firebase Firestore into my final project of the Program. Using my existing database knowledge I was able to sucessfully implement this database type to ensure the final product would have a scalable and live database for users to save their information, store their login credentials, and even upload images to. 

I look forward to what opportunities and challenges this career path will bring. I enjoy challenging myself and learning new ways that I can improve myself and my code during the development process and from collaborating with others. 

<p style="text-align:center;"><b>ePortfolio / My Journal Application</b></p>

**Code Review**

The original version of this application was designed to be a weight tracker with a basic notification system to remind users to log their weight. Each weight log in the app also holds data on when the log was added, the weight, and a description. Below is a video describing its original functionality and a code review of the project.

<iframe width="560" height="315" src="https://www.youtube.com/embed/0DCGfCQTNR8" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

Based on the code review, I decided to take this project into a different direction. I've worked towards converting it to a full journal logging application as most of the functionality was already there. Not only that, but I wanted to implement a remote database to make the application more scalable and, in the future, bring this Android application over to iOS with the same database implementation. 


<p style="text-align:center;"><b>Artifacts</b></p>

**Software Design and Engineering**

To start, I felt that the application needed a few UI enhancements. I've also added in a few new buttons and features to the user interface as placeholders for future code that will be implemented into the project. The first enhancement is in the login activity of the application. Here, I've adjusted the login options for a user. I've added a sign in with Google button and a sign in with Facebook button. While these are not yet functional, they will serve as UI placeholders for the Firebase OAuth functionality that will be added during my new database implementation later.

<img style="align:center; display: block;" src="https://i.postimg.cc/P5JW3j0G/Login-screen.png" width="300" alt="login screen"/>

The next UI design update was in the MainActivity file where each data card with the note title and creation date is displayed. This was a more minor change as the card was given rounded edges and more padding. I noticed the cards seemed a bit too close together in the previous version and giving them smoothed edges and padding makes differentiating each card in the list view a bit easier. In additional to interface updates, I’ve also allowed the Title of the note card to read any character or string that is entered. This was done by unrestricting the XML file of this activities editText value. Previously this was implemented to only allow integers or double values.
  
<img style="align:center; display: block;" src="https://i.postimg.cc/HxwXcs8M/Card-Views.png" width="300" alt="card views"/>

Next, I've made a few changes to the structure of my code. I've removed the notification button and functionality since I am transitioning the functionality towards a journaling application. A notification system for this type of app doesn’t seem beneficial to the end user. I’ve also gone ahead and restructured and refactored all variable names in the code to fit the final Journal app objective.
  
**Algorithms and Data Structure**
 
The structure of my new application design is more so built around database functionality, giving the user the ability to create, save, update, and delete information from a real-time database. Due to this, my application needs a database built out entirely before algorithms can be deeply tested and investigated. While I do plan to implement a searching/filtering feature to the app, I'm afraid that can't be done until the database implementation is finished and Firebase is extremely robust and feature rich. I have still implemented a few hashing algorithms to store and structure data in a specific way. 
  
My artifact upgrade was these hashing algorithms to ensure user data is structured clearly. To start, in my "RegisterActivity.java" file, I've added a hashing algorithm using a Hash Map algorithm.
  
<img style="align:center; display: block;" src="https://i.postimg.cc/0y1vZkzj/hash-algo.png" width="300" alt="card views"/>
 
Here, the Hash Map is created and takes in Strings, one from the user and the other is randomly generated. Then, a key is assigned to each userObj reference which will ultimately add a user ID and username to the database when this map is called. Once the code is executed, the user data is placed in the Firestore database like so:
 
<img style="align:center; display: block;" src="https://i.postimg.cc/hPTvK3xJ/firestore-users.png" width="300" alt="card views"/>

You can see that each user is assigned a unique user ID and then given the username they type in on the create account activity screen. This algorithm and data structure are important since, together, they will allow me to differentiate each user and ensure that each user only sees their information in the app unlike the previous version of this application. I've also used this same hash map algorithm to layout data when new journal entries are added.

**Databases**

For the next enhancement, I have chosen to move my application’s database into Cloud Firestore by Google. This is a real-time database that will not only store user data, but it will also store login credentials and verify them as well. Since I’ve moved the app to the new database, I’ve also been able to visually see the structure of the database as data is written to it, which makes troubleshooting and managing the data much easier. When a user creates an account on the app for the first time their credentials are stored and a userID is generated. It looks like this in Firebase:

<img style="align:center; display: block;" src="https://i.postimg.cc/0jGTBmMN/firebase.png" width="300" alt="card views"/>

You can see that the Database now stores the username and user ID in the real-time database which will be used to match journal entries to user IDs. I’m also utilizing Firebase’s authentication features which stores a user’s email address here:

<img style="align:center; display: block;" src="https://i.postimg.cc/sXYHjzV6/firebase-auth.png" width="300" alt="card views"/>

I’ve included this in my ePortfolio as it will showcase an ability to implement a wide variety of features and database usage. I also think that implementing Firebase was a better solution to using local databases as more data can be collected and it makes the application a bit more scalable. Eventually, this database could also be used in an iOS or web based version of the same application.
