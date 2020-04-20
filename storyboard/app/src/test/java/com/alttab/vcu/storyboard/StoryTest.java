package com.alttab.vcu.storyboard;

import com.alttab.vcu.storyboard.model.Story;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class StoryTest {
    @Test
    public void storyUpdateisCorrect() throws Exception {

        Story story = new Story();
        story.setText("TEST");
        story.setName("TEST NAME");
        assertEquals("Story name updated", story.getName(), "TEST NAME");
        assertEquals("Stroy Text Updated" ,story.getText(), "TEST");
    }
}