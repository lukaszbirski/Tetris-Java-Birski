public class Block {

    public boolean[][] tab = new boolean[4][4];
    private boolean[][] tempTab = new boolean[4][4];     //tablica pomocnicza
    byte blockColor;                                     //zmienna pomocnicza łapiąca numer koloru

    Block()
    {
        setBlock((byte) 0);
    }

    /**metoda ustawia klocek*/
    public void setBlock(byte k)
    {
        blockColor = k;
        for (byte x=0; x<4; x++)
            for (byte y=0; y<4; y++)
                tab[y][x] = Blocks.BLOCKS[blockColor][x][y];
    }

    /**metoda wykonuje obrót figury*/
    public void rotation()
    {
        for (byte x=0; x<4; x++) for (byte y=0; y<4; y++) tempTab[x][y] = tab[x][y];
        for (byte x=0; x<4; x++) for (byte y=0; y<4; y++) tab[3-y][x] = tempTab[x][y];
    }

    /**metoda prepisuje z tablicy pomocniczej na tablice główną; zabezpieczenie przed wyjściem figury poza plansze*/
    public void reversedRotation()
    {
        for (byte x=0; x<4; x++) for (byte y=0; y<4; y++) tab[x][y] = tempTab[x][y];
    }

}