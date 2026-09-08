package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager  {

    public static void playSound(String fileName){

        try{
            URL soundURL = SoundManager.class.getResource(fileName);
            if(soundURL == null){
                System.err.println("Missing audio file: " + fileName);
            }

            //audio stream from url
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);

            //Player idk
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

//            clip.start();

        } catch (Exception e){
            System.err.println("Something wrong: " + fileName);
        }
    }
}
