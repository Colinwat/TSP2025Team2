import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class MinesweeperGUI {
    private JFrame frame;
    private JPanel boardPanel;
    private JLabel revealedCounterLabel;
    private int revealedCells;
    private final int ROWS = 4, COLS = 4, MINES = 0;
    private Cell[][] board;

    public MinesweeperGUI() {
        board = new Cell[ROWS][COLS];
        revealedCells = 0;

        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create board panel
        boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        frame.add(boardPanel, BorderLayout.CENTER);

        // Create revealed counter label
        revealedCounterLabel = new JLabel("Revealed Cells: 0", SwingConstants.CENTER);
        frame.add(revealedCounterLabel, BorderLayout.SOUTH);

        // Initialize board
        initializeBoard();

        frame.pack();
        frame.setVisible(true);
    }
    

    private void initializeBoard() {
        // Create buttons and cells
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                board[row][col] = new Cell();
                int r = row, c = col;

          board[row][col].addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mousePressed(java.awt.event.MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            board[r][c].toggleFlag(); 
        } else if (SwingUtilities.isLeftMouseButton(e)) {
            revealCell(r, c);        
        }
    }
});
                boardPanel.add(board[row][col]);
            }
        }

        // Place mines randomly
        placeMines();

        // Calculate hint numbers
        calculateHints();
    }

    private void placeMines() {
        Random rand = new Random();
        int placedMines = 0;

        while (placedMines < MINES) {
            int r = rand.nextInt(ROWS);
            int c = rand.nextInt(COLS);

            if (!board[r][c].isMine()) {
                board[r][c].setMine();
                placedMines++;
            }
        }
    }

    private void calculateHints() {
        int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col].isMine()) continue;

                int mineCount = 0;
                for (int i = 0; i < 8; i++) {
                    int nr = row + dr[i], nc = col + dc[i];
                    if (isValid(nr, nc) && board[nr][nc].isMine()) {
                        mineCount++;
                    }
                }
                board[row][col].setHint(mineCount);
            }
        }
    }

    private void revealCell(int row, int col) {
        if (!isValid(row, col) || board[row][col].isRevealed()) return;

        board[row][col].revealCell();

        if (board[row][col].isMine()) {
            JOptionPane.showMessageDialog(frame, "Game Over! You hit a mine.");
            revealAllMines();
            return;
        }

        revealedCells++;
        revealedCounterLabel.setText("Revealed Cells: " + revealedCells);

        if (board[row][col].getHint() == 0) {
            // Reveal all adjacent cells recursively
            int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};
            for (int i = 0; i < 8; i++) {
                revealCell(row + dr[i], col + dc[i]);
            }
        }
    }

    private void revealAllMines() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c].isMine()) {
                    board[r][c].revealCell();
                }
            }
        }
    }

    private boolean isValid(int row, int col) {
        return row >= 0 && row < ROWS && col >= 0 && col < COLS;
    }
    

}
