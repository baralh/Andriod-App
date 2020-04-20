package com.alttab.vcu.storyboard;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class CreateMyStoryTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addStory() throws Exception {
    }

    @Test
    public void onStory() throws Exception {
    }

    @Test
    public void checkLength_Correctinput_Retrunstrue() throws Exception {

        CreateMyStory cms = new CreateMyStory();

        String st = "short";
        assertThat(cms.checkLength(st, 10), is(true));

    }

    @Test

    public void checkLength_Incorrectinput_Retrunsflase() throws Exception {

        CreateMyStory cms = new CreateMyStory();

        String st = "short";
        assertThat(cms.checkLength(st, 4), is(false));

    }

}