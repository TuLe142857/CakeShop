package edu.ptithcm.view.AdminView;

import edu.ptithcm.controller.AdminProcess;
import edu.ptithcm.model.Data.Admin;
import edu.ptithcm.view.Login.LoginFrame;
import edu.ptithcm.view.Window.SubWindow;

public class AdminFrame extends SubWindow{

    private Admin admin;
    public AdminFrame(String adminEmail){
        setAdmin(adminEmail);
    }
    private void setAdmin(String adminEmail){
        admin = AdminProcess.selectByEmail(adminEmail);
        if(admin == null)
            throw new RuntimeException("Admin is null");
        setTitle("Hello admin " + admin.getName());
    }
    public static void login(){
        LoginFrame l = new LoginFrame();
        l.setPermitRegister(false);
        l.setChecker((u, p)-> AdminProcess.checkLogin(u, p));
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