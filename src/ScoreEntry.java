public class ScoreEntry{
    private final int score;
    private final String date;


    public ScoreEntry(int score, String date){
        this.score = score;
        this.date = date;
    }

    public int getScore(){ return score; }
    public String getDate(){ return date; }


}