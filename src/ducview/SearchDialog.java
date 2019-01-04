/*     */ package ducview;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ 
/*     */ public class SearchDialog extends javax.swing.JDialog implements java.awt.event.ActionListener
/*     */ {
/*     */   private DucView ducView;
/*     */   private javax.swing.JTextField textField;
/*  12 */   private String text = "";
/*  13 */   private boolean infoOK = false;
/*     */   private JRadioButton inputTextBtn;
/*     */   private JRadioButton scuLabelBtn;
/*     */   
/*     */   public SearchDialog(DucView ducView) {
/*  18 */     super(ducView, "Find", true);
/*     */     
/*  20 */     this.ducView = ducView;
/*     */     
/*  22 */     setDefaultCloseOperation(1);
/*     */     
/*  24 */     JPanel contentPane = (JPanel)getContentPane();
/*  25 */     contentPane.setLayout(new javax.swing.BoxLayout(contentPane, 1));
/*  26 */     contentPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
/*     */     
/*     */ 
/*  29 */     contentPane.registerKeyboardAction(this, "cancel", javax.swing.KeyStroke.getKeyStroke(27, 0), 2);
/*     */     
/*  31 */     JPanel labelPanel = new JPanel();
/*  32 */     labelPanel.add(new javax.swing.JLabel("Enter the regular expression to search for"));
/*  33 */     contentPane.add(labelPanel);
/*     */     
/*     */ 
/*  36 */     this.textField = new javax.swing.JTextField() { public Dimension getMaximumSize() { return new Dimension(Integer.MAX_VALUE, getPreferredSize().height); } };
/*  37 */     this.textField.addActionListener(this);
/*  38 */     this.textField.setActionCommand("ok");
/*  39 */     contentPane.add(this.textField);
/*     */     
/*  41 */     JPanel radioPanel = new JPanel();
/*  42 */     javax.swing.ButtonGroup buttonGroup = new javax.swing.ButtonGroup();
/*     */     
/*  44 */     this.inputTextBtn = new JRadioButton("Search text");
/*  45 */     this.inputTextBtn.setMnemonic('t');
/*  46 */     buttonGroup.add(this.inputTextBtn);
/*  47 */     radioPanel.add(this.inputTextBtn);
/*     */     
/*  49 */     this.scuLabelBtn = new JRadioButton("Search SCU labels");
/*  50 */     this.scuLabelBtn.setMnemonic('l');
/*  51 */     buttonGroup.add(this.scuLabelBtn);
/*  52 */     radioPanel.add(this.scuLabelBtn);
/*     */     
/*  54 */     contentPane.add(radioPanel);
/*     */     
/*  56 */     contentPane.add(javax.swing.Box.createVerticalStrut(5));
/*     */     
/*  58 */     JPanel buttonsPanel = new JPanel();
/*     */     
/*  60 */     JButton okBtn = new JButton("OK");
/*  61 */     okBtn.setActionCommand("ok");
/*  62 */     okBtn.addActionListener(this);
/*  63 */     buttonsPanel.add(okBtn);
/*     */     
/*  65 */     buttonsPanel.add(javax.swing.Box.createHorizontalStrut(10));
/*     */     
/*  67 */     JButton cancelBtn = new JButton("Cancel");
/*  68 */     cancelBtn.setActionCommand("cancel");
/*  69 */     cancelBtn.addActionListener(this);
/*  70 */     buttonsPanel.add(cancelBtn);
/*     */     
/*  72 */     okBtn.setPreferredSize(cancelBtn.getPreferredSize());
/*     */     
/*  74 */     buttonsPanel.setMaximumSize(buttonsPanel.getPreferredSize());
/*  75 */     contentPane.add(buttonsPanel);
/*     */     
/*  77 */     addWindowListener(new java.awt.event.WindowAdapter()
/*     */     {
/*     */       public void windowActivated(java.awt.event.WindowEvent e) {
/*  80 */         SearchDialog.this.textField.requestFocusInWindow();
/*     */       }
/*     */       
/*  83 */     });
/*  84 */     pack();
/*     */   }
/*     */   
/*     */   public void actionPerformed(java.awt.event.ActionEvent e)
/*     */   {
/*  89 */     if (e.getActionCommand().equals("ok"))
/*     */     {
/*  91 */       this.infoOK = true;
/*  92 */       this.text = this.textField.getText();
/*     */     }
/*  94 */     hide();
/*     */   }
/*     */   
/*     */   public String getSearchString()
/*     */   {
/*  99 */     this.infoOK = false;
/* 100 */     this.textField.setText(this.text);
/* 101 */     this.textField.setSelectionStart(0);
/* 102 */     this.textField.setSelectionEnd(this.text.length());
/* 103 */     this.textField.requestFocus();
/*     */     
/* 105 */     if ((!this.inputTextBtn.isSelected()) && (!this.scuLabelBtn.isSelected()))
/*     */     {
/* 107 */       if (this.ducView.isPeerLoaded) {
/* 108 */         this.scuLabelBtn.setSelected(true);
/*     */       } else {
/* 110 */         this.inputTextBtn.setSelected(true);
/*     */       }
/*     */     }
/*     */     
/* 114 */     Dimension ducViewSize = this.ducView.getSize();
/* 115 */     java.awt.Point topLeft = this.ducView.getLocationOnScreen();
/* 116 */     Dimension mySize = getSize();
/*     */     //int x;
/*     */     int x;
/* 119 */     if (ducViewSize.width > mySize.width) {
/* 120 */       x = (ducViewSize.width - mySize.width) / 2 + topLeft.x;
/*     */     } else
/* 122 */       x = topLeft.x;
/*     */     //int y;
/* 124 */     int y; if (ducViewSize.height > mySize.height) {
/* 125 */       y = (ducViewSize.height - mySize.height) / 2 + topLeft.y;
/*     */     } else
/* 127 */       y = topLeft.y;
/* 128 */     setLocation(x, y);
/*     */     
/* 130 */     show();
/*     */     
/* 132 */     if (this.infoOK) {
/* 133 */       return this.text;
/*     */     }
/* 135 */     return null;
/*     */   }
/*     */   
/*     */   public boolean isSearchingText()
/*     */   {
/* 140 */     return this.inputTextBtn.isSelected();
/*     */   }
/*     */   
/*     */   public static void main(String[] s)
/*     */   {
/* 145 */     new SearchDialog(null).show();
/*     */   }
/*     */ }


/* Location:              /home/grads/szb754/Downloads/DUCView-1.4.jar!/ducview/SearchDialog.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */