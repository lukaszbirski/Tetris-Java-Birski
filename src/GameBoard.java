import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameBoard extends MyCanvas implements MouseListener, KeyListener{

    final static short WIDTH = Blocks.SIZE * 10;            //wymiary pojedyńczego bloczka
    final static short HEIGTH = Blocks.SIZE * 20;

    byte[][] tab = new byte[12][22];

    Block block = new Block();
    byte blockX, blockY;                //pozycja klocka

    boolean keyLeft, keyRight, keyZ, keyDown, speedKey;             //obsługa klawiszy

    short speed, speedMax;              //prędkość klocka

    Sound soundRotation, soundBlock, soundLine;

    boolean gamePlay, pause;
    Color gameBoardFontColor;

    GameBoard() {
        super(WIDTH, HEIGTH);
        addMouseListener(this);
        addKeyListener(this);
        soundRotation = new Sound("rotation.wav");
        soundBlock = new Sound("block.wav");
        soundLine = new Sound("line.wav");
        for (byte x=0; x<12; x++) {tab[x][0]=1;tab[x][21]=1;}
        for (byte y=0; y<22; y++) {tab[0][y]=1;tab[11][y]=1;}
        blockX = 4;
        blockY = 0;
        speedMax = (short) (21 - Tetris.level);
        gamePlay = false;
        pause = false;
        graphic.setFont(new Font("System", Font.BOLD, 18));
        gameBoardFontColor = Color.BLACK;
    }

    /**metoda obsługuje pole gry i wszystkie zdarzenia*/
    @Override
    public void drawImage() {
        if (gamePlay)
        {
            key();
            cmpGameBord();
            gameBoarPrint();
            blockPrintOut(blockX, blockY);
            if (!pause)
            {
                if (speed<speedMax) speed++;else
                {
                    speed=0;
                    if (checkIfBlockIsOnTheGameBoard(blockX, (byte)(blockY+1))) blockY++; else
                    {
                        blockStops();
                        newBlock();
                    }
                }
            }
            else
            {
                graphic.setColor(Color.GRAY);
                graphic.fillRect(76, 200, 100, 40);
                graphic.setColor(gameBoardFontColor);
                graphic.drawString("PAUSE", 90, 225);
            }
        }
        else
        {
            graphic.setColor(Blocks.COLOR[0]);
            graphic.fillRect(0, 0, WIDTH, HEIGTH);
            graphic.setColor(gameBoardFontColor);
            graphic.drawString("TETRIS", 90, 50);
            graphic.drawString("Author: Lukasz Birski", 10, 70);
            graphic.drawString("Project made as part", 10, 150);
            graphic.drawString("of the OOP course", 25, 170);
            graphic.drawString("PW, Warsaw", 65, 230);
            graphic.drawString("November 2018", 45, 250);
            graphic.drawString("CLICK to START", 57, 495);

        }
    }

    /**metoda ma za zadanie wydrukować kratownicę na polu gry*/
    public void drawLines(){
        graphic.setColor(Blocks.COLOR[9]);
        for (int i = 1; i < HEIGTH; i++) {
            graphic.drawLine(0,i*Blocks.SIZE, WIDTH*Blocks.SIZE, i*Blocks.SIZE);
        }
        for (int j = 1; j < WIDTH; j++) {
            graphic.drawLine(j*Blocks.SIZE,0,j*Blocks.SIZE,HEIGTH*Blocks.SIZE);
        }
    }

    /**metoda tworzy nową figurę*/
    public void newBlock()
    {
        blockX = 4;
        blockY = 0;
        speedMax = (short) (20 - Tetris.level);
        if (speedMax<0) speedMax=0;
        block.setBlock(Tetris.next.block);
        soundBlock.play();
        Tetris.next.blockRandom();
        Tetris.points+= block.blockColor;
        Tetris.linePointsString.setText(String.valueOf(Tetris.points));
    }

    /**metoda powoduje zatrzymanie się figury gdy dotrze do dolnej granicy planszy lub innej figury*/
    public void blockStops()
    {
        for (byte xx=0; xx<4; xx++)
            for (byte yy=0; yy<4; yy++)
                if (block.tab[xx][yy]) tab[xx+blockX][yy+blockY]=(byte)(block.blockColor+1);
    }

    /**metoda drukuje pole do gry*/
    private void gameBoarPrint()
    {
        for (byte x=1; x<11; x++)
            for (byte y=1; y<21; y++)
            {
                graphic.setColor(Blocks.COLOR[tab[x][y]]);
                graphic.fillRect((x* Blocks.SIZE)- Blocks.SIZE, (y* Blocks.SIZE)- Blocks.SIZE, Blocks.SIZE, Blocks.SIZE);
                graphic.setColor(Color.BLACK);
                if (tab[x][y] > 0)
                    graphic.drawRect((x * Blocks.SIZE) - Blocks.SIZE, (y * Blocks.SIZE) - Blocks.SIZE, Blocks.SIZE - 1, Blocks.SIZE-1);
                drawLines();
            }
    }

    /**metoda ustawia pojedyńczą kostkę na planszy*/
    private void cubePrintOut(byte x, byte y, byte k)
    {
        graphic.setColor(Blocks.COLOR[k]);
        graphic.fillRect((x* Blocks.SIZE)- Blocks.SIZE, (y* Blocks.SIZE)- Blocks.SIZE, Blocks.SIZE, Blocks.SIZE);
        graphic.setColor(Color.BLACK);
        graphic.drawRect((x* Blocks.SIZE)- Blocks.SIZE, (y* Blocks.SIZE)- Blocks.SIZE, Blocks.SIZE-1, Blocks.SIZE-1);
    }

    /**metoda dla danej linii sprawdza czy wszystkie pola w linii mają wypełnienie*/
    private boolean isLine(byte y)
    {
        for (byte x=1; x<11; x++) {if (tab[x][y]==0) return false;}
        return true;
    }

    /**metoda sprawdza czy kostki nie wypełniły całej planszy*/
    private boolean isFull()
    {
        for (byte x=1; x<11; x++) {if (tab[x][1]!=0) return true;}
        return false;
    }

    /**metoda zmienia kolor klocków na biały gdy wszystkie są w jednej linii, dodawanie punktow, liczbę linii i prędkość**/
    private void setWhiteLine(byte y)
    {
        for (byte x=1; x<11; x++) tab[x][y]=8;
        Tetris.lines++;
        Tetris.numberOfLinesString.setText(String.valueOf(Tetris.lines));
        Tetris.points+=(Tetris.level * 10);
        Tetris.linePointsString.setText(String.valueOf(Tetris.points));
        if (Tetris.lines==(Tetris.level*Tetris.level))
        {
            Tetris.level++;
            Tetris.lineLevelString.setText(String.valueOf(Tetris.level));
            if (speedMax>0) speedMax--;
        }
    }

    /**metoda usuwa linię gdy są w niej wszystkie klocki*/
    private void downGameBoard(byte y)
    {
        for (byte ty=y; ty>1; ty--)
            for (byte x=1; x<11; x++) tab[x][ty]=tab[x][ty-1];
        for (byte x=1; x<11; x++) tab[x][1]=0;
        soundLine.play();
    }

    /**metoda łączy zmianę koloru linii, usunięcie linii, wypełnienie planszy i resuje zmienne zawierające punktację*/
    private void cmpGameBord()
    {
        for (byte y=1; y<21; y++)
        {
            if (tab[1][y]==8) downGameBoard(y);
            if (isLine(y)) setWhiteLine(y);
        }
        if (isFull())
        {
            gamePlay = false;
            Tetris.level=1;
            Tetris.lines=0;
            Tetris.points=0;
            blockX = 4;
            blockY = 0;
            speedMax = (short) (21 - Tetris.level);
            for (byte x=1; x<11; x++) for (byte y=1; y<21; y++) tab[x][y]=0;
        }
    }

    /**metoda drukuje figurę, tzw block złożony z 4x4 kostek*/
    public void blockPrintOut(byte x, byte y)
    {
        for (byte tx=0; tx<4; tx++)
            for (byte ty=0; ty<4; ty++)
                if (block.tab[tx][ty]) cubePrintOut((byte)(tx+x),(byte) (ty+y), (byte) (block.blockColor+1));
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        if (!gamePlay)
        {
            gamePlay=true;
            Tetris.linePointsString.setText(String.valueOf(Tetris.points));
            Tetris.numberOfLinesString.setText(String.valueOf(Tetris.lines));
            Tetris.lineLevelString.setText(String.valueOf(Tetris.level));
        }else
            pause=!pause;
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    /**metoda przechwytuje jaki klawisz został naciśnięty*/
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key==e.VK_Z) keyZ = true;
        if (key==e.VK_DOWN) keyDown = true;
        if (key==e.VK_LEFT) keyLeft = true;
        if (key==e.VK_RIGHT) keyRight = true;
    }

    /**po zwolnieniu klawisza metoda analuje to co zrobiłą metoda keyPressed*/
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key==e.VK_Z) keyZ = false;
        if (key==e.VK_DOWN) keyDown = false;
        if (key==e.VK_LEFT) keyLeft = false;
        if (key==e.VK_RIGHT) keyRight = false;
    }

    /**metoda sprawdza czy figura jest na planszy*/
    private boolean checkIfBlockIsOnTheGameBoard(byte x, byte y)
    {
        for (byte xx=0; xx<4; xx++)
            for (byte yy=0; yy<4; yy++)
                if (block.tab[xx][yy] && tab[xx+x][yy+y]>0) return false;
        return true;
    }

    /**metoda pozwala na wykonanie ruchu figury w lewo*/
    private boolean moveLeft()
    {
        if (!checkIfBlockIsOnTheGameBoard((byte)(blockX-1), blockY)) return false;
        return true;
    }

    /**metoda pozwala na wykonanie ruchu figury w prawo*/
    private boolean moveRight()
    {
        if (!checkIfBlockIsOnTheGameBoard((byte)(blockX+1), blockY)) return false;
        return true;
    }

    /**metoda obsługuje naciśnięcie klawiszy*/
    private void key()
    {
        speedKey=!speedKey;
        if (keyZ && speedKey) {
            block.rotation();                             //wykonanie obrotu figury
            if (!checkIfBlockIsOnTheGameBoard(blockX, blockY)){
                block.reversedRotation();        //jeżeli tablica jest poza polem gdy wracamy do pierwotnego ustawienia
            } else {
                soundRotation.play();
            }
        }

        if (keyLeft && moveLeft()) blockX--;                                //obsługa ruchu w lewo
        if (keyRight && moveRight()) blockX++;                              //obsługa ruchu w prawo
        if (keyDown && speedMax>0) {speedMax=0;Tetris.points+=5;Tetris.linePointsString.setText(String.valueOf(Tetris.points));}        //obsługa ruchu w dów
    }

}
