import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JLabel scoreLabel = new JLabel();
    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameover = false;
    int turns = 0;

int xCounter = 0;
int oCounter = 0;
int ties = 0;

JButton playAgainButton = new JButton("Play Again");
JButton menu = new JButton("Return to Menu");

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.black);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

           // Score Label
        scoreLabel.setBackground(Color.black);
        scoreLabel.setForeground(Color.white);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setOpaque(true);
        scoreLabel.setText("X Wins: " + xCounter + " | O Wins: " + oCounter + " | Ties: " + ties);


        textPanel.setLayout(new GridLayout(2, 1)); // Adjust layout for 2 labels
        textPanel.add(textLabel);
        textPanel.add(scoreLabel);
        frame.add(textPanel, BorderLayout.NORTH);


        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.black);
        frame.add(boardPanel);


JPanel buttonPanel = new JPanel();
buttonPanel.setBackground(Color.black);
playAgainButton.setFont(new Font("Arial", Font.BOLD, 30));
playAgainButton.setFocusable(false);
playAgainButton.setVisible(false); // Hidden initially
playAgainButton.setBackground(Color.green);
playAgainButton.setForeground(Color.black);
playAgainButton.setBorder(BorderFactory.createLineBorder(Color.darkGray));
playAgainButton.setOpaque(true); // Ensure color appears

playAgainButton.addActionListener(e -> {
    resetGame();
    playAgainButton.setVisible(false); // Hide again after reset
});

menu.setFont(new Font("Arial", Font.BOLD, 30));
menu.setFocusable(false);
menu.setVisible(false); // Hidden initially
menu.setBackground(Color.red);
menu.setForeground(Color.black);
menu.setBorder(BorderFactory.createLineBorder(Color.darkGray));
menu.setOpaque(true); // Ensure color appears

menu.addActionListener(e -> goToMenu());

buttonPanel.add(playAgainButton);
buttonPanel.add(menu);
frame.add(buttonPanel, BorderLayout.SOUTH);
  menu.setVisible(true);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.black);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                int top = (r == 0) ? 0 : 3;
                int left = (c == 0) ? 0 : 3;
                int bottom = (r == 2) ? 0 : 3;
                int right = (c == 2) ? 0 : 3;
                tile.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.white));
                // tile.setText(currentPlayer);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameover)
                            return;
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText() == "") {
                            tile.setText(currentPlayer);
                            animatePopIn(tile);
                            turns++;
                            checkWinner();
                            if (!gameover) {
                                currentPlayer = currentPlayer == playerX ? playerO : playerX;
                                textLabel.setText(currentPlayer + "'s turn.");
                            }
                        }
                    }
                });
            }
        }

    }

    void checkWinner() {
        // horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "")
                continue;

            if (board[r][0].getText() == board[r][1].getText() &&
                    board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                updateWinnerCount(currentPlayer);
                return;
            }
        }

        // vertical
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "")
                continue;

            if (board[0][c].getText() == board[1][c].getText() &&
                    board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                updateWinnerCount(currentPlayer);
                return;
            }
        }

        // diagnol
        if (board[0][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][2].getText() &&
                board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            updateWinnerCount(currentPlayer);
            return;
        }
        // other diagnol
        if (board[0][2].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][0].getText() &&
                board[0][2].getText() != "") {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            updateWinnerCount(currentPlayer);
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }

            }
            ties++;
            updateScoreLabel();
            gameover = true;
            return;
        }
    }
    

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " is the winner!");
    playAgainButton.setVisible(true);
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.black);
        textLabel.setText("It's a Tie!");
       
    playAgainButton.setVisible(true);
    }
    void resetGame() {
    for (int r = 0; r < 3; r++) {
        for (int c = 0; c < 3; c++) {
            board[r][c].setText(""); // Clear text
            board[r][c].setBackground(Color.black);
            board[r][c].setForeground(Color.white);
        }
    }
    currentPlayer = playerX;
    gameover = false;
    turns = 0;
    textLabel.setText("Tic-Tac-Toe");
}
 void updateWinnerCount(String winner) {
        if (winner.equals(playerX)) xCounter++;
        else if (winner.equals(playerO)) oCounter++;
        updateScoreLabel();
        gameover = true;
    }

void updateScoreLabel() {
        scoreLabel.setText("X Wins: " + xCounter + " | O Wins: " + oCounter + " | Ties: " + ties);
    }


void goToMenu() {
    frame.dispose(); 
    new miniGames();
}
void animatePopIn(JButton tile) {
        Timer timer = new Timer(1, new ActionListener() {
            int size = 10;
            public void actionPerformed(ActionEvent e) {
                size += 10;
                tile.setFont(new Font("Arial", Font.BOLD, size));
                if (size >= 120) ((Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }
}
