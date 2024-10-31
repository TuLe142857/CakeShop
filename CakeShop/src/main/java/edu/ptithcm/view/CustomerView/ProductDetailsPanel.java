package edu.ptithcm.view.CustomerView;

import edu.ptithcm.controller.CartProcess;
import edu.ptithcm.controller.CustomerProcess;
import edu.ptithcm.controller.ReviewProcess;
import edu.ptithcm.model.Data.Product;
import edu.ptithcm.model.Data.Review;
import edu.ptithcm.model.Data.User;
import edu.ptithcm.util.DoSomething;
import edu.ptithcm.util.ImageProcess;
import edu.ptithcm.util.TextFont;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProductDetailsPanel extends JPanel{
    private JButton backButton = new JButton("Go Back");
    private JButton addToCartButton = new JButton("Add to cart");
    private InformationPanel inforPanel;
    private AddToCartDialog addToCartDialog = new AddToCartDialog();
    {
        addToCartDialog.setTitle("Them vao gio hang");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        addToCartDialog.setSize(dim.width/4, dim.height/4);
        addToCartDialog.setModal(true);
        addToCartDialog.setLocationRelativeTo(null);
    }


    private DoSomething doAfterClickBackButton = (o)->{/*Do nothing*/};
    private User user;
    private Product product;

    public ProductDetailsPanel(User user){
        this.user = user;

        //ADD COMONENT TO LAYOUT
        inforPanel = new InformationPanel();
        JScrollPane scrollPane = new JScrollPane(inforPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        setLayout(new BorderLayout());
        add(backButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(addToCartButton, BorderLayout.SOUTH);

        // ACTION LISENER
        addToCartButton.addActionListener(e->addToCartDialog.showDialog());
        backButton.addActionListener(e->doAfterClickBackButton.doing(null));
    }


    public void setProduct(Product product) {
        this.product = product;
        inforPanel.uploadProduct();
    }

    public void setDoAfterClickBackButton(DoSomething doAfterClickBackButton) {
        this.doAfterClickBackButton = doAfterClickBackButton;
    }

    private class InformationPanel extends JPanel{
            private JLabel imageLabel = new JLabel();
            private JLabel nameLabel = new JLabel();
            private JLabel priceLabel = new JLabel();
            private JTextArea descriptionTextArea = new JTextArea();
            private ReviewPanel reviewPanel = new ReviewPanel();

            public InformationPanel(){
                descriptionTextArea.setEditable(false);
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                add(imageLabel);
                add(nameLabel);
                add(priceLabel);
                add(descriptionTextArea);

                JLabel reviewLable = new JLabel("Review");
                reviewLable.setFont(TextFont.mediumFont);
                add(reviewLable);
                add(reviewPanel);
            }

            public void uploadProduct(){
                imageLabel.setIcon(ImageProcess.getImageIcon(ImageProcess.dirProductImage + product.getImage_url(), 300, 300));
                nameLabel.setText(product.getName());
                priceLabel.setText(String.valueOf(product.getFinalPrice()));
                descriptionTextArea.setText(product.getDescription());


                reviewPanel.uploadReview(product);
            }
    }

    private class ReviewPanel extends JPanel{
        private ReviewPanel(){
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(new Label("CUSTOMER FEED BACK ABOUT THIS PRODUCT"));
        }
        public void uploadReview(Product product){
            System.out.println("upload review p" + product.getId() + " " + product.getName());
            removeAll();
            ArrayList<Review> reviewList = ReviewProcess.selectAllReviewAboutProduct(product.getId());
            for(Review rv:reviewList){
                User user = CustomerProcess.selectById(rv.getUserID());
                JTextArea text = new JTextArea();
                text.setText(
                        String.format("User %s vote %d start\n", user.getName(), rv.getRating())
                        + rv.getComment()
                );
                text.setFont(TextFont.smallFont);
                System.out.println(text.getText());
                add(text);
            }
        }
    }

    private class AddToCartDialog extends JDialog{
        private JLabel iamgeLabel = new JLabel();
        private JLabel nameLabel = new JLabel();

        private JLabel totalPriceLabel = new JLabel();
        private SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 1000, 1);
        private JSpinner quantitySpinner = new JSpinner(spinnerModel);

        private JButton okButton = new JButton("Ok");
        private JButton cancelButton = new JButton("Cancel");


        public AddToCartDialog() {
            super();
            setComponentLayout();

            //Lisener
            quantitySpinner.addChangeListener(change->{
                totalPriceLabel.setText(String.valueOf(product.getFinalPrice() * (int)quantitySpinner.getValue()));
            });

            okButton.addActionListener(e ->{
                CartProcess.addProductToCart(user.getId(), product.getId(), (int)quantitySpinner.getValue());
                setVisible(false);
                JOptionPane.showMessageDialog(null,
                        "Them thanh cong " + (int) quantitySpinner.getValue() + " san pham vao gio hang",
                        "Thong bao",
                        JOptionPane.PLAIN_MESSAGE);

            });

            cancelButton.addActionListener(e->setVisible(false));
        }

        private void setComponentLayout(){
            JPanel wrapperPanel = new JPanel();
            wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));

            JPanel totalPriceAndQuantityPanel = new JPanel(new GridLayout(1, 4, 10, 10));
            totalPriceAndQuantityPanel.add(new JLabel("Quantity: "));       totalPriceAndQuantityPanel.add(quantitySpinner);
            totalPriceAndQuantityPanel.add(new JLabel("Total price: "));    totalPriceAndQuantityPanel.add(totalPriceLabel);

            JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
            buttonPanel.add(okButton);  buttonPanel.add(cancelButton);

            wrapperPanel.add(iamgeLabel);
            wrapperPanel.add(nameLabel);
            wrapperPanel.add(totalPriceAndQuantityPanel);
            wrapperPanel.add(buttonPanel);

            add(wrapperPanel);
        }

        public void showDialog(){
            iamgeLabel.setIcon(ImageProcess.getImageIcon(ImageProcess.dirProductImage + product.getImage_url(), 100, 100));
            nameLabel.setText(product.getName());

            totalPriceLabel.setText(String.valueOf(product.getFinalPrice()));
            quantitySpinner.setValue(1);
            setVisible(true);
        }
    }

}