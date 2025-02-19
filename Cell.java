/**
 * This class implements a custom object for use with playing minesweeper.
 */

public class Cell {
    // Instance variables
    private int hint;
    private boolean mine;
    private boolean flag;
    private boolean revealed;

    /**
     * The constructor for a new cell
     */
    public Cell(){
        hint = 0;
        mine = false;
        flag = false;
        revealed = false;
    }

    /**
     * Returns the cell's mine status
     * @return True if the cell is a mine, false otherwise
     */
    public boolean isMine(){
        return this.mine;
    }

    /**
     * Sets a cell to a mine
     */
    public void setMine() {
        this.mine = true;
    }

    /**
     * Returns the cell's hint value
     * @return The number of mines adjacent to the cell
     */
    public int getHint(){
        return this.hint;
    }

    /**
     * Increments a cell's hint value
     */
    public void addHint(){
        this.hint += 1;
    }

    /**
     * Returns the cell's flag status
     * @return True if the cell is flagged, false otherwise
     */
    public boolean isFlag(){
        return this.flag;
    }

    /**
     * Swaps the cell's flag status
     */
    public void swapFlag(){
        this.flag = !this.flag;
    }

    /**
     * Returns the cell's revealed status
     * @return True if the cell is revealed, false otherwise
     */
    public boolean isRevealed(){
        return this.revealed;
    }

    /**
     * Sets the cell to be revealed
     */
    public void revealCell() {
        this.revealed = true;
    }

    /**
     * Sets the cell to be hidden. Used for debugging
     */
    public void hideCell(){
        this.revealed = false;
    }
}
