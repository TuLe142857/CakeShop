package edu.ptithcm.view.CustomerView;


import edu.ptithcm.model.Data.*;
import edu.ptithcm.model.MySql;
import edu.ptithcm.view.Window.MainWindow;

import javax.swing.*;
import java.awt.*;

public class ProductView extends JPanel{
    public static void main(String []args){
        MySql.setDefaultPasswd("tule123");
        MainWindow view = new MainWindow();
        view.setLayout(new BorderLayout());
        view.add(new ProductView(null));
        view.setVisible(true);
    }

    private CardLayout cardLayout;
    private static final String PRODUCT_CATALOG = "catalog";
    private static final String PRODUCT_DETAILS = "details";

    private ProductCatalogPanel productCatalogPanel;
    private ProductDetailsPanel productDetailsPanel;

    private User user;
    public ProductView(User user) {
        this.user = user;
        productCatalogPanel = new ProductCatalogPanel();
        productDetailsPanel = new ProductDetailsPanel(user);

        cardLayout = new CardLayout();
        setLayout(cardLayout);
        add(productCatalogPanel, PRODUCT_CATALOG);
        add(productDetailsPanel, PRODUCT_DETAILS);
        cardLayout.show(this, PRODUCT_CATALOG);

        //Set Interface DoingSomething
        productCatalogPanel.setDoAfterClickToProduct((product)->{
            productDetailsPanel.setProduct((Product) product);
            cardLayout.show(this, PRODUCT_DETAILS);
        });

        productDetailsPanel.setDoAfterClickBackButton((o)->{
            cardLayout.show(this, PRODUCT_CATALOG);
        });
    }
}