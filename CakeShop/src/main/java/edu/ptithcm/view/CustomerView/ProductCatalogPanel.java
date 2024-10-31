package edu.ptithcm.view.CustomerView;

import edu.ptithcm.controller.CategoryProcess;
import edu.ptithcm.controller.ProductProcess;
import edu.ptithcm.model.Data.Category;
import edu.ptithcm.model.Data.Product;
import edu.ptithcm.model.MySql;
import edu.ptithcm.util.DoSomething;
import edu.ptithcm.util.PaginationPanel;
import edu.ptithcm.util.WrapLayout;
import edu.ptithcm.view.Window.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Period;
import java.util.ArrayList;

public class ProductCatalogPanel extends JPanel{
    public static void main(String []args){
        MySql.setDefaultPasswd("tule123");
        MainWindow view = new MainWindow();
        view.setLayout(new BorderLayout());
        view.add(new ProductCatalogPanel());
        view.setVisible(true);
    }

    private FilterPanel filterPanel = new FilterPanel();
    private ContentPanel contentPanel = new ContentPanel();
    private PaginationPanel paginationPanel;
    private DoSomething doAfterClickToProduct = (product)->{/*Do nothing*/};
    private static final int maxDisplayableProducts = 30;
    private ArrayList<Product> products = new ArrayList<>();


    public ProductCatalogPanel(){
        add(new JLabel("product catalog"));

        JScrollPane scrollpane = new JScrollPane(contentPanel);
        paginationPanel = new PaginationPanel(scrollpane);

        setLayout(new BorderLayout());
        add(filterPanel, BorderLayout.NORTH);
        add(paginationPanel, BorderLayout.CENTER);

        paginationPanel.setPageChangeLisener((event)->
            contentPanel.display(products, (paginationPanel.getCurrentPage()-1)*maxDisplayableProducts)
        );

        setFilterPanelLisener();
        reloadData();
    }

    public void setDoAfterClickToProduct(DoSomething doAfterClickToProduct) {
        this.doAfterClickToProduct = doAfterClickToProduct;
    }

    public void reloadData(){
        filterPanel.reload();
        int totalPage = (int)Math.ceil((double)products.size()/maxDisplayableProducts);
        paginationPanel.changeTotalPage(totalPage);
    }

