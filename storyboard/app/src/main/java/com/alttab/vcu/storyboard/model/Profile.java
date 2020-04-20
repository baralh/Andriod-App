package com.alttab.vcu.storyboard.model;

import com.google.firebase.firestore.IgnoreExtraProperties;

/*Story POJO*/

@IgnoreExtraProperties
public class Profile {

    public static final String FIELD_FNAME = "FirstName";
    public static final String FIELD_LNAME = "LastName";
    public static final String FIELD_EMAIL = "Email";
    public static final String FIELD_BIO = "Bio";
    public static final String FIELD_LOCATION = "Location";
    public static final String FIELD_ID = "ID";

    private String fnAme;
    private String lasTname;
    private String myEmail;
    private String myBio;
    private String myLocation;
    private String userId;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Profile(){}

    @Override
    public String toString() {
        return "Profile{"
                + "fnAme='" + fnAme + '\''
                + ", lasTname='" + lasTname + '\''
                + ", myEmail='" + myEmail + '\''
                + ", myBio='" + myBio + '\''
                + ", myLocation='" + myLocation + '\''
                + ", userId='" + userId + '\''
                + '}';
    }

    public Profile(String fnAme, String lasTname, String myEmail, String myBio, String myLocation, String userId) {
        this.fnAme = fnAme;
        this.lasTname = lasTname;
        this.myEmail = myEmail;
        this.myBio = myBio;
        this.myLocation = myLocation;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getfnAme() {
        return fnAme;
    }

    public void setfnAme(String fnAme) {
        this.fnAme = fnAme;
    }



    public String getLasTname() {
        return lasTname;
    }

    public void setLasTname(String lasTname) {
        this.lasTname = lasTname;
    }


    public String getmyEmail() {
        return myEmail;
    }

    public void setmyEmail(String myEmail) {
        this.myEmail = myEmail;
    }



    public String getmyBio() {
        return myBio;
    }

    public void setmyBio(String myBio) {
        this.myBio = myBio;
    }



    public String getmyLocation() {
        return myLocation;
    }

    public void setmyLocation(String myLocation) {
        this.myLocation = myLocation;
    }

}
