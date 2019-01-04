/*    */ package ducview;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class SCUContributorPart implements Serializable {
/*    */   private int startIndex;
/*    */   private int endIndex;
/*    */   private String text;
/*    */   
/* 10 */   public SCUContributorPart(int startIndex, int endIndex, String text) { this.startIndex = startIndex;
/* 11 */     this.endIndex = endIndex;
/* 12 */     this.text = text;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 17 */     return this.text;
/*    */   }
/*    */   
/*    */   public int getStartIndex()
/*    */   {
/* 22 */     return this.startIndex;
/*    */   }
/*    */   
/*    */   public int getEndIndex()
/*    */   {
/* 27 */     return this.endIndex;
/*    */   }
/*    */   
/*    */   public String getText()
/*    */   {
/* 32 */     return this.text;
/*    */   }
/*    */ }


/* Location:              /home/grads/szb754/Downloads/DUCView-1.4.jar!/ducview/SCUContributorPart.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */
