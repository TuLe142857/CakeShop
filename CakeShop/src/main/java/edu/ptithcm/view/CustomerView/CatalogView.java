package edu.ptithcm.view.CustomerView;

import edu.ptithcm.controller.CategoryProcess;
import edu.ptithcm.controller.ProductProcess;
import edu.ptithcm.model.Data.Category;
import edu.ptithcm.model.Data.Product;
import edu.ptithcm.model.Data.User;
import edu.ptithcm.util.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CatalogView extends JPanel{

    private FilterPanel filterBar;
    private ContentPanel contentPanel;

    private User user;
    public CatalogView(User user){
        this.user = user;
        setLayout(new BorderLayout());

        filterBar = new FilterPanel();

        contentPanel = new ContentPanel();
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        add(filterBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        //ACTION LISTENER
        filterBar.filterButton.addActionListener(e->{
            contentPanel.resetProductsList(filterBar.getProductList(), 0);
        });
        updateData();

    }

    /**
     * Cap nhat du lieu tu database
     */
    public void updateData(){
        filterBar.resetCategoriesComboBoxItem();
        contentPanel.resetProductsList(filterBar.getProductList(), 0);
    }

    private class FilterPanel extends JPanel{

        private static final String PRIOR_LOW_PRICE = "Gia thap";
        private static final String PRIOR_HIGH_PRICE = "Gia cao";
        private static final String PRIOR_IS_SALING = "Dang giam gia";

        private JComboBox<Category> categoriesComboBox = new JComboBox<>();
        private JComboBox<String> priorityComboBox = new JComboBox<>();
        private JCheckBox availableProductCheckBox = new JCheckBox("San pham co san de giao");
        private JButton filterButton = new JButton("Tim kiem");

        public FilterPanel(){
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

            JLabel cLabel = new JLabel("Category");
            cLabel.setHorizontalAlignment(JLabel.RIGHT);
            resetCategoriesComboBoxItem();

            JLabel pLabel = new JLabel("Priority");
            pLabel.setHorizontalAlignment(JLabel.RIGHT);
            priorityComboBox.addItem(PRIOR_LOW_PRICE);
            priorityComboBox.addItem(PRIOR_HIGH_PRICE);
            priorityComboBox.addItem(PRIOR_IS_SALING);

            add(cLabel);    add(categoriesComboBox);
            add(pLabel);    add(priorityComboBox);
            add(availableProductCheckBox);
            add(filterButton);
        }

        /**
         * Cap nhat lai sanh sach category thu database
         */
        public void resetCategoriesComboBoxItem(){
            categoriesComboBox.removeAllItems();
            ArrayList<Category> categories = CategoryProcess.selectCategoryThatInBussiness();
            for(Category cat : categories){
                categoriesComboBox.addItem(cat);
            }
        }

        public ArrayList<Product> getProductList(){
            Category c = (Category) categoriesComboBox.getSelectedItem();
            System.out.println(c.getId() + c.getName());
            //TH khong cdu lieu
            if(c == null)
                return null;
            return ProductProcess.selectByCategory(c.getId());
        }

    }

    private class ContentPanel extends JPanel{

        private static final int maxDisplay = 30;
        private ProductCard []productsDisplay = new ProductCard[maxDisplay];

        public ContentPanel(){
            setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

            for(int i = 0; i < maxDisplay; i++){
                productsDisplay[i] = new ProductCard();
                add(productsDisplay[i]);
            }

        }

        public void resetProductsList(ArrayList<Product> products, int startIndex){
            System.out.println("reload " + products.size())  ;
            for(int i = 0; i < maxDisplay; i++){
                if(startIndex + i < products.size()){
                    productsDisplay[i].setProduct(products.get(i));
                    productsDisplay[i].setVisible(true);
                }
                else{
                    productsDisplay[i].setVisible(false);
                }
            }
        }

    }
}