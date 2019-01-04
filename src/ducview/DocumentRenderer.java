/*     */ package ducview;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Shape;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.PrintStream;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.plaf.TextUI;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.PlainDocument;
/*     */ import javax.swing.text.View;
/*     */ import javax.swing.text.html.HTMLDocument;
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
/*     */ public class DocumentRenderer
/*     */   implements Printable
/*     */ {
/*  45 */   protected int currentPage = -1;
/*     */   
/*     */ 
/*     */ 
/*     */   protected JEditorPane jeditorPane;
/*     */   
/*     */ 
/*     */ 
/*  53 */   protected double pageEndY = 0.0D;
/*     */   
/*     */ 
/*  56 */   protected double pageStartY = 0.0D;
/*     */   
/*     */ 
/*  59 */   protected boolean scaleWidthToFit = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected PageFormat pFormat;
/*     */   
/*     */ 
/*     */ 
/*     */   protected PrinterJob pJob;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DocumentRenderer()
/*     */   {
/*  75 */     this.pFormat = new PageFormat();
/*  76 */     this.pJob = PrinterJob.getPrinterJob();
/*     */   }
/*     */   
/*     */ 
/*     */   public Document getDocument()
/*     */   {
/*  82 */     if (this.jeditorPane != null) return this.jeditorPane.getDocument();
/*  83 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean getScaleWidthToFit()
/*     */   {
/*  89 */     return this.scaleWidthToFit;
/*     */   }
/*     */   
/*     */ 
/*     */   public void pageDialog()
/*     */   {
/*  95 */     this.pFormat = this.pJob.pageDialog(this.pFormat);
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
/*     */   {
/* 152 */     double scale = 1.0D;
/*     */     
/*     */ 
/*     */ 
/* 156 */     Graphics2D graphics2D = (Graphics2D)graphics;
/*     */     
/* 158 */     this.jeditorPane.setSize((int)pageFormat.getImageableWidth(), Integer.MAX_VALUE);
/* 159 */     this.jeditorPane.validate();
/*     */     
/* 161 */     View rootView = this.jeditorPane.getUI().getRootView(this.jeditorPane);
/*     */     
/* 163 */     if ((this.scaleWidthToFit) && (this.jeditorPane.getMinimumSize().getWidth() > pageFormat.getImageableWidth()))
/*     */     {
/* 165 */       scale = pageFormat.getImageableWidth() / this.jeditorPane.getMinimumSize().getWidth();
/*     */       
/* 167 */       graphics2D.scale(scale, scale);
/*     */     }
/*     */     
/* 170 */     graphics2D.setClip((int)(pageFormat.getImageableX() / scale), (int)(pageFormat.getImageableY() / scale), (int)(pageFormat.getImageableWidth() / scale), (int)(pageFormat.getImageableHeight() / scale));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 175 */     if (pageIndex > this.currentPage) {
/* 176 */       this.currentPage = pageIndex;
/* 177 */       this.pageStartY += this.pageEndY;
/* 178 */       this.pageEndY = graphics2D.getClipBounds().getHeight();
/*     */     }
/*     */     
/* 181 */     graphics2D.translate(graphics2D.getClipBounds().getX(), graphics2D.getClipBounds().getY());
/*     */     
/*     */ 
/* 184 */     Rectangle allocation = new Rectangle(0, (int)-this.pageStartY, (int)this.jeditorPane.getMinimumSize().getWidth(), (int)this.jeditorPane.getPreferredSize().getHeight());
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 189 */     if (printView(graphics2D, allocation, rootView)) {
/* 190 */       return 0;
/*     */     }
/*     */     
/* 193 */     this.pageStartY = 0.0D;
/* 194 */     this.pageEndY = 0.0D;
/* 195 */     this.currentPage = -1;
/* 196 */     return 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void print(HTMLDocument htmlDocument)
/*     */   {
/* 203 */     setDocument(htmlDocument);
/* 204 */     printDialog();
/*     */   }
/*     */   
/*     */ 
/*     */   public void print(JEditorPane jedPane)
/*     */   {
/* 210 */     setDocument(jedPane);
/* 211 */     printDialog();
/*     */   }
/*     */   
/*     */ 
/*     */   public void print(PlainDocument plainDocument)
/*     */   {
/* 217 */     setDocument(plainDocument);
/* 218 */     printDialog();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void printDialog()
/*     */   {
/* 225 */     if (this.pJob.printDialog()) {
/* 226 */       this.pJob.setPrintable(this, this.pFormat);
/*     */       try {
/* 228 */         this.pJob.print();
/*     */       }
/*     */       catch (PrinterException printerException) {
/* 231 */         this.pageStartY = 0.0D;
/* 232 */         this.pageEndY = 0.0D;
/* 233 */         this.currentPage = -1;
/* 234 */         System.out.println("Error Printing Document");
/*     */       }
/*     */     }
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean printView(Graphics2D graphics2D, Shape allocation, View view)
/*     */   {
/* 261 */     boolean pageExists = false;
/* 262 */     Rectangle clipRectangle = graphics2D.getClipBounds();
/*     */     
/*     */ 
/*     */ 
/* 266 */     if (view.getViewCount() > 0) {
/* 267 */       for (int i = 0; i < view.getViewCount(); i++) {
/* 268 */         Shape childAllocation = view.getChildAllocation(i, allocation);
/* 269 */         if (childAllocation != null) {
/* 270 */           View childView = view.getView(i);
/* 271 */           if (printView(graphics2D, childAllocation, childView)) {
/* 272 */             pageExists = true;
/*     */           }
/*     */           
/*     */         }
/*     */       }
/*     */     }
/* 278 */     else if (allocation.getBounds().getMaxY() >= clipRectangle.getY()) {
/* 279 */       pageExists = true;
/*     */       
/* 281 */       if ((allocation.getBounds().getHeight() > clipRectangle.getHeight()) && (allocation.intersects(clipRectangle)))
/*     */       {
/* 283 */         view.paint(graphics2D, allocation);
/*     */ 
/*     */       }
/* 286 */       else if (allocation.getBounds().getY() >= clipRectangle.getY()) {
/* 287 */         if (allocation.getBounds().getMaxY() <= clipRectangle.getMaxY()) {
/* 288 */           view.paint(graphics2D, allocation);
/*     */ 
/*     */         }
/* 291 */         else if (allocation.getBounds().getY() < this.pageEndY) {
/* 292 */           this.pageEndY = allocation.getBounds().getY();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 299 */     return pageExists;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void setContentType(String type)
/*     */   {
/* 305 */     this.jeditorPane.setContentType(type);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDocument(HTMLDocument htmlDocument)
/*     */   {
/* 311 */     this.jeditorPane = new JEditorPane();
/* 312 */     setDocument("text/html", htmlDocument);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setDocument(JEditorPane jedPane)
/*     */   {
/* 322 */     this.jeditorPane = new JEditorPane();
/* 323 */     setDocument(jedPane.getContentType(), jedPane.getDocument());
/*     */   }
/*     */   
/*     */ 
/*     */   public void setDocument(PlainDocument plainDocument)
/*     */   {
/* 329 */     this.jeditorPane = new JEditorPane();
/* 330 */     setDocument("text/plain", plainDocument);
/*     */   }
/*     */   
/*     */ 
/*     */   protected void setDocument(String type, Document document)
/*     */   {
/* 336 */     setContentType(type);
/* 337 */     this.jeditorPane.setDocument(document);
/*     */   }
/*     */   
/*     */ 
/*     */   public void setScaleWidthToFit(boolean scaleWidth)
/*     */   {
/* 343 */     this.scaleWidthToFit = scaleWidth;
/*     */   }
/*     */ }


/* Location:              /home/grads/szb754/Downloads/DUCView-1.4.jar!/ducview/DocumentRenderer.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */