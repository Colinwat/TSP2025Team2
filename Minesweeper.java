import java.util.Scanner;

/**
 * This program runs a game of Minesweeper. The user plays the game by using text commands, followed by an x and y coordinate.
 * Date last modified: 8/18/2024
 * @author Colin Watson
 * Made beacuse I felt like it
 */

// Minesweeper is 18 x 14

//TODO: add win detection (test)

public class Minesweeper{
    final static int WIDTH = 18;
    final static int LENGTH = 14;
    final static int MINES = 40;

    public static void main(String[] args) {
        // Printing game instructions
        System.out.println("HOW TO PLAY: Type in the command (\"flg\", \"dig\", \"chd\"), then the x and y coordinates of the tile");
        System.out.println("dig reveals the selected tile, flg flags or unflags the selected tile.");
        System.out.println("chd (chord) command will reveal all cells adjacent to the selected one, as long as the cell has a revealed " +
                "number and has that many flags adjacent");
        System.out.println("Example: \"dig 3 5\" will reveal the tile on (3, 5)");
        System.out.print("\nSTARTING GAME\n\n");

        // Set up the minefield and helpers for the game
        Cell[][] field = new Cell[WIDTH][LENGTH]; // minesweeper is WIDTH x LENGTH
        initializeField(field);
        int revealedCells = 0; // Counts number of revealed cells
        int numFlags = 0; // Counter for number of flags placed
        Scanner scan = new Scanner(System.in);

        printField(field); // Initial print of the field
        System.out.printf("%d out of %d cells revealed\n", revealedCells, WIDTH * LENGTH);

        // Running the game, endless loop checks for user input
        while(true){
            // Read and process the command
            String input = scan.next();

            if(input.compareTo("help") == 0){
                System.out.println("HOW TO PLAY: Type in the command (\"flg\", \"dig\", \"chd\"), then the x and y coordinates of the tile");
                System.out.println("dig reveals the selected tile, flg flags or unflags the selected tile.");
                System.out.println("chd (chord) command will reveal all cells adjacent to the selected one, as long as the cell has a revealed " +
                        "number and has that many flags adjacent");
                System.out.println("Example: \"dig 3 5\" will reveal the tile on (3, 5)");
                scan.nextLine(); // Clear the scanner buffer
                continue;
            }

            int x = scan.nextInt();
            int y = scan.nextInt();
            if(x > WIDTH - 1 || x < 0 || y > LENGTH - 1 || y < 0){
                System.out.println("Error: X or Y coordinate out of bounds");
                continue;
            }

            // Dig command
            if(input.compareTo("dig") == 0){
                if(field[x][y].isFlag()){
                    System.out.println("Cell is flagged");
                    continue;
                }
                else if(field[x][y].isMine()){
                    System.out.println("You hit a mine, Game Over");
                    revealField(field);
                    break;
                }

                field[x][y].revealCell();
                revealedCells++;

                if(field[x][y].getHint() == 0){
                    revealedCells += revealAdjacent(field, x, y, 0);
                }
            }

            // Flag command
            else if(input.compareTo("flg") == 0){
                if(field[x][y].isRevealed()){
                    System.out.println("Cell is already revealed");
                    continue;
                }
                else{
                    field[x][y].swapFlag();
                    if(field[x][y].isFlag()){
                        numFlags++;
                    }
                    else{
                        numFlags--;
                    }
                }
            }

            // Chord command
            else if(input.compareTo("chd") == 0){
                if(field[x][y].isFlag()){
                    System.out.println("Cell is flagged");
                    continue;
                }
                else if(!field[x][y].isRevealed()){
                    System.out.println("Cell is not revealed");
                    continue;
                }

                int numAdjacentFlags = 0;
                // Access each adjacent cell if they exist, then add to the flag count if that cell is flagged
                if((x - 1 >= 0) && (y - 1 >= 0) && field[x - 1][y - 1].isFlag()){
                    numAdjacentFlags++; // Bottom left
                }
                if((y - 1 >= 0) && field[x][y - 1].isFlag()){
                    numAdjacentFlags++; // Bottom middle
                }
                if((x + 1 < WIDTH) && (y - 1 >= 0) && field[x + 1][y - 1].isFlag()){
                    numAdjacentFlags++; // Bottom right
                }
                if((x - 1 >= 0) && field[x - 1][y].isFlag()){
                    numAdjacentFlags++; // Middle left
                }
                if((x + 1 < WIDTH) && field[x + 1][y].isFlag()){
                    numAdjacentFlags++; // Middle right
                }
                if((x - 1 >= 0) && (y + 1 < LENGTH) && field[x - 1][y + 1].isFlag()){
                    numAdjacentFlags++; // Top left
                }
                if((y + 1 < LENGTH) && field[x][y + 1].isFlag()){
                    numAdjacentFlags++; // Top middle
                }
                if((x + 1 < WIDTH) && (y + 1 < LENGTH) && field[x + 1][y + 1].isFlag()){
                    numAdjacentFlags++; // Top right
                }

                if(numAdjacentFlags == field[x][y].getHint()){
                    revealedCells += revealAdjacent(field, x, y, 0);
                }
                else if(numAdjacentFlags < field[x][y].getHint()){
                    System.out.println("Not enough flags nearby");
                    continue;
                }
                else{
                    System.out.println("Too many flags nearby");
                    continue;
                }
            }

            // Command not recognized
            else{
                System.out.println("Error: Command not recognized. Commands are in the form [command] [x coordinate] [y coordinate]");
                scan.nextLine(); // Clear the scanner buffer
                continue;
            }

            printField(field); // Print the field after a command successfully completes
            System.out.printf("%d out of %d cells revealed\n", revealedCells, WIDTH * LENGTH - MINES);
            System.out.printf("%d out of %d flags\n", numFlags, MINES);

            if(revealedCells == WIDTH * LENGTH - MINES){
                System.out.println("Congratulations, you won!");
                break;
            }
        }
    }

