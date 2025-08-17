package Style;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;

public class RoundedComboBox extends JComboBox<String> {
    public RoundedComboBox(String[] items) {
        super(items);
        setUI(new CustomComboBoxUI());
        setOpaque(false);
        setFocusable(false);
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
    }

    static class CustomComboBoxUI extends BasicComboBoxUI {
        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(comboBox.getBackground());
            g2.fillRoundRect(1, 1, comboBox.getWidth() - 2, comboBox.getHeight() - 2, 20, 20);
        }

        @Override
        protected JButton createArrowButton() {
            JButton arrow = new JButton("\u25BC");
            arrow.setBorderPainted(false);
            arrow.setContentAreaFilled(false);
            arrow.setFocusPainted(false);
            arrow.setForeground(Color.DARK_GRAY);
            arrow.setFont(new Font("Segoe UI", Font.BOLD, 12));
            return arrow;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            super.paint(g, c);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(0, 153, 153));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(1, 1, comboBox.getWidth() - 3, comboBox.getHeight() - 3, 20, 20);
            g2.dispose();
        }
    }
}

