package edu.ptithcm.util;
import javax.swing.*;
import java.awt.*;

public class PaginationPanel extends JPanel{
    public static final int CHANGE_TO_NEXT_PAGE = 0;
    public static final int CHANGE_TO_PREVIOUS_PAGE = 1;
    public static final int CHANGE_TOTALPAGE_RESET_CURRENT_PAGE_TO_1 = 3;
    public interface PageChangeLisener
    {
        public void Action(int event);
    }

    private JButton nextPageButton = new JButton("NextPage");
    private JButton prevPageButton = new JButton("PreviousPage");
    private JLabel pageLabel = new JLabel();
    private PageChangeLisener pageChangeLisener = (e)->{};

    private int totalPage = 1;
    private int currentPage = 1;

    public PaginationPanel(Component component){
        if(component == null)
            throw new IllegalArgumentException("Can not add null component to PaginationPanel");

        JPanel pagePanel = new JPanel(new GridLayout(1, 3, 10, 10));
        pagePanel.add(prevPageButton);
        pagePanel.add(pageLabel);
        pagePanel.add(nextPageButton);

        pageLabel.setText(String.format("page %d/%d", currentPage, totalPage));

        nextPageButton.addActionListener(e->{
            if(currentPage < totalPage){
                currentPage ++;
                pageLabel.setText(String.format("page %d/%d", currentPage, totalPage));
                pageChangeLisener.Action(CHANGE_TO_NEXT_PAGE);
            }
        });

        prevPageButton.addActionListener(e->{
            if(currentPage > 1){
                currentPage --;
                pageLabel.setText(String.format("page %d/%d", currentPage, totalPage));
                pageChangeLisener.Action(CHANGE_TOTALPAGE_RESET_CURRENT_PAGE_TO_1);

            }
        });

        setLayout(new BorderLayout());
        add(component, BorderLayout.CENTER);
        add(pagePanel, BorderLayout.SOUTH);
    }

    /**
     * Change total page and set current page to 1
     */
    public void changeTotalPage(int totalPage) {
        if(totalPage < 0)
            throw new IllegalArgumentException("Can not set totalPage < 0");
        if(totalPage == 0) totalPage++;
        this.totalPage = totalPage;
        currentPage = 1;
        pageLabel.setText(String.format("page %d/%d", currentPage, totalPage));
        pageChangeLisener.Action(CHANGE_TOTALPAGE_RESET_CURRENT_PAGE_TO_1);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setPageChangeLisener(PageChangeLisener pageChangeLisener){
        this.pageChangeLisener = pageChangeLisener;
    }

    public int getTotalPage() {
        return totalPage;
    }

}