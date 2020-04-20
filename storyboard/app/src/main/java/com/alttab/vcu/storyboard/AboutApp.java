package com.alttab.vcu.storyboard;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class AboutApp extends Activity {

    static Button backHomeScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        backHomeScreen = (Button)findViewById(R.id.backHomeScreen);
        backHomeScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent1 = new Intent(AboutApp.this, StoryListActivity.class);
                startActivity(intent1);
            }
        });
    }

}
