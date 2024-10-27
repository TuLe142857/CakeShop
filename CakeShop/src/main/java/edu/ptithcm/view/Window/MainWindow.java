package edu.ptithcm.view.Window;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Khi dong <code>MainWindow</code>, toan bo chuong trinh dung (goi ham <code>System.exit(0)</code>
 * @author Le Ngoc Tu
 */
public class MainWindow extends AbstractWindow {
    public MainWindow(){
        setConfirmClosingDialogMessage("Đóng cửa sổ chính sẽ dừng toàn bộ chương trình.\nBạn có muốn đóng cửa sổ chính?");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(showConfirmClosingDialog())
                    System.exit(0);
            }
        });
    }
}