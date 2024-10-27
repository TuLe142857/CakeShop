package edu.ptithcm.util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Le Ngoc Tu
 */
public class ImageProcess{

    public static final String dirAppImage = "./Data/AppImage/";
    public static final String dirProductImage = "./Data/ProductImage/";
    public static final String dirCustomerAvatar = "./Data/CustomerAvatar/";
    public static final String dirAdminAvatar = "./Data/AdminAvatar/";

    public static ImageIcon getImageIcon(String path){
        ImageIcon icon = new ImageIcon(path);
        if(icon.getImageLoadStatus() != MediaTracker.COMPLETE){
            System.out.printf("Load ImageIcon from %s failed", path);
        }
        return icon;
    }

    public static ImageIcon getImageIcon(String path, int newWidth, int newHeight){
        return resizeImageIcon(getImageIcon(path), newWidth, newHeight);
    }

    public static ImageIcon resizeImageIcon(ImageIcon originalIcon, int newWidth, int newHeight){
        Image image = originalIcon.getImage();
        image = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(image);
    }

    public static BufferedImage getBufferedImage(String path) throws IOException {
        return ImageIO.read(new File(path));
    }

    public static BufferedImage getBufferedImage(String path, int newWidth, int newHeight) throws IOException{
        return resizeBufferedImage(getBufferedImage(path), newWidth, newHeight);
    }

    public static BufferedImage resizeBufferedImage(BufferedImage originalImage, int newWidth, int newHeight){
        BufferedImage newImage = new BufferedImage(newWidth, newHeight, originalImage.getType());
        Graphics2D g = newImage.createGraphics();
        g.drawImage(originalImage,0, 0, newWidth, newHeight, null);
        g.dispose();
        return newImage;
    }
}