package Style;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundButton extends JButton {
    private Shape shape;

    public RoundButton(String label) {
        super(label);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setFont(new Font("Segoe UI Semibold", Font.BOLD, 15));
        setBackground(new Color(0, 200, 180));
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
    }

    @Override
    protected void paintComponent(Graphics g) {
        int arc = getHeight();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background with round shape
        g2.setColor(getBackground());
        g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, arc, arc);

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        int arc = getHeight();
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground().darker());
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(1, 1, getWidth() - 2, getHeight() - 2, getHeight(), getHeight());
        }
        return shape.contains(x, y);
    }
}
