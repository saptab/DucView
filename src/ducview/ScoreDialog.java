/*    */ package ducview;
/*    */ 
/*    */ import java.awt.Container;
/*    */ import java.awt.Dimension;
/*    */ import javax.swing.JTextPane;
/*    */ 
/*    */ public class ScoreDialog extends javax.swing.JDialog
/*    */ {
/*    */   private DucView ducView;
/*    */   private JTextPane scoreTextPane;
/*    */   
/*    */   public ScoreDialog(DucView ducView)
/*    */   {
/* 14 */     super(ducView, "Peer annotation score", false);
/*    */     
/* 16 */     this.ducView = ducView;
/*    */     
/* 18 */     setDefaultCloseOperation(0);
/* 19 */     addWindowListener(new ScoreDialogWindowAdapter(this));
/* 20 */     this.scoreTextPane = new JTextPane()
/*    */     {
/*    */       public boolean getScrollableTracksViewportWidth()
/*    */       {
/* 24 */         return false;
/*    */       }
/*    */       
/* 27 */     };
/* 28 */     this.scoreTextPane = new ScoreTextPane();
/* 29 */     this.scoreTextPane.setEditable(false);
/* 30 */     this.scoreTextPane.setContentType("text/html");
/* 31 */     getContentPane().add(new javax.swing.JScrollPane(this.scoreTextPane));
/*    */   }
/*    */   
/*    */   public void setText(String text)
/*    */   {
/* 36 */     this.scoreTextPane.setText(text);
/*    */   }
/*    */   
/*    */   private class ScoreDialogWindowAdapter extends java.awt.event.WindowAdapter
/*    */   {
/*    */     ScoreDialog scoreDlg;
/*    */     
/*    */     public ScoreDialogWindowAdapter(ScoreDialog scoreDlg)
/*    */     {
/* 45 */       this.scoreDlg = scoreDlg;
/*    */     }
/*    */     
/*    */     public void windowClosing(java.awt.event.WindowEvent e)
/*    */     {
/* 50 */       this.scoreDlg.setVisible(false);
/* 51 */       this.scoreDlg.ducView.fileShowPeerAnnotationScoreMenuItem.setSelected(false);
/*    */     }
/*    */   }
/*    */   
/* 55 */   private class ScoreTextPane extends JTextPane { 
/*    */     
/*    */ 
/*    */ 
/*    */     public void setSize(Dimension d)
/*    */     {
/* 61 */       if (d.width < getParent().getSize().width) {
/* 62 */         d.width = getParent().getSize().width;
/*    */       }
/* 64 */       super.setSize(d);
/*    */     }
/*    */     
/*    */     public boolean getScrollableTracksViewportWidth()
/*    */     {
/* 69 */       return false;
/*    */     }
/*    */     
/*    */     private ScoreTextPane() {}
/*    */   }
/*    */ }


/* Location:              /home/grads/szb754/Downloads/DUCView-1.4.jar!/ducview/ScoreDialog.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */