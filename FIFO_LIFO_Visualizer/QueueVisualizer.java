package com.FIFO_LIFO_Visualizer;

import javax.swing.*;
import java.awt.*;

public class QueueVisualizer extends JFrame {
    private int queueSize;
    private int front = -1;
    private int rear = -1;
    private String[] queueArray;
    private JTextField inputField;
    private QueuePanel queuePanel;
    private JLabel frontLabel, rearLabel;

    public QueueVisualizer() {
        queueSize = askQueueSize();
        queueArray = new String[queueSize];

        setTitle("Queue Visualizer");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(144, 182, 221));

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(37, 239, 239));
        JLabel headerLabel = new JLabel("FIFO - First In First Out");
        headerLabel.setFont(new Font("Serif", Font.BOLD, 30));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Queue Panel
        queuePanel = new QueuePanel();
        queuePanel.setLayout(null);
        add(queuePanel, BorderLayout.CENTER);

        // create FRONT label to show Front element


        frontLabel = new JLabel("FRONT", SwingConstants.CENTER);
        rearLabel = new JLabel("REAR", SwingConstants.CENTER);

        frontLabel.setOpaque(true);
        frontLabel.setBackground(new Color(255, 103, 21));   // red
        frontLabel.setForeground(Color.WHITE);
        frontLabel.setFont(new Font("Arial", Font.BOLD, 14));
        frontLabel.setSize(60, 30);
        frontLabel.setVisible(false);
        queuePanel.add(frontLabel);

        rearLabel.setOpaque(true);
        rearLabel.setBackground(new Color(0, 255, 4));   //green
        rearLabel.setForeground(Color.WHITE);
        rearLabel.setFont(new Font("Arial", Font.BOLD, 14));
        rearLabel.setSize(60, 30);
        rearLabel.setVisible(false);
        queuePanel.add(rearLabel);

        // Control Panel
        JPanel controlPanel = new JPanel();
        controlPanel.setBackground(new Color(158, 255, 255));
        controlPanel.setLayout(new FlowLayout());

        JLabel inputLabel = new JLabel("Enter Number:");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputField = new JTextField(8);
        inputField.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton enqueueBtn = new JButton("Enqueue");
        JButton dequeueBtn = new JButton("Dequeue");
        JButton clearBtn = new JButton("Clear");

        styleButton(enqueueBtn, new Color(103, 11, 189));
        styleButton(dequeueBtn, new Color(255, 99, 71));
        styleButton(clearBtn, new Color(255, 0, 175));

        controlPanel.add(inputLabel);
        controlPanel.add(inputField);
        controlPanel.add(enqueueBtn);
        controlPanel.add(dequeueBtn);
        controlPanel.add(clearBtn);
        add(controlPanel, BorderLayout.SOUTH);

        // Button actions
        enqueueBtn.addActionListener(e -> enqueueAction());
        dequeueBtn.addActionListener(e -> dequeueAction());
        clearBtn.addActionListener(e -> clearAction());
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
    }

    private int askQueueSize() {
        int size = 0;
        while (size < 1 || size > 10) {
            String input = JOptionPane.showInputDialog(this, "Enter queue size (1-10):", "Queue Size", JOptionPane.QUESTION_MESSAGE);
            if (input == null) System.exit(0);
            try {
                size = Integer.parseInt(input);
                if (size < 1 || size > 10) JOptionPane.showMessageDialog(this, "Size must be between 1 and 10.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            }
        }
        return size;
    }

    private void enqueueAction() {
        if (rear >= queueSize - 1) {
            JOptionPane.showMessageDialog(this, "Queue Overflow!");
            return;
        }
        String value = inputField.getText().trim();
        if (value.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number.");
            return;
        }
        inputField.setText("");

        if (front == -1 && rear == -1) {
            front = 0;
            rear = 0;
        } else {
            rear++;
        }

        JLabel animLabel = createAnimLabel(value);
        int targetX = 50 + rear * 70;
        int y = 150;
        animLabel.setLocation(700, y); // start from right outside frame
        queuePanel.add(animLabel);
        queuePanel.setComponentZOrder(animLabel, 0);

        Timer timer = new Timer(5, null);
        timer.addActionListener(e -> {
            Point p = animLabel.getLocation();
            if (p.x > targetX) {
                animLabel.setLocation(p.x - 10, y); // move left
            } else {
                ((Timer) e.getSource()).stop();
                queueArray[rear] = value;
                queuePanel.remove(animLabel);
                queuePanel.repaint();
                updateLabels();
            }
        });
        timer.start();
    }

    private void dequeueAction() {
        if (front == -1 && rear == -1) {
            JOptionPane.showMessageDialog(this, "Queue Underflow!");
            return;
        }

        String value = queueArray[front];
        if (value == null) return;

        JLabel animLabel = createAnimLabel(value);
        int x = 50 + front * 70;
        int y = 150;
        animLabel.setLocation(x, y);
        queuePanel.add(animLabel);
        queuePanel.setComponentZOrder(animLabel, 0);

        Timer timer = new Timer(5, null);
        timer.addActionListener(e -> {
            Point p = animLabel.getLocation();
            if (p.x > -100) {
                animLabel.setLocation(p.x - 10, y); // move left
            } else {
                ((Timer) e.getSource()).stop();
                queueArray[front] = null;
                if (front == rear) {
                    front = -1;
                    rear = -1;
                } else {
                    front++;
                }
                queuePanel.remove(animLabel);
                queuePanel.repaint();
                updateLabels();
            }
        });
        timer.start();
    }

    private void clearAction() {
        for (int i = 0; i < queueSize; i++) queueArray[i] = null;
        front = -1;
        rear = -1;
        queuePanel.repaint();
        updateLabels();
    }

    private JLabel createAnimLabel(String value) {
        JLabel label = new JLabel(value, SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(255, 229, 38));
        label.setFont(new Font("Arial", Font.BOLD, 18));
        label.setSize(60, 40);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return label;
    }

    private void updateLabels() {
        if (front == -1) {
            frontLabel.setVisible(false);
            rearLabel.setVisible(false);
            return;
        }
        int startX = 50;
        int y = 150;
        int width = 60;
        int height = 40;

        frontLabel.setLocation(startX + (front * (width + 10)), y - 35);
        frontLabel.setVisible(true);

        rearLabel.setLocation(startX + (rear * (width + 10)), y + height + 5);
        rearLabel.setVisible(true);
    }

    private class QueuePanel extends JPanel {
        QueuePanel() {
            setBackground(new Color(240, 255, 255));
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int startX = 50;
            int y = 150;
            int width = 60, height = 40;

            for (int i = 0; i < queueSize; i++) {
                int x = startX + i * (width + 10);
                if (i >= front && i <= rear && front != -1) g.setColor(new Color(255, 229, 38));
                else g.setColor(new Color(159, 255, 240));

                g.fillRoundRect(x, y, width, height, 10, 10);
                g.setColor(Color.BLACK);
                g.drawRoundRect(x, y, width, height, 10, 10);

                if (queueArray[i] != null) {
                    g.setFont(new Font("Arial", Font.BOLD, 16));
                    g.drawString(queueArray[i], x + 20 - queueArray[i].length() * 3, y + 25);
                }

                g.setFont(new Font("Arial", Font.PLAIN, 14));
                g.drawString(String.valueOf(i), x + 25 - String.valueOf(i).length() * 2, y + 60);
            }
        }
    }
}

