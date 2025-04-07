import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class MinesweeperGUI {
    private JFrame frame;
    private JPanel boardPanel;
    private JLabel revealedCounterLabel;
    private JLabel flagCounterLabel;  // Flag counter label
    private int revealedCells;
    private int flagCount;  // To keep track of remaining flags
    private final int ROWS = 16, COLS = 16, MINES = 40;
    private Cell[][] board;
    private boolean gameOver;

    public MinesweeperGUI() {
        board = new Cell[ROWS][COLS];
        revealedCells = 0;
        flagCount = MINES;

        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create top panel with counter and return to menu button
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Flag counter
        flagCounterLabel = new JLabel("Flags Left: " + flagCount, SwingConstants.CENTER);
        topPanel.add(flagCounterLabel, BorderLayout.WEST);

        // Cells revealed counter
        revealedCounterLabel = new JLabel("Revealed Cells: 0", SwingConstants.CENTER);
        topPanel.add(revealedCounterLabel, BorderLayout.CENTER);

        // Return to menu button
        JButton returnToMenuButton = new JButton("Return to Menu");
        returnToMenuButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(frame, "Return to main menu?", "", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                frame.dispose();  // closes the game window
                new miniGames();
            }
        });
        topPanel.add(returnToMenuButton, BorderLayout.EAST);

        frame.add(topPanel, BorderLayout.NORTH);

        // Create board panel
        boardPanel = new JPanel(new GridLayout(ROWS, COLS));
        frame.add(boardPanel, BorderLayout.CENTER);

        // Initialize board
        initializeBoard();

        // Create play again button (initially hidden)
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.addActionListener(e -> resetGame());  // Directly reset the game

        // Bottom panel for play again button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(playAgainButton, BorderLayout.CENTER);
        bottomPanel.setVisible(false);  // Initially hidden

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Maximize the window
        frame.setUndecorated(true);  // Remove window decorations (optional)
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
                            updateFlagCount();
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
            gameOver = true;
            JOptionPane.showMessageDialog(frame, "Game Over! You hit a mine.");
            revealAllMines();
            showPlayAgainButton();
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

    private void showPlayAgainButton() {
        // Show the play again button
        JPanel bottomPanel = (JPanel) frame.getContentPane().getComponent(2);  // Getting the bottom panel
        bottomPanel.setVisible(true);
    }

    private void updateFlagCount() {
        int flagsUsed = 0;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (board[r][c].isFlagged()) {
                    flagsUsed++;
                }
            }
        }
        flagCount = MINES - flagsUsed;
        flagCounterLabel.setText("Flags Left: " + flagCount);

        if (flagCount == 0) {
            // Disable flagging once flags run out
            for (int r = 0; r < ROWS; r++) {
                for (int c = 0; c < COLS; c++) {
                    if (!board[r][c].isRevealed()) {
                        board[r][c].setEnabled(false);
                    }
                }
            }
        }
    }

    private void resetGame() {
        // Reset game state and UI
        revealedCells = 0;
        flagCount = MINES;
        revealedCounterLabel.setText("Revealed Cells: 0");
        flagCounterLabel.setText("Flags Left: " + flagCount);

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                board[r][c].hideCell();
            }
        }

        placeMines();
        calculateHints();

        // Hide the play again button
        JPanel bottomPanel = (JPanel) frame.getContentPane().getComponent(2);
        bottomPanel.setVisible(false);
    }
    
}

