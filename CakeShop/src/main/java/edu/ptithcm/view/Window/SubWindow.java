package edu.ptithcm.view.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Dong <code>SubWindow</code> khong anh huong den cac cua so con lai
 * @author Le Ngoc Tu
 */
public class SubWindow extends AbstractWindow{
    public SubWindow(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(showConfirmClosingDialog())
                    dispose();
            }
        });
    }
}