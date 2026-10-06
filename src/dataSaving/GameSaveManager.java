package dataSaving;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameSaveManager {

    private final Path filePath, appFolder;
    private final Gson gson;

    private AppSaveData saveData;

    /*
    GameSaveManager receives the score and game mode -> creates a date and a ScoreEntry object (a single entry containing the score and time).
    GameSaveManager passes this object to the AppSaveData class -> selects the desired game (snake or spaceInvaders).
    From AppSaveData, the data goes to the GameData class -> the addScoreEntry() method adds an entry to the history and updates the bestScore record if it has been beaten.
    The entire updated structure is returned to GameSaveManager -> the Gson library converts the data into JSON text.
    GameSaveManager writes the finished JSON text to the drive in the file game_scores.json.
     */

    public GameSaveManager(){

        //Current user folder (any OS)
        String userHome = System.getProperty("user.home");
        //Set path to app folder and save file

        //Paths.get create a path base
        //resolve extend it
        this.appFolder = Paths.get(userHome, ".luckystar");
        this.filePath = appFolder.resolve("game_scores.json");

        //Initialize Gson for JSON formatting
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        //Create folder and load existing data
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
                //read and parse existing JSON save file
                String jsonText = Files.readString(filePath);
                this.saveData = gson.fromJson(jsonText, AppSaveData.class);

                if (this.saveData == null) {
                    this.saveData = new AppSaveData();
                }
                System.out.println("Successfully read JSON");
            } else {
                //File not found, initialize new save data
                System.out.println("Save file not found. New save file will be created");
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
        //get current date and time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        String formattedDate = now.format(formatter);

        //create new score entry object
        ScoreEntry newEntry = new ScoreEntry(score, formattedDate);

        //Detect game mode and add entry
        if (gameMode.equalsIgnoreCase("snake")) {
            saveData.getSnake().addScoreEntry(newEntry);
        } else if (gameMode.equalsIgnoreCase("spaceInvaders")) {
            saveData.getSpaceInvaders().addScoreEntry(newEntry);
        }

        //Convert data to JSON and write to file
        try {
            String jsonText = gson.toJson(saveData);
            Files.writeString(filePath, jsonText);
            System.out.println("Result saved to JSON for gamemode: " + gameMode);
        } catch (IOException e) {
            System.err.println("Failed to save JSON: " + e.getMessage());
        }
    }

    public AppSaveData getSaveData() { return this.saveData; }

    public void deleteUserData(){
        if(Files.exists(filePath)){
            try{
                Files.delete(filePath);
                System.out.println("Save file Successfully deleted");
            } catch (IOException e){
                System.err.println("Failed to delete save file: " + e.getMessage());
            } finally {
                this.saveData = new AppSaveData();
            }
        } else {
            System.out.println("Save file did not exist.");
        }
    }
}
