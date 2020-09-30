package com.usac.ipc1.p2;

import javax.swing.UIManager;
import javax.swing.SwingUtilities;

/**
 *
 * @author erick
 */
public class Plotter {  

    public static void main(String args[]){
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e){
        }
        SwingUtilities.invokeLater(()->{
            PlotterFrame app = new PlotterFrame();
            app.setLocationRelativeTo(null);
            app.setVisible(true);  
        });
    }
}