    /**
     * Initializes a 2D array of cell objects. Adds mines and sets hints appropriately.
     * @param field The field to add cells to
     */
    static public void initializeField(Cell[][] field){
        // Initialize the field with cell objects
        for(int y = 0; y < LENGTH; y++){
            for(int x = 0; x < WIDTH; x++) {
                Cell cell = new Cell();
                field[x][y] = cell;
            }
        }

        // Add mines to random cells
        for(int i = 0; i < MINES; i++){
            int x = 0;
            int y = 0;

            // Select random pairs of coordinates until a cell at those coordinates does not contain a mine
            do{
                x = (int) (Math.random() * WIDTH);
                y = (int) (Math.random() * LENGTH);
            } while(field[x][y].isMine());

            field[x][y].setMine();

            setHints(field, x, y);
        }
    }

    /**
     * Helper method for initializeField, updates the cells around a new mine to accurately represent the number of adjacent mines.
     * @param field The minefield
     * @param x The mine's x coordinate
     * @param y The mine's y coordinate
     */
    private static void setHints(Cell[][] field, int x, int y){
        // Access each adjacent cell if they exist, then add one to their hint value
        // Bottom left
        if((x - 1 >= 0) && (y - 1 >= 0)){
            field[x - 1][y - 1].addHint();
        }
        // Bottom middle
        if((y - 1 >= 0)){
            field[x][y - 1].addHint();
        }
        // Bottom right
        if((x + 1 < WIDTH) && (y - 1 >= 0)){
            field[x + 1][y - 1].addHint();
        }
        // Middle left
        if((x - 1 >= 0)){
            field[x - 1][y].addHint();
        }
        // Middle right
        if((x + 1 < WIDTH)){
            field[x + 1][y].addHint();
        }
        // Top left
        if((x - 1 >= 0) && (y + 1 < LENGTH)){
            field[x - 1][y + 1].addHint();
        }
        // Top middle
        if((y + 1 < LENGTH)){
            field[x][y + 1].addHint();
        }
        // Top right
        if((x + 1 < WIDTH) && (y + 1 < LENGTH)){
            field[x + 1][y + 1].addHint();
        }
    }

    /**
     * Prints the current state of the minefield
     * @param field The minefield to print
     */
    static public void printField(Cell[][] field){
        for(int y = 0; y < LENGTH; y++){
            System.out.printf("%-4d" , y); // Print the current row number
            for(int x = 0; x < WIDTH; x++){
                if(field[x][y].isFlag() && !field[x][y].isMine() && field[x][y].isRevealed()){
                    System.out.print("X  "); // Print an X if a cell is flagged but doesn't contain a mine (once the game is over)
                }
                else if(field[x][y].isFlag()){
                    System.out.print("F  "); // Print an F if that cell is flagged
                }
                else if(!field[x][y].isRevealed()){
                    System.out.print("?  "); // Print a ? if that cell is not revealed
                }
                else if(field[x][y].isMine()){
                    System.out.print("*  "); // Print a * if that cell is unflagged and a mine (once the game is over)
                }
                else{
                    System.out.print(field[x][y].getHint() + "  "); // Print the cell's hint value
                }
            }
            System.out.print("\n");
        }
        System.out.println("    0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17"); // Print the column numbers
    }

