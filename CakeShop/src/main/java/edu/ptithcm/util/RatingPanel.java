package edu.ptithcm.util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * <p>Rating value : [0.0, 5.0]</p>
 * @author Le Ngoc Tu
 */
public class RatingPanel extends JPanel{
    private static ImageIcon []originalIcon = new ImageIcon[11];

    private static final int defaulIconSize = 32;

    private ImageIcon []starIcon = new ImageIcon[11];
    private JButton []starButton = new JButton[5];
    private double ratingValue ;


    static{
        for(int i = 0; i < 11; i++)
            originalIcon[i] = ImageProcess.getImageIcon(ImageProcess.dirAppImage + "star" + String.valueOf(i) + ".png");
    }

    public RatingPanel(){
        this(5, defaulIconSize);
    }

    public RatingPanel(double ratingValue){
        this(ratingValue, defaulIconSize);
    }

    public RatingPanel(double ratingValue, int starIconSize){
        for(int i = 0; i < 11; i++)
            starIcon[i] = ImageProcess.resizeImageIcon(originalIcon[i], starIconSize, starIconSize);

        for(int i = 0; i < 5; i++){
            starButton[i] = new JButton(starIcon[10]);
            starButton[i].setPreferredSize(new Dimension(starIconSize, starIconSize));
            starButton[i].addActionListener(new SetRatingValueWhenClickAction(i));
            starButton[i].setBorderPainted(false);
            add(starButton[i]);
        }

        setRatingValue(ratingValue);
        setBackground(Color.WHITE);
    }

    public void setRatingValue(double ratingValue){
        if(!(ratingValue >= 0 && ratingValue <= 5.0))
            throw new IllegalArgumentException("Rating value must be in range [0.0, 5.0]");
        this.ratingValue = ratingValue;

        for(int i = 0; i < 5; i++){
            if (i < (int) ratingValue) {
                starButton[i].setIcon(starIcon[10]);
            } else if (i == (int) ratingValue) {
                int index = (int) ((ratingValue - (int) ratingValue) * 10);
                starButton[i].setIcon(starIcon[index]);
            } else {
                starButton[i].setIcon(starIcon[0]);
            }
        }
    }

    public double getRatingValue(){
        return ratingValue;
    }

    public void resizeStarIcon(int size){
        for(int i = 0; i < 11; i++){
            starIcon[i] = ImageProcess.resizeImageIcon(originalIcon[i], size, size);
        }
        for(int i = 0; i < 5; i++)
            starButton[i].setPreferredSize(new Dimension(size, size));
        setRatingValue(ratingValue);
    }

    @Override
    public void setBackground(Color c){
        super.setBackground(c);

        if(starButton == null)
            return;
        for(int i = 0; i < 5; i++)
            if(starButton[i] != null)
                starButton[i].setBackground(c);
    }

    public void adjustRatingValueWhenClick(boolean b){
        for(int i = 0; i < 5; i++){
            ActionListener []ac = starButton[i].getActionListeners();
            for(int j = 0; j < ac.length; j++)
                starButton[i].removeActionListener(ac[j]);
            if(b)
                starButton[i].addActionListener(new SetRatingValueWhenClickAction(i));
            else
                starButton[i].addActionListener(event->{});
        }
    }

    private class SetRatingValueWhenClickAction implements ActionListener {
        private int index;
        public SetRatingValueWhenClickAction(int index){
            this.index = index;
        }
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < 5; i++)
                if(i <= index)
                    starButton[i].setIcon(starIcon[10]);
                else
                    starButton[i].setIcon(starIcon[0]);
            ratingValue = (double)index+1;
        }
    }
}
