/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ailife;

import java.awt.EventQueue;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author baDcoder
 */
public class AiLife {

    public static void main(String[]args)
   {
       EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    try {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    } catch (UnsupportedLookAndFeelException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                }
                     DrawImageOnJFrame diojf=new DrawImageOnJFrame();
               
            }

        });
   
   }
    
}
