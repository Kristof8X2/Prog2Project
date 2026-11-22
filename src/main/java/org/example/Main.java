package org.example;
import domain.Hero;
import domain.Warrior;
import domain.Archer;
import domain.Medic;
import javax.swing.*;
import java.awt.*;

public class Main {
    static Hero player = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Character Select");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout());
        JButton warriorBtn = new JButton("Warrior");
        JButton archerBtn = new JButton("Archer");
        JButton medicBtn = new JButton("Medic");
        JLabel statusLabel = new JLabel("Choose a class to start the game!");
        warriorBtn.addActionListener(e -> {
            player = new Warrior("Player");
            startGame(frame, player);
        });
        archerBtn.addActionListener(e -> {
            player = new Archer("Player");
            startGame(frame, player);
        });
        medicBtn.addActionListener(e -> {
            player = new Medic("Player");
            startGame(frame, player);
        });
        frame.add(warriorBtn);
        frame.add(archerBtn);
        frame.add(medicBtn);
        frame.add(statusLabel);

        frame.setVisible(true);
    }
    private static void startGame(JFrame frame, Hero selectedPlayer) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        frame.setTitle("RPG Game - " + selectedPlayer.getClass().getSimpleName());

        GamePanel gamePanel = new GamePanel(selectedPlayer);
        frame.add(gamePanel);

        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocus();
    }
}