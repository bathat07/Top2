import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Naber {
    public static void main(String[] args) {
        for (int i = 0; i < 3; i++) {
            try {
                System.out.println("Naber");
                Thread.sleep(1000);  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
       
        ses();
    }

    public static void ses() {
        String filePath = "/home/batu/top_oyunu/Tornado.wav"; 
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Audio file not found: " + filePath);
            return;  
        }

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(file)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            clip.start();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();  
                    synchronized (clip) {
                        clip.notify(); 
                    }
                }
            });

           
            synchronized (clip) {
                clip.wait();  
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();  
        }
    }
}
