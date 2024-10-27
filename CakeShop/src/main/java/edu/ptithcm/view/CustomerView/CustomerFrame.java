package edu.ptithcm.view.CustomerView;

import edu.ptithcm.view.Login.LoginFrame;
import edu.ptithcm.view.Window.SubWindow;

public class CustomerFrame extends SubWindow{

    public CustomerFrame(String loginName){
        setTitle("Hello user "  + loginName);
    }

    public static void login(){
        LoginFrame l = new LoginFrame();
        l.setPermitRegister(true);
        l.setChecker((u, p)->(u.compareTo("tule") == 0 && p.compareTo("123") == 0 ));

        l.setDoRegister(o-> System.out.println("Chọn chức năng đăng ký tài khoảng"));

        l.setDoAfterLogin((userLoginName)->
                {
                    CustomerFrame view = new CustomerFrame((String)(userLoginName));
                    view.setVisible(true);
                }
        );

        l.setTitle("Customer login");
        l.setVisible(true);
    }
}