/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;
/**
 *
 * @author Aeon
 */
import API.IContact;
import API.IWindowManager;
import control.ContactCheck;
import control.WindowManager;
import control.dao.ContactDAOFactory;
import control.dao.DataToDB;
import ini.DBtype;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Contact;

public class AddContactUI extends javax.swing.JFrame implements IWindowManager {

    /**
     * Create new form AddContactUI
     * and initialise all frame components
     */
    public AddContactUI() {
        
        initComponents();
    }
    
    /**
     * Turns all text input fields to empty
     */
    private void clearfields()
     {
        jTextFieldFname.setText(null);
        jTextFieldLname.setText(null);
        jTextFieldEmail.setText(null);
        jTextFieldTel.setText(null);
        jTextFieldMtel.setText(null);
        jTextFieldAddress.setText(null);
        jTextFieldCity.setText(null);
    }
    
    private IContact createContact()
    {
        String Fname=jTextFieldFname.getText();
        String Lname=jTextFieldLname.getText();
        String Email=jTextFieldEmail.getText();
        String Tel=jTextFieldTel.getText();
        String Mtel=jTextFieldMtel.getText();
        String Address=jTextFieldAddress.getText();
        String City=jTextFieldCity.getText();
        
        Contact newContact = new Contact(Fname,Tel);
        newContact.setLastname(Lname);
        newContact.setEmail(Email);
        newContact.setMtelephone(Mtel);
        newContact.setAddress(Address);
        newContact.setCity(City);
        
        return newContact;
    }
    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelTel = new javax.swing.JLabel();
        jTextFieldMtel = new javax.swing.JTextField();
        jTextFieldLname = new javax.swing.JTextField();
        jLabelAddress = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jTextFieldAddress = new javax.swing.JTextField();
        jLabelEmail = new javax.swing.JLabel();
        jTextFieldFname = new javax.swing.JTextField();
        jTextFieldCity = new javax.swing.JTextField();
        jLabelFname = new javax.swing.JLabel();
        jLabelLname = new javax.swing.JLabel();
        jTextFieldTel = new javax.swing.JTextField();
        jLabelMtel = new javax.swing.JLabel();
        jLabelCity = new javax.swing.JLabel();
        jComboBoxGroup = new javax.swing.JComboBox();
        jLabelGroup = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButtonClear = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Add New Contact");
        setMinimumSize(new java.awt.Dimension(400, 395));
        setPreferredSize(new java.awt.Dimension(400, 395));

        jLabelTel.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabelTel.setText("Telephone");

        jTextFieldMtel.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N

        jTextFieldLname.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N

        jLabelAddress.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabelAddress.setText("Address");

        jTextFieldEmail.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N

        jTextFieldAddress.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N

        jLabelEmail.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabelEmail.setText("Email");

        jTextFieldFname.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        jTextFieldFname.setToolTipText("");

        jTextFieldCity.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N

        jLabelFname.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabelFname.setText("Firstname");

        jLabelLname.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabelLname.setText("Lastname");

        jTextFieldTel.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N

        jLabelMtel.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabelMtel.setText("Mobile Tel.");

        jLabelCity.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabelCity.setText("City");

        jComboBoxGroup.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N

        jLabelGroup.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jLabelGroup.setText("Group");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLname)
                    .addComponent(jLabelEmail)
                    .addComponent(jLabelFname)
                    .addComponent(jLabelTel)
                    .addComponent(jLabelMtel)
                    .addComponent(jLabelAddress)
                    .addComponent(jLabelCity)
                    .addComponent(jLabelGroup))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldFname, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                    .addComponent(jTextFieldLname)
                    .addComponent(jTextFieldEmail)
                    .addComponent(jTextFieldTel)
                    .addComponent(jTextFieldMtel)
                    .addComponent(jTextFieldAddress)
                    .addComponent(jTextFieldCity)
                    .addComponent(jComboBoxGroup, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelFname)
                    .addComponent(jTextFieldFname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLname)
                    .addComponent(jTextFieldLname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmail)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTel)
                    .addComponent(jTextFieldTel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMtel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelMtel))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCity))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxGroup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelGroup))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jButtonClear.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jButtonClear.setText("Clear");
        jButtonClear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonClearActionPerformed(evt);
            }
        });

        jButtonSave.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jButtonSave.setText("Save");
        jButtonSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jButtonClose.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jButtonClose.setText("Cancel");
        jButtonClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonClose, javax.swing.GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                    .addComponent(jButtonSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonClear, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButtonClear)
                .addGap(49, 49, 49)
                .addComponent(jButtonSave)
                .addGap(18, 18, 18)
                .addComponent(jButtonClose)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        jButton1.setText("New group");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(20, 20, 20))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonClearActionPerformed
        clearfields();
    }//GEN-LAST:event_jButtonClearActionPerformed

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        control.WindowManager windowManager = new control.WindowManager();
        windowManager.openFrame(new AddressBookUI());
        windowManager.closeFrame(this);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed

        IContact newContact=createContact();
        
        ContactCheck contactCheck=new ContactCheck(newContact);
        if( contactCheck.isProperContact() )
        {
            try 
            {
                DataToDB dbData = new DataToDB( ContactDAOFactory.getInstance(DBtype.db_type) );
                dbData.saveContact(newContact);
                JOptionPane.showMessageDialog(null,"Contact saved!");
                
                WindowManager windowManager= new WindowManager();
                windowManager.openFrame( new AddressBookUI() );
                windowManager.closeFrame(this);
            }
            catch (Exception ex) 
            {
                JOptionPane.showMessageDialog(null,"error! can't save to database!!");
                Logger.getLogger(AddContactUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Error!! No proper contact to save!");
        }
    }//GEN-LAST:event_jButtonSaveActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonClear;
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JComboBox jComboBoxGroup;
    private javax.swing.JLabel jLabelAddress;
    private javax.swing.JLabel jLabelCity;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelFname;
    private javax.swing.JLabel jLabelGroup;
    private javax.swing.JLabel jLabelLname;
    private javax.swing.JLabel jLabelMtel;
    private javax.swing.JLabel jLabelTel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextFieldAddress;
    private javax.swing.JTextField jTextFieldCity;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldFname;
    private javax.swing.JTextField jTextFieldLname;
    private javax.swing.JTextField jTextFieldMtel;
    private javax.swing.JTextField jTextFieldTel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void addCancelListener(ActionListener listener) {
        
    }

    @Override
    public void addObjectCancelListener(Object listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void addCommitListener(ActionListener listener) {
        
    }

    @Override
    public void addObjectCommitListener(Object listener) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public IWindowManager getOwnerManager() {
        return null;
    }

    @Override
    public JFrame getFrame() {
        return this;
    }

    @Override
    public void setModalRelativeTo(IWindowManager winManager) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void disposeWindow() {
        this.dispose();
    }
}