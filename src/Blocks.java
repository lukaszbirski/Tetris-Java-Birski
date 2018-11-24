import java.awt.Color;

public class Blocks {
    /**klasa zawiera definicję wszystkich klocków i ich kolorów*/
    final static short SIZE = 25;
    final static Color[] COLOR = {Color.GRAY, Color.RED, Color.GREEN, Color.BLUE, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.YELLOW, Color.WHITE, Color.BLACK};
    final static boolean[][][] BLOCKS =
            {
                    {
                            {false, false, false, false},	//....
                            {true , true , true , false},	//###.
                            {false, false, true , false},	//..#.
                            {false, false, false, false}	//....
                    },
                    {
                            {false, false, false, false},	//....
                            {true , true , true , false},	//###.
                            {false, true , false, false},	//.#..
                            {false, false, false, false}	//....
                    },
                    {
                            {false, false, false, false},	//....
                            {false, false, true , false},	//..#.
                            {true , true , true , false},	//###.
                            {false, false, false, false}	//....
                    },
                    {
                            {false, false, false, false},	//....
                            {true , true , true , true },	//####
                            {false, false, false, false},	//....
                            {false, false, false, false}	//....
                    },
                    {
                            {false, false, false, false},	//....
                            {false, true , true , false},	//.##.
                            {false, true , true , false},	//.##.
                            {false, false, false, false}	//....
                    },
                    {
                            {false, false, false, false},	//....
                            {false, true , true , false},	//.##.
                            {true , true , false, false},	//##..
                            {false, false, false, false}	//....
                    },
                    {
                            {false, false, false, false},	//....
                            {true , true , false, false},	//##..
                            {false, true , true , false},	//.##.
                            {false, false, false, false}	//....
                    }
            };
}