import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameSaveManager {

    private int bestScore = 0;

    private final Path filePath, appFolder;
    private final Gson gson;

    private AppSaveData saveData;

    public GameSaveManager(){

        //Current user folder (any OS)
        String userHome = System.getProperty("user.home");
        //Path to app folder
        //Paths.get create a path base
        //resolve extend it
        this.appFolder = Paths.get(userHome, ".luckystar");
        this.filePath = appFolder.resolve("game_scores.json");

        this.gson = new GsonBuilder().setPrettyPrinting().create();

        createAppFolder();
        loadData();
    }

    private void createAppFolder(){
        try{
            Files.createDirectories(appFolder);
        } catch (IOException e){
            System.err.println("Failed to create application directory: " + e.getMessage());
        }
    }

    //JSON read
    private void loadData(){
        try {
            if (Files.exists(filePath)) {
                String jsonText = Files.readString(filePath);
                this.saveData = gson.fromJson(jsonText, AppSaveData.class);

                if (this.saveData == null) {
                    this.saveData = new AppSaveData();
                }
                System.out.println("Successfully read JSON");
            } else {
                //
                System.out.println("Save file not found. Creating new save data.");
                this.saveData = new AppSaveData();
            }
        } catch (IOException e) {
            System.err.println("Failed to read JSON: " + e.getMessage());
            //
            this.saveData = new AppSaveData();
        }
    }

    //Add score and save score
    public void registerNewScore(String gameMode, int score) {
        //get time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDate = now.format(formatter);


        ScoreEntry newEntry = new ScoreEntry(score, formattedDate);

        //Detect game
        if (gameMode.equalsIgnoreCase("snake")) {
            saveData.getSnake().addScoreEntry(newEntry);
        } else if (gameMode.equalsIgnoreCase("spaceInvaders")) {
            saveData.getSpaceInvaders().addScoreEntry(newEntry);
        }

        //Trying to merge
        try {
            String jsonText = gson.toJson(saveData);
            Files.writeString(filePath, jsonText);
            System.out.println("Result saved to JSON for gamemode: " + gameMode);
        } catch (IOException e) {
            System.err.println("Failed to save JSON: " + e.getMessage());
        }
    }

    public AppSaveData getSaveData() { return this.saveData; }
}
