import java.awt.*;
import javax.swing.*;

public class Cell extends JButton {
    private int hint;
    private boolean mine;
    private boolean flag;
    private boolean revealed;

    public Cell() {
        hint = 0;
        mine = false;
        flag = false;
        revealed = false;

        setFont(new Font("Arial", Font.BOLD, 14));
        setFocusPainted(false);
        setBackground(Color.LIGHT_GRAY);
    }

    public boolean isMine() {
        return mine;
    }

     public void setFlagged(boolean flag) {
        this.flag = flag;
    }
 public void toggleFlag() {
        if (isRevealed()) return;

        flag = !flag;
        setText(flag ? "F" : "");
    }

    public void setMine() {
        mine = true;
    }

    public int getHint() {
        return hint;
    }

    public void setHint(int hint) {  // ✅ FIX: Added this method
        this.hint = hint;
    }

    public boolean isFlagged() {
        return flag;
    }

    public void swapFlag() {
        if (!revealed) {
            flag = !flag;
            setText(flag ? "F" : "");
        }
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void revealCell() {
        if (!revealed) {
            revealed = true;
            setEnabled(false);
            setBackground(Color.WHITE);

            if (mine) {
                setText("💣");
                setForeground(Color.RED);
            } else if (hint > 0) {
                setText(String.valueOf(hint));
                setForeground(Color.BLUE);
            }
        }
    }

    public void hideCell() {
        revealed = false;
        flag = false;
        setText("");
        setEnabled(true);
        setBackground(Color.LIGHT_GRAY);
    }
}
