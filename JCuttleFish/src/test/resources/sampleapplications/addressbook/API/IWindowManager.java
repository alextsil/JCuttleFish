/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package API;

/**
 * The interface which defines the function that standalone windows and dialogs
 * in the UI framework must be capable of supporting.
 * A IWindowManager can display or hide its window,
 * and must be capable of cascading its window
 * relative to other windows on the screen.
 * 
 * @author Γρηγόρης
 */
public interface IWindowManager {
    
    /**
     * Adds an ActionListener which is notified when the user 
     * cancels out of a window.
     * @param listener 
     */
    void addCancelListener(java.awt.event.ActionListener listener);
    
    void addObjectCancelListener(java.lang.Object listener);
    
    /**
     * Adds an ActionListener which is notified
     * when commit processing is complete.
     * @param listener 
     */
    void addCommitListener(java.awt.event.ActionListener listener);
    
    void addObjectCommitListener(java.lang.Object listener);
    
    /**
     * Returns the IWindowManager in relation to which the receiver 
     * has been set modal, or null if the receiver is not modal 
     * relative to another window
     * @return winManager - IWindowManager instance
     */
    IWindowManager getOwnerManager();
    
    /**
     * Returns the window being managed by this IWindowManager
     * @return frame - a JFrame instance
     */
    javax.swing.JFrame getFrame();
    
    //void handleDataException(IllegalUserDataException  ex);
    
    /**
     * Sets the location of the frame being managed relative to a frame
     * managed by the specified IWindowManager
     * @param winManager 
     */
    void setModalRelativeTo(IWindowManager winManager);
    
    
    /**
     * Closes the frame and releases all resources used by the frame
     * and its associated UI components.
     */
    void disposeWindow();
}
