package src.buisnesEntities;

import com.example.champions.DAL.MyComparator;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;

// This class represents a leaderboard containing scores of players
public class Leaderboard implements Serializable {
    // A map storing user IDs and their scores, sorted by score
    private Map<String, Integer> sortedTable;

    public Leaderboard() {
        // Using custom comparator to sort the map by score
        MyComparator comp = new MyComparator();
        sortedTable = new TreeMap<>(comp);
        comp.setSortedMap(sortedTable);
    }

    // Getter for the sorted table
    public Map<String, Integer> getSortedTable() {
        return sortedTable;
    }

    // Add new user to the leaderboard with score 0
    public void addNewUser(String userID) {
        this.sortedTable.put(userID, 0);
    }

    // Get the current score of a specific user
    public int getUserScore(String userID) {
        return this.sortedTable.get(userID);
    }

    /**
     * Update the score of a specific user
     * @param userID
     * @param scoreToAdd how many points to add
     */
    public void updateUserScore(String userID, int scoreToAdd) {
        int currScore = getUserScore(userID);
        this.sortedTable.put(userID, (currScore + scoreToAdd));
    }

    // Convert the object to a HashMap
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> toReturn = new HashMap<>();
        toReturn.put("Leaderboard", sortedTable.toString());
        return toReturn;
    }

    @Override
    public String toString() {
        return "Leaderboard: { " +
                sortedTable +
                " } ";
    }
}
