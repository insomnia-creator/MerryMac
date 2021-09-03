package com.insomnia.creator.MerryMac;

//imports

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.util.Scanner;

public class Commands{

    //Run Command
    private static String runCommand(final String command){
        System.out.println("runCommand was executed");
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
                System.out.println("Success POG");
            } else {
                JOptionPane.showMessageDialog(null, "There was an error executing that Command!");
            }
            System.out.println(output);
        } catch(IOException | InterruptedException err){
            err.printStackTrace();

        }
        return output.toString();
    }
    private static void killfinder(){
        runCommand("killall finder");
    }
    private static void killdock(){runCommand("killall Dock");}



    public static Runnable createAndShowGUI() {


        final PopupMenu popupmenu = new PopupMenu();
        final TrayIcon trayIcon = new TrayIcon(createImage());


        final SystemTray tray = SystemTray.getSystemTray();
        //Menu items start here.
        final MenuItem battery = new MenuItem("Battery");
        final MenuItem sleep = new MenuItem("Sleep");
        final MenuItem touchbarReset = new MenuItem("Reset TouchBar");
        final Menu computer = new Menu("Computer Actions");

        final MenuItem restart = new MenuItem("Restart");
        final MenuItem screensaver = new MenuItem("Screensaver");
        final MenuItem exit = new MenuItem("Exit");
        final MenuItem shutdown = new MenuItem("Shutdown");

        //computer commands done
        final Menu finder = new Menu("Finder");
        final Menu showHiddenFiles = new Menu("Show hidden files");
        final Menu showDesktop = new Menu("Show desktop files");
        final Menu showPath = new Menu("Show Path on tab");
        final Menu showExtensions = new Menu("Show file extensions");
        final MenuItem showDesktopNO = new MenuItem("NO");
        final MenuItem showExtNO = new MenuItem("NO");
        final MenuItem showPathNO = new MenuItem("NO");
        final MenuItem showHFNO = new MenuItem("NO");
        final MenuItem showDesktopYES = new MenuItem("YES");
        final MenuItem showExtYES = new MenuItem("YES");
        final MenuItem showHFYES = new MenuItem("YES");
        final MenuItem showPathYES = new MenuItem("YES");

        //Dock

        final Menu dock = new Menu("Dock");

        final Menu dockAutoHide = new Menu("Auto hide");
        final MenuItem dahNO = new MenuItem("NO");
        final MenuItem dahYES = new MenuItem("YES");

        final Menu dockMagnify = new Menu("Magnify");
        final MenuItem dmNO = new MenuItem("NO");
        final MenuItem dmYES = new MenuItem("YES");


        final Menu dockPosition = new Menu("Dock position");
        final MenuItem dockPosBottom = new MenuItem("BOTTOM");
        final MenuItem dockPosLeft = new MenuItem("LEFT");
        final MenuItem dockPosRight = new MenuItem("RIGHT");


        final MenuItem dockAddBlank = new MenuItem("Add blank space");
        final MenuItem dockRecentItems = new MenuItem("Add recent items");
        final MenuItem dockPrune = new MenuItem("Prune");

        //dock done





        //network???? or MISC STUFF!

        final MenuItem ip_local = new MenuItem("Local IP address");
        final MenuItem ip_global = new MenuItem("Public IP address");





        //Add all the items

        computer.add(battery);
        computer.add(sleep);
        computer.add(restart);
        computer.add(touchbarReset);
        computer.add(shutdown);
        computer.add(screensaver);
        computer.add(ip_local);
        computer.add(ip_global);
        popupmenu.add(computer);


        //computer done
        popupmenu.addSeparator();


        showDesktop.add(showDesktopYES);
        showDesktop.add(showDesktopNO);
        showExtensions.add(showExtYES);
        showExtensions.add(showExtNO);
        showHiddenFiles.add(showHFYES);
        showHiddenFiles.add(showHFNO);
        showPath.add(showPathYES);
        showPath.add(showPathNO);
        finder.add(showExtensions);
        finder.add(showDesktop);
        finder.add(showPath);
        finder.add(showHiddenFiles);

        //finder done

        popupmenu.add(finder);

        popupmenu.addSeparator();

        dockPosition.add(dockPosBottom);
        dockPosition.add(dockPosLeft);
        dockPosition.add(dockPosRight);

        dockMagnify.add(dmYES);
        dockMagnify.add(dmNO);

        dockAutoHide.add(dahYES);
        dockAutoHide.add(dahNO);
        dock.add(dockPosition);
        dock.add(dockMagnify);
        dock.add(dockAutoHide);

        dock.addSeparator();

        dock.add(dockAddBlank);
        dock.add(dockRecentItems);
        dock.addSeparator();
        dock.add(dockPrune);
        popupmenu.add(dock);

        //dooooock

        //wifiiiii


        popupmenu.addSeparator();


        popupmenu.addSeparator();



        popupmenu.addSeparator();


        popupmenu.add(exit);




        //Try to add the Tray Icon
        try {
            tray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
            return null;
        }

        //Action listeners

        battery.addActionListener(evn -> {
            final String res = runCommand("pmset -g batt");
            Object[] options = {"OK", "Copy"};
            final JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            int result = JOptionPane.showOptionDialog(dialog, res, "Battery status",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, null);
            if (result == JOptionPane.NO_OPTION){
                copyToClipboard(res);
                System.out.println("whyyy");
             }
        });
        sleep.addActionListener(e -> {
            runCommand("pmset displaysleepnow");
        });

        touchbarReset.addActionListener(e -> {
            runCommand("pkill \"Touch Bar agent\"");
            runCommand("killall ControlStrip");
        });


        restart.addActionListener(e -> {
            runCommand("osascript -e 'tell app \"loginwindow\" to «event aevtrrst»'");
        });
        shutdown.addActionListener(e -> {
            runCommand("osascript -e 'tell app \"loginwindow\" to «event aevtrsdn»'");
        });
        exit.addActionListener(e -> {
            System.exit(0);
        });
        screensaver.addActionListener(e -> {
            runCommand("open /System/Library/CoreServices/ScreenSaverEngine.app");
        });
        //computer commands end

        //finder commands start

        showDesktop.addActionListener(e -> {
            String res = runCommand("defaults read com.apple.finder CreateDesktop");
            res = res == "0" ? "Finder does not show the desktop files." : "Finder shows the desktop files.";
            final JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, res, "Desktop status", JOptionPane.PLAIN_MESSAGE);
        });

        showExtensions.addActionListener(e -> {
            String res = runCommand("defaults read NSGlobalDomain AppleShowAllExtensions");
            final JDialog dialog = new JDialog();
            res = res == "0" ? "Finder shows the extensions of all the files." : "Finder does not show the extensions of all the files.";
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, res, "Extensions?", JOptionPane.PLAIN_MESSAGE);
        });

        showHiddenFiles.addActionListener(e -> {
            String res = runCommand("defaults read com.apple.finder AppleShowAllFiles");
            res = res == "0" ? "Finder does not show hidden files." : "Finder shows hidden files.";
            final JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, res, "Hidden files?", JOptionPane.PLAIN_MESSAGE);
        });
        showPath.addActionListener(e -> {
            String res = runCommand("defaults read com.apple.finder _FXShowPosixPathInTitle");
            res = res == "0" ? "Finder does not show the path of the folder on the tab bar." : "Finder shows the path of folder on the tab bar.";
            final JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, res, "Show path on Finder tab?", JOptionPane.PLAIN_MESSAGE);
        });

        showPathNO.addActionListener(e -> {
            runCommand("defaults write com.apple.finder _FXShowPosixPathInTitle -bool false");
            killfinder();
        });
        showPathYES.addActionListener(e -> {
            runCommand("defaults write com.apple.finder _FXShowPosixPathInTitle -bool true");
            killfinder();
        });
        showDesktopNO.addActionListener(e -> {
            runCommand("defaults write com.apple.finder CreateDesktop -bool false");
            killfinder();
        });
        showDesktopYES.addActionListener(e -> {
            runCommand("defaults write com.apple.finder CreateDesktop -bool true");
            killfinder();
        });
        showExtNO.addActionListener(e -> {
            runCommand("defaults write NSGlobalDomain AppleShowAllExtensions -bool false");
            killfinder();
        });
        showExtYES.addActionListener(e -> {
            runCommand("defaults write NSGlobalDomain AppleShowAllExtensions -bool true");
            killfinder();
        });
        showHFNO.addActionListener(e -> {
            runCommand("defaults write com.apple.finder AppleShowAllFiles -bool false");
            killfinder();
        });
        showHFYES.addActionListener(e -> {
            runCommand("defaults write com.apple.finder AppleShowAllFiles -bool true");
            killfinder();
        });
        //dock k

        dockAddBlank.addActionListener(e -> {
            runCommand("defaults write com.apple.dock persistent-apps -array-add '{\"tile-type\"=\"spacer-tile\";}'");
            killdock();
        });
        dockMagnify.addActionListener(e -> {
            String res = runCommand("defaults read com.apple.dock magnification");
            res = res == "1" ? "Magnification is enabled" : "Magnification is disabled";
            final JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, res, "Dock magnification status", JOptionPane.PLAIN_MESSAGE);
        });
        dockAutoHide.addActionListener(e -> {
            final String res = runCommand("defaults read com.apple.dock autohide-time-modifier");
            final JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, res, "Dock Auto Hide time", JOptionPane.PLAIN_MESSAGE);
        });
        dockPosition.addActionListener(e -> {
            String res = "Dock position is: ";
            res += runCommand("defaults read com.apple.dock orientation");
            final JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            JOptionPane.showMessageDialog(dialog, res, "Dock Position", JOptionPane.PLAIN_MESSAGE);
        });
        dockPosBottom.addActionListener(e -> {
            runCommand("defaults write com.apple.dock orientation bottom");
            killdock();
        });
        dockPosLeft.addActionListener(e -> {
            runCommand("defaults write com.apple.dock orientation left");
            killdock();
        });
        dockPosRight.addActionListener(e -> {
            runCommand("defaults write com.apple.dock orientation right");
            killdock();
        });
        dmNO.addActionListener(e -> {
            runCommand("defaults write com.apple.dock magnification -bool false");
            killdock();
        });
        dmYES.addActionListener(e -> {
            runCommand("defaults write com.apple.dock magnification -bool true");
            killdock();
        });
        dahNO.addActionListener(e -> {
            runCommand("defaults write com.apple.dock autohide -bool false");
        });
        dahYES.addActionListener(e -> {
            runCommand("defaults write com.apple.dock autohide -bool true");
        });
        dockAddBlank.addActionListener(e -> {
            runCommand("defaults write com.apple.dock persistent-apps -array-add '{\"tile-type\"=\"spacer-tile\";}'");
            killdock();
        });
        dockRecentItems.addActionListener(e -> {
            runCommand("defaults write com.apple.dock persistent-others -array-add '{\"tile-data\" = {\"list-type\" = 1;}; \"tile-type\" = \"recents-tile\";}';");
            killdock();
        });
        dockPrune.addActionListener(e -> {
            runCommand("defaults write com.apple.dock persistent-apps '()'");
            killdock();
        });
        /* wiiiiiiiiiiiiiiiiffffffffiiiiiiiieeeeee */

        ip_global.addActionListener(e -> {
            final String res = runCommand("curl https://checkip.amazonaws.com");
            //stop telling me to go to ipgrab.io and whatnot.
            //i will donate my soul to the one and only amazon
            Object[] options = {"OK", "Copy"};
            final JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            int result = JOptionPane.showOptionDialog(dialog, res, "Public IP address.",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, null);
            if (result == JOptionPane.NO_OPTION){
                copyToClipboard(res);
                System.out.println("whyyy");
            }

        });
        ip_local.addActionListener(e -> {
            String res = runCommand("ifconfig | sed -En 's/127.0.0.1//;s/.*inet (addr:)?(([0-9]*\\.){3}[0-9]*).*/\\2/p'");
            Scanner scanner = new Scanner(res);
            //watch me use big bran java lmao
            for(int i = 0; i < 3; i++){
                if(i == 0){
                    scanner.nextLine();
                    continue;
                } else if(i == 2){
                    res = scanner.nextLine();
                }
            }

            Object[] options = {"OK", "Copy"};
            final JDialog dialog = new JDialog();
            dialog.setAlwaysOnTop(true);
            int result = JOptionPane.showOptionDialog(dialog, res, "Local  IP address.",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, options, null);
            if (result == JOptionPane.NO_OPTION){
                copyToClipboard(res);
                System.out.println("whyyy");
            }
        });








        //COLOUR TIME LESSSS GOOO








        //set le popup menu
        trayIcon.setPopupMenu(popupmenu);
        return null;
    }

    private static BufferedImage resizeImage(BufferedImage image, int height, int width) {
        Image tmp = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2d = resized.createGraphics();
        graphics2d.drawImage(tmp, 0, 0, null);
        graphics2d.dispose();
        return resized;
    }
    //Create image function go brrrrr
    private static Image createImage() {
        URL imageURL = Commands.class.getResource("M.png");
        return (new ImageIcon(imageURL, "tray icon")).getImage();
    }

    //lol clipboard

    private static void copyToClipboard(String content){
        StringSelection stringSelection = new StringSelection(content);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
