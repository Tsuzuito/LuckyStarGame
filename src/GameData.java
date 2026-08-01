import java.util.ArrayList;
import java.util.List;

public class GameData {

    //set best score and create a history of scores
    private int bestScore = 0;
    private final List<ScoreEntry> history = new ArrayList<>();

    public int getBestScore(){ return bestScore; }
    public void setBestScore(int bestScore){ this.bestScore = bestScore; }

    public List<ScoreEntry> getHistory(){ return new ArrayList<>(history); }

    public void addScoreEntry(ScoreEntry entry){
        history.add(entry);
        if(entry.getScore() > bestScore){
            bestScore = entry.getScore();
        }
    }
}