    private void setFilterPanelLisener(){

        ActionListener action = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterPanel.filter();
                int totalPage = (int)Math.ceil((double)products.size()/maxDisplayableProducts);
                paginationPanel.changeTotalPage(totalPage);
            }
        };
        filterPanel.categoriesComboBox.addActionListener(action);
        filterPanel.priorityComboBox.addActionListener(action);
        filterPanel.availableProductCheckBox.addActionListener(action);
        filterPanel.reloadButton.addActionListener(e->reloadData());
    }

    private class FilterPanel extends JPanel{

        private static final String LOW_PRICE = "Gia thap";
        private static final String HIGH_PRICE = "Gia cao";
        private static final String IS_SALING = "Dang giam gia";
        private static final Category SELECT_ALL_CATEGORY =
                new Category(-999, "Chon tat ca", "Select all category option");

        private JComboBox<Category> categoriesComboBox = new JComboBox<>();
        private JComboBox<String> priorityComboBox = new JComboBox<>();
        private JCheckBox availableProductCheckBox = new JCheckBox("San pham co san de giao");
        private JButton reloadButton = new JButton("Reload Page");


        public FilterPanel(){
            JLabel cLabel = new JLabel("Category");
            cLabel.setHorizontalAlignment(JLabel.RIGHT);
            reload();

            JLabel pLabel = new JLabel("Priority");
            pLabel.setHorizontalAlignment(JLabel.RIGHT);
            priorityComboBox.addItem(LOW_PRICE);
            priorityComboBox.addItem(HIGH_PRICE);
            priorityComboBox.addItem(IS_SALING);

            add(cLabel);    add(categoriesComboBox);
            add(pLabel);    add(priorityComboBox);
            add(availableProductCheckBox);
            add(reloadButton);
        }

        /**
         * Cap nhat lai sanh sach category thu database
         */
        public void reload(){
            categoriesComboBox.removeAllItems();
            categoriesComboBox.addItem(SELECT_ALL_CATEGORY);
            ArrayList<Category> categories = CategoryProcess.selectCategoryThatInBussiness();
            for(Category cat : categories){
                categoriesComboBox.addItem(cat);
            }
            availableProductCheckBox.setSelected(true);
        }

        public void filter(){
            Category c = (Category) categoriesComboBox.getSelectedItem();
            if(c == null)return;
            if(c.getId() == SELECT_ALL_CATEGORY.getId()){
                products = ProductProcess.selectAllProductInBussiness(availableProductCheckBox.isSelected());
            }else{
                products = ProductProcess.selectByFilter(c.getId(), availableProductCheckBox.isSelected());
            }

            //SORT
            String priority = (String)priorityComboBox.getSelectedItem();
            if(priority.compareTo(LOW_PRICE ) == 0)
                products.sort((p1, p2)->Double.compare(p1.getFinalPrice(), p2.getFinalPrice()));
            else if(priority.compareTo(HIGH_PRICE) == 0)
                products.sort((p1, p2)->Double.compare(p2.getFinalPrice(), p1.getFinalPrice()));
            else if(priority.compareTo(IS_SALING) == 0)
                products.sort((p1, p2)->(p2.getDiscount() - p1.getDiscount()));

        }
    }

    private class ContentPanel extends JPanel{
        private CustomProductCard []productsDisplay = new CustomProductCard[maxDisplayableProducts];
        public ContentPanel(){
            setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));
            for(int i = 0; i < maxDisplayableProducts; i++){
                productsDisplay[i] = new CustomProductCard();
                productsDisplay[i].setBackground(Color.CYAN);
                add(productsDisplay[i]);
            }
        }
        public void display(ArrayList<Product> products, int startIndex){
            System.out.printf("Display product, start index = %d(total Products in array = %d\n", startIndex, products.size());
            for(int i = 0; i < maxDisplayableProducts; i++){
                if(startIndex + i < products.size()){
                    productsDisplay[i].setProduct(products.get(i + startIndex));
                    productsDisplay[i].setVisible(true);
                }
                else{
                    productsDisplay[i].setVisible(false);
                }
            }
        }
    }


    private class CustomProductCard extends ProductCard{
        private JButton showDetailButton = new JButton("Xem chi tiết");
        private Product product;

        public CustomProductCard() {
            super();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;      gbc.gridy = 8;
            gbc.gridwidth = 5;  gbc.gridheight = 2;
            add(showDetailButton, gbc);

            showDetailButton.addActionListener(e->{
                doAfterClickToProduct.doing(product);
            });
        }

        public CustomProductCard(Product product) {
            this();
            setProduct(product);
        }

        @Override
        public void setProduct(Product product) {
            super.setProduct(product);
            this.product = product;
            String name = "<p style = 'color:black; font-size:15'>" +  product.getName()+"</p>";

            String price = "";
            if(product.getDiscount() > 0){
                price = String.format(
                        "<p style = 'color:red; font-size:15'>Sale%d%% <span style='color:#FF9999;font-size:15'><strike>%.01f </strike></span>%.01f</p>",
                        product.getDiscount(),product.getPrice(), product.getFinalPrice()
                );
            }
            else{
                price = String.format(
                        "<p style = 'color:red; font-size:15'>%.01f</p>",
                        product.getPrice()
                );
            }

            String quanity = "";
            if(product.getQuantity() == 0){
                quanity = "<p style = 'color:black; font-size:15'>Sản phẩm sẽ được làm khi có đơn hàng</p>";
            }else{
                quanity = String.format("<p style = 'color:black; font-size:15'>Có sẵn để giao(còn %d sản phẩm)</p>", product.getQuantity());
            }
            String html = "<html>" + name + price + quanity +"</html>";
            contentLabel.setText(html);
        }
    }
}