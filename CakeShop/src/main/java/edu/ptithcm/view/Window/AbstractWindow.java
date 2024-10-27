package edu.ptithcm.view.Window;

import edu.ptithcm.util.ImageProcess;

import javax.swing.*;
import java.awt.*;

/**
 * @author  Le Ngoc Tu
 */
public abstract class AbstractWindow extends JFrame{
    private static final Dimension defaultSize;
    private String confirmClosingDialogMessage = "Bạn có xác nhận đóng cửa sổ?";
    private String confirmClosingDialogTitle = "Xác nhận đóng cửa sổ";

    static{
        defaultSize = Toolkit.getDefaultToolkit().getScreenSize();
        defaultSize.width = defaultSize.width*2/3;
        defaultSize.height = defaultSize.height*2/3;
    }

    public AbstractWindow(){
        setSize(defaultSize);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationByPlatform(true);
        setTitleAndIcon("Window", ImageProcess.dirAppImage + "AppIcon.png");
    }

    protected boolean showConfirmClosingDialog(){
        int result = JOptionPane.showConfirmDialog(
                this,
                confirmClosingDialogMessage,
                confirmClosingDialogTitle,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    public void setTitleAndIcon(String title, String iconPath){
        setTitle(title);
        setIconImage(ImageProcess.getImageIcon(iconPath).getImage());
    }

    public String getConfirmClosingDialogMessage() {
        return confirmClosingDialogMessage;
    }

    public void setConfirmClosingDialogMessage(String confirmClosingDialogMessage) {
        this.confirmClosingDialogMessage = confirmClosingDialogMessage;
    }

    public String getConfirmClosingDialogTitle() {
        return confirmClosingDialogTitle;
    }

    public void setConfirmClosingDialogTitle(String confirmClosingDialogTitle) {
        this.confirmClosingDialogTitle = confirmClosingDialogTitle;
    }
}