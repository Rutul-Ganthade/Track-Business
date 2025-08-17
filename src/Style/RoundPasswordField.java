package Style;

import javax.swing.*;
import java.awt.*;

public class RoundPasswordField extends JPasswordField {
    private final String hint;
    private final Insets padding = new Insets(8, 12, 8, 12);  // top, left, bottom, right

    public RoundPasswordField(String hint) {
        this.hint = hint;
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(padding.top, padding.left, padding.bottom, padding.right));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBackground(Color.WHITE);
    }

    @Override
    public Insets getInsets() {
        return padding;
    }

    @Override
    protected void paintComponent(Graphics g) {
        int arc = 20;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(getBackground());
        g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, arc, arc);

        super.paintComponent(g);

        // Hint text
        if (getPassword().length == 0 && !isFocusOwner()) {
            g2.setColor(new Color(160, 160, 160));
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            FontMetrics fm = g2.getFontMetrics();
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            g2.drawString(hint, padding.left, y);  // align with left inset
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        int arc = 20;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(0, 153, 255)); // Border color
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);
        g2.dispose();
    }
}
