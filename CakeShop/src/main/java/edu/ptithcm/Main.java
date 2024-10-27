package edu.ptithcm;

import edu.ptithcm.view.App;

import javax.swing.*;

public class Main {
    static{
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("Set system LookAndFeel ok\n");
        }
        catch(Exception e){
            System.out.println("Can not set system LookAndFeel, exception message: " + e.getMessage() + "\n");
        }
    }
    public static void main(String[] args) {
        App app = new App();
        app.setVisible(true);
    }
}