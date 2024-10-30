package edu.ptithcm.view.CustomerView;

import edu.ptithcm.model.Data.User;

import javax.swing.*;

public class AccountView extends JPanel{

    private User user;
    public AccountView(User user){
        this.user = user;
        add(new JLabel("ACCOUNT VIEW BEING BUILT..........."));
    }
}