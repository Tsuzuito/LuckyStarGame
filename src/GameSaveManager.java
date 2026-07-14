import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class GameSaveManager {

    private int bestScore = 0;
    private final Path filePath, appFolder;

    public GameSaveManager(){

        //Current user folder (any OS)
        String userHome = System.getProperty("user.home");
        //Path to app folder
        //Paths.get create a path base
        //resolve extend it
        this.appFolder = Paths.get(userHome, ".luckystar");
        this.filePath = appFolder.resolve("game_scores.txt");

        createAppFolder();

        try{

            if(Files.exists(filePath)){
                String temp = Files.readString(filePath);
                bestScore = Integer.parseInt(temp);
            } else {
                System.out.println("Save file not found");
            }
            //NumberFormatException if file corrupted or empty
        } catch (IOException | NumberFormatException e){
            System.err.println("Failed to read: " + e.getMessage());
        }
    }

    private void createAppFolder(){
        try{
            Files.createDirectories(appFolder);
        } catch (IOException e){
            System.err.println("Failed to create application directory: " + e.getMessage());
        }
    }

    public int getBestScore(){ return this.bestScore; }
//    public void setBestScore(int score){ this.bestScore = score; }

    public void saveGame(int score){
        try {
            Files.writeString(filePath, String.valueOf(score));
            System.out.println("Successfully saved: " + filePath.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Failed to save: " + e.getMessage());
        }
        this.bestScore = score;
    }
}
