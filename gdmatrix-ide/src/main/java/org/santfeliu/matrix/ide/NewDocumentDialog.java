/*
 * GDMatrix
 *  
 * Copyright (C) 2020, Ajuntament de Sant Feliu de Llobregat
 *  
 * This program is licensed and may be used, modified and redistributed under 
 * the terms of the European Public License (EUPL), either version 1.1 or (at 
 * your option) any later version as soon as they are approved by the European 
 * Commission.
 *  
 * Alternatively, you may redistribute and/or modify this program under the 
 * terms of the GNU Lesser General Public License as published by the Free 
 * Software Foundation; either  version 3 of the License, or (at your option) 
 * any later version. 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 *    
 * See the licenses for the specific language governing permissions, limitations 
 * and more details.
 *    
 * You should have received a copy of the EUPL1.1 and the LGPLv3 licenses along 
 * with this program; if not, you may find them at: 
 *    
 * https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 * http://www.gnu.org/licenses/ 
 * and 
 * https://www.gnu.org/licenses/lgpl.txt
 */
package org.santfeliu.matrix.ide;

import java.awt.Component;
import java.util.ArrayList;

/**
 *
 * @author real
 */
public class NewDocumentDialog extends javax.swing.JDialog
{
  private DocumentType documentType;

  /** Creates new form NewDocumentDialog */
  public NewDocumentDialog(java.awt.Frame parent, boolean modal)
  {
    super(parent, modal);
    initComponents();
  }

  public DocumentType showDialog(Component parent,
    ArrayList<DocumentType> documentTypes)
  {
    Object[] elems = documentTypes.toArray();
    documentTypesList.setListData(elems);
    if (elems.length > 0) documentTypesList.setSelectedIndex(0);
    setSize(220, 300);
    setMinimumSize(getSize());
    setLocationRelativeTo(parent);
    pack();
    setVisible(true);
    return documentType;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents()
  {

    centerPanel = new javax.swing.JPanel();
    documentTypesLabel = new javax.swing.JLabel();
    scrollPane = new javax.swing.JScrollPane();
    documentTypesList = new javax.swing.JList();
    southPanel = new javax.swing.JPanel();
    acceptButton = new javax.swing.JButton();
    cancelButton = new javax.swing.JButton();

    setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
    setTitle("New document");

    centerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 6, 4, 6));
    centerPanel.setLayout(new java.awt.BorderLayout());

    documentTypesLabel.setText("Document types:");
    documentTypesLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 0, 4, 0));
    centerPanel.add(documentTypesLabel, java.awt.BorderLayout.NORTH);

    documentTypesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    documentTypesList.setCellRenderer(new DocumentTypeCellRenderer());
    scrollPane.setViewportView(documentTypesList);

    centerPanel.add(scrollPane, java.awt.BorderLayout.CENTER);

    getContentPane().add(centerPanel, java.awt.BorderLayout.CENTER);

    acceptButton.setText("Accept");
    acceptButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        acceptButtonActionPerformed(evt);
      }
    });
    southPanel.add(acceptButton);

    cancelButton.setText("Cancel");
    cancelButton.addActionListener(new java.awt.event.ActionListener()
    {
      public void actionPerformed(java.awt.event.ActionEvent evt)
      {
        cancelButtonActionPerformed(evt);
      }
    });
    southPanel.add(cancelButton);

    getContentPane().add(southPanel, java.awt.BorderLayout.PAGE_END);

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_acceptButtonActionPerformed
  {//GEN-HEADEREND:event_acceptButtonActionPerformed
    documentType = (DocumentType)documentTypesList.getSelectedValue();
    setVisible(false);
    dispose();
  }//GEN-LAST:event_acceptButtonActionPerformed

  private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelButtonActionPerformed
  {//GEN-HEADEREND:event_cancelButtonActionPerformed
    documentType = null;
    setVisible(false);
    dispose();
  }//GEN-LAST:event_cancelButtonActionPerformed


  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton acceptButton;
  private javax.swing.JButton cancelButton;
  private javax.swing.JPanel centerPanel;
  private javax.swing.JLabel documentTypesLabel;
  private javax.swing.JList documentTypesList;
  private javax.swing.JScrollPane scrollPane;
  private javax.swing.JPanel southPanel;
  // End of variables declaration//GEN-END:variables
}
