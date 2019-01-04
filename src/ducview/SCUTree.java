/*     */ package ducview;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.dnd.DragSourceDragEvent;
/*     */ import java.awt.dnd.DropTargetDropEvent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Iterator;
/*     */ import java.util.Vector;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.tree.DefaultMutableTreeNode;
/*     */ import javax.swing.tree.DefaultTreeModel;
/*     */ import javax.swing.tree.TreePath;
/*     */ 
/*     */ public class SCUTree extends javax.swing.JTree implements javax.swing.event.TreeSelectionListener, java.util.Comparator, java.awt.dnd.DragGestureListener, java.awt.dnd.DragSourceListener, java.awt.dnd.DropTargetListener, java.awt.dnd.Autoscroll, javax.swing.event.TreeExpansionListener
/*     */ {
/*     */   private DefaultTreeModel treeModel;
/*     */   private DucView ducView;
/*  23 */   private SCUTextPane textPane = null;
/*  24 */   private SCUTextPane pyramidReferenceTextPane = null;
/*  25 */   private static DataFlavor SCUContributorListTransferableDataFlavor = null;
/*  26 */   private boolean noScrollOnNextNodeSelection = false;
/*  27 */   private Vector highlightedNodes = new Vector();
/*     */   
/*     */   public SCUTree(DucView ducView)
/*     */   {
/*  31 */     this.treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Root Node"));
/*  32 */     setModel(this.treeModel);
/*  33 */     this.ducView = ducView;
/*  34 */     setRootVisible(false);
/*  35 */     setShowsRootHandles(true);
/*  36 */     getSelectionModel().setSelectionMode(1);
/*  37 */     setEditable(false);
/*  38 */     addTreeSelectionListener(this);
/*  39 */     addTreeExpansionListener(this);
/*  40 */     setCellRenderer(new SCUTreeCellRenderer());
/*  41 */     javax.swing.ToolTipManager.sharedInstance().registerComponent(this);
/*     */     
/*  43 */     if (SCUContributorListTransferableDataFlavor == null)
/*     */     {
/*     */       try
/*     */       {
/*  47 */         SCUContributorListTransferableDataFlavor = new DataFlavor("application/x-java-jvm-local-objectref;class=" + SCUContributorListTransferable.class.getName());
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*     */ 
/*  53 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*  57 */     java.awt.dnd.DragSource.getDefaultDragSource();java.awt.dnd.DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, 2, this);
/*     */     
/*  59 */     new java.awt.dnd.DropTarget(this, this);
/*     */   }
/*     */   
/*     */   public void reset()
/*     */   {
/*  64 */     this.treeModel = new DefaultTreeModel(new DefaultMutableTreeNode("Root Node"));
/*  65 */     setModel(this.treeModel);
/*     */   }
/*     */   
/*     */   public void rebuildTree(javax.swing.tree.TreeNode root)
/*     */   {
/*  70 */     this.treeModel.setRoot(root);
/*  71 */     expandTree();
/*     */   }
/*     */   
/*     */   public void expandTree()
/*     */   {
/*  76 */     for (int i = 0; i < getRowCount(); i++)
/*     */     {
/*  78 */       expandRow(i);
/*     */     }
/*  80 */     scrollRowToVisible(0);
/*     */   }
/*     */   
/*     */   public void collapseTree()
/*     */   {
/*  85 */     for (int i = 0; i < getRowCount(); i++)
/*     */     {
/*  87 */       collapseRow(i);
/*     */     }
/*  89 */     scrollRowToVisible(0);
/*     */   }
/*     */   
/*     */   public void setSCUTextPane(SCUTextPane textPane)
/*     */   {
/*  94 */     this.textPane = textPane;
/*     */   }
/*     */   
/*     */   public void setPyramidReferenceTextPane(SCUTextPane pyramidReferenceTextPane)
/*     */   {
/*  99 */     this.pyramidReferenceTextPane = pyramidReferenceTextPane;
/*     */   }
/*     */   
/*     */   public DefaultMutableTreeNode getRootNode()
/*     */   {
/* 104 */     return (DefaultMutableTreeNode)this.treeModel.getRoot();
/*     */   }
/*     */   
/*     */ 
/*     */   public void insertNodeInto(DefaultMutableTreeNode newChild, DefaultMutableTreeNode parent)
/*     */   {
/* 110 */     insertNodeInto(newChild, parent, parent.getChildCount());
/*     */   }
/*     */   
/*     */ 
/*     */   public void insertNodeInto(DefaultMutableTreeNode newChild, DefaultMutableTreeNode parent, int index)
/*     */   {
/* 116 */     this.noScrollOnNextNodeSelection = true;
/* 117 */     this.treeModel.insertNodeInto(newChild, parent, index);
/* 118 */     scrollPathToVisible(new TreePath(newChild.getPath()));
/*     */   }
/*     */   
/*     */   public void nodeChanged(javax.swing.tree.TreeNode node)
/*     */   {
/* 123 */     this.treeModel.nodeChanged(node);
/*     */   }
/*     */   
/*     */   public void removeNodeFromParent(DefaultMutableTreeNode node)
/*     */   {
/* 128 */     this.treeModel.removeNodeFromParent(node);
/*     */   }
/*     */   
/*     */   public void valueChanged(javax.swing.event.TreeSelectionEvent e)
/*     */   {
/* 133 */     DefaultMutableTreeNode node = (DefaultMutableTreeNode)getLastSelectedPathComponent();
/*     */     
/*     */ 
/* 136 */     while (this.highlightedNodes.size() > 0)
/*     */     {
/* 138 */       DefaultMutableTreeNode highlightedNode = (DefaultMutableTreeNode)this.highlightedNodes.remove(0);
/*     */       
/* 140 */       nodeChanged(highlightedNode);
/*     */     }
/*     */     
/* 143 */     this.textPane.modifyTextHighlight(0, this.textPane.getText().length() - 1, false);
/* 144 */     if (this.pyramidReferenceTextPane != null)
/*     */     {
/* 146 */       this.pyramidReferenceTextPane.modifyTextHighlight(0, this.pyramidReferenceTextPane.getText().length() - 1, false);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 151 */     JButton addBtn = this.ducView.getAddBtn();
/* 152 */     JButton removeBtn = this.ducView.getRemoveBtn();
/* 153 */     JButton renameBtn = this.ducView.getRenameBtn();
/* 154 */     JButton setLabelBtn = this.ducView.getSetLabelBtn();
/* 155 */     JButton commentBtn = this.ducView.getCommentBtn();
/*     */     
/* 157 */     if (node == null)
/*     */     {
/* 159 */       addBtn.setEnabled(false);
/* 160 */       removeBtn.setEnabled(false);
/* 161 */       commentBtn.setEnabled(false);
/* 162 */       if (renameBtn != null)
/*     */       {
/* 164 */         renameBtn.setEnabled(false);
/* 165 */         setLabelBtn.setEnabled(false);
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 172 */       if (this.pyramidReferenceTextPane != null)
/*     */       {
/* 174 */         SCU scu = (SCU)((DefaultMutableTreeNode)node.getPath()[1]).getUserObject();
/* 175 */         if (scu.getId() != 0)
/*     */         {
/*     */ 
/* 178 */           ArrayList highlightIndexes = new ArrayList();
/* 179 */           Enumeration pyramidSCUs = this.ducView.pyramidTree.getRootNode().children();
/* 180 */           while (pyramidSCUs.hasMoreElements())
/*     */           {
/* 182 */             DefaultMutableTreeNode scuNode = (DefaultMutableTreeNode)pyramidSCUs.nextElement();
/*     */             
/* 184 */             if (((SCU)scuNode.getUserObject()).getId() == scu.getId())
/*     */             {
/*     */ 
/* 187 */               Enumeration pyramidScuContributorNodeEnum = scuNode.children();
/* 188 */               while (pyramidScuContributorNodeEnum.hasMoreElements())
/*     */               {
/* 190 */                 Iterator pyramidSCUContributorIterator = ((SCUContributor)((DefaultMutableTreeNode)pyramidScuContributorNodeEnum.nextElement()).getUserObject()).elements();
/*     */                 
/*     */ 
/*     */ 
/* 194 */                 while (pyramidSCUContributorIterator.hasNext())
/*     */                 {
/* 196 */                   SCUContributorPart scuContributorPart = (SCUContributorPart)pyramidSCUContributorIterator.next();
/*     */                   
/* 198 */                   this.pyramidReferenceTextPane.modifyTextHighlight(scuContributorPart.getStartIndex(), scuContributorPart.getEndIndex(), true);
/*     */                   
/*     */ 
/* 201 */                   highlightIndexes.add(new Integer(scuContributorPart.getStartIndex()));
/*     */                 }
/*     */               }
/*     */               
/* 205 */               java.util.Collections.sort(highlightIndexes);
/* 206 */               this.ducView.pyramidReferenceTextPaneHighlightIndexes = new int[highlightIndexes.size()];
/*     */               
/* 208 */               for (int i = 0; i < highlightIndexes.size(); i++)
/*     */               {
/* 210 */                 this.ducView.pyramidReferenceTextPaneHighlightIndexes[i] = ((Integer)highlightIndexes.get(i)).intValue();
/*     */               }
/*     */               
/* 213 */               this.ducView.currentPyramidReferenceTextPaneHighlightIndex = 0;
/* 214 */               this.pyramidReferenceTextPane.showText(this.ducView.pyramidReferenceTextPaneHighlightIndexes[0]);
/*     */               
/*     */ 
/* 217 */               this.ducView.pyramidReferencePrevContributorBtn.setEnabled(false);
/* 218 */               this.ducView.pyramidReferenceNextContributorBtn.setEnabled(this.ducView.pyramidReferenceTextPaneHighlightIndexes.length > 1);
/*     */               
/* 220 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 226 */       int smallestHighlightIndex = Integer.MAX_VALUE;
/*     */       
/* 228 */       if (node.getLevel() == 1)
/*     */       {
/* 230 */         addBtn.setEnabled(true);
/* 231 */         removeBtn.setEnabled(true);
/* 232 */         commentBtn.setEnabled(true);
/* 233 */         if (renameBtn != null)
/*     */         {
/* 235 */           renameBtn.setEnabled(true);
/* 236 */           setLabelBtn.setEnabled(false);
/*     */         }
/*     */         else
/*     */         {
/* 240 */           removeBtn.setEnabled(false);
/*     */         }
/*     */         
/* 243 */         Enumeration nodeEnum = node.children();
/* 244 */         while (nodeEnum.hasMoreElements())
/*     */         {
/* 246 */           DefaultMutableTreeNode childNode = (DefaultMutableTreeNode)nodeEnum.nextElement();
/*     */           
/* 248 */           Iterator iterator = ((SCUContributor)childNode.getUserObject()).elements();
/* 249 */           while (iterator.hasNext())
/*     */           {
/* 251 */             SCUContributorPart scuContributorPart = (SCUContributorPart)iterator.next();
/* 252 */             this.textPane.modifyTextHighlight(scuContributorPart.getStartIndex(), scuContributorPart.getEndIndex(), true);
/*     */             
/* 254 */             smallestHighlightIndex = Math.min(smallestHighlightIndex, scuContributorPart.getStartIndex());
/*     */           }
/*     */           
/*     */         }
/*     */       }
/* 259 */       else if (node.getLevel() == 2)
/*     */       {
/* 261 */         addBtn.setEnabled(true);
/* 262 */         removeBtn.setEnabled(true);
/* 263 */         commentBtn.setEnabled(true);
/* 264 */         if (renameBtn != null)
/*     */         {
/* 266 */           renameBtn.setEnabled(false);
/* 267 */           setLabelBtn.setEnabled(true);
/*     */         }
/*     */         
/* 270 */         Iterator iterator = ((SCUContributor)node.getUserObject()).elements();
/* 271 */         while (iterator.hasNext())
/*     */         {
/* 273 */           SCUContributorPart scuContributorPart = (SCUContributorPart)iterator.next();
/* 274 */           this.textPane.modifyTextHighlight(scuContributorPart.getStartIndex(), scuContributorPart.getEndIndex(), true);
/*     */           
/* 276 */           smallestHighlightIndex = Math.min(smallestHighlightIndex, scuContributorPart.getStartIndex());
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 282 */         addBtn.setEnabled(false);
/* 283 */         removeBtn.setEnabled(true);
/* 284 */         commentBtn.setEnabled(false);
/* 285 */         if (renameBtn != null)
/*     */         {
/* 287 */           renameBtn.setEnabled(false);
/* 288 */           setLabelBtn.setEnabled(false);
/*     */         }
/*     */         
/* 291 */         SCUContributorPart scuContributorPart = (SCUContributorPart)node.getUserObject();
/*     */         
/* 293 */         this.textPane.modifyTextHighlight(scuContributorPart.getStartIndex(), scuContributorPart.getEndIndex(), true);
/*     */         
/* 295 */         smallestHighlightIndex = scuContributorPart.getStartIndex();
/*     */       }
/*     */       
/*     */ 
/* 299 */       if ((this.textPane.getSelectedText() == null) && (smallestHighlightIndex != Integer.MAX_VALUE) && (!this.noScrollOnNextNodeSelection))
/*     */       {
/*     */ 
/*     */ 
/* 303 */         this.textPane.showText(smallestHighlightIndex);
/*     */       }
/* 305 */       this.noScrollOnNextNodeSelection = false;
/*     */     }
/*     */   }
/*     */   
/*     */   public void order()
/*     */   {
/* 311 */     ArrayList scuNodeList = new ArrayList();
/* 312 */     DefaultMutableTreeNode rootNode = getRootNode();
/* 313 */     Enumeration scuNodeEnum = rootNode.children();
/* 314 */     while (scuNodeEnum.hasMoreElements())
/*     */     {
/* 316 */       scuNodeList.add(scuNodeEnum.nextElement());
/*     */     }
/* 318 */     java.util.Collections.sort(scuNodeList, this);
/* 319 */     Iterator scuNodeIterator = scuNodeList.iterator();
/* 320 */     while (scuNodeIterator.hasNext())
/*     */     {
/* 322 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)scuNodeIterator.next();
/* 323 */       removeNodeFromParent(node);
/* 324 */       insertNodeInto(node, rootNode);
/*     */     }
/* 326 */     expandTree();
/*     */   }
/*     */   
/*     */   public int compare(Object o1, Object o2)
/*     */   {
/* 331 */     DefaultMutableTreeNode n1 = (DefaultMutableTreeNode)o1;
/* 332 */     DefaultMutableTreeNode n2 = (DefaultMutableTreeNode)o2;
/*     */     
/* 334 */     if (n1.getChildCount() < n2.getChildCount())
/*     */     {
/* 336 */       return 1;
/*     */     }
/* 338 */     if (n1.getChildCount() > n2.getChildCount())
/*     */     {
/* 340 */       return -1;
/*     */     }
/*     */     
/*     */ 
/* 344 */     SCU scu1 = (SCU)n1.getUserObject();
/* 345 */     SCU scu2 = (SCU)n2.getUserObject();
/* 346 */     Pattern p = Pattern.compile("^\\((\\d+)\\) ");
/* 347 */     Matcher m1 = p.matcher(scu1.toString());
/* 348 */     Matcher m2 = p.matcher(scu2.toString());
/* 349 */     if ((m1.lookingAt()) && (m2.lookingAt()))
/*     */     {
/* 351 */       int num1 = Integer.parseInt(m1.group(1));
/* 352 */       int num2 = Integer.parseInt(m2.group(1));
/* 353 */       if (num1 < num2)
/*     */       {
/* 355 */         return 1;
/*     */       }
/* 357 */       if (num2 < num1)
/*     */       {
/* 359 */         return -1;
/*     */       }
/*     */       
/*     */ 
/* 363 */       return 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 368 */     return scu1.toString().compareToIgnoreCase(scu2.toString());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public Vector getSCUNodesContainingIndex(int index)
/*     */   {
/* 375 */     Vector scuNodes = new Vector();
/*     */     
/* 377 */     Enumeration scuNodeEnum = getRootNode().children();
/* 378 */     label133: while (scuNodeEnum.hasMoreElements())
/*     */     {
/* 380 */       DefaultMutableTreeNode scuNode = (DefaultMutableTreeNode)scuNodeEnum.nextElement();
/*     */       
/* 382 */       Enumeration scuContributorNodeEnum = scuNode.children();
/*     */       
/* 384 */       while (scuContributorNodeEnum.hasMoreElements())
/*     */       {
/* 386 */         SCUContributor scuContributor = (SCUContributor)((DefaultMutableTreeNode)scuContributorNodeEnum.nextElement()).getUserObject();
/*     */         
/*     */ 
/*     */ 
/* 390 */         for (int i = 0; i < scuContributor.getNumParts(); i++)
/*     */         {
/* 392 */           SCUContributorPart scuContributorPart = scuContributor.getSCUContributorPart(i);
/*     */           
/* 394 */           if ((scuContributorPart.getStartIndex() <= index) && (scuContributorPart.getEndIndex() >= index))
/*     */           {
/*     */ 
/* 397 */             scuNodes.add(scuNode.getUserObject());
/*     */             
/*     */             break label133;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 404 */     return scuNodes;
/*     */   }
/*     */   
/*     */   public void selectSCUNode(int scuId)
/*     */   {
/* 409 */     Enumeration scuNodeEnum = getRootNode().children();
/* 410 */     while (scuNodeEnum.hasMoreElements())
/*     */     {
/* 412 */       DefaultMutableTreeNode scuNode = (DefaultMutableTreeNode)scuNodeEnum.nextElement();
/*     */       
/*     */ 
/* 415 */       SCU scu = (SCU)scuNode.getUserObject();
/*     */       
/* 417 */       if (scu.getId() == scuId)
/*     */       {
/* 419 */         final TreePath path = new TreePath(scuNode.getPath());
/* 420 */         this.noScrollOnNextNodeSelection = true;
/* 421 */         setSelectionPath(path);
/*     */         
/*     */ 
/* 424 */         scrollRowToVisible(getRowCount() - 1);
/* 425 */         javax.swing.SwingUtilities.invokeLater(new Runnable() {
/*     */           //private final TreePath val$path;
/*     */           
/*     */           public void run() {
/* 429 */             SCUTree.this.scrollPathToVisible(path);
/*     */           }
/* 431 */         });
/* 432 */         break;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public int highlightSCUsNodesWithLabelmatchingPattern(Pattern pattern)
/*     */   {
/* 439 */     this.highlightedNodes.removeAllElements();
/* 440 */     Enumeration scuNodeEnum = getRootNode().children();
/* 441 */     while (scuNodeEnum.hasMoreElements())
/*     */     {
/* 443 */       DefaultMutableTreeNode scuNode = (DefaultMutableTreeNode)scuNodeEnum.nextElement();
/*     */       
/*     */ 
/* 446 */       Matcher matcher = pattern.matcher(scuNode.toString());
/* 447 */       if (matcher.find())
/*     */       {
/* 449 */         this.highlightedNodes.add(scuNode);
/* 450 */         nodeChanged(scuNode);
/*     */       }
/*     */     }
/* 453 */     return this.highlightedNodes.size();
/*     */   }
/*     */   
/*     */   public void dragGestureRecognized(java.awt.dnd.DragGestureEvent e)
/*     */   {
/* 458 */     DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)getLastSelectedPathComponent();
/*     */     
/* 460 */     if (selectedNode != null)
/*     */     {
/* 462 */       SCUContributorListTransferable scuContributors = new SCUContributorListTransferable();
/*     */       
/* 464 */       if (selectedNode.getLevel() == 2)
/*     */       {
/* 466 */         scuContributors.add(selectedNode);
/*     */       }
/* 468 */       else if (selectedNode.getLevel() == 1)
/*     */       {
/* 470 */         scuContributors.setDraggingSCU();
/* 471 */         Enumeration scuContributorNodes = selectedNode.children();
/* 472 */         while (scuContributorNodes.hasMoreElements())
/*     */         {
/* 474 */           scuContributors.add(scuContributorNodes.nextElement());
/*     */         }
/*     */       }
/* 477 */       if (scuContributors.size() > 0)
/*     */       {
/* 479 */         e.startDrag(java.awt.dnd.DragSource.DefaultMoveNoDrop, scuContributors, this);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public java.awt.Insets getAutoscrollInsets()
/*     */   {
/* 487 */     int margin = 10;
/* 488 */     Rectangle outer = getBounds();
/* 489 */     Rectangle inner = getParent().getBounds();
/* 490 */     return new java.awt.Insets(inner.y - outer.y + margin, inner.x - outer.x + margin, outer.height - inner.height - inner.y + outer.y + margin, outer.width - inner.width - inner.x + outer.x + margin);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void autoscroll(Point p)
/*     */   {
/* 499 */     int realrow = getClosestRowForLocation(p.x, p.y);
/* 500 */     Rectangle outer = getBounds();
/*     */     
/*     */ 
/* 503 */     realrow = realrow < getRowCount() - 3 ? realrow + 3 : p.y + outer.y <= 10 ? realrow - 3 : realrow < 3 ? 0 : realrow;
/*     */     
/* 505 */     scrollRowToVisible(realrow);
/*     */   }
/*     */   
/*     */ 
/*     */   public void dragExit(java.awt.dnd.DropTargetEvent e) {}
/*     */   
/*     */ 
/*     */   public void dropActionChanged(java.awt.dnd.DropTargetDragEvent e) {}
/*     */   
/*     */ 
/*     */   public void dragEnter(java.awt.dnd.DropTargetDragEvent e) {}
/*     */   
/*     */ 
/*     */   public void dragOver(java.awt.dnd.DropTargetDragEvent e) {}
/*     */   
/*     */ 
/*     */   public void dragDropEnd(java.awt.dnd.DragSourceDropEvent e) {}
/*     */   
/*     */ 
/*     */   public void dragEnter(DragSourceDragEvent e) {}
/*     */   
/*     */ 
/*     */   public void dragExit(java.awt.dnd.DragSourceEvent e) {}
/*     */   
/*     */ 
/*     */   public void dropActionChanged(DragSourceDragEvent e) {}
/*     */   
/*     */   public void dragOver(DragSourceDragEvent e)
/*     */   {
/* 534 */     java.awt.dnd.DragSourceContext context = e.getDragSourceContext();
/* 535 */     Point loc = e.getLocation();
/* 536 */     javax.swing.SwingUtilities.convertPointFromScreen(loc, this);
/* 537 */     TreePath destinationPath = getPathForLocation(loc.x, loc.y);
/* 538 */     if (destinationPath == null)
/*     */     {
/* 540 */       context.setCursor(java.awt.dnd.DragSource.DefaultMoveNoDrop);
/*     */     }
/*     */     else
/*     */     {
/* 544 */       DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode)destinationPath.getLastPathComponent();
/*     */       
/* 546 */       if (targetNode.getLevel() == 1)
/*     */       {
/* 548 */         context.setCursor(java.awt.dnd.DragSource.DefaultMoveDrop);
/*     */       }
/*     */       else
/*     */       {
/* 552 */         context.setCursor(java.awt.dnd.DragSource.DefaultMoveNoDrop);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void drop(DropTargetDropEvent e)
/*     */   {
/* 559 */     Point loc = e.getLocation();
/* 560 */     TreePath destinationPath = getPathForLocation(loc.x, loc.y);
/* 561 */     if (destinationPath == null)
/*     */     {
/* 563 */       e.rejectDrop();
/*     */     }
/*     */     else
/*     */     {
/* 567 */       DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode)destinationPath.getLastPathComponent();
/*     */       
/* 569 */       if (targetNode.getLevel() == 1)
/*     */       {
/* 571 */         SCUContributorListTransferable sourceNodeList = null;
/*     */         try
/*     */         {
/* 574 */           sourceNodeList = (SCUContributorListTransferable)e.getTransferable().getTransferData(SCUContributorListTransferableDataFlavor);
/*     */ 
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/* 579 */           ex.printStackTrace();
/*     */         }
/*     */         
/* 582 */         DefaultMutableTreeNode sourceNode = (DefaultMutableTreeNode)sourceNodeList.get(0);
/*     */         
/* 584 */         DefaultMutableTreeNode sourceSCUNode = (DefaultMutableTreeNode)sourceNode.getParent();
/*     */         
/*     */ 
/* 587 */         if (targetNode.isNodeDescendant(sourceNode))
/*     */         {
/* 589 */           e.rejectDrop();
/*     */ 
/*     */ 
/*     */         }
/* 593 */         else if ((this.pyramidReferenceTextPane == null) && (sourceNodeList.isDraggingSCU()) && (this.ducView.draggingScuMove))
/*     */         {
/*     */ 
/* 596 */           removeNodeFromParent(sourceSCUNode);
/* 597 */           insertNodeInto(sourceSCUNode, getRootNode(), this.treeModel.getIndexOfChild(getRootNode(), targetNode) + 1);
/*     */         }
/*     */         else
/*     */         {
/* 601 */           Iterator nodesEnum = sourceNodeList.iterator();
/* 602 */           while (nodesEnum.hasNext())
/*     */           {
/* 604 */             DefaultMutableTreeNode node = (DefaultMutableTreeNode)nodesEnum.next();
/* 605 */             removeNodeFromParent(node);
/* 606 */             insertNodeInto(node, targetNode);
/*     */           }
/*     */           
/*     */ 
/* 610 */           if ((sourceSCUNode.getChildCount() == 0) && (this.pyramidReferenceTextPane == null))
/*     */           {
/* 612 */             removeNodeFromParent(sourceSCUNode);
/*     */           }
/*     */           
/* 615 */           if (this.pyramidReferenceTextPane == null)
/*     */           {
/* 617 */             this.ducView.setPyramidModified(true);
/*     */           }
/*     */           else
/*     */           {
/* 621 */             this.ducView.scoreDlg.setText(this.ducView.getScore());
/* 622 */             this.ducView.setPeerModified(true);
/*     */           }
/* 624 */           e.acceptDrop(2);
/* 625 */           e.dropComplete(true);
/* 626 */           expandTree();
/* 627 */           setSelectionPath(new TreePath(targetNode.getPath()));
/*     */         }
/*     */         
/*     */       }
/*     */       else
/*     */       {
/* 633 */         e.rejectDrop();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void print()
/*     */   {
/* 640 */     Enumeration SCUNodes = getRootNode().children();
/* 641 */     StringBuffer buffer = new StringBuffer();
/* 642 */     while (SCUNodes.hasMoreElements())
/*     */     {
/* 644 */       buffer.append(((DefaultMutableTreeNode)SCUNodes.nextElement()).getUserObject().toString());
/* 645 */       buffer.append("<br>");
/*     */     }
/*     */     
/*     */     try
/*     */     {
/* 650 */       new DocumentRenderer().print(new javax.swing.JEditorPane("text/html", buffer.toString()));
/*     */     }
/*     */     catch (Exception ex)
/*     */     {
/* 654 */       ex.printStackTrace();
/* 655 */       javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", 0);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void treeCollapsed(javax.swing.event.TreeExpansionEvent event) {}
/*     */   
/*     */   public void treeExpanded(javax.swing.event.TreeExpansionEvent event)
/*     */   {
/* 664 */     JButton btn = this.ducView.getCollapseBtn();
/* 665 */     btn.setText("Collapse");
/* 666 */     btn.setMnemonic('l');
/* 667 */     btn.setActionCommand("collapse");
/*     */   }
/*     */   
/*     */   private class SCUContributorListTransferable extends Vector implements java.awt.datatransfer.Transferable {
/* 671 */     
/*     */     
/* 673 */     boolean isDraggingSCU = false;
/*     */     
/*     */     public Object getTransferData(DataFlavor flavor)
/*     */     {
/* 677 */       return this;
/*     */     }
/*     */     
/*     */     public DataFlavor[] getTransferDataFlavors()
/*     */     {
/* 682 */       return new DataFlavor[] { SCUTree.SCUContributorListTransferableDataFlavor };
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public boolean isDataFlavorSupported(DataFlavor flavor)
/*     */     {
/* 689 */       return flavor.equals(SCUTree.SCUContributorListTransferableDataFlavor);
/*     */     }
/*     */     
/* 692 */     public void setDraggingSCU() { this.isDraggingSCU = true; }
/* 693 */     public boolean isDraggingSCU() { return this.isDraggingSCU; }
/*     */     
/*     */     private SCUContributorListTransferable() {}
/*     */   }
/*     */   
/*     */   class SCUTreeCellRenderer extends javax.swing.tree.DefaultTreeCellRenderer {
/*     */     public SCUTreeCellRenderer() {
/* 700 */       setClosedIcon(null);
/* 701 */       setOpenIcon(null);
/* 702 */       setLeafIcon(null);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public java.awt.Component getTreeCellRendererComponent(javax.swing.JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus)
/*     */     {
/* 709 */       super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
/*     */       
/*     */ 
/* 712 */       setFont(tree.getFont());
/*     */       
/* 714 */       DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
/*     */       
/* 716 */       if (node.getLevel() == 1)
/*     */       {
/* 718 */         setFont(getFont().deriveFont(1));
/*     */       }
/*     */       else
/*     */       {
/* 722 */         setFont(getFont().deriveFont(0));
/*     */       }
/*     */       
/* 725 */       if ((node.getLevel() == 1) && (node.toString().equals("All non-matching SCUs go here")))
/*     */       {
/*     */ 
/* 728 */         setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.yellow));
/*     */       }
/*     */       else
/*     */       {
/* 732 */         setBorder(null);
/*     */       }
/*     */       
/* 735 */       if (SCUTree.this.highlightedNodes.contains(node))
/*     */       {
/* 737 */         setForeground(java.awt.Color.magenta);
/*     */       }
/*     */       
/*     */ 
/* 741 */       Object userObject = node.getUserObject();
/*     */       
/* 743 */       String comment = null;
/*     */       
/* 745 */       if ((userObject instanceof SCU))
/*     */       {
/* 747 */         comment = ((SCU)userObject).getComment();
/*     */       }
/* 749 */       else if ((userObject instanceof SCUContributor))
/*     */       {
/* 751 */         comment = ((SCUContributor)userObject).getComment();
/*     */       }
/*     */       
/* 754 */       if ((comment != null) && (comment.length() > 0))
/*     */       {
/* 756 */         setToolTipText(comment);
/* 757 */         setText(getText() + " *");
/*     */       }
/*     */       
/* 760 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/grads/szb754/Downloads/DUCView-1.4.jar!/ducview/SCUTree.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */