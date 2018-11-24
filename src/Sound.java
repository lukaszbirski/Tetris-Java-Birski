import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {

    private AudioClip clip;

    Sound(String fileName)
    {
        clip = Applet.newAudioClip(Sound.class.getResource("./" + fileName));
    }

    /**Metoda odgrywa dzwięk*/
    public void play()
    {
        new Thread(){public void run(){clip.play();}}.start();
    }
}
