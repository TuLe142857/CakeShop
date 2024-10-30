package edu.ptithcm.view.CustomerView;

import edu.ptithcm.model.Data.Product;
import edu.ptithcm.model.Data.User;
import edu.ptithcm.util.DoSomething;

import javax.swing.*;
import java.awt.*;

public class ProductDetailsPanel extends JPanel{

    private JButton backButton;


    private DoSomething doAfterClickBackButton = (o)->{/*Do nothing*/};
    private Product productDisplay;
    private User user;
    public ProductDetailsPanel(User user){
        this.user = user;
        add(new JLabel("product details"));
        backButton = new JButton("Back");
        backButton.addActionListener(e->doAfterClickBackButton.doing(null));
        setLayout(new BorderLayout());
        add(backButton, BorderLayout.SOUTH);
    }

    public void setProductDisplay(Product productDisplay) {
        this.productDisplay = productDisplay;
        System.out.println("display " + productDisplay.toString());
    }


    public void setDoAfterClickBackButton(DoSomething doAfterClickBackButton) {
        this.doAfterClickBackButton = doAfterClickBackButton;
    }
}