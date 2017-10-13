import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

interface Openable {
    static int countNeighbours(Block t) {
        int x = t.coorX;
        int y = t.coorY;
        int a = Minesweeper.getMines(Minesweeper.field[x - 1][y - 1]);
        int b = Minesweeper.getMines(Minesweeper.field[x][y - 1]);
        int c = Minesweeper.getMines(Minesweeper.field[x + 1][y - 1]);
        int d = Minesweeper.getMines(Minesweeper.field[x - 1][y]);
        int e = Minesweeper.getMines(Minesweeper.field[x + 1][y]);
        int f = Minesweeper.getMines(Minesweeper.field[x - 1][y + 1]);
        int g = Minesweeper.getMines(Minesweeper.field[x][y + 1]);
        int h = Minesweeper.getMines(Minesweeper.field[x + 1][y + 1]);

        int cnt = a + b + c + d + e + f + g + h;
        return cnt;
    }
    /*  cell diagram
         --- --- ---
        | a | b | c |
        |---|---|---|
        | d | t | e |  <- set t as target and count field around.
        |---|---|---|
        | f | g | h |
        '--- --- ---
     */
}

/*==================== Block class ====================*/

class Block {
    public int coorX; // Block x coordinate
    public int coorY; // Block y coordinate
    public int state; // 0:closed 1:open 2:flagged
    public int hasMine; // 0:false 1:true

    // constructor (default)
    public Block() {
    }

}

/*==================== Cell class ====================*/

class Cell extends Block implements Openable {
    // constructor
    public Cell(int x, int y) {
        this.coorX = x;
        this.coorY = y;
        this.state = 0;
        this.hasMine = 0;
    }
}

/*==================== Wall class ====================*/

class Wall extends Block {
    // constructor
    public Wall(int x, int y) {
        this.coorX = x;
        this.coorY = y;
        this.state = 0;
        this.hasMine = 0;
    }
}

/*==================== Minesweeper class ====================*/

public class Minesweeper {
    private static int side; // cells per side
    private static String playerName; // player's name
    private static int mineTotal; // Total count of mines
    public static Block[][] field; // create field array

    /*-------------------- main method --------------------*/
    public static void main(String[] args) throws IOException {
        try {
            side = Integer.parseInt(args[0]); // set level from command line
        } catch (Exception e) {
            side = 9;  // set default level to 9 * 9
        }
        try {
            mineTotal = Integer.parseInt(args[1]);  //  set mine count from command line
        } catch (Exception e) {
            mineTotal = (int) (side * side * 0.13); // set default mine count to 13%
        }

        // read player's name
        System.out.println("Type your name and press ENTER to begin.");
        nameReader();

        // set up game field
        newGame();

        // print title
        System.out.println();
        System.out.println("Minesweeper [" + side + "×" + side + "  mine:" + mineTotal + "]");

        // display game field (answer)  <- for debugging
        // Display.display_answer();

        // display game field (hidden)
        Display.display_hidden();

        // start timer
        Timer.start();

        // game loop
        while (!gameClear()) {
            // Wait for command
            cmdToAction();
        }

        // stop timer
        Timer.end();

        System.out.println();
        System.out.println("<=== GAME CLEAR!! ===>");
        System.out.println("clear time : \u001B[00;31m" + Timer.getTime() + "\u001B[00m seconds"); // display time in red
        System.out.println();
        // show ranking
        System.out.println("time ranking : ");
        Ranking.rank();
    }

    // set player's name
    private static void nameReader() {
        System.out.print("> ");
        BufferedReader nameReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            setPlayerName(nameReader.readLine());
        } catch (IOException e) {
            System.out.println("Invalid name. Try again.");
            nameReader();
        }
    }

    // set up new game field
    private static void newGame() {
        // make array and initialize with Cell/Wall instances
        field = new Block[side + 2][side + 2];
        for (int j = 0; j < side + 2; j++) {
            for (int i = 0; i < side + 2; i++) {
                field[i][j] = (j == 0 || j == side + 2 || i == 0 || i == side + 2) ? new Wall(i, j) : new Cell(i, j);
            }
        }

        // randomly change to mine
        int[][] list = new int[mineTotal][2];
        int i = 0;
        while (i < list.length) {
            Loop:
            while (true) {
                list[i][0] = (int) (Math.random() * side + 1);
                list[i][1] = (int) (Math.random() * side + 1);
                for (int j = 0; j < i; j++) {
                    if (list[i][0] == list[j][0] && list[i][1] == list[j][1]) {
                        continue Loop;
                    }
                }
                break;
            }
            int k = list[i][0];
            int l = list[i][1];
            if (isCell(field[k][l])) {
                field[k][l].hasMine = 1;
                i++;
            }
        }
    }

    // convert command input to actual command
    public static void cmdToAction() throws IOException {
        System.out.print("> ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = null;
        try {
            str = br.readLine();
        } catch (IOException e) {
            System.out.println("invalid input.");
        }
        String[] input;
        input = str.split("\\s+"); // split string to array

        if (!Objects.equals(input[0], "o") && !Objects.equals(input[0], "open") && !Objects.equals(input[0], "f") && !Objects.equals(input[0], "flag")) {
            System.out.println("'" + input[0] + "' is an invalid command. Try again.");
        } else if (input.length != 3) {
            System.out.println("input statement must be in form of 'command x y'");
        } else if (!isNumeric(input[1]) || !isNumeric(input[2]) || Integer.parseInt(input[1]) > side || Integer.parseInt(input[2]) > side) {
            System.out.println("invalid coordinate. Try again.");
        } else {
            String cmd = input[0];
            int x = Integer.parseInt(input[1]);
            int y = Integer.parseInt(input[2]);
            switch (cmd) {
                case "open":
                case "o":
                    Action.open(field[x][y]);
                    Display.display_hidden();
                    break;
                case "flag":
                case "f":
                    Action.flag(field[x][y]);
                    Display.display_hidden();
                    break;
                default:
                    System.out.println("invalid command");
                    cmdToAction();
                    break;
            }
        }
    }

    // check if game clear
    public static boolean gameClear() {
        int check = 0;
        for (int y = 0; y < field.length; y++) {
            for (int x = 0; x < field.length; x++) {
                if (isCell(field[x][y])) { // is cell
                    if (getMines(field[x][y]) == 0) { // does not have mine
                        if (field[x][y].state == 1) { // cell opened
                            check++;
                        } else {
                            return false;
                        }
                    } else { // has mine
                        if (field[x][y].state == 0 || field[x][y].state == 2) { // cell containing mine closed or flagged
                            check++;
                        } else {
                            return false;
                        }
                    }
                }
            }
        }
        return check == side * side;
    }

    /*---------- getters and setters ----------*/

    // side getter
    public static int getSide() {
        return side;
    }

    // name getter
    public static String getName() {
        return playerName;
    }

    // cell getter
    public static int getMines(Block t) {
        return t.hasMine;
    }

    // state getter
    public static int getState(Block t) {
        return t.state;
    }

    // name setter
    public static void setPlayerName(String name) {
        Minesweeper.playerName = name;
    }

    // mine counter
    public static int countMines(Block t) {
        if (isCell(t)) { //Cell
            return Openable.countNeighbours(t); // count surrounding mines
        } else {
            return 0; // Wall has no Mine count
        }
    }

    public static boolean isCell(Block t) {
        return t.coorX != 0 && t.coorX != side + 1 && t.coorY != 0 && t.coorY != side + 1;
    }

    // check if String is Number
    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+"); // normal expression.
    }

}
