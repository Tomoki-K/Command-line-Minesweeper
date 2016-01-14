/*==================== Action class ====================*/

public class Action {

    // flag to check if first move or not
    private static boolean isFirst = true;

    // open a cell
    public static void open(Block target) {

        if (target.state == 0) { // closed
            if (target.hasMine == 0) { // target does not contain mine
                openBlock(target);
                if (isFirst) {
                    isFirst = false; // change flag to false
                }
                if (Minesweeper.countMines(target) == 0) { // neighbours also do not contain mine
                    openNeighbours(target);
                }
            } else { // target.hasMine == 1 (contains mine)
                if (isFirst) { // first move
                    haveMercy(target);
                    open(target);
                    isFirst = false; // change flag
                } else { // isFirst == false
                    gameOver();
                }
            }
        } else if (target.state == 1) { // open
            System.out.println("that cell is already open"); // do nothing
        } else { // flagged
            closeBlock(target); // remove flag and close cell
        }
    }

    // if target of first move contains mine, switch with a cell that doesn't.
    private static void haveMercy(Block target) {
        // search from top-left corner for a cell that does not contain mine.
        loop:
        for (int i = 1; i < Minesweeper.field.length - 1; i++) {
            for (int j = 1; j < Minesweeper.field.length - 1; i++) {

                if (Minesweeper.field[i][j].hasMine == 0) {
                    Minesweeper.field[i][j].hasMine = 1; // change hasMine to 1
                    target.hasMine = 0; // change hasMine to 0
                    break loop;
                }

            }

        }
    }

    // flag a cell
    public static void flag(Block target) {
        if (target.state == 0) {
            target.state = 2;
        } else {
            target.state = 0;// remove flag
        }
    }

    private static void openNeighbours(Block target) {
        int x = target.coorX;
        int y = target.coorY;
        /*  game field diagram
             --- --- ---
            | 1 | 2 | 3 |
            |---|---|---|
            | 4 | T | 5 |   T = target
            |---|---|---|
            | 6 | 7 | 8 |
             --- --- ---
        */
        Block Neighbours[] = new Block[8]; // neighbour blocks into array
        Neighbours[0] = Minesweeper.field[x - 1][y - 1];
        Neighbours[1] = Minesweeper.field[x][y - 1];
        Neighbours[2] = Minesweeper.field[x + 1][y - 1];
        Neighbours[3] = Minesweeper.field[x - 1][y];
        Neighbours[4] = Minesweeper.field[x + 1][y];
        Neighbours[5] = Minesweeper.field[x - 1][y + 1];
        Neighbours[6] = Minesweeper.field[x][y + 1];
        Neighbours[7] = Minesweeper.field[x + 1][y + 1];


        for (Block Neighbour : Neighbours) {
            if (Neighbour.hasMine == 0 && Neighbour.state == 0) { // closed and does not contain mine
                openBlock(Neighbour);// open cell
                if (Minesweeper.countMines(Neighbour) == 0 && Minesweeper.isCell(Neighbour)) { // neighbours do not have mine
                    //System.out.println(target.id);
                    openNeighbours(Neighbour); // recursion
                }
            }
        }
    }

    // game over action
    private static void gameOver() {
        System.out.println();
        System.out.println("＿人人人人人人人＿");
        System.out.println("＞　GAME OVER! ＜");
        System.out.println("￣Y^Y^Y^Y^Y^Y￣");
        System.out.println();
        System.out.println("answer : ");
        Display.display_answer();
        System.exit(0);
    }

    // open Block
    private static void openBlock(Block t) {
        t.state = 1;
    }

    // close Block
    private static void closeBlock(Block t) {
        t.state = 0;
    }

}