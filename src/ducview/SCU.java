/*    */ package ducview;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*  5 */ public class SCU implements Serializable { private static int nextAvailableId = 1;
/*    */   private int id;
/*    */   private String label;
/*  8 */   private String comment = "";
/*    */   
/*    */   public SCU(String label)
/*    */   {
/* 12 */     this.id = (nextAvailableId++);
/* 13 */     this.label = label;
/*    */   }
/*    */   
/*    */   public SCU(int id, String label)
/*    */   {
/* 18 */     this(id, label, "");
/*    */   }
/*    */   
/*    */   public SCU(int id, String label, String comment)
/*    */   {
/* 23 */     this.id = id;
/* 24 */     if (id >= nextAvailableId)
/* 25 */       nextAvailableId = id + 1;
/* 26 */     this.label = label;
/* 27 */     this.comment = comment;
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 32 */     return this.label;
/*    */   }
/*    */   
/* 35 */   public String getComment() { return this.comment; }
/* 36 */   public void setComment(String comment) { this.comment = comment; }
/*    */   
/* 38 */   public void setLabel(String label) { this.label = label; }
/*    */   
/* 40 */   public int getId() { return this.id; }
/*    */ }


/* Location:              /home/grads/szb754/Downloads/DUCView-1.4.jar!/ducview/SCU.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */