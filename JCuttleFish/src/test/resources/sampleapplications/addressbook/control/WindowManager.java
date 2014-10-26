/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;

/**
 *
 * @author Γρηγόρης
 */
public class WindowManager implements WindowListener {
    
    /**
     * Create and show a new jFrame
     */
    public void openFrame(JFrame frame)
    {   
        frame.setVisible(true);
        frame.addWindowListener(this);
    }
   
    /**
     * Hide a specific jFrame given as parameter
     * @param frame 
     */
    public void hideFrame(JFrame frame)
    {
        frame.setVisible(false);
    }
    
    /**
     * Close a running JFrame
     * @param frame 
     */
     public void closeFrame(JFrame frame)
    {
        frame.dispose();
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //nothing
    }

    @Override
    public void windowClosing(WindowEvent e) {
        //nothnig
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //nothing
    }

    @Override
    public void windowIconified(WindowEvent e) {
        //nothing
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        //nothing
    }

    @Override
    public void windowActivated(WindowEvent e) {
        //nothing
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        //nothing
    }
}
