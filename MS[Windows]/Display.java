/*==================== Display class ====================*/

public class Display {

    static int side = Minesweeper.getSide();
    static Block field[][] = Minesweeper.field;

    // unopened cells shown as "■"
    public static void display_hidden() {
        // display format
        String disClosed = "■"; // unopened cell
        String disWall = " "; // wall
        String disCell = " +"; // cell without surrounding mines
        String disFlag = " *"; // cell marked as flag

        for (int y = 0; y < side + 2; y++) {
            for (int x = 0; x < side + 2; x++) {
                if (x == 0 && y != 0 && y != side + 1) {
                    if (y < 10) {
                        System.out.print(y + " |");
                    } else {
                        System.out.print(y + "|");
                    }
                } else {
                    if (Minesweeper.isCell(field[x][y])) { // is cell
                        if (Minesweeper.getState(field[x][y]) == 0) { // Closed
                            System.out.print(disClosed);
                        } else if (Minesweeper.getState(field[x][y]) == 1) { //open
                            if (Minesweeper.countMines(field[x][y]) == 0) { // opened (no surrounding mines)
                                System.out.print(disCell);
                            } else { // opened (surrounded by mine)
                                System.out.print(" " + Minesweeper.countMines(field[x][y]));
                            }
                        } else {// flagged
                            System.out.print(disFlag);
                        } 
                    } else { // is wall
                        if (x != side + 1 && y == 0) {
                            if (x == 0) {
                                System.out.print("  |");
                            } else {
                                if (x < 10) { // prevent column shifting
                                    System.out.print(" " + x);
                                } else {
                                    System.out.print(x);
                                }
                            }
                        } else if (x == side + 1 && y == 0) {
                            System.out.println();
                            for (int l = 0; l < side * 2 + 3; l++) {
                                if (l == 2) {
                                    System.out.print("+");
                                } else {
                                    System.out.print("-");
                                }
                            }
                        } else {
                            System.out.print(" " + disWall);
                        }
                    }
                }
            }
            System.out.println();
        }

    }

    // show all answers (used in gameOver method)
    public static void display_answer() {
        // display format
        String disWall = " "; // walls
        String disCell = " +"; // cell without surrounding mines (grey +)
        String disMine = " ◎"; // cell containing mine (red ◎)
        for (int y = 0; y < side + 2; y++) {
            for (int x = 0; x < side + 2; x++) {
                if (x == 0 && y != 0 && y != side + 1) {
                    if (y < 10) {
                        System.out.print(y + " |"); //prevent column shifting
                    } else {
                        System.out.print(y + "|");
                    }
                } else {
                    if (Minesweeper.isCell(field[x][y])) { // is cell
                        if (field[x][y].hasMine == 0) { // does not contain mine
                            if (Minesweeper.countMines(field[x][y]) == 0) { // no surrounding mines
                                System.out.print(disCell);
                            } else {// cell with surrounding mine(s)
                                System.out.print(" "+Minesweeper.countMines(field[x][y]));
                            }
                        } else { // contains mine
                            System.out.print(disMine);
                        }

                    } else { // is wall
                        if (x != side + 1 && y == 0) {
                            if (x == 0) {
                                System.out.print("  |");
                            } else {
                                if (x < 10) {
                                    System.out.print(" " + x);
                                } else {
                                    System.out.print(x);
                                }
                            }
                        } else if (x == side + 1 && y == 0) {
                            System.out.println();
                            for (int l = 0; l < side * 2 + 3; l++) {
                                if (l == 2) {
                                    System.out.print("+");
                                } else {
                                    System.out.print("-");
                                }
                            }
                        } else {
                            System.out.print(" " + disWall);
                        }
                    }
                }
            }
            System.out.println();
        }

    }


}