    /**
     * Sets each cell in the field to a revealed state, then prints the revealed field
     * @param field The field of cells to reveal and print
     */
    static public void revealField(Cell[][] field){
        for(int y = 0; y < LENGTH; y++){
            for(int x = 0; x < WIDTH; x++){
                field[x][y].revealCell();
            }
        }
        printField(field);
    }

    /**
     * Hides all cells, used for debugging
     * @param field The field to hide
     */
    static public void hideField(Cell[][] field){
        for(int y = 0; y < LENGTH; y++){
            for(int x = 0; x < WIDTH; x++){
                field[x][y].hideCell();
            }
        }
    }

    /**
     * Reveals all cells adjacent to the given cell coordinates.
     * @param field The minefield
     * @param x The X coordinate of the cell
     * @param y The Y coordinate of the cell
     * @return The number of revealed cells
     */
    static int revealAdjacent(Cell[][] field, int x, int y, int revealedTotal){
        // Access each adjacent cell if they exist and are unflagged. Then reveal the cell and adjacent cells if a 0 was revealed
        // Bottom left
        if((x - 1 >= 0) && (y - 1 >= 0) && !field[x - 1][y - 1].isFlag()){
            if(!field[x - 1][y - 1].isRevealed()) {
                field[x - 1][y - 1].revealCell();
                revealedTotal++;
                if(field[x - 1][y - 1].getHint() == 0){
                    revealedTotal += revealAdjacent(field, x - 1, y - 1, 0);
                }
            }
        }

        // Bottom middle
        if((y - 1 >= 0) && !field[x][y - 1].isFlag()){
            if(!field[x][y - 1].isRevealed()) {
                field[x][y - 1].revealCell();
                revealedTotal++;
                if(field[x][y - 1].getHint() == 0){
                    revealedTotal += revealAdjacent(field, x, y - 1, 0);
                }
            }
        }

        // Bottom right
        if((x + 1 < WIDTH) && (y - 1 >= 0) && !field[x + 1][y - 1].isFlag()){
            if(!field[x + 1][y - 1].isRevealed()) {
                field[x + 1][y - 1].revealCell();
                revealedTotal++;
                if(field[x + 1][y - 1].getHint() == 0){
                    revealedTotal += revealAdjacent(field, x + 1, y - 1, 0);
                }
            }
        }

        // Middle left
        if((x - 1 >= 0) && !field[x - 1][y].isFlag()){
            if(!field[x - 1][y].isRevealed()) {
                field[x - 1][y].revealCell();
                revealedTotal++;
                if(field[x - 1][y].getHint() == 0){
                    revealedTotal += revealAdjacent(field, x - 1, y, 0);
                }
            }
        }

        // Middle right
        if((x + 1 < WIDTH) && !field[x + 1][y].isFlag()){
            if(!field[x + 1][y].isRevealed()) {
                field[x + 1][y].revealCell();
                revealedTotal++;
                if(field[x + 1][y].getHint() == 0){
                    revealedTotal += revealAdjacent(field, x + 1, y, 0);
                }
            }
        }

        // Top left
        if((x - 1 >= 0) && (y + 1 < LENGTH) && !field[x - 1][y + 1].isFlag()){
            if(!field[x - 1][y + 1].isRevealed()) {
                field[x - 1][y + 1].revealCell();
                revealedTotal++;
                if(field[x - 1][y + 1].getHint() == 0){
                    revealedTotal += revealAdjacent(field, x - 1, y + 1, 0);
                }
            }
        }

        // Top middle
        if((y + 1 < LENGTH) && !field[x][y + 1].isFlag()){
            if(!field[x][y + 1].isRevealed()) {
                field[x][y + 1].revealCell();
                revealedTotal++;
                if(field[x][y + 1].getHint() == 0){
                    revealedTotal += revealAdjacent(field, x, y + 1, 0);
                }
            }
        }

        // Top right
        if((x + 1 < WIDTH) && (y + 1 < LENGTH) && !field[x + 1][y + 1].isFlag()){
            if(!field[x + 1][y + 1].isRevealed()) {
                field[x + 1][y + 1].revealCell();
                revealedTotal++;
                if(field[x + 1][y + 1].getHint() == 0){
                    revealedTotal += revealAdjacent(field, x + 1, y + 1, 0);
                }
            }
        }
        return revealedTotal;
    }
}
