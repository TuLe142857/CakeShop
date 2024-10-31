package edu.ptithcm.util;

import edu.ptithcm.view.Window.MainWindow;

import javax.swing.*;
import java.awt.*;

public class TextFont{
    public static void main(String []args){
        MainWindow w = new MainWindow();
        w.setLayout(new FlowLayout());

        JLabel []l = new JLabel[3];
        for(int i = 0; i < l.length; i++){
            l[i] = new JLabel();
            l[i].setText("Text Label " + String.valueOf(i));
            w.add(l[i]);
        }

        l[0].setFont(smallFont);
        l[1].setFont(mediumFont);
        l[2].setFont(bigFont);

        w.setVisible(true);


   }
    public static final Font smallFont = new Font("Arial", Font.PLAIN, 15);
    public static final Font mediumFont = new Font("Arial", Font.PLAIN, 20);
    public static final Font bigFont = new Font("Arial", Font.PLAIN, 30);
}