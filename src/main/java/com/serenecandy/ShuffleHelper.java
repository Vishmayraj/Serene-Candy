package com.serenecandy;

import java.util.*;
import java.util.prefs.Preferences;

public class ShuffleHelper {
    
    public static void initializeShuffle(Preferences prefs, int totalSongs) {
        String storedList = prefs.get("shuffledIndexes", "");
        int storedCount = prefs.getInt("shuffledCount", -1);
        
        // Whether or not we need a shuffle
        boolean needNewShuffle = storedList.isEmpty() || 
                                 storedCount != totalSongs ||
                                 hasCompletedShuffle(prefs, totalSongs);
        
        if (needNewShuffle) {
            createNewShuffle(prefs, totalSongs);
        }
        
        // If we have a stored list but no position, start at 0
        if (prefs.getInt("shufflePosition", -1) == -1) {
            prefs.putInt("shufflePosition", 0);
        }
    }
    
    private static boolean hasCompletedShuffle(Preferences prefs, int totalSongs) {
        int position = prefs.getInt("shufflePosition", 0);
        return position >= totalSongs; // If we've gone through all songs
    }
    
    private static void createNewShuffle(Preferences prefs, int totalSongs) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < totalSongs; i++) indexes.add(i);
        
        Collections.shuffle(indexes);
        
        // Store shuffled list
        StringBuilder sb = new StringBuilder();
        for (int idx : indexes) sb.append(idx).append(",");
        prefs.put("shuffledIndexes", sb.toString());
        prefs.putInt("shuffledCount", totalSongs);
        
        // Reset to position 0
        prefs.putInt("shufflePosition", 0);
    }
    
    public static int getCurrentShuffledIndex(Preferences prefs) {
        String stored = prefs.get("shuffledIndexes", "");
        if (stored.isEmpty()) return 0;
        
        try {
            List<Integer> indexes = new ArrayList<>();
            for (String s : stored.split(",")) {
                if (!s.isBlank()) indexes.add(Integer.parseInt(s.trim()));
            }
            
            int position = prefs.getInt("shufflePosition", 0);
            
            // If we're at the end, reshuffle
            if (position >= indexes.size()) {
                // Don't reshuffle here, let navigation handle it
                return indexes.get(indexes.size() - 1);
            }
            
            return indexes.get(position);
            
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static int getNextShuffledIndex(Preferences prefs, int totalSongs) {
        String stored = prefs.get("shuffledIndexes", "");
        if (stored.isEmpty()) return 0;
        
        try {
            List<Integer> indexes = new ArrayList<>();
            for (String s : stored.split(",")) {
                if (!s.isBlank()) indexes.add(Integer.parseInt(s.trim()));
            }
            
            int currentPos = prefs.getInt("shufflePosition", 0);
            int nextPos = currentPos + 1;
            
            // If we've reached the end of the shuffled list
            if (nextPos >= totalSongs) {
                // Create a completely new shuffle
                createNewShuffle(prefs, totalSongs);
                nextPos = 0; // Start from beginning of new shuffle
            }
            
            // Save new position
            prefs.putInt("shufflePosition", nextPos);
            
            // Get the new shuffled list (might be different if we reshuffled)
            stored = prefs.get("shuffledIndexes", "");
            indexes.clear();
            for (String s : stored.split(",")) {
                if (!s.isBlank()) indexes.add(Integer.parseInt(s.trim()));
            }
            
            return indexes.get(nextPos);
            
        } catch (Exception e) {
            return 0;
        }
    }
    
    public static int getPreviousShuffledIndex(Preferences prefs, int totalSongs) {
        String stored = prefs.get("shuffledIndexes", "");
        if (stored.isEmpty()) return 0;
        
        try {
            List<Integer> indexes = new ArrayList<>();
            for (String s : stored.split(",")) {
                if (!s.isBlank()) indexes.add(Integer.parseInt(s.trim()));
            }
            
            int currentPos = prefs.getInt("shufflePosition", 0);
            int prevPos = currentPos - 1;
            
            // If we're going back from the beginning, go to end
            if (prevPos < 0) {
                prevPos = totalSongs - 1;
            }
            
            // Save new position
            prefs.putInt("shufflePosition", prevPos);
            
            return indexes.get(prevPos);
            
        } catch (Exception e) {
            return 0;
        }
    }
    
    // Optional: Add a method to force reshuffle
    public static void reshuffleNow(Preferences prefs, int totalSongs) {
        createNewShuffle(prefs, totalSongs);
    }
    
    // Optional: Add a shuffle button that calls this
    public static int getNextWithReshuffle(Preferences prefs, int totalSongs) {
        // 50% chance to reshuffle when getting next
        Random rand = new Random();
        if (rand.nextInt(100) < 50) { // 50% chance
            // Check if we should reshuffle
            int position = prefs.getInt("shufflePosition", 0);
            if (position > totalSongs * 0.7) { // If we've played 70% of songs
                createNewShuffle(prefs, totalSongs);
            }
        }
        return getNextShuffledIndex(prefs, totalSongs);
    }
}