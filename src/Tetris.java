import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tetris extends JPanel implements Runnable {

    static Tetris tetris = new Tetris();
    static JFrame window = new JFrame("Tetris - LUKASZ BIRSKI");
    static Thread thread = new Thread(tetris);

    static GameBoard gameBoard = new GameBoard();
    static Next next = new Next();

    boolean start = false;
    short op = 30;

    static int lines = 0, points = 0, level = 1;
    static JLabel linePointsString, numberOfLinesString, lineLevelString, linePointsNumber, numberOfLinesNumber, lineLevelNumber, keysLabel1, keysLabel2, keysLabel3, keysLabel4;

    static Font firstFont, secondFont;

    /** konstruktor tworzy grę Tetris*/
    Tetris()
    {
        super();
        setBackground(Color.DARK_GRAY);
        setLayout(null);
        start = true;
        firstFont = new Font("System", Font.BOLD, 20);
        secondFont = new Font("System", Font.BOLD, 10);
    }

    public static void main(String[] args) {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(tetris);
        window.setSize(400,555);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        linePointsString = new JLabel("0", JLabel.LEFT);
        linePointsString.setForeground(Color.BLACK);
        linePointsString.setFont(firstFont);
        linePointsString.setBounds(270, 300, 100, 20);tetris.add(linePointsString);
        linePointsNumber = new JLabel("POINTS:", JLabel.LEFT);
        linePointsNumber.setBounds(270, 290, 100, 10);linePointsNumber.setForeground(Color.BLACK);
        linePointsNumber.setFont(secondFont);tetris.add(linePointsNumber);

        numberOfLinesString = new JLabel("0", JLabel.LEFT);
        numberOfLinesString.setForeground(Color.BLACK);numberOfLinesString.setFont(firstFont);
        numberOfLinesString.setBounds(270, 230, 100, 20);tetris.add(numberOfLinesString);
        numberOfLinesNumber = new JLabel("LINES:", JLabel.LEFT);
        numberOfLinesNumber.setBounds(270, 220, 100, 10);numberOfLinesNumber.setForeground(Color.BLACK);
        numberOfLinesNumber.setFont(secondFont);tetris.add(numberOfLinesNumber);

        lineLevelString = new JLabel("1", JLabel.LEFT);
        lineLevelString.setForeground(Color.BLACK);lineLevelString.setFont(firstFont);
        lineLevelString.setBounds(270, 160, 100, 20);tetris.add(lineLevelString);
        lineLevelNumber = new JLabel("LEVEL:", JLabel.LEFT);
        lineLevelNumber.setBounds(270, 150, 100, 10);lineLevelNumber.setForeground(Color.BLACK);
        lineLevelNumber.setFont(secondFont);tetris.add(lineLevelNumber);

        keysLabel1 = new JLabel("KEYS:", JLabel.CENTER);
        keysLabel1.setForeground(Color.BLACK); keysLabel1.setFont(secondFont);
        keysLabel1.setBounds(270, 360, 100, 20); tetris.add(keysLabel1);

        keysLabel2 = new JLabel(" <- -> = movement", JLabel.LEFT);
        keysLabel2.setForeground(Color.BLACK); keysLabel2.setFont(secondFont);
        keysLabel2.setBounds(270, 375, 120, 20); tetris.add(keysLabel2);

        keysLabel3 = new JLabel("Z = rotation", JLabel.LEFT);
        keysLabel3.setForeground(Color.BLACK); keysLabel3.setFont(secondFont);
        keysLabel3.setBounds(270, 390, 120, 20); tetris.add(keysLabel3);

        keysLabel4 = new JLabel("mouse = play/pause", JLabel.LEFT);
        keysLabel4.setForeground(Color.BLACK); keysLabel4.setFont(secondFont);
        keysLabel4.setBounds(270, 405, 120, 20); tetris.add(keysLabel4);

        gameBoard.setLocation(10, 10);tetris.add(gameBoard);
        next.setLocation(270, 10);tetris.add(next);
        window.setVisible(true);
        thread.start();
    }

    /**Metoda powoduje wykonanie się wszystkich metod w stałych odstępach czasu*/
    @Override
    public void run() {
        long wait, startCycle, timeCycle;
        while (start)
        {
            startCycle = System.nanoTime();
            gameBoard.run();
            next.run();
            timeCycle = System.nanoTime() - startCycle;
            wait = op - timeCycle / 1000000;
            if (wait<=0) wait = 5;
            try {thread.sleep(wait);} catch (InterruptedException e) {e.printStackTrace();}
        }
    }

}