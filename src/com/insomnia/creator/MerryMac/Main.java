package com.insomnia.creator.MerryMac;
import javax.swing.*;

import static com.insomnia.creator.MerryMac.Commands.*;
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
