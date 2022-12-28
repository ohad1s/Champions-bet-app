package src.buisnesEntities;

import com.example.champions.DAL.MyComparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Leaderboard implements Serializable {
    private Map<String, Integer> sortedTable;

    public Leaderboard() {
        MyComparator comp = new MyComparator();
        sortedTable = new TreeMap<>(comp);
        comp.setSortedMap(sortedTable);
    }


    public Map<String, Integer> getSortedTable() {
        return sortedTable;
    }

    public void addNewUser(String userID) {
        this.sortedTable.put(userID, 0);
    }

    public int getUserScore(String userID) {
        return this.sortedTable.get(userID);
    }

    public void updateUserScore(String userID, int scoreToAdd) {
        int currScore = getUserScore(userID);
        this.sortedTable.put(userID, (currScore + scoreToAdd));
    }

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
