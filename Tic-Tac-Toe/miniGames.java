import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
public class miniGames {
    static JFrame frame;

    public static Font loadFont(String fontPath) throws Exception {
    InputStream is = miniGames.class.getResourceAsStream(fontPath);
    Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(24f);
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    ge.registerFont(font);
    return font;
}
    public static void main(String[] args) throws Exception {
        showMenu();
    }

     
    private static void open(String game){
        switch (game) {
            case "Tic Tac Toe":
                new TicTacToe();
                frame.dispose();
                break;
            case "Guess the Number":
                new GuessTheNumberGame().setVisible(true);;
                frame.dispose();
                break;
            case "Rock Paper Scissors":
                new RPS();
                frame.dispose();
                break;
            case "MineSweeper":
                new MinesweeperGUI();
                frame.dispose();
                break;
            default:
                System.out.println("Game not implemented yet!");
        }
    }
    static class RoundedBorder implements Border {
        private int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.GRAY);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(4));
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
    // RoundedShadowPane class for custom rounded corners with shadow
    static class RoundedShadowPane extends JPanel {
        private double cornerRadius = 20;
        private int shadowSize = 5;
        private Color shadowColor = Color.BLACK;
        private float shadowAlpha = 0.25f;
        private JComponent contentPane;
        public RoundedShadowPane() {
            setOpaque(false);
            int shadowSize = getShadowSize();
            int cornerRadius = (int) getCornerRadius() / 4;
            setBorder(new EmptyBorder(
                    shadowSize + cornerRadius,
                    shadowSize + cornerRadius,
                    shadowSize + cornerRadius + shadowSize,
                    shadowSize + cornerRadius + shadowSize
            ));
            setLayout(new BorderLayout());
            add(getContentPane());
        }

        public JComponent getContentPane() {
            if (contentPane == null) {
                contentPane = new JPanel();
            }
            return contentPane;
        }

        public void setContentPane(JComponent contentPane) {
            if (this.contentPane != null) {
                remove(this.contentPane);
            }
            this.contentPane = contentPane;
            this.setBackground(this.contentPane.getBackground());
            add(this.contentPane);
        }

        public double getCornerRadius() {
            return cornerRadius;
        }

        public int getShadowSize() {
            return shadowSize;
        }

        public Color getShadowColor() {
            return shadowColor;
        }

        public float getShadowAlpha() {
            return shadowAlpha;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.MAGENTA);
            g2d.fillRect(0, 0, getWidth(), getHeight());

            double cornerRadius = getCornerRadius();
            double cornerInsets = cornerRadius / 4d;

            int width = getWidth();
            int height = getHeight();
            Insets insets = getInsets();
            insets.left -= cornerInsets;
            insets.right -= cornerInsets;
            insets.top -= cornerInsets;
            insets.bottom -= cornerInsets;

            width -= insets.left + insets.right;
            height -= insets.top + insets.bottom;

            RoundRectangle2D border = new RoundRectangle2D.Double(0, 0, width, height, cornerRadius, cornerRadius);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getShadowColor());
            g2d.fill(border);

            g2d.setColor(Color.BLACK);
            g2d.translate(insets.left, insets.top);
            g2d.draw(border);
            g2d.dispose();
        }
    }

    public static void showMenu() {
        frame = new JFrame("Main Menu");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.anchor = GridBagConstraints.CENTER;  
    
        try {
            Font font = loadFont("/ARCADECLASSIC.ttf");
    
            JLabel choose = new JLabel("CHOOSE GAME", SwingConstants.CENTER);
            choose.setFont(font.deriveFont(30f));
            choose.setForeground(Color.YELLOW); 
            gbc.gridy = 0; 
            frame.add(choose, gbc);
    
            String[] gameNames = {"Guess the Number", "Rock Paper Scissors", "MineSweeper", "Tic Tac Toe", "Close"};
            Color[] colors = {Color.CYAN, Color.CYAN, Color.CYAN, Color.CYAN, Color.RED};
    
            for (int i = 0; i < gameNames.length; i++) {
                JButton button = new JButton(gameNames[i]);
                button.setPreferredSize(new Dimension(300, 50));
                button.setFont(font.deriveFont(24f));
                button.setForeground(colors[i]);
                button.setContentAreaFilled(false);
                button.setBorder(new RoundedBorder(30));
    
                final String game = gameNames[i];
                button.addActionListener(e -> {
                    if (game.equals("Close")) {
                        System.exit(0);
                    } else {
                        open(game);
                    }
                });
    
                gbc.gridy = i + 1;
                frame.add(button, gbc);
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}