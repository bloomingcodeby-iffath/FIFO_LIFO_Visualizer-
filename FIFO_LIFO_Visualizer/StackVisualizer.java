package com.FIFO_LIFO_Visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StackVisualizer extends JFrame {
    private int stackSize;
    private int top = -1;
    private ArrayList<String> stack;
    private JTextField inputField;
    private StackPanel stackPanel;//StackPanel is Created in line:225
    private JLabel topLabel;

    public StackVisualizer() {
        //Stack Size Asking:

        stackSize = askStackSize(); //askStackSize() method in 94 number line
        stack = new ArrayList<>(stackSize);

        // Frame setup
        setTitle("Stack Visualizer");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(144, 182, 221));

       //Header Panel:
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(37, 239, 239));
        JLabel headerLabel = new JLabel("LIFO- Last In First Out");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 30));
        headerLabel.setForeground(new Color(0, 0, 0, 255));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Stack drawing panel:
        stackPanel = new StackPanel();
        stackPanel.setLayout(null);
        add(stackPanel, BorderLayout.CENTER);

        //Showing Top Value: creating Top Label:
        topLabel = new JLabel("TOP", SwingConstants.CENTER);
        topLabel.setOpaque(true);
        topLabel.setBackground(new Color(255, 69, 0)); // orange-red
        topLabel.setForeground(Color.WHITE);
        topLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topLabel.setSize(50, 30);
        topLabel.setVisible(false);
        stackPanel.add(topLabel);

        // Custom Control Panel:
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(158, 255, 255));
        controlPanel.setLayout(new FlowLayout());
        //Label for Entering number:
        JLabel inputLabel = new JLabel("Enter Number:");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 14));
        //Input Field:
        inputField = new JTextField(8);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));

        //Buttons Add to the Control panel:
        JButton pushBtn = new JButton("Push");
        JButton popBtn = new JButton("Pop");
        JButton clearBtn = new JButton("Clear");

        //Button's Custom Style, StyleButton Method line:88
        styleButton(pushBtn, new Color(68, 28, 223));
        styleButton(popBtn, new Color(255, 99, 71));
        styleButton(clearBtn, new Color(218, 112, 214));

        //Adding all of these to the Control Panel:
        controlPanel.add(inputLabel);
        controlPanel.add(inputField);
        controlPanel.add(pushBtn);
        controlPanel.add(popBtn);
        controlPanel.add(clearBtn);
        add(controlPanel, BorderLayout.SOUTH);

        // Button Actions
        pushBtn.addActionListener(e->pushAction());//line 118
        popBtn.addActionListener(e -> popAction());//line 132
        clearBtn.addActionListener(e -> clearAction());//line 140
    }
    //Custom Style for Buttons, called in line:70
    private void styleButton(JButton btn, Color bg) {
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
    }
     //Message Dialogue with Asking size( Called in line: 18)
     private int askStackSize() {
        int size = 0;
        while (size < 1 || size > 10) {
            String input = JOptionPane.showInputDialog(
                    this,
                    "Enter stack size (1-10):",
                    "Stack Size",
                    JOptionPane.QUESTION_MESSAGE
            );
            if (input == null) System.exit(0);
            try {
                size = Integer.parseInt(input);
                if (size < 1 || size > 10) {
                    JOptionPane.showMessageDialog(this, "Size must be between 1 and 10.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        }
        return size;
    }

    //Action Listener For pushButton
    private void pushAction() {
        if (top >= stackSize - 1) {
            JOptionPane.showMessageDialog(this, "Stack Overflow!");
            return;
        }
        String value = inputField.getText().trim();
        if (value.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number.");
            return;
        }
        inputField.setText("");
        animatePush(value);
    }
    //Action Listener For popButton
    private void popAction() {
        if (top < 0) {
            JOptionPane.showMessageDialog(this, "Stack Underflow!");
            return;
        }
        String removed = stack.get(top);
        animatePop(removed);
    }
    //Action Listener For clearButton
    private void clearAction() {
        stack.clear();
        top = -1;
        stackPanel.repaint();
        updateTopLabel();
    }

    //Top label Update:
    private void updateTopLabel() {
        if (top < 0) {
            topLabel.setVisible(false);
            return;
        }
        int y = stackPanel.getHeight() - (top + 1) * 60 - 10;
        int x = 150 + 200 - topLabel.getWidth() - 10; // বক্সের ভিতরে ডান পাশে
        topLabel.setLocation(x, y + 10);  // বক্সের মধ্যে মাঝামাঝি
        topLabel.setVisible(true);
        stackPanel.repaint();
    }

    // Push Animation
    private void animatePush(String value) {
        JLabel animLabel = createAnimLabel(value); //createAnimLabel() method in line: 215
        stackPanel.add(animLabel);
        stackPanel.setComponentZOrder(animLabel, 0);

        int targetY = stackPanel.getHeight() - (top + 2) * 60 - 10;
        animLabel.setLocation(180, -50);

        Timer timer = new Timer(10, null);
        timer.addActionListener(e -> {
            Point p = animLabel.getLocation();
            if (p.y < targetY) {
                animLabel.setLocation(p.x, p.y + 5);
            } else {
                timer.stop();
                stackPanel.remove(animLabel);

                top++;
                stack.add(value);
                stackPanel.repaint();
                updateTopLabel();
            }
        });
        timer.start();
    }

    // Pop Animation
    private void animatePop(String value) {
        JLabel animLabel = createAnimLabel(value);
        int startY = stackPanel.getHeight() - (top + 1) * 60 - 10;

        animLabel.setLocation(180, startY);
        stackPanel.add(animLabel);
        stackPanel.setComponentZOrder(animLabel, 0);

        Timer timer = new Timer(10, null);
        timer.addActionListener(e -> {
            Point p = animLabel.getLocation();
            if (p.y > -50) {
                animLabel.setLocation(p.x, p.y - 5);
            } else {
                timer.stop();
                stackPanel.remove(animLabel);

                stack.remove(top);
                top--;
                stackPanel.repaint();
                updateTopLabel();
            }
        });
        timer.start();
    }

    //called in line:163,190
    private JLabel createAnimLabel(String value) {
        JLabel label = new JLabel(value, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(255, 229, 38));
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setSize(80, 40);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return label;
    }

    //Declare in line:12
    private class StackPanel extends JPanel {
        StackPanel() {
            setBackground(new Color(240, 255, 255));
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            for (int i = 0; i < stackSize; i++) {
                int y = getHeight() - (i + 1) * 60 - 10;

                // Different Background For Top
                if (i == top) {
                    g.setColor(new Color(255, 229, 38));  // light yellow
                } else {
                    g.setColor(new Color(159, 255, 240));
                }
                g.fillRoundRect(150, y, 200, 50, 15, 15);

                g.setColor(Color.BLACK);
                g.drawRoundRect(150, y, 200, 50, 15, 15);

                if (i <= top) {
                    String value = stack.get(i);
                    g.setFont(new Font("Arial", Font.BOLD, 18));
                    g.setColor(Color.BLACK);
                    g.drawString(value, 240 - value.length() * 4, y + 30);
                }

                // Index circle
                int circleX = 365;
                int circleY = y + 15;
                int circleSize = 20;

                if (i % 2 == 0) {
                    g.setColor(new Color(167, 236, 218));
                } else {
                    g.setColor(new Color(255, 0, 242));
                }
                g.fillOval(circleX, circleY, circleSize, circleSize);

                g.setColor(Color.BLACK);
                g.drawOval(circleX, circleY, circleSize, circleSize);

                g.setFont(new Font("Arial", Font.BOLD, 12));
                FontMetrics fm = g.getFontMetrics();
                String indexText = String.valueOf(i);
                int textX = circleX + (circleSize - fm.stringWidth(indexText)) / 2;
                int textY = circleY + ((circleSize - fm.getHeight()) / 2) + fm.getAscent();
                g.drawString(indexText, textX, textY);
            }
        }
    }
}

