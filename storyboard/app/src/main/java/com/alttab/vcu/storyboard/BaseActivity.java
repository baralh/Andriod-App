package com.alttab.vcu.storyboard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/*
 * BaseActivity is an AppCompatActivity which sets up a Toolbar if present in the layout.
 */
public class BaseActivity extends AppCompatActivity {
    public static final String TAG = "BaseActivity";
    protected static final int RC_SIGN_IN = 9001;
    private Menu menu;


    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        setupToolbar();


    }

    /**
     * Finds and sets the Toolbar as the support ActionBar if it is non-null. Returns the Toolbar.
     *
     * @return The Toolbar view or null if not found.
     */
    protected Toolbar setupToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        return toolbar;
    }

    /**
     * inflates the actions for menu
     *
     * @param menu menu item
     * @return returns false if it fails
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        this.menu = menu;
        if ( shouldStartSignIn() ) {
            this.menu.findItem(R.id.action_logout).setVisible(false);
            this.menu.findItem(R.id.action_login).setVisible(true);
            this.menu.findItem(R.id.action_create_new).setVisible(false);
            //            this.menu.findItem(R.id.action_Profile).setVisible(false);
            this.menu.findItem(R.id.action_profile_view).setVisible(false);
        } else {
            this.menu.findItem(R.id.action_logout).setVisible(true);
            this.menu.findItem(R.id.action_login).setVisible(false);
            this.menu.findItem(R.id.action_create_new).setVisible(true);
            //  this.menu.findItem(R.id.action_Profile).setVisible(true);
            this.menu.findItem(R.id.action_profile_view).setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          case R.id.action_settings:
              Intent intent5 = new Intent(this, AboutApp.class);
              startActivity(intent5);
              return true;
          case R.id.action_main:
              Intent intent = new Intent(this, StoryListActivity.class);
              startActivity(intent);
              return true;

          case R.id.action_create_new:
              Intent createNewIntent = new Intent(this, CreateMyStory.class);
              startActivity(createNewIntent);
              return true;

          case R.id.action_logout:
              // User chose the "Home" action, mark the current item
              Intent intent1 = new Intent(this, GoogleSignIn.class);
              intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent1);
              return true;
          case R.id.action_login:
              Intent intent4 = new Intent(this, GoogleSignIn.class);
              intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
              startActivity(intent4);
              return true;



          case R.id.action_profile_view:
              Intent intent3 = new Intent(this, ProfileView.class);
              startActivity(intent3);
              return true;

          default:
              // If we got here, the user's action was not recognized.
              // Invoke the superclass to handle it.
              return super.onOptionsItemSelected(item);

        }
    }

    protected boolean shouldStartSignIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //tries to get the current user

        if (user == null) { //if null no current user

            return true;
        } else {
            Log.d(TAG, "user signed in");
            return false;
        }
    }


}
