package com.insomnia.creator.m;
import javax.swing.*;


import static com.insomnia.creator.m.Commands.createAndShowGUI;

public class Main {

    public static void main(String[] args) {

        System.setProperty("apple.awt.UIElement", "true");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException err) {
            err.printStackTrace();
        }


        SwingUtilities.invokeLater(createAndShowGUI());


    }
}
