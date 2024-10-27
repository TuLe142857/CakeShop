package edu.ptithcm.view.AdminView;

import edu.ptithcm.view.Login.LoginFrame;
import edu.ptithcm.view.Window.SubWindow;

public class AdminFrame extends SubWindow{

    public AdminFrame(String loginName){
        setTitle("Hello admin " + loginName);
    }
    public static void login(){
        LoginFrame l = new LoginFrame();
        l.setPermitRegister(false);
        l.setChecker((u, p)->(u.compareTo("tule") == 0 && p.compareTo("123") == 0 ));
        l.setDoAfterLogin(
                (userName)->{
                    AdminFrame view = new AdminFrame((String)(userName));
                    view.setVisible(true);
                }
        );

        l.setTitle("Admin login");
        l.setVisible(true);
    }

}