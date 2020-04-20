package com.alttab.vcu.storyboard.utils;

import com.alttab.vcu.storyboard.model.Chunk;

import java.util.List;

public class StoryUtils {
    public static String mergeChunks(List<Chunk> chunks) {

        StringBuilder sb = new StringBuilder("");
        for (Chunk chunk: chunks) {
            sb.append(String.format(" %s (%s)",chunk.getText(), chunk.getUname()));
        }
        return sb.toString();
    }

}