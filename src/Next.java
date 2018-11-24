import java.awt.Color;
import java.util.Random;

public class Next extends MyCanvas {

    byte block;
    private Random random = new Random();

    Next() {
        super((byte)100, (byte)100);
        blockRandom();
    }

    @Override
    public void drawImage() {
        blockPrintOut();
    }

    /**metoda losuje nową kostkę - zakres od 1 do 7*/
    public void blockRandom()
    {
        block = (byte)(random.nextInt(6)+1);
    }

    /**metoda drukuje pojedynczą kostkę*/
    private void cubePrintOut(byte x, byte y, byte k)
    {
        graphic.setColor(Blocks.COLOR[k]);
        graphic.fillRect(x* Blocks.SIZE, y* Blocks.SIZE, Blocks.SIZE, Blocks.SIZE);
        graphic.setColor(Color.BLACK);
        graphic.drawRect(x* Blocks.SIZE, y* Blocks.SIZE, Blocks.SIZE-1, Blocks.SIZE-1);
    }

    /**metoda drukuje figurę, tzw block złożony z 4x4 kostek*/
    private void blockPrintOut()
    {
        graphic.setColor(Color.DARK_GRAY);
        graphic.fillRect(0, 0, 4* Blocks.SIZE, 4* Blocks.SIZE);
        for (byte tx=0; tx<4; tx++)
            for (byte ty=0; ty<4; ty++)
                if (Blocks.BLOCKS[block][ty][tx]) cubePrintOut(tx,ty, (byte) (block+1));
    }


}
