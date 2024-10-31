package edu.ptithcm.view.CustomerView;

import edu.ptithcm.controller.*;
import edu.ptithcm.model.Data.*;

import javax.swing.*;

import java.awt.BorderLayout;
import java.util.*;

public class CartView extends JPanel{

    private JTabbedPane tab = new JTabbedPane();
    private JPanel cartPanel = new JPanel();
    private JPanel orderPanel = new JPanel();


    private User user;
    private ArrayList<Cart> cartList;
    private ArrayList<Order> orderList;
    public CartView(User user){
        this.user = user;
        cartPanel.add(new JLabel("cart"));
        orderPanel.add(new JLabel("pending order"));
        
        tab.add("Cart", cartPanel);
        tab.add("order", orderPanel);
        
        setLayout(new BorderLayout());
        add(tab, BorderLayout.CENTER);
    }

    private void reloadCartList(){
        cartList = CartProcess.selectUserCart(user.getId());
    }


    private class CartPanel extends JPanel{
        
    }

    
}