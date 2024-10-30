package edu.ptithcm.view.CustomerView;

import edu.ptithcm.model.Data.Product;
import edu.ptithcm.util.ImageProcess;

import javax.swing.*;
import java.awt.*;

/**
 * <p>Có thể kế thừa class này để tùy chỉnh hiển thị sản phẩm</p>
 * <p>Có thể dùng html trong contentLabel</p>
 * @author Le Ngoc Tu
 */
public class ProductCard extends JPanel{
    /**
     * Display product image
     */
    protected JLabel imageLabel;

    /**
     * Display information
     */
    protected JLabel contentLabel;

    protected Dimension imageSize = new Dimension(200, 200);

    public ProductCard(){
        imageLabel = new JLabel();
        contentLabel = new JLabel();

        contentLabel.setHorizontalAlignment(SwingConstants.LEFT);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;      gbc.gridy = 0;
        gbc.gridwidth = 5;  gbc.gridheight = 5;
        add(imageLabel, gbc);

        gbc.gridx = 0;      gbc.gridy = 5;
        gbc.gridwidth = 5;  gbc.gridheight = 3;
        add(contentLabel, gbc);
    }

    public ProductCard(Product product){
        this();
        setProduct(product);
    }

    public Dimension getImageSize() {
        return imageSize;
    }

    public void setImageSize(Dimension imageSize) {
        this.imageSize = imageSize;
    }

    public void setProduct(Product product){
        imageLabel.setIcon(ImageProcess.getImageIcon(ImageProcess.dirProductImage + product.getImage_url(), imageSize.width, imageSize.height));

        String name = "<p style = 'color:black; font-size:15'>" +  product.getName()+"</p>";

        String price = "";
        if(product.getDiscount() > 0){
            price = String.format(
                    "<p style = 'color:red; font-size:15'>Sale <span style='color:#FF9999;font-size:15'><strike>%.01f </strike></span>%.01f</p>",
                    product.getPrice(), product.getFinalPrice()
            );
        }
        else{
            price = String.format(
                    "<p style = 'color:red; font-size:15'>%.01f</p>",
                    product.getPrice()
            );
        }
        String html = "<html>" + name + price +"</html>";
        contentLabel.setText(html);
    }
}