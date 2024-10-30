package edu.ptithcm.view.CustomerView;

import edu.ptithcm.model.Data.Cart;
import edu.ptithcm.model.Data.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.PrimitiveIterator;

public class CartView extends JPanel{

    private User user;
    private ArrayList<Cart> cartList;

    public CartView(User user){
        this.user = user;
        add(new JLabel("CART VIEW BEING BUILT..........."));
    }
}