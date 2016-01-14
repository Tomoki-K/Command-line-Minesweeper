/*==================== Display class ====================*/

public class Display {

    static int side = Minesweeper.getSide();
    static Block field[][] = Minesweeper.field;

    // unopened cells shown as "■"
    public static void display_hidden() {
        // display format
        String disClosed = "■"; // unopened cell
        String disWall = " "; // wall
        String disCell = "+"; // cell without surrounding mines
        String disFlag = "\u001B[00;31m*\u001B[00m"; // cell marked as flag (red *)


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
                            System.out.print(" " + disClosed);
                        } else if (Minesweeper.getState(field[x][y]) == 1) { //open
                            if (Minesweeper.countMines(field[x][y]) == 0) { // opened (no surrounding mines)
                                System.out.print(" " + disCell);
                            } else { // opened (surrounded by mine)
                                System.out.print(color(field[x][y]));
                            }
                        } else {// flagged
                            System.out.print(" " + disFlag);
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
        String disCell = "\u001B[00;37m+\u001b[00m"; // cell without surrounding mines (grey +)
        String disMine = "\u001B[00;31m◎\u001B[00m"; // cell containing mine (red ◎)
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
                                System.out.print(" " + disCell);
                            } else {// cell with surrounding mine(s)
                                System.out.print(color(field[x][y]));
                            }
                        } else { // contains mine
                            System.out.print(" " + disMine);
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

    // change display color
    private static String color(Block t) {
        switch (Minesweeper.countMines(t)) {
            case 1:
                return "\u001B[00;34m 1\u001b[00m"; // blue
            case 2:
                return "\u001B[00;36m 2\u001b[00m"; // light blue
            case 3:
                return "\u001B[00;32m 3\u001b[00m"; // green
            case 4:
                return "\u001B[00;35m 4\u001b[00m"; // purple
            case 5:
                return "\u001B[00;35m 5\u001b[00m"; // purple
            case 6:
                return "\u001B[00;35m 6\u001b[00m"; // purple
            case 7:
                return "\u001B[00;35m 7\u001b[00m"; // purple
            case 8:
                return "\u001B[00;35m 8\u001b[00m"; // purple
        }
        return null;
    }
}
