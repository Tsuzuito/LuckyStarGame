import sound.SoundManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SettingsManager {

    private final int baseVolumeValueSFX = 20;
    private boolean saveFileExist;

    private int volumeValueSFX;

    private final Path filePath, appFolder;

    public SettingsManager(){

        //Current user folder (any OS)
        String userHome = System.getProperty("user.home");
        //Path to app folder
        //Paths.get create a path base
        //resolve extend it
        this.appFolder = Paths.get(userHome, ".luckystar");
        this.filePath = appFolder.resolve("settings.txt");

        createAppFolder();
        readSettings();

        SoundManager.setAndConvertVolumeLevel(volumeValueSFX);
    }

    private void createAppFolder(){
        volumeValueSFX = baseVolumeValueSFX;

        try{
            Files.createDirectories(appFolder);
        } catch (IOException e){
            System.err.println("Failed to create application directory: " + e.getMessage());
        }
    }

    public int getVolumeValue(){ return this.volumeValueSFX; }

    public void setVolumeValue(int value){
        this.volumeValueSFX = value;
        SoundManager.setAndConvertVolumeLevel(volumeValueSFX);
    }

    public void readSettings(){
        //read
        try{
            if(Files.exists(filePath)){
                saveFileExist = true;
                volumeValueSFX = Integer.parseInt(Files.readString(filePath));
            } else {
                saveFileExist = false;
                System.out.println("Settings file not found");
            }
        } catch (IOException | NumberFormatException e){
            System.err.println("Failed to read: " + e.getMessage());
        }
    }

    public void saveSettings(){
        try {
            Files.writeString(filePath, String.valueOf(volumeValueSFX));
        } catch (IOException e){
            System.err.println("Failed to save: " + e.getMessage());
        }
    }
}
