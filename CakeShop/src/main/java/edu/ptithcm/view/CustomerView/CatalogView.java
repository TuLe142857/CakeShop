package edu.ptithcm.view.CustomerView;

import edu.ptithcm.controller.CategoryProcess;
import edu.ptithcm.controller.ProductProcess;
import edu.ptithcm.model.Data.Category;
import edu.ptithcm.model.Data.Product;
import edu.ptithcm.model.Data.User;
import edu.ptithcm.util.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CatalogView extends JPanel{

    private FilterPanel filterBar;
    private ContentPanel contentPanel;
    private PaginationPanel paginationPanel;
    private static final int maxDisplayableProducts = 30;

    private User user;
    public CatalogView(User user){
        this.user = user;

        //INIT COMPONENT
        filterBar = new FilterPanel();

        contentPanel = new ContentPanel();
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        paginationPanel = new PaginationPanel();

        //ADD ACTION
        setActionLisener();

        setLayout(new BorderLayout());
        add(filterBar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(paginationPanel, BorderLayout.SOUTH);

        reloadData();
    }

    /**
     * Cap nhat du lieu tu database
     */
    public void reloadData(){
        filterBar.reload();
        int maxPage = 0;
        int count  = 0;
        do{
            maxPage ++ ;
            count += maxDisplayableProducts;
        }while(count < filterBar.getTotalProductsFiltered());
        paginationPanel.setPageInfor(maxPage, 1);
        contentPanel.display(filterBar.filter(), paginationPanel.getFirstProductIndexInPage());
    }


    private void setActionLisener(){
        ActionListener action = (e)->{
            System.out.println("refilter");
            filterBar.filter();

            int maxPage = 0;
            int count  = 0;
            do{
                maxPage ++ ;
                count += maxDisplayableProducts;
            }while(count < filterBar.getTotalProductsFiltered());
            paginationPanel.setPageInfor(maxPage, paginationPanel.currentPage);

            contentPanel.display(filterBar.products, paginationPanel.getFirstProductIndexInPage());
        };

        filterBar.categoriesComboBox.addActionListener(action);
        filterBar.priorityComboBox.addActionListener(action);
        filterBar.availableProductCheckBox.addActionListener(action);
        filterBar.reloadButton.addActionListener(e->reloadData());

        paginationPanel.prevPageButton.addActionListener(e->{
            if(paginationPanel.canGotoPreviousPage()){
                paginationPanel.toPrevPage();
                contentPanel.display(filterBar.products, paginationPanel.getFirstProductIndexInPage());
            }
        });

        paginationPanel.nextPageButton.addActionListener(e->{
            if(paginationPanel.canGotoNextPage()){
                paginationPanel.toNextPage();
                contentPanel.display(filterBar.products, paginationPanel.getFirstProductIndexInPage());
            }
        });
    }

    private class FilterPanel extends JPanel{

        private static final String PRIOR_LOW_PRICE = "Gia thap";
        private static final String PRIOR_HIGH_PRICE = "Gia cao";
        private static final String PRIOR_IS_SALING = "Dang giam gia";

        private static final Category SELECT_ALL_CATEGORY;
        static{
            SELECT_ALL_CATEGORY = new Category(-999, "Chon tat ca", "Select all category option");
        }
        private JComboBox<Category> categoriesComboBox = new JComboBox<>();
        private JComboBox<String> priorityComboBox = new JComboBox<>();
        private JCheckBox availableProductCheckBox = new JCheckBox("San pham co san de giao");
        private JButton reloadButton = new JButton("Reload Page");

        private ArrayList<Product> products;

        public FilterPanel(){
            JLabel cLabel = new JLabel("Category");
            cLabel.setHorizontalAlignment(JLabel.RIGHT);
            reload();

            JLabel pLabel = new JLabel("Priority");
            pLabel.setHorizontalAlignment(JLabel.RIGHT);
            priorityComboBox.addItem(PRIOR_LOW_PRICE);
            priorityComboBox.addItem(PRIOR_HIGH_PRICE);
            priorityComboBox.addItem(PRIOR_IS_SALING);

            add(cLabel);    add(categoriesComboBox);
            add(pLabel);    add(priorityComboBox);
            add(availableProductCheckBox);
            add(reloadButton);
        }

        public int getTotalProductsFiltered(){
            if(products == null)
                return 0;
            return products.size();
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


        public ArrayList<Product> filter(){
            Category c = (Category) categoriesComboBox.getSelectedItem();

            //TH database empty
            if(c == null)
                return null;

            if(c.getId() == SELECT_ALL_CATEGORY.getId()){
                products = ProductProcess.selectAllProductInBussiness(availableProductCheckBox.isSelected());
            }else{
                products = ProductProcess.selectByFilter(c.getId(), availableProductCheckBox.isSelected());
            }

            //SHORT
            String priority = (String)priorityComboBox.getSelectedItem();
            if(priority.compareTo(PRIOR_LOW_PRICE ) == 0){
                products.sort((p1, p2)->{
                    double cost1 = p1.getFinalPrice();
                    double cost2 = p2.getFinalPrice();
                    if(cost1 < cost2)
                        return -1;
                    if(cost1 > cost2)
                        return 1;
                    return 0;
                });
            }
            else if(priority.compareTo(PRIOR_HIGH_PRICE) == 0){
                products.sort((p1, p2)->{
                    double cost1 = p1.getFinalPrice();
                    double cost2 = p2.getFinalPrice();
                    if(cost1 < cost2)
                        return 1;
                    if(cost1 > cost2)
                        return -1;
                    return 0;
                });
            }
            else if(priority.compareTo(PRIOR_IS_SALING) == 0){
                products.sort((p1, p2)->(p2.getDiscount() - p1.getDiscount()));
            }
            return products;
        }

    }

    private class ContentPanel extends JPanel{
        private ProductCard []productsDisplay = new ProductCard[maxDisplayableProducts];
        public ContentPanel(){
            setLayout(new WrapLayout(FlowLayout.LEFT, 10, 10));

            for(int i = 0; i < maxDisplayableProducts; i++){
//                productsDisplay[i] = new ProductCard();
                productsDisplay[i] = new CustomProductCard();
                productsDisplay[i].setBackground(Color.CYAN);
                add(productsDisplay[i]);
            }
        }

        public void display(ArrayList<Product> products, int startIndex){
            if(products == null){
                System.out.println("Display products, total = 0");
                for(ProductCard p:productsDisplay)
                    p.setVisible(false);
                return;
            }

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

    private class PaginationPanel extends JPanel{
        private JButton prevPageButton = new JButton("Previous Page");
        private JButton nextPageButton = new JButton("Next Page");
        private  JLabel pageLabel = new JLabel();

        private int currentPage = 1;
        private int totalPages = 1;

        public PaginationPanel(){
            pageLabel.setHorizontalAlignment(JLabel.CENTER);
            setLayout(new GridLayout(1, 3, 10, 10));
            add(prevPageButton);
            add(pageLabel);
            add(nextPageButton);

            setPageInfor(1, 1);
        }

        public void setPageInfor(int totalPages, int currentPage){
            if(totalPages < 0)
                throw new IllegalArgumentException("Can not set negative value for totalPages");

            if(!(currentPage >= 1 && currentPage <= totalPages))
                currentPage = 1;

            this.totalPages = totalPages;
            this.currentPage = currentPage;

            String textDisplay = "Page %d/%d";
            pageLabel.setText(String.format(textDisplay, currentPage, totalPages));
        }

        public int getFirstProductIndexInPage(){
            return (currentPage-1)*maxDisplayableProducts;
        }

        public boolean canGotoNextPage(){
            return (currentPage + 1 <= totalPages);
        }
        public void toNextPage(){
            if(!canGotoNextPage())
                throw new IllegalArgumentException(String.format("Can not go to next page because current page is %d/%d", currentPage,totalPages));
            currentPage ++;
            String textDisplay = "Page %d/%d";
            pageLabel.setText(String.format(textDisplay, currentPage, totalPages));
        }

        public boolean canGotoPreviousPage(){
            return (currentPage >= 2);
        }

        public void toPrevPage(){
            if(!canGotoPreviousPage())
                throw new IllegalArgumentException(String.format("Can not go to previous page because current page is %d/%d", currentPage,totalPages));
            currentPage --;
            String textDisplay = "Page %d/%d";
            pageLabel.setText(String.format(textDisplay, currentPage, totalPages));
        }
    }

    private class CustomProductCard extends ProductCard{
        private JButton showDetailButton = new JButton("Xem chi tiết");
        private int productID;

        public CustomProductCard() {
            super();
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;      gbc.gridy = 8;
            gbc.gridwidth = 5;  gbc.gridheight = 2;
            add(showDetailButton, gbc);

            showDetailButton.addActionListener(e->{
                JOptionPane.showMessageDialog(null,
                        "Chức năng này đang làm",
                        "Chờ chút :))",
                        JOptionPane.PLAIN_MESSAGE);
            });
        }

        public CustomProductCard(Product product) {
            this();
            setProduct(product);
        }

        @Override
        public void setProduct(Product product) {
            super.setProduct(product);
            this.productID = product.getId();
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