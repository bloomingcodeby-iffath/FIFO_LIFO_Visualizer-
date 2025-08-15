package com.FIFO_LIFO_Visualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeFrame extends JFrame {
    private Container c;
    private JLabel welcomeLabel, questionLabel,imglv,imglv2;
    private Font f1,f2;
    private ImageIcon img,img2;

    WelcomeFrame() {
        initComponents();
    }

    private void initComponents() {
        c = getContentPane();
        c.setBackground(new Color(37, 239, 239));
        c.setForeground(Color.WHITE);
        c.setLayout(new BorderLayout()); //Layout set

        // Font for welcome
        f1 = new Font("Serif", Font.BOLD, 50);
        f2=new Font("Arial", Font.PLAIN, 26);


        img=new ImageIcon(getClass().getResource("Queue.jpg"));
        Image scaledImage = img.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        img = new ImageIcon(scaledImage);
        imglv=new JLabel(img);
        imglv.setBounds(700,250,img.getIconWidth(),img.getIconHeight());
        c.add(imglv);


        img2=new ImageIcon(getClass().getResource("Stack.jpg"));
        Image scaledImage2 = img2.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        img2 = new ImageIcon(scaledImage2);
        imglv2=new JLabel(img2);
        imglv2.setBounds(70,250,img2.getIconWidth(),img2.getIconHeight());
        c.add(imglv2);


        // Welcome Label
        welcomeLabel = new JLabel("WELCOME to FIFO-LIFO Visualizer!", SwingConstants.CENTER);
        welcomeLabel.setFont(f1);
        welcomeLabel.setForeground(Color.BLACK);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(80, 10, 20, 10)); // spacing
        c.add(welcomeLabel, BorderLayout.NORTH);

        // Center panel for question + buttons
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(200, 255, 255));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Question Label
        questionLabel = new JLabel("Which one do you want to start?", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 26));
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 20, 10));
        centerPanel.add(questionLabel);

        // Buttons
        JButton stackBtn = new JButton("Stack");
        JButton queueBtn = new JButton("Queue");

        stackBtn.setFont(new Font("Arial", Font.BOLD, 20));
        queueBtn.setFont(new Font("Arial", Font.BOLD, 20));
        stackBtn.setForeground(Color.WHITE);
        queueBtn.setForeground(Color.WHITE);

        stackBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        queueBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        stackBtn.setMaximumSize(new Dimension(150, 50));
        queueBtn.setMaximumSize(new Dimension(150, 50));

        stackBtn.setBackground(new Color(255, 0, 183));
        stackBtn.setForeground(Color.BLACK);
        queueBtn.setBackground(new Color(255, 156, 84));
        queueBtn.setForeground(Color.BLACK);

        centerPanel.add(Box.createVerticalStrut(10)); // spacing
        centerPanel.add(stackBtn);
        centerPanel.add(Box.createVerticalStrut(20)); // spacing
        centerPanel.add(queueBtn);

        // Add center panel to container
        c.add(centerPanel, BorderLayout.CENTER);
        stackBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                StackVisualizer s=new StackVisualizer();
                s.setVisible(true);
               // WelcomeFrame.this.setVisible(false);
            }
        });
        queueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                QueueVisualizer q=new QueueVisualizer();
                q.setVisible(true);
              //  WelcomeFrame.this.setVisible(false);
            }
        });


    }

    public static void main(String[] args) {
        WelcomeFrame frame = new WelcomeFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 90, 1000, 550);
        frame.setTitle("Stack-Queue Visualizer");
        frame.setResizable(true);
    }
}

