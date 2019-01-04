/*    */ package ducview;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class SCUContributor implements java.util.Comparator, Serializable
/*    */ {
/*    */   private ArrayList scuContributorParts;
/*  9 */   private String comment = "";
/*    */   
/*    */   public SCUContributor(SCUContributorPart part)
/*    */   {
/* 13 */     this(part, "");
/*    */   }
/*    */   
/*    */   public SCUContributor(SCUContributorPart part, String comment)
/*    */   {
/* 18 */     this.scuContributorParts = new ArrayList();
/* 19 */     this.scuContributorParts.add(part);
/* 20 */     this.comment = comment;
/*    */   }
/*    */   
/*    */   public void add(SCUContributorPart part)
/*    */   {
/* 25 */     this.scuContributorParts.add(part);
/*    */   }
/*    */   
/*    */   public int getNumParts()
/*    */   {
/* 30 */     return this.scuContributorParts.size();
/*    */   }
/*    */   
/*    */   public SCUContributorPart getSCUContributorPart(int index)
/*    */   {
/* 35 */     return (SCUContributorPart)this.scuContributorParts.get(index);
/*    */   }
/*    */   
/*    */   public java.util.Iterator elements()
/*    */   {
/* 40 */     return this.scuContributorParts.iterator();
/*    */   }
/*    */   
/*    */   public void removeSCUContributorPart(SCUContributorPart part)
/*    */   {
/* 45 */     this.scuContributorParts.remove(part);
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 50 */     if (this.scuContributorParts.size() == 1)
/*    */     {
/* 52 */       return this.scuContributorParts.get(0).toString();
/*    */     }
/*    */     
/*    */ 
/* 56 */     Object[] parts = this.scuContributorParts.toArray();
/* 57 */     java.util.Arrays.sort(parts, this);
/* 58 */     StringBuffer buffer = new StringBuffer();
/* 59 */     for (int i = 0; i < parts.length; i++)
/*    */     {
/* 61 */       if (buffer.length() > 0)
/*    */       {
/* 63 */         buffer.append("...");
/*    */       }
/* 65 */       buffer.append(((SCUContributorPart)parts[i]).getText());
/*    */     }
/* 67 */     return buffer.toString();
/*    */   }
/*    */   
/*    */ 
/* 71 */   public String getComment() { return this.comment; }
/* 72 */   public void setComment(String comment) { this.comment = comment; }
/*    */   
/*    */   public int compare(Object o1, Object o2)
/*    */   {
/* 76 */     SCUContributorPart part1 = (SCUContributorPart)o1;
/* 77 */     SCUContributorPart part2 = (SCUContributorPart)o2;
/* 78 */     if (part1.getStartIndex() < part2.getStartIndex())
/*    */     {
/* 80 */       return -1;
/*    */     }
/* 82 */     if (part2.getStartIndex() < part1.getStartIndex())
/*    */     {
/* 84 */       return 1;
/*    */     }
/*    */     
/*    */ 
/* 88 */     return 0;
/*    */   }
/*    */ }


/* Location:              /home/grads/szb754/Downloads/DUCView-1.4.jar!/ducview/SCUContributor.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */