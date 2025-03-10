import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
public class miniGames {
    public static void main(String[] args) {
Font font = loadFont("C:/Users/mwood/OneDrive/Desktop/Team Project/ARCADECLASSIC.ttf");
        JFrame frame = new JFrame("Main Menu");
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(10, 10, 10, 10); 
        gbc.anchor = GridBagConstraints.CENTER;  
        
        JButton button1 = new JButton("Chess");
        JButton button2 = new JButton("Rock Paper Scissors");
        JButton button3 = new JButton("MineSweeper");
        JButton button4 = new JButton("Tic Tac Toe");
        JButton button5 = new JButton("Close");
        Dimension buttonSize = new Dimension(300, 50); 
        button1.setPreferredSize(buttonSize);
        button2.setPreferredSize(buttonSize);
        button3.setPreferredSize(buttonSize);
        button4.setPreferredSize(buttonSize);
        button5.setPreferredSize(buttonSize);
        button1.setFont(font.deriveFont(24f));
        button2.setFont(font.deriveFont(24f));
        button3.setFont(font.deriveFont(24f)); 
        button4.setFont(font.deriveFont(24f)); 
        button5.setFont(font.deriveFont(24f));
      
       JLabel choose = new JLabel("CHOOSE GAME", SwingConstants.CENTER);
       choose.setFont(font.deriveFont(30f));
        choose.setForeground(Color.YELLOW); 
        gbc.gridy = 0; 
        frame.add(choose, gbc);
        button1.setForeground(Color.CYAN);
        button2.setForeground(Color.CYAN);
        button3.setForeground(Color.CYAN);
        button4.setForeground(Color.CYAN);
        button5.setForeground(Color.RED);
        button1.setContentAreaFilled(false);
        button2.setContentAreaFilled(false);
        button3.setContentAreaFilled(false);
        button4.setContentAreaFilled(false);
        button5.setContentAreaFilled(false);
        Border roundedBorder = new RoundedBorder(30);
        button1.setBorder(roundedBorder);
        button2.setBorder(roundedBorder);
        button3.setBorder(roundedBorder);
        button4.setBorder(roundedBorder);
        button5.setBorder(roundedBorder);
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                open("Chess");
            }
        });
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 open("Rock Paper Scissors");
            }
        });
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 open("MineSweeper");
            }
        });
        button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                open("Tic Tac Toe");
            }
        });
        button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        gbc.gridy = 1;
        frame.add(button1, gbc);
        gbc.gridy = 2;
        frame.add(button2, gbc);
        gbc.gridy = 3;
        frame.add(button3, gbc);
        gbc.gridy = 4;
        frame.add(button4, gbc);
        gbc.gridy = 5;
        frame.add(button5, gbc);
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);   
    }

      private static Font loadFont(String fontPath) {
        try {
            // Load the font from the file
            File fontFile = new File(fontPath);
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            return font.deriveFont(24f); // Return the font with a default size
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            // If the font fails to load, fall back to the default font
            return new Font("Courier", Font.PLAIN, 24);
        }
    }
    private static void open(String game){
    JFrame gameFrame = new JFrame(game);
    gameFrame.setSize(400, 300);
    gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    JLabel label = new JLabel(game + " placeholder", SwingConstants.CENTER);
    label.setFont(new Font("Arial", Font.PLAIN, 20));
    gameFrame.add(label);
    gameFrame.setLocationRelativeTo(null);
    gameFrame.setVisible(true);
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

}