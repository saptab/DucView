/*     */ package ducview;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.UIDefaults;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.text.Style;
/*     */ import javax.swing.text.StyleConstants;
/*     */ import javax.swing.text.StyledDocument;
/*     */ 
/*     */ public class SCUTextPane extends javax.swing.JTextPane implements javax.swing.event.CaretListener, java.awt.event.ActionListener
/*     */ {
/*  14 */   private boolean ignoreNextCaretUpdate = false;
/*  15 */   private boolean ignoreAllCaretUpdates = false;
/*  16 */   String selectedText = null;
/*  17 */   int selectionStart = -1;
/*  18 */   int selectionEnd = -1;
/*     */   short[] selectionIndexes;
/*     */   short[] highlightIndexes;
/*  21 */   SCUTree tree = null;
/*     */   private JPopupMenu popupMenu;
/*     */   
/*     */   public SCUTextPane()
/*     */   {
/*  26 */     setEditable(false);
/*  27 */     addCaretListener(this);
/*  28 */     setPreferredSize(new java.awt.Dimension(500, 500));
/*  29 */     addStyle("plain", null);
/*  30 */     Style grayedStyle = addStyle("grayed", null);
/*  31 */     StyleConstants.setForeground(grayedStyle, java.awt.Color.blue);
/*  32 */     Style selectedStyle = addStyle("selected", null);
/*  33 */     StyleConstants.setBackground(selectedStyle, UIManager.getLookAndFeelDefaults().getColor("TextArea.selectionBackground"));
/*     */     
/*     */ 
/*  36 */     StyleConstants.setForeground(selectedStyle, UIManager.getLookAndFeelDefaults().getColor("TextArea.selectionForeground"));
/*     */     
/*     */ 
/*  39 */     Style undelinedStyle = addStyle("highlighted", null);
/*  40 */     StyleConstants.setBackground(undelinedStyle, new java.awt.Color(255, 255, 100));
/*     */     
/*  42 */     this.popupMenu = new JPopupMenu("SCUs in which this text appears");
/*     */   }
/*     */   
/*  45 */   public void setTree(SCUTree tree) { this.tree = tree; }
/*     */   
/*     */   public void updateSelectedStyle()
/*     */   {
/*  49 */     Style selectedStyle = addStyle("selected", null);
/*  50 */     StyleConstants.setBackground(selectedStyle, UIManager.getLookAndFeelDefaults().getColor("TextArea.selectionBackground"));
/*     */     
/*     */ 
/*  53 */     StyleConstants.setForeground(selectedStyle, UIManager.getLookAndFeelDefaults().getColor("TextArea.selectionForeground"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getSelectedText()
/*     */   {
/*  60 */     return this.selectedText;
/*     */   }
/*     */   
/*     */   public int getSelectionStartIndex()
/*     */   {
/*  65 */     return this.selectionStart;
/*     */   }
/*     */   
/*     */   public int getSelectionEndIndex()
/*     */   {
/*  70 */     return this.selectionEnd;
/*     */   }
/*     */   
/*     */   public void setIgnoreNextCaretUpdate()
/*     */   {
/*  75 */     this.ignoreNextCaretUpdate = true;
/*     */   }
/*     */   
/*     */   public void caretUpdate(javax.swing.event.CaretEvent e)
/*     */   {
/*  80 */     if (getHighlighter() == null) {
/*  81 */       return;
/*     */     }
/*  83 */     if (this.ignoreNextCaretUpdate)
/*     */     {
/*  85 */       this.ignoreNextCaretUpdate = false;
/*  86 */       return;
/*     */     }
/*     */     
/*  89 */     if ((this.ignoreAllCaretUpdates) || (getText() == null) || (getText().length() == 0))
/*     */     {
/*  91 */       return;
/*     */     }
/*     */     
/*  94 */     if (getSelectionEnd() - getSelectionStart() > 0)
/*     */     {
/*  96 */       this.selectionStart = getSelectionStart();
/*  97 */       this.selectionEnd = getSelectionEnd();
/*     */       
/*     */ 
/* 100 */       char[] buff = getText().replaceAll("[\\t\\n\\r\\f\\,\\.\\-\\:]", " ").toCharArray();
/*     */       
/*     */ 
/*     */ 
/* 104 */       if (buff[this.selectionStart] != ' ')
/*     */       {
/*     */ 
/* 107 */         while ((this.selectionStart > 0) && (buff[(this.selectionStart - 1)] != ' '))
/*     */         {
/* 109 */           this.selectionStart -= 1;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 115 */       while ((this.selectionStart < buff.length - 1) && (buff[this.selectionStart] == ' '))
/*     */       {
/* 117 */         this.selectionStart += 1;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 122 */       if (buff[(this.selectionEnd - 1)] != ' ')
/*     */       {
/*     */ 
/* 125 */         while ((this.selectionEnd < buff.length - 1) && (buff[this.selectionEnd] != ' '))
/*     */         {
/* 127 */           this.selectionEnd += 1;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 133 */       while ((this.selectionEnd > 0) && (buff[(this.selectionEnd - 1)] == ' '))
/*     */       {
/* 135 */         this.selectionEnd -= 1;
/*     */       }
/*     */       
/* 138 */       this.selectedText = getText().substring(this.selectionStart, this.selectionEnd);
/* 139 */       this.ignoreNextCaretUpdate = true;
/* 140 */       select(getSelectionEnd(), getSelectionEnd());
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 145 */       this.selectedText = null;
/*     */       
/*     */ 
/* 148 */       Vector scuNodes = this.tree.getSCUNodesContainingIndex(getSelectionStart());
/*     */       
/* 150 */       if (scuNodes.size() == 1)
/*     */       {
/* 152 */         this.tree.selectSCUNode(((SCU)scuNodes.get(0)).getId());
/*     */       }
/* 154 */       else if (scuNodes.size() > 1)
/*     */       {
/* 156 */         this.popupMenu.removeAll();
/*     */         
/* 158 */         java.util.Enumeration scuNodeEnum = scuNodes.elements();
/* 159 */         while (scuNodeEnum.hasMoreElements())
/*     */         {
/* 161 */           SCU scu = (SCU)scuNodeEnum.nextElement();
/* 162 */           JMenuItem menuItem = new JMenuItem(scu.toString());
/* 163 */           menuItem.setActionCommand(String.valueOf(scu.getId()));
/* 164 */           menuItem.addActionListener(this);
/* 165 */           this.popupMenu.add(menuItem);
/*     */         }
/* 167 */         this.popupMenu.pack();
/*     */         try
/*     */         {
/* 170 */           java.awt.Rectangle rect = modelToView(getSelectionStart());
/* 171 */           this.popupMenu.show(this, rect.x, rect.y);
/*     */         } catch (javax.swing.text.BadLocationException ex) {
/* 173 */           ex.printStackTrace();
/*     */         }
/*     */       } }
/* 176 */     updateTextColors();
/*     */   }
/*     */   
/*     */   public void loadFile(java.io.File file) throws java.io.IOException
/*     */   {
/* 181 */     java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(file));
/* 182 */     StringBuffer buffer = new StringBuffer();
/*     */     String line;
/* 184 */     while ((line = reader.readLine()) != null)
/*     */     {
/* 186 */       buffer.append(line);
/* 187 */       buffer.append("\n");
/*     */     }
/* 189 */     loadText(buffer.toString());
/* 190 */     reader.close();
/*     */   }
/*     */   
/*     */   public void showText(final int position)
/*     */   {
/* 195 */     boolean prevVal = this.ignoreAllCaretUpdates;
/* 196 */     this.ignoreAllCaretUpdates = true;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 209 */     setCaretPosition(getText().length());
/* 210 */     javax.swing.SwingUtilities.invokeLater(new Runnable() { public void run() { SCUTextPane.this.setCaretPosition(position); } });
/* 211 */     this.ignoreAllCaretUpdates = false;
/* 212 */     this.ignoreAllCaretUpdates = prevVal;
/* 213 */     this.ignoreNextCaretUpdate = true;
/*     */   }
/*     */   
/*     */   public void loadText(String text)
/*     */   {
/* 218 */     this.ignoreAllCaretUpdates = true;
/* 219 */     setText(text);
/* 220 */     setCaretPosition(0);
/* 221 */     this.ignoreAllCaretUpdates = false;
/*     */     
/* 223 */     this.selectionIndexes = new short[getText().length()];
/* 224 */     for (int i = 0; i < this.selectionIndexes.length; i++)
/*     */     {
/* 226 */       this.selectionIndexes[i] = 0;
/*     */     }
/*     */     
/* 229 */     this.highlightIndexes = new short[getText().length()];
/* 230 */     for (int i = 0; i < this.highlightIndexes.length; i++)
/*     */     {
/* 232 */       this.highlightIndexes[i] = 0;
/*     */     }
/*     */   }
/*     */   
/*     */   //private final int val$position;
/*     */   public void modifyTextSelection(int startIndex, int endIndex, boolean selectionAdded)
/*     */   {
/* 239 */     int increment = selectionAdded ? 1 : -1;
/* 240 */     if (selectionAdded)
/*     */     {
/* 242 */       this.selectedText = null;
/*     */     }
/*     */     
/* 245 */     for (int i = startIndex; i <= endIndex; i++)
/*     */     {
/* 247 */       int tmp35_33 = i; short[] tmp35_30 = this.selectionIndexes;tmp35_30[tmp35_33] = ((short)(tmp35_30[tmp35_33] + increment));
/*     */     }
/* 249 */     updateTextColors();
/*     */   }
/*     */   
/*     */ 
/*     */   public void modifyTextHighlight(int startIndex, int endIndex, boolean selectionAdded)
/*     */   {
/* 255 */     int increment = selectionAdded ? 1 : -1;
/*     */     
/* 257 */     for (int i = startIndex; i <= endIndex; i++)
/*     */     {
/* 259 */       int tmp26_24 = i; short[] tmp26_21 = this.highlightIndexes;tmp26_21[tmp26_24] = ((short)(tmp26_21[tmp26_24] + increment));
/* 260 */       if (this.highlightIndexes[i] < 0)
/*     */       {
/* 262 */         this.highlightIndexes[i] = 0;
/*     */       }
/*     */     }
/* 265 */     updateTextColors();
/*     */   }
/*     */   
/*     */   private void updateTextColors()
/*     */   {
/* 270 */     getStyledDocument().setCharacterAttributes(0, getText().length(), getStyle("plain"), true);
/*     */     
/*     */ 
/* 273 */     for (int i = 0; i < this.selectionIndexes.length; i++)
/*     */     {
/* 275 */       if (this.selectionIndexes[i] > 0)
/*     */       { int j;
/*     */ 
/* 278 */         for (j = i + 1; (j < this.selectionIndexes.length) && (this.selectionIndexes[j] > 0); j++) {}
/*     */         
/*     */ 
/*     */ 
/* 282 */         getStyledDocument().setCharacterAttributes(i, j - i, getStyle("grayed"), true);
/*     */         
/* 284 */         i = j;
/*     */       }
/*     */     }
/*     */     
/* 288 */     for (int i = 0; i < this.highlightIndexes.length; i++)
/*     */     {
/* 290 */       if (this.highlightIndexes[i] > 0)
/*     */       { int j;
/*     */ 
/* 293 */         for (j = i + 1; (j < this.highlightIndexes.length) && (this.highlightIndexes[j] > 0); j++) {}
/*     */         
/*     */ 
/*     */ 
/* 297 */         getStyledDocument().setCharacterAttributes(i, j - i - 1, getStyle("highlighted"), false);
/*     */         
/* 299 */         i = j;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 304 */     if ((getSelectedText() != null) && (getSelectedText().length() > 0)) {
/* 305 */       getStyledDocument().setCharacterAttributes(getSelectionStartIndex(), getSelectionEndIndex() - getSelectionStartIndex(), getStyle("selected"), true);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void actionPerformed(java.awt.event.ActionEvent e)
/*     */   {
/* 312 */     this.tree.selectSCUNode(Integer.parseInt(e.getActionCommand()));
/*     */   }
/*     */ }


/* Location:              /home/grads/szb754/Downloads/DUCView-1.4.jar!/ducview/SCUTextPane.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */
