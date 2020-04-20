package com.alttab.vcu.storyboard;

import com.alttab.vcu.storyboard.model.Chunk;
import com.alttab.vcu.storyboard.model.Profile;
import com.alttab.vcu.storyboard.model.Story;

import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProfileTest {


    @Mock
    List<Chunk> fakeChunk;

    @Test

    public void profileUpdateisCorrect() throws Exception {

        //TODO: Fix this !!! chunks !!! fake chunks
        Profile profile = new Profile("1","2","3","4","5","6");

        assertEquals("Profile name updated", profile.getfnAme(), "1");
        assertEquals("Profile lname updated", profile.getLasTname(), "2");
        assertEquals("Profile Email updated" ,profile.getmyEmail(), "3");
        assertEquals("Profile bio updated" ,profile.getmyBio(), "4");
        assertEquals("Profile loc updated" ,profile.getmyLocation(), "5");
        assertEquals("Profile loc updated" ,profile.getUserId(), "6");

    }
}