# android-app-alt-tab

android-app-alt-tab created by GitHub Classroom

## Iteration 1

On our first iteration we focused mainly on our Log in page, Main page, and Create my story page.When you open "Story Board" it will ask you to log in, so for our log in page we used firebased google authentication. After loging in, Main page opens up. You can go to create story or you can log out from there. We used user interface for our new story. For the our database we are using Firestore. We also did story listing to all of our stories. Since, we were just three people in our group we all worked together on everysingle scenerio and helped each other out. We ran in to lots of problem during this iterating while testing google authentication. Since we used `google SignIn`, our espresso test didn't recoginze `onClick()` method while loging in. That's why we had to use espresso and UI automator for our testing to work. we also had really hard time figuring out `Google service.json` file becasue every user need to have a unique `.json` file. 

## Iteration 2

On our second iteration we focused mainly on our profile page, create my story page, and the database. When you are in the "Create Story" page, clicking the submit button will result in different pop ups depending on how much text you input into the textboxes. The pop ups will either tell user to input text, ask if they want to continue editing text, or submit the user text. The story created will then go into the database and the user will be taken to the main page where they can see their story as well as other recently submitted stories in the database. The user can also create a profile by going to the "My Profile" page and inputting their data. This data will then be displayed to them as well as go into the database. Even if the data is random text, the app will accept it into the profile account. If the user doesn't put in text for any of the profile text boxes, a message will pop up asking them to put in text. Due to only having 3 members on our development team, we were only able to create 2 user stories as we used a lot of time getting the database to function properly and accept our stories and profiles. Our app uses a lot of user interface instead of functions so unit testing was hard to implement.


## Data Model
This is how our story data model should look like, both in the db and client: 

```
[ Story : 
  { title: "story title", 
    Uid : Uid,
    timestamp : timestamp, 
    chunks : [{
        
        text: text ,
        uid: uid ,
        timestamp , 
    
    }]
  }
 ]
 ```
    
