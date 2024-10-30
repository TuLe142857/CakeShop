package edu.ptithcm.view.CustomerView;

import edu.ptithcm.controller.CustomerProcess;
import edu.ptithcm.model.Data.User;
import edu.ptithcm.util.ImageProcess;
import edu.ptithcm.view.Login.LoginFrame;
import edu.ptithcm.view.Window.SubWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * @author Le Ngoc Tu
 */
public class CustomerFrame extends SubWindow{
//
    private JPanel sidebarPanel;

    private JPanel contentPanel;
    private CardLayout contentPanelCardLayout;
    private static final String PRODUCT_VIEW = "product";
    private static final String CART_VIEW = "cart";
    private static final String ACCOUNT_VIEW = "account";

    private User user;
    public CustomerFrame(String userEmail){
        setUser(userEmail);

        initSidebarPanel();
        initContentPanel();

        setLayout(new BorderLayout());
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    public static void login(){
        LoginFrame l = new LoginFrame();
        l.setPermitRegister(true);
        l.setChecker((user, password)-> CustomerProcess.checkLogin(user, password));
        l.setDoRegister(o->{
            RegisterFrame view = new RegisterFrame();
            view.setVisible(true);
        });

        l.setDoAfterLogin((userLoginName)->
                {
                    CustomerFrame view = new CustomerFrame((String)(userLoginName));
                    view.setVisible(true);
                }
        );

        l.setTitle("Customer login");
        l.setVisible(true);
    }

    private void setUser(String userEmail){
        user = CustomerProcess.selectByEmail(userEmail);
        if(user == null){
            throw new RuntimeException("KHong tim thay email tuong ung");
        }
        setTitle("Hello " + user.getName());
    }

    private void initSidebarPanel(){
        sidebarPanel = new JPanel();

        JLabel accountLabel = new JLabel("Account");
        accountLabel.setIcon(ImageProcess.getImageIcon(ImageProcess.dirAppImage + "doraemon.jpg", 64, 64));

        JLabel productLabel = new JLabel("Catalog");
        productLabel.setIcon(ImageProcess.getImageIcon(ImageProcess.dirAppImage + "cakeIcon.png", 64, 64));

        JLabel cartLabel = new JLabel("Cart");
        cartLabel.setIcon(ImageProcess.getImageIcon(ImageProcess.dirAppImage + "cartIcon.png", 64, 64));

        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.add(accountLabel);
        sidebarPanel.add(productLabel);
        sidebarPanel.add(cartLabel);
        sidebarPanel.setBackground(Color.CYAN);

        // Add ActionLisener
        accountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("ACCOUNT");
                contentPanelCardLayout.show(contentPanel, ACCOUNT_VIEW);
            }
        });

        productLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("PRODUCT");
                contentPanelCardLayout.show(contentPanel, PRODUCT_VIEW);
            }
        });

        cartLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("CART");
                contentPanelCardLayout.show(contentPanel, CART_VIEW);
            }
        });
    }

    private void initContentPanel(){
        contentPanel = new JPanel();
        contentPanelCardLayout = new CardLayout();
        contentPanel.setLayout(contentPanelCardLayout);

        contentPanel.add(new AccountView(user), ACCOUNT_VIEW);
        contentPanel.add(new ProductView(user), PRODUCT_VIEW);
        contentPanel.add(new CartView(user), CART_VIEW);

        contentPanelCardLayout.show(contentPanel, PRODUCT_VIEW);
    }
}