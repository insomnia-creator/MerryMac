package com.insomnia.creator.m;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Commands{
    private static String runCommand(final String command){
        System.out.println("hi");
        ProcessBuilder processBuilder = new ProcessBuilder();
        StringBuilder output = new StringBuilder();
        processBuilder.command("zsh", "-c", command);
        try {

            Process process = processBuilder.start();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            final int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success pog");
            }
            System.out.println(output);
        } catch(IOException | InterruptedException err){
            err.printStackTrace();
        }
        return output.toString();
    }




    public static Runnable createAndShowGUI() {


        final PopupMenu popupmenu = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(createImage());
        final SystemTray tray = SystemTray.getSystemTray();

        MenuItem lock = new MenuItem("Lock");
        MenuItem battery = new MenuItem("Battery");
        MenuItem sleep = new MenuItem("Sleep");
        Menu computer = new Menu("Computer Actions");
        CheckboxMenuItem flightmode = new CheckboxMenuItem("Flight mode");
        MenuItem exit = new MenuItem("Exit");


        computer.add(lock);
        computer.add(battery);
        computer.add(sleep);
        computer.add(flightmode);
        popupmenu.add(computer);
        popupmenu.addSeparator();
        popupmenu.add(exit);





        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
            return null;
        }


        lock.addActionListener(e -> {
            runCommand("m lock");
        });

        battery.addActionListener(e -> {
            final String res = runCommand("m battery status");
            JOptionPane.showMessageDialog(null, res, "Battery status", JOptionPane.INFORMATION_MESSAGE);
        });
        sleep.addActionListener(e -> {
            runCommand("m sleep");
        });

        flightmode.addItemListener(e -> {
            final int change = e.getStateChange();
            if (change == 1){
                runCommand("m flightmode on");
            } else {
                runCommand("m flightmode off");
            }
        });
        exit.addActionListener(e -> {
            System.exit(0);
        });



        trayIcon.setPopupMenu(popupmenu);
        return null;
    }
    private static Image createImage() {
        final URL imageURL = Commands.class.getResource("./M.png");
        System.out.println(imageURL);
        return new ImageIcon(imageURL, "tray icon").getImage();
    }
}
