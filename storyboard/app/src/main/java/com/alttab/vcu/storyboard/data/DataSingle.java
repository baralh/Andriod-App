package com.alttab.vcu.storyboard.data;


import com.alttab.vcu.storyboard.model.Profile;

public class DataSingle {
    private Profile profile;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }



    private static final DataSingle holder = new DataSingle();


    public static DataSingle getInstance() {
        return holder;
    }

}
