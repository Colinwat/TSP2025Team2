import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.io.InputStream;

public class RPS extends JFrame {

    private String[] choices = {"Rock", "Paper", "Scissors"};
    private Random rand = new Random();
    private JLabel resultLabel;

    private int wins = 0;
    private int losses = 0;
    private int ties = 0;

    private JLabel winLabel;
    private JLabel lossLabel;
    private JLabel tieLabel;

    public RPS() {
        setTitle("Rock Paper Scissors");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLACK);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Rock - Paper - Scissors", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.YELLOW);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(titleLabel);

        // Record Panel (Wins, Losses, Ties)
        JPanel recordPanel = new JPanel();
        recordPanel.setBackground(Color.BLACK);
        recordPanel.setLayout(new GridLayout(1, 3));

        winLabel = new JLabel("Wins: 0", SwingConstants.CENTER);
        winLabel.setForeground(Color.GREEN);
        lossLabel = new JLabel("Losses: 0", SwingConstants.CENTER);
        lossLabel.setForeground(Color.RED);
        tieLabel = new JLabel("Ties: 0", SwingConstants.CENTER);
        tieLabel.setForeground(Color.LIGHT_GRAY);

        Font scoreFont = new Font("Arial", Font.BOLD, 14);
        winLabel.setFont(scoreFont);
        lossLabel.setFont(scoreFont);
        tieLabel.setFont(scoreFont);

        recordPanel.add(winLabel);
        recordPanel.add(lossLabel);
        recordPanel.add(tieLabel);

        titlePanel.add(recordPanel);
        add(titlePanel, BorderLayout.NORTH);

        // Middle Panel for images and result text
        JPanel middlePanel = new JPanel();
        middlePanel.setBackground(Color.BLACK);
        middlePanel.setLayout(new BorderLayout());

        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
        resultLabel.setForeground(Color.CYAN);

        middlePanel.add(resultLabel, BorderLayout.SOUTH);
        add(middlePanel, BorderLayout.CENTER);

        // Bottom Panel with "Choose your move:" and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.BLACK);
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        JLabel chooseMoveLabel = new JLabel("Choose your move:", SwingConstants.CENTER);
        chooseMoveLabel.setFont(new Font("Arial", Font.BOLD, 22));
        chooseMoveLabel.setForeground(Color.YELLOW);
        bottomPanel.add(chooseMoveLabel);

        // Button panel with Rock, Paper, Scissors buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.BLACK);
        JButton rockBtn = createButton("Rock");
        JButton paperBtn = createButton("Paper");
        JButton scissorsBtn = createButton("Scissors");

        buttonPanel.add(rockBtn);
        buttonPanel.add(paperBtn);
        buttonPanel.add(scissorsBtn);
        bottomPanel.add(buttonPanel);

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.setPreferredSize(new Dimension(100, 40));
        quitButton.setFont(new Font("Arial", Font.BOLD, 16));
        quitButton.setForeground(Color.BLACK);
        quitButton.setBackground(Color.RED);
        quitButton.addActionListener(e -> {
            dispose();
            miniGames.showMenu();
        });
        bottomPanel.add(quitButton, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton createButton(String move) {
        JButton button = new JButton(move);
        button.setPreferredSize(new Dimension(100, 40));
        button.setFont(new Font("Ariel", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.DARK_GRAY);
        button.addActionListener(e -> play(move));
        return button;
    }

    private void play(String playerChoice) {
        String computerChoice = choices[rand.nextInt(3)];
        String outcome;

        if (playerChoice.equals(computerChoice)) {
            outcome = "It's a Draw!";
            ties++;
        } else if (
            (playerChoice.equals("Rock") && computerChoice.equals("Scissors")) ||
            (playerChoice.equals("Paper") && computerChoice.equals("Rock")) ||
            (playerChoice.equals("Scissors") && computerChoice.equals("Paper"))
        ) {
            outcome = "You Win!";
            wins++;
        } else {
            outcome = "You Lose!";
            losses++;
        }

        resultLabel.setText("You chose " + playerChoice + ", Computer chose " + computerChoice + ". " + outcome);
        updateScores();
    }

    private void updateScores() {
        winLabel.setText("Wins: " + wins);
        lossLabel.setText("Losses: " + losses);
        tieLabel.setText("Ties: " + ties);
    }
}