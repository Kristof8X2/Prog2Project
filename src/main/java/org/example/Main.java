package org.example;

import domain.*;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;

public class Main {
    static Hero player = null;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Character Select");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        showMenu(frame);
        frame.setVisible(true);
    }

    private static void showMenu(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.setTitle("Character Select");
        frame.setSize(420, 200);
        frame.setLayout(new FlowLayout());
        JButton warriorBtn = new JButton("Warrior");
        JButton archerBtn = new JButton("Archer");
        JButton medicBtn = new JButton("Medic");
        JButton loadBtn = new JButton("Load Game");
        JLabel statusLabel = new JLabel("Choose a class to start the game!");

        SaveManager saveManager = new SaveManager();
        loadBtn.setEnabled(saveManager.hasSaveFile());

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
        loadBtn.addActionListener(e -> {
            try {
                SaveManager.SaveData save = saveManager.loadGame();
                if (save != null) {
                    startLoadedGame(frame, save);
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Failed to load save: " + ex.getMessage());
            }
        });
        frame.add(warriorBtn);
        frame.add(archerBtn);
        frame.add(medicBtn);
        frame.add(loadBtn);
        frame.add(statusLabel);

        frame.revalidate();
        frame.repaint();
    }
    private static void startGame(JFrame frame, Hero selectedPlayer) {
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        frame.setTitle("RPG Game - " + selectedPlayer.getClass().getSimpleName());

        GamePanel gamePanel = new GamePanel(selectedPlayer, () -> showMenu(frame));
        frame.add(gamePanel);

        frame.revalidate();
        frame.repaint();
        gamePanel.requestFocus();
    }
    private static void startLoadedGame(JFrame frame, SaveManager.SaveData save){
            frame.getContentPane().removeAll();
            frame.setLayout(new BorderLayout());
            frame.setSize(800, 600);
            frame.setTitle("RPG Game - " + save.heroClass);

            GamePanel gamePanel = new GamePanel(save, () -> showMenu(frame));
            frame.add(gamePanel);

            frame.revalidate();
            frame.repaint();
            gamePanel.requestFocus();
        }
}