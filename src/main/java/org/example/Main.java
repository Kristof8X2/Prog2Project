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
        // Főablak beállítása
        JFrame frame = new JFrame("Karakterválasztás");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new FlowLayout()); // Egymás mellé teszi az elemeket

        // Gombok létrehozása
        JButton warriorBtn = new JButton("Warrior");
        JButton archerBtn = new JButton("Archer");
        JButton medicBtn = new JButton("Medic");

        // Egy egyszerű visszajelző szöveg az ablak alján
        JLabel statusLabel = new JLabel("Válassz Classot a játék indításához!");

        // Warrior gomb eseménye
        warriorBtn.addActionListener(e -> {
            player = new Warrior("Játékos");
            statusLabel.setText("Kiválasztva: Warrior.");
        });

        // Archer gomb eseménye
        archerBtn.addActionListener(e -> {
            player = new Archer("Játékos");
            statusLabel.setText("Kiválasztva: Archer.");
        });

        // Medic gomb eseménye
        medicBtn.addActionListener(e -> {
            player = new Medic("Játékos");
            statusLabel.setText("Kiválasztva: Medic. ");
        });

        frame.add(warriorBtn);
        frame.add(archerBtn);
        frame.add(medicBtn);
        frame.add(statusLabel);

        frame.setVisible(true);
    }
}