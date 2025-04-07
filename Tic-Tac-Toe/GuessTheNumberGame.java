import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

 
public class GuessTheNumberGame extends JFrame {
    // Variables for each GUI part
    private JLabel instructionLabel;
    private JTextField guessField;
    private JButton guessButton;
    private JLabel resultLabel;
    private JLabel streakLabel;
    private JButton retryButton;

    // Game Variables
    private int currentMax = 3; // Starting range: 1-3 (Difficulty level increases with each win)
    private int randomNumber;
    private int winStreak = 0;
    private Random random;

    public GuessTheNumberGame() {
        super("Guess the Number Game");
        random = new Random();
        initComponents();
        startNewRound();
    }

    private void initComponents() {
        // Set up panel and layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 5, 5));

        instructionLabel = new JLabel();
        updateInstructionLabel();

        guessField = new JTextField();
        guessButton = new JButton("Guess");

        resultLabel = new JLabel("Enter your guess and press 'Guess'.");
        streakLabel = new JLabel("Win Streak: " + winStreak);

        retryButton = new JButton("Retry");
        retryButton.setEnabled(false); // Start retry disabled till game over

        // Enters the guess when the button is pressed
        guessButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });

        // Enters guess when enter key is pressed
        guessField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                processGuess();
            }
        });

        // Retry button resets the game when the user loses
        retryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        // Add buttons and GUI to the panel
        panel.add(instructionLabel);
        panel.add(guessField);
        panel.add(guessButton);
        panel.add(resultLabel);
        panel.add(streakLabel);
        panel.add(retryButton);

        // Set up the frame
        add(panel);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centers the window on the screen
    }

    // Tells user what number range to guess from, increases as difficulty increases 
    private void updateInstructionLabel() {
        instructionLabel.setText("Guess a number between 1 and " + currentMax + ":");
    }

    // Read the user's guess and check if it's correct
    private void processGuess() {
        String input = guessField.getText().trim();
        try {
            int guess = Integer.parseInt(input);
            if (guess < 1 || guess > currentMax) {
                resultLabel.setText("Please enter a number between 1 and " + currentMax + ".");
                return;
            }
            if (guess == randomNumber) {
                winStreak++;
                resultLabel.setText("Correct! You won this round.");
                streakLabel.setText("Win Streak: " + winStreak);
                // Increase the range for the next round
                currentMax++;
                startNewRound();
            } else {
                resultLabel.setText("Wrong guess. The correct number was " + randomNumber + ". Game over!");
                winStreak = 0;
                streakLabel.setText("Win Streak: " + winStreak);
                // Disable input and enable the retry button
                guessButton.setEnabled(false);
                guessField.setEnabled(false);
                retryButton.setEnabled(true);
            }
        } catch (NumberFormatException ex) {
            resultLabel.setText("Invalid input. Please enter a valid number.");
        }
        guessField.setText("");
    }

    // Set up a new round with a new random number
    private void startNewRound() {
        randomNumber = random.nextInt(currentMax) + 1; // Generates a number from 1 to currentMax 
        updateInstructionLabel();
    }

    // Reset the game to its initial state
    private void resetGame() {
        currentMax = 3;
        winStreak = 0;
        streakLabel.setText("Win Streak: " + winStreak);
        resultLabel.setText("Enter your guess and press 'Guess'.");
        guessButton.setEnabled(true);
        guessField.setEnabled(true);
        retryButton.setEnabled(false);
        startNewRound();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GuessTheNumberGame().setVisible(true);
        });
    }
}
