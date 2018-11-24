import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class MyCanvas extends Canvas {

    BufferedImage image;
    Graphics2D graphic;

    MyCanvas(short width, short heigth)
    {
        super();
        setSize(width, heigth);
        image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
        graphic = (Graphics2D) image.getGraphics();
    }

    public abstract void drawImage();

    /**metoda wyświetla na ekranie grafikę*/
    private void putOnScreen()
    {
        Graphics g = getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
    }

    public void run()
    {
        drawImage();
        putOnScreen();
    }

}