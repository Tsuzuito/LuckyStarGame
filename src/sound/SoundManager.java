package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class SoundManager  {

    static float volumeLevel = 0f;

    public static void playSound(String fileName){

        try{
            URL soundURL = SoundManager.class.getResource(fileName);
            if(soundURL == null){
                System.err.println("Missing audio file: " + fileName);
            }

            //audio stream from url
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);

            //Player IDK
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            //
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

                gainControl.setValue(volumeLevel);
            }

            clip.start();

        } catch (Exception e){
            System.err.println("Something wrong: " + fileName);
        }
    }

    public static void setAndConvertVolumeLevel(int volumePer){
        volumeLevel = -80.0f + (volumePer / 100.0f) * 80.0f;

    }
}
