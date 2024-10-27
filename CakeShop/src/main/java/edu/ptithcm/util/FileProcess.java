package edu.ptithcm.util;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @author Le Ngoc Tu
 */
public class FileProcess{

    /**
     * test
     */
//    public static void main(String []args){
//        File in = chooseImageFile("Chọn ảnh", null);
//        if(in == null) return;
//        File dirOut = chooseFolder("Chọn nơi copy file ảnh vừa chọn qua", null);
//        try{
//            copyFile(in.getPath(), dirOut.getPath() + "/copied." + getFileExtension(in.getPath()));
//        } catch (IOException e) {
//            System.out.println("Exception: " + e.getMessage());
//        }
//    }

    /**
     * @return <code>null</code> neu khong chon file nao
     */
    public static File chooseFile(FileFilter filter, String dialogTitle, Component parent){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        fileChooser.setFileFilter(filter);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int selection = fileChooser.showOpenDialog(parent);
        if(selection == JFileChooser.APPROVE_OPTION){
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    /**
     * @return <code>null</code> neu khong chon file nao
     */
    public static File chooseImageFile(String dialogTitle, Component parent){
        File f = chooseFile(
                new FileNameExtensionFilter("Image File", "jpg", "jpeg", "png"),
                dialogTitle,
                parent
        );
        if(f == null)
            return null;
        String ext = getFileExtension(f.getName());
        if(!(ext.compareTo("jpg") == 0 || ext.compareTo("png") == 0 || ext.compareTo("jpeg") == 0))
            return null;
        return f;
    }

    /**
     * @return <code>null</code> neu khong chon
     */
    public static File chooseFolder(String dialogTitle, Component parent){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle(dialogTitle);

        int result = chooser.showOpenDialog(parent);
        if(result != JFileChooser.APPROVE_OPTION)
            return null;
        return chooser.getSelectedFile();

    }

    /**
     * Copy file, neu o noi den da ton tai thi thay the file cu thanh file moi
     */
    public static void copyFile(String sourcePath, String destinationPath) throws IOException {
        Files.copy(Path.of(sourcePath), Path.of(destinationPath), StandardCopyOption.REPLACE_EXISTING);
    }

    public static String getFileExtension(String fileName){
        int index = fileName.lastIndexOf('.');
        if(index == -1)
            throw new IllegalArgumentException("getFilExtension(String fileName): fileName khong hop le!");

        return fileName.substring(index+1);
    }

    /**
     * Code nay copy tren mang
     * @param fileUrl link file
     * @param destinationFile noi muon luu(bao gom ten file), vd D:/image.txt
     * @throws IOException
     */
    public static void downloadFile(String fileUrl, String destinationFile) throws IOException {
        // Tạo đối tượng URL từ chuỗi URL
        URL url = new URL(fileUrl);

        // Mở kết nối và tạo InputStream
        try (InputStream in = new BufferedInputStream(url.openStream());
             FileOutputStream fos = new FileOutputStream(destinationFile)) {

            byte[] dataBuffer = new byte[1024]; // Buffer để lưu dữ liệu tạm thời
            int bytesRead;

            // Đọc dữ liệu từ InputStream và ghi vào FileOutputStream
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                fos.write(dataBuffer, 0, bytesRead);
            }
        }
    }
}