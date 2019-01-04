/*      */ package ducview;
/*      */ 
/*      */ import javax.swing.JMenuItem;
/*      */ 
/*      */ public class DucView extends javax.swing.JFrame implements java.awt.event.ActionListener, org.xml.sax.ErrorHandler { private SCUTextPane pyramidTextPane;
/*      */   private SCUTextPane peerTextPane;
/*      */   private SCUTextPane pyramidReferenceTextPane;
/*      */   protected SCUTree pyramidTree;
/*      */   protected SCUTree peerTree;
/*      */   private javax.swing.JLabel statusLbl;
/*      */   private javax.swing.JButton addBtn;
/*      */   private javax.swing.JButton renameBtn;
/*      */   private javax.swing.JButton setLabelbtn;
/*      */   private javax.swing.JButton removeBtn;
/*      */   private javax.swing.JButton orderBtn;
/*      */   private javax.swing.JButton addBtn_peer;
/*      */   private javax.swing.JButton removeBtn_peer;
/*      */   private javax.swing.JButton orderBtn_peer;
/*      */   private javax.swing.JButton newBtn;
/*      */   private javax.swing.JButton collapseBtn;
/*      */   private javax.swing.JButton collapseBtn_peer;
/*      */   private javax.swing.JButton commentBtn;
/*      */   private javax.swing.JButton commentBtn_peer;
/*      */   protected javax.swing.JButton pyramidReferencePrevContributorBtn;
/*      */   protected javax.swing.JButton pyramidReferenceNextContributorBtn;
/*      */   protected javax.swing.JCheckBoxMenuItem fileShowPeerAnnotationScoreMenuItem;
/*      */   private int currentTextSize;
/*   28 */   private String defaultFilePath = System.getProperty("user.dir");
/*   29 */   private String defaultPANFileLocation = null;
/*   30 */   private boolean isPyramidLoaded = false;
/*   31 */   protected boolean isPeerLoaded = false;
/*   32 */   private boolean isPyramidModified = false;
/*   33 */   private boolean isPeerModified = false;
/*   34 */   private java.io.File pyramidFile = null; private java.io.File peerFile = null;
/*      */   private JMenuItem fileNewPyramidMenuItem;
/*      */   private JMenuItem fileLoadPyramidMenuItem;
/*      */   private JMenuItem fileSavePyramidMenuItem;
/*      */   private JMenuItem fileSavePyramidAsMenuItem;
/*      */   private JMenuItem fileClosePyramidMenuItem;
/*      */   private JMenuItem fileNewPeerAnnotationMenuItem;
/*      */   private JMenuItem fileSavePeerAnnotationMenuItem;
/*      */   private JMenuItem fileSavePeerAnnotationAsMenuItem;
/*   43 */   private JMenuItem fileClosePeerAnnotationMenuItem; private JMenuItem editUndoMenuItem; private JMenuItem editRedoMenuItem; private JMenuItem filePrintMenuItem; private JMenuItem editAutoannotateFromPeersMenuItem; private JMenuItem editAutoannotateFromPyramidMenuItem; private JMenuItem editAutoannotateFromBothMenuItem; private JMenuItem documentStartRegexMenuItem; private javax.swing.JPanel mainPanel; private static final String eol = System.getProperty("line.separator");
/*      */   private static final String titleString = "DucView v. 1.4";
/*   45 */   protected int[] pyramidReferenceTextPaneHighlightIndexes = null;
/*   46 */   protected int currentPyramidReferenceTextPaneHighlightIndex = 0;
/*      */   protected ScoreDialog scoreDlg;
/*   48 */   private UndoController pyramidUndoController = new UndoController();
/*   49 */   private UndoController peerUndoController = new UndoController();
/*      */   private SearchDialog searchDialog;
/*   51 */   private String pyramidInputTextFile = null; private String peerInputTextFile = null;
/*      */   private javax.swing.JRadioButtonMenuItem dragScuMoveMenuItem;
/*   53 */   private javax.swing.JRadioButtonMenuItem dragScuMergeMenuItem; protected boolean draggingScuMove = true;
/*   54 */   String startDocumentPatternStr = null;
/*   55 */   int[] startDocumentIndexes = null;
/*      */   
/*      */   public DucView()
/*      */   {
/*   59 */     super("DucView v. 1.4");
/*   60 */     setDefaultCloseOperation(0);
/*   61 */     addWindowListener(new DucViewWindowAdapter(this));
/*   62 */     setResizable(true);
/*      */     
/*   64 */     javax.swing.JPanel contentPane = (javax.swing.JPanel)getContentPane();
/*   65 */     contentPane.setLayout(new java.awt.BorderLayout());
/*   66 */     this.currentTextSize = new javax.swing.JTextPane().getFont().getSize();
/*      */     
/*      */ 
/*   69 */     contentPane.registerKeyboardAction(this, "find", javax.swing.KeyStroke.getKeyStroke(70, 2), 2);
/*      */     
/*   71 */     this.searchDialog = new SearchDialog(this);
/*      */     
/*   73 */     setJMenuBar(createMenuBar());
/*      */     
/*   75 */     this.mainPanel = new javax.swing.JPanel(new java.awt.CardLayout());
/*      */     
/*      */ 
/*      */ 
/*   79 */     this.pyramidTextPane = new SCUTextPane();
/*      */     
/*   81 */     javax.swing.JPanel pyramidSecondPanel = new javax.swing.JPanel();
/*   82 */     pyramidSecondPanel.setPreferredSize(new java.awt.Dimension(250, 500));
/*   83 */     pyramidSecondPanel.setLayout(new javax.swing.BoxLayout(pyramidSecondPanel, 1));
/*      */     
/*      */ 
/*   86 */     javax.swing.JPanel pyramidButtonsPanel = new javax.swing.JPanel(new java.awt.GridLayout(2, 4));
/*      */     
/*   88 */     pyramidButtonsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
/*      */     
/*   90 */     this.newBtn = new javax.swing.JButton("New SCU");
/*   91 */     this.newBtn.setMnemonic('n');
/*   92 */     this.newBtn.setActionCommand("new");
/*   93 */     this.newBtn.addActionListener(this);
/*   94 */     this.newBtn.setEnabled(false);
/*   95 */     pyramidButtonsPanel.add(this.newBtn);
/*      */     
/*   97 */     this.addBtn = new javax.swing.JButton("Add Contributor");
/*   98 */     this.addBtn.setMnemonic('a');
/*   99 */     this.addBtn.setActionCommand("add");
/*  100 */     this.addBtn.addActionListener(this);
/*  101 */     this.addBtn.setEnabled(false);
/*  102 */     pyramidButtonsPanel.add(this.addBtn);
/*      */     
/*  104 */     this.renameBtn = new javax.swing.JButton("Change Label");
/*  105 */     this.renameBtn.setMnemonic('c');
/*  106 */     this.renameBtn.setActionCommand("rename");
/*  107 */     this.renameBtn.addActionListener(this);
/*  108 */     this.renameBtn.setEnabled(false);
/*  109 */     pyramidButtonsPanel.add(this.renameBtn);
/*      */     
/*  111 */     this.setLabelbtn = new javax.swing.JButton("Set SCU Label");
/*  112 */     this.setLabelbtn.setMnemonic('s');
/*  113 */     this.setLabelbtn.setActionCommand("setLabel");
/*  114 */     this.setLabelbtn.addActionListener(this);
/*  115 */     this.setLabelbtn.setEnabled(false);
/*  116 */     pyramidButtonsPanel.add(this.setLabelbtn);
/*      */     
/*  118 */     this.removeBtn = new javax.swing.JButton("Remove");
/*  119 */     this.removeBtn.setMnemonic('r');
/*  120 */     this.removeBtn.setActionCommand("remove");
/*  121 */     this.removeBtn.addActionListener(this);
/*  122 */     this.removeBtn.setEnabled(false);
/*  123 */     pyramidButtonsPanel.add(this.removeBtn);
/*      */     
/*  125 */     this.orderBtn = new javax.swing.JButton("Order");
/*  126 */     this.orderBtn.setMnemonic('o');
/*  127 */     this.orderBtn.setActionCommand("order");
/*  128 */     this.orderBtn.addActionListener(this);
/*  129 */     this.orderBtn.setEnabled(false);
/*  130 */     pyramidButtonsPanel.add(this.orderBtn);
/*      */     
/*  132 */     this.collapseBtn = new javax.swing.JButton("Collapse");
/*  133 */     this.collapseBtn.setMnemonic('l');
/*  134 */     this.collapseBtn.setActionCommand("collapse");
/*  135 */     this.collapseBtn.addActionListener(this);
/*  136 */     this.collapseBtn.setEnabled(false);
/*  137 */     pyramidButtonsPanel.add(this.collapseBtn);
/*      */     
/*  139 */     this.commentBtn = new javax.swing.JButton("Comment");
/*  140 */     this.commentBtn.setMnemonic('m');
/*  141 */     this.commentBtn.setActionCommand("comment");
/*  142 */     this.commentBtn.addActionListener(this);
/*  143 */     this.commentBtn.setEnabled(false);
/*  144 */     pyramidButtonsPanel.add(this.commentBtn);
/*      */     
/*  146 */     pyramidButtonsPanel.setMaximumSize(pyramidButtonsPanel.getPreferredSize());
/*      */     
/*  148 */     pyramidSecondPanel.add(pyramidButtonsPanel);
/*      */     
/*  150 */     this.pyramidTree = new SCUTree(this);
/*  151 */     this.pyramidTree.setSCUTextPane(this.pyramidTextPane);
/*  152 */     this.pyramidTextPane.setTree(this.pyramidTree);
/*  153 */     pyramidSecondPanel.add(new javax.swing.JScrollPane(this.pyramidTree));
/*      */     
/*  155 */     javax.swing.JSplitPane pyramidSplitPane = new javax.swing.JSplitPane(1, new javax.swing.JScrollPane(this.pyramidTextPane), pyramidSecondPanel);
/*      */     
/*      */ 
/*  158 */     pyramidSplitPane.setResizeWeight(0.5D);
/*      */     
/*  160 */     this.mainPanel.add(pyramidSplitPane, "pyramid");
/*      */     
/*      */ 
/*      */ 
/*  164 */     this.peerTextPane = new SCUTextPane();
/*      */     
/*  166 */     javax.swing.JPanel peerSecondPanel = new javax.swing.JPanel();
/*  167 */     peerSecondPanel.setPreferredSize(new java.awt.Dimension(250, 500));
/*  168 */     peerSecondPanel.setLayout(new javax.swing.BoxLayout(peerSecondPanel, 1));
/*      */     
/*  170 */     javax.swing.Box peerButtonsPanel = javax.swing.Box.createHorizontalBox();
/*      */     
/*  172 */     peerButtonsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
/*      */     
/*  174 */     this.addBtn_peer = new javax.swing.JButton("Add Contributor");
/*  175 */     this.addBtn_peer.setMnemonic('a');
/*  176 */     this.addBtn_peer.setActionCommand("add");
/*  177 */     this.addBtn_peer.addActionListener(this);
/*  178 */     this.addBtn_peer.setEnabled(false);
/*  179 */     peerButtonsPanel.add(this.addBtn_peer);
/*      */     
/*  181 */     this.removeBtn_peer = new javax.swing.JButton("Remove");
/*  182 */     this.removeBtn_peer.setMnemonic('r');
/*  183 */     this.removeBtn_peer.setActionCommand("remove");
/*  184 */     this.removeBtn_peer.addActionListener(this);
/*  185 */     this.removeBtn_peer.setEnabled(false);
/*  186 */     peerButtonsPanel.add(this.removeBtn_peer);
/*      */     
/*  188 */     this.orderBtn_peer = new javax.swing.JButton("Order");
/*  189 */     this.orderBtn_peer.setMnemonic('o');
/*  190 */     this.orderBtn_peer.setActionCommand("order");
/*  191 */     this.orderBtn_peer.addActionListener(this);
/*  192 */     peerButtonsPanel.add(this.orderBtn_peer);
/*      */     
/*  194 */     this.collapseBtn_peer = new javax.swing.JButton("Collapse");
/*  195 */     this.collapseBtn_peer.setMnemonic('l');
/*  196 */     this.collapseBtn_peer.setActionCommand("order");
/*  197 */     this.collapseBtn_peer.addActionListener(this);
/*  198 */     peerButtonsPanel.add(this.collapseBtn_peer);
/*      */     
/*  200 */     this.commentBtn_peer = new javax.swing.JButton("Comment");
/*  201 */     this.commentBtn_peer.setMnemonic('m');
/*  202 */     this.commentBtn_peer.setActionCommand("comment");
/*  203 */     this.commentBtn_peer.addActionListener(this);
/*  204 */     this.commentBtn_peer.setEnabled(false);
/*  205 */     peerButtonsPanel.add(this.commentBtn_peer);
/*      */     
/*  207 */     peerSecondPanel.add(peerButtonsPanel);
/*      */     
/*  209 */     this.peerTree = new SCUTree(this);
/*  210 */     this.peerTree.setSCUTextPane(this.peerTextPane);
/*  211 */     this.peerTextPane.setTree(this.peerTree);
/*      */     
/*  213 */     peerSecondPanel.add(new javax.swing.JScrollPane(this.peerTree));
/*      */     
/*  215 */     javax.swing.JPanel peerThirdPanel = new javax.swing.JPanel();
/*  216 */     peerThirdPanel.setPreferredSize(new java.awt.Dimension(250, 500));
/*  217 */     peerThirdPanel.setLayout(new javax.swing.BoxLayout(peerThirdPanel, 1));
/*      */     
/*  219 */     javax.swing.Box pyramidReferenceButtonsPanel = javax.swing.Box.createHorizontalBox();
/*      */     
/*  221 */     pyramidReferenceButtonsPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
/*      */     
/*      */ 
/*  224 */     this.pyramidReferencePrevContributorBtn = new javax.swing.JButton("   <   ");
/*  225 */     this.pyramidReferencePrevContributorBtn.setMnemonic('<');
/*  226 */     this.pyramidReferencePrevContributorBtn.setActionCommand("pyramidReferencePrevContributor");
/*      */     
/*  228 */     this.pyramidReferencePrevContributorBtn.addActionListener(this);
/*  229 */     this.pyramidReferencePrevContributorBtn.setEnabled(false);
/*  230 */     pyramidReferenceButtonsPanel.add(this.pyramidReferencePrevContributorBtn);
/*      */     
/*  232 */     this.pyramidReferenceNextContributorBtn = new javax.swing.JButton("   >   ");
/*  233 */     this.pyramidReferenceNextContributorBtn.setMnemonic('>');
/*  234 */     this.pyramidReferenceNextContributorBtn.setActionCommand("pyramidReferenceNextContributor");
/*      */     
/*  236 */     this.pyramidReferenceNextContributorBtn.addActionListener(this);
/*  237 */     this.pyramidReferenceNextContributorBtn.setEnabled(false);
/*  238 */     pyramidReferenceButtonsPanel.add(this.pyramidReferenceNextContributorBtn);
/*      */     
/*  240 */     peerThirdPanel.add(pyramidReferenceButtonsPanel);
/*      */     
/*  242 */     this.pyramidReferenceTextPane = new SCUTextPane();
/*  243 */     this.pyramidReferenceTextPane.setHighlighter(null);
/*  244 */     peerThirdPanel.add(new javax.swing.JScrollPane(this.pyramidReferenceTextPane));
/*  245 */     this.peerTree.setPyramidReferenceTextPane(this.pyramidReferenceTextPane);
/*      */     
/*  247 */     javax.swing.JSplitPane peerSplitPane1 = new javax.swing.JSplitPane(1, peerSecondPanel, peerThirdPanel);
/*      */     
/*  249 */     peerSplitPane1.setResizeWeight(0.5D);
/*      */     
/*  251 */     javax.swing.JSplitPane peerSplitPane2 = new javax.swing.JSplitPane(1, new javax.swing.JScrollPane(this.peerTextPane), peerSplitPane1);
/*      */     
/*      */ 
/*  254 */     peerSplitPane2.setResizeWeight(0.5D);
/*      */     
/*  256 */     this.mainPanel.add(peerSplitPane2, "peer");
/*      */     
/*  258 */     contentPane.add(this.mainPanel, "Center");
/*      */     
/*  260 */     this.statusLbl = new javax.swing.JLabel("Ready");
/*  261 */     this.statusLbl.setBorder(javax.swing.BorderFactory.createBevelBorder(1));
/*  262 */     contentPane.add(this.statusLbl, "South");
/*      */     
/*  264 */     pack();
/*      */     
/*      */ 
/*  267 */     peerSplitPane2.setDividerLocation(Math.round((peerSplitPane2.getSize().width - peerSplitPane2.getInsets().left - peerSplitPane2.getInsets().right) / 3 + peerSplitPane2.getInsets().left));
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  274 */     java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
/*  275 */     setBounds(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2, getWidth(), getHeight());
/*      */     
/*      */ 
/*      */ 
/*  279 */     this.scoreDlg = new ScoreDialog(this);
/*      */   }
/*      */   
/*      */   public static void main(String[] args) throws Exception
/*      */   {
/*  284 */     DucView ducView = new DucView();
/*      */     
/*  286 */     if (args.length > 0)
/*      */     {
/*      */ 
/*  289 */       for (int i = 0; i < args.length; i++)
/*      */       {
/*      */         try
/*      */         {
/*  293 */           if (i > 0)
/*      */           {
/*  295 */             System.out.println();
/*      */           }
/*  297 */           System.out.println("-------------------\n" + args[i] + "\n-------------------\n");
/*      */           
/*  299 */           org.w3c.dom.Document doc = ducView.makeDocument(new java.io.File(args[i]));
/*  300 */           ducView.loadTree((org.w3c.dom.Element)doc.getElementsByTagName("pyramid").item(0), false);
/*  301 */           ducView.loadTree((org.w3c.dom.Element)doc.getElementsByTagName("annotation").item(0), true);
/*  302 */           ducView.isPyramidLoaded = true;
/*  303 */           ducView.isPeerLoaded = true;
/*  304 */           String regexStr = null;
/*  305 */           try { regexStr = doc.getElementsByTagName("startDocumentRegEx").item(0).getFirstChild().getNodeValue();
/*      */           } catch (NullPointerException ex) {}
/*  307 */           if (regexStr != null) {
/*  308 */             ducView.initializeStartDocumentIndexes(regexStr);
/*      */           }
/*  310 */           System.out.println(ducView.getScore().replaceAll("<.*?>", ""));
/*      */         }
/*      */         catch (Exception ex)
/*      */         {
/*  314 */           ex.printStackTrace();
/*      */         }
/*  316 */         ducView.dispose();
/*      */       }
/*      */       
/*      */     }
/*      */     else {
/*  321 */       ducView.setVisible(true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void actionPerformed(java.awt.event.ActionEvent e)
/*      */   {
/*  327 */     if (e.getActionCommand().equals("helpAbout"))
/*      */     {
/*  329 */       javax.swing.JOptionPane.showMessageDialog(this, "(c)2018\nSaptarashmi Bandyopadhyay\nPennsylvania State University\nszb754@psu.edu", "About DucView v. 1.4_2018", 1);
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*  334 */     else if (e.getActionCommand().equals("fileNewPyramid"))
/*      */     {
/*      */ 
/*  337 */       if ((!this.isPyramidModified) || (saveModifiedPyramid()))
/*      */       {
/*  339 */         javax.swing.JFileChooser chooser = new javax.swing.JFileChooser(this.defaultFilePath);
/*  340 */         chooser.setDialogTitle("Choose the initial text file");
/*  341 */         if (chooser.showOpenDialog(this) == 0)
/*      */         {
/*      */           try
/*      */           {
/*  345 */             this.pyramidInputTextFile = chooser.getSelectedFile().getName();
/*  346 */             this.pyramidTextPane.loadFile(chooser.getSelectedFile());
/*      */             
/*  348 */             this.startDocumentPatternStr = null;
/*  349 */             this.startDocumentIndexes = null;
/*      */             
/*  351 */             this.pyramidTree.reset();
/*  352 */             msg("Loaded file " + chooser.getSelectedFile());
/*  353 */             this.defaultFilePath = chooser.getSelectedFile().getCanonicalPath();
/*  354 */             this.pyramidFile = null;
/*  355 */             setPyramidLoaded(true);
/*      */           }
/*      */           catch (java.io.IOException ex)
/*      */           {
/*  359 */             ex.printStackTrace();
/*  360 */             msg(ex.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  365 */     if (e.getActionCommand().equals("fileNewPeerAnnotation"))
/*      */     {
/*      */ 
/*  368 */       if ((!this.isPeerModified) || (saveModifiedPeer()))
/*      */       {
/*  370 */         javax.swing.JFileChooser chooser = new javax.swing.JFileChooser(this.defaultFilePath);
/*  371 */         chooser.setDialogTitle("Choose the initial text file");
/*  372 */         if (chooser.showOpenDialog(this) == 0)
/*      */         {
/*      */           try
/*      */           {
/*  376 */             this.peerInputTextFile = chooser.getSelectedFile().getName();
/*  377 */             this.peerTextPane.loadFile(chooser.getSelectedFile());
/*  378 */             this.peerTree.reset();
/*  379 */             msg("Loaded file " + chooser.getSelectedFile());
/*  380 */             this.defaultFilePath = chooser.getSelectedFile().getCanonicalPath();
/*  381 */             this.peerFile = null;
/*  382 */             java.util.Enumeration scuNodeEnum = this.pyramidTree.getRootNode().children();
/*  383 */             while (scuNodeEnum.hasMoreElements())
/*      */             {
/*  385 */               javax.swing.tree.DefaultMutableTreeNode origSCUNode = (javax.swing.tree.DefaultMutableTreeNode)scuNodeEnum.nextElement();
/*      */               
/*  387 */               SCU origScu = (SCU)origSCUNode.getUserObject();
/*  388 */               int numContributors = origSCUNode.getChildCount();
/*      */               
/*      */ 
/*  391 */               this.peerTree.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(new SCU(origScu.getId(), "(" + numContributors + ") " + origScu.toString())), this.peerTree.getRootNode());
/*      */             }
/*      */             
/*      */ 
/*  395 */             this.peerTree.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(new SCU(0, "All non-matching SCUs go here")), this.peerTree.getRootNode());
/*      */             
/*  397 */             this.peerTree.order();
/*      */             
/*  399 */             if (this.peerTree.getRootNode().getChildCount() > 1)
/*      */             {
/*  401 */               this.orderBtn_peer.setEnabled(true);
/*      */             }
/*      */             else
/*      */             {
/*  405 */               this.orderBtn_peer.setEnabled(false);
/*      */             }
/*      */             
/*  408 */             this.pyramidReferenceTextPane.loadText(this.pyramidTextPane.getText());
/*  409 */             setPeerLoaded(true);
/*      */           }
/*      */           catch (java.io.IOException ex)
/*      */           {
/*  413 */             ex.printStackTrace();
/*  414 */             msg(ex.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  419 */     else if (e.getActionCommand().equals("fileLoadPyramid"))
/*      */     {
/*      */ 
/*  422 */       if ((!this.isPyramidModified) || (saveModifiedPyramid()))
/*      */       {
/*  424 */         javax.swing.JFileChooser chooser = new DucViewFileChooser(this.defaultFilePath, false, true);
/*  425 */         if (chooser.showOpenDialog(this) == 0)
/*      */         {
/*      */           try
/*      */           {
/*  429 */             this.defaultFilePath = chooser.getSelectedFile().getCanonicalPath();
/*  430 */             org.w3c.dom.Document doc = makeDocument(chooser.getSelectedFile());
/*  431 */             loadTree((org.w3c.dom.Element)doc.getElementsByTagName("pyramid").item(0), false);
/*  432 */             this.pyramidFile = chooser.getSelectedFile();
/*      */             
/*  434 */             String regexStr = null;
/*  435 */             try { regexStr = doc.getElementsByTagName("startDocumentRegEx").item(0).getFirstChild().getNodeValue();
/*      */             }
/*      */             catch (NullPointerException ex) {}
/*  438 */             if ((regexStr == null) || (initializeStartDocumentIndexes(regexStr)))
/*      */             {
/*  440 */               setPyramidLoaded(true);
/*  441 */               msg("Loaded " + chooser.getSelectedFile());
/*      */             }
/*      */             else
/*      */             {
/*  445 */               this.pyramidTextPane.setText("");
/*  446 */               this.pyramidTree.reset();
/*  447 */               setPyramidLoaded(false);
/*  448 */               msg("Error loading " + chooser.getSelectedFile());
/*      */             }
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*  453 */             ex.printStackTrace();
/*  454 */             msg(ex.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  459 */     else if (e.getActionCommand().equals("fileLoadPeerAnnotation"))
/*      */     {
/*      */ 
/*  462 */       if (((!this.isPeerModified) || (saveModifiedPeer())) && ((!this.isPyramidModified) || (saveModifiedPyramid())))
/*      */       {
/*      */ 
/*  465 */         javax.swing.JFileChooser chooser = new DucViewFileChooser(this.defaultFilePath, false, false);
/*  466 */         if (chooser.showOpenDialog(this) == 0)
/*      */         {
/*      */           try
/*      */           {
/*  470 */             this.defaultFilePath = chooser.getSelectedFile().getCanonicalPath();
/*  471 */             org.w3c.dom.Document doc = makeDocument(chooser.getSelectedFile());
/*      */             
/*  473 */             loadTree((org.w3c.dom.Element)doc.getElementsByTagName("pyramid").item(0), false);
/*  474 */             loadTree((org.w3c.dom.Element)doc.getElementsByTagName("annotation").item(0), true);
/*  475 */             this.pyramidReferenceTextPane.loadText(this.pyramidTextPane.getText());
/*  476 */             this.peerFile = chooser.getSelectedFile();
/*      */             
/*  478 */             String regexStr = null;
/*  479 */             try { regexStr = doc.getElementsByTagName("startDocumentRegEx").item(0).getFirstChild().getNodeValue();
/*      */             }
/*      */             catch (NullPointerException ex) {}
/*  482 */             if ((regexStr == null) || (initializeStartDocumentIndexes(regexStr)))
/*      */             {
/*  484 */               setPyramidLoaded(true);
/*  485 */               setPeerLoaded(true);
/*  486 */               msg("Loaded " + chooser.getSelectedFile());
/*      */             }
/*      */             else
/*      */             {
/*  490 */               this.pyramidTextPane.setText("");
/*  491 */               this.pyramidReferenceTextPane.setText("");
/*  492 */               this.peerTextPane.setText("");
/*  493 */               this.pyramidTree.reset();
/*  494 */               this.peerTree.reset();
/*  495 */               setPeerLoaded(false);
/*  496 */               setPyramidLoaded(false);
/*  497 */               msg("Error loading " + chooser.getSelectedFile());
/*      */             }
/*      */             
/*      */           }
/*      */           catch (Exception ex)
/*      */           {
/*  503 */             ex.printStackTrace();
/*  504 */             msg(ex.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  509 */     else if (e.getActionCommand().equals("fileSavePyramid"))
/*      */     {
/*  511 */       if (this.pyramidFile == null) {
/*  512 */         savePyramid(true);
/*      */       } else {
/*  514 */         savePyramid(false);
/*      */       }
/*  516 */     } else if (e.getActionCommand().equals("fileSavePyramidAs"))
/*      */     {
/*  518 */       savePyramid(true);
/*      */     }
/*  520 */     else if (e.getActionCommand().equals("fileSavePeerAnnotation"))
/*      */     {
/*  522 */       if (this.peerFile == null) {
/*  523 */         savePeer(true);
/*      */       } else {
/*  525 */         savePeer(false);
/*      */       }
/*  527 */     } else if (e.getActionCommand().equals("fileSavePeerAnnotationAs"))
/*      */     {
/*  529 */       savePeer(true);
/*      */     }
/*  531 */     else if (e.getActionCommand().equals("fileClosePyramid"))
/*      */     {
/*  533 */       if ((!this.isPyramidModified) || (saveModifiedPyramid()))
/*      */       {
/*  535 */         this.pyramidTextPane.loadText("");
/*  536 */         this.pyramidTree.reset();
/*  537 */         setPyramidLoaded(false);
/*      */       }
/*      */     }
/*  540 */     else if (e.getActionCommand().equals("fileShowPeerAnnotationScore"))
/*      */     {
/*  542 */       this.scoreDlg.setVisible(this.fileShowPeerAnnotationScoreMenuItem.isSelected());
/*      */     }
/*  544 */     else if (e.getActionCommand().equals("fileClosePeerAnnotation"))
/*      */     {
/*  546 */       if ((!this.isPeerModified) || (saveModifiedPeer()))
/*      */       {
/*  548 */         this.peerTextPane.loadText("");
/*  549 */         this.peerTree.reset();
/*  550 */         setPeerLoaded(false);
/*      */       }
/*      */     }
/*  553 */     else if (e.getActionCommand().equals("pyramidReferenceNextContributor"))
/*      */     {
/*  555 */       this.pyramidReferenceTextPane.showText(this.pyramidReferenceTextPaneHighlightIndexes[(++this.currentPyramidReferenceTextPaneHighlightIndex)]);
/*      */       
/*      */ 
/*  558 */       if (this.currentPyramidReferenceTextPaneHighlightIndex + 1 == this.pyramidReferenceTextPaneHighlightIndexes.length)
/*      */       {
/*      */ 
/*  561 */         this.pyramidReferenceNextContributorBtn.setEnabled(false);
/*      */       }
/*  563 */       if (this.currentPyramidReferenceTextPaneHighlightIndex > 0)
/*      */       {
/*  565 */         this.pyramidReferencePrevContributorBtn.setEnabled(true);
/*      */       }
/*      */     }
/*  568 */     else if (e.getActionCommand().equals("pyramidReferencePrevContributor"))
/*      */     {
/*  570 */       this.pyramidReferenceTextPane.showText(this.pyramidReferenceTextPaneHighlightIndexes[(--this.currentPyramidReferenceTextPaneHighlightIndex)]);
/*      */       
/*      */ 
/*  573 */       if (this.currentPyramidReferenceTextPaneHighlightIndex + 1 < this.pyramidReferenceTextPaneHighlightIndexes.length)
/*      */       {
/*      */ 
/*  576 */         this.pyramidReferenceNextContributorBtn.setEnabled(true);
/*      */       }
/*  578 */       if (this.currentPyramidReferenceTextPaneHighlightIndex == 0)
/*      */       {
/*  580 */         this.pyramidReferencePrevContributorBtn.setEnabled(false);
/*      */       }
/*      */     }
/*  583 */     else if (e.getActionCommand().equals("new"))
/*      */     {
/*  585 */       if ((this.pyramidTextPane.getSelectedText() != null) && (this.pyramidTextPane.getSelectedText().length() > 0))
/*      */       {
/*      */ 
/*  588 */         if ((this.startDocumentIndexes != null) && (this.pyramidTextPane.getSelectionStartIndex() < this.startDocumentIndexes[0]))
/*      */         {
/*  590 */           showError("Not in document", "The selection starts before the beginning of the first document");
/*  591 */           return;
/*      */         }
/*      */         
/*  594 */         msg("Creating new SCU \"" + this.pyramidTextPane.getSelectedText() + "\"");
/*      */         
/*      */ 
/*  597 */         javax.swing.tree.DefaultMutableTreeNode rootNode = this.pyramidTree.getRootNode();
/*  598 */         javax.swing.tree.DefaultMutableTreeNode scuNode = new javax.swing.tree.DefaultMutableTreeNode(new SCU(this.pyramidTextPane.getSelectedText()));
/*      */         
/*  600 */         this.pyramidTree.insertNodeInto(scuNode, rootNode);
/*  601 */         this.pyramidTree.setSelectionPath(new javax.swing.tree.TreePath(scuNode.getPath()));
/*      */         
/*      */ 
/*  604 */         javax.swing.tree.DefaultMutableTreeNode contributorNode = new javax.swing.tree.DefaultMutableTreeNode(new SCUContributor(new SCUContributorPart(this.pyramidTextPane.getSelectionStartIndex(), this.pyramidTextPane.getSelectionEndIndex(), this.pyramidTextPane.getSelectedText())));
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  609 */         this.pyramidTree.insertNodeInto(contributorNode, scuNode);
/*  610 */         this.pyramidTextPane.modifyTextSelection(this.pyramidTextPane.getSelectionStartIndex(), this.pyramidTextPane.getSelectionEndIndex(), true);
/*      */         
/*  612 */         setPyramidModified(true);
/*  613 */         if (rootNode.getChildCount() > 1)
/*      */         {
/*  615 */           this.orderBtn.setEnabled(true);
/*      */         }
/*  617 */         if (rootNode.getChildCount() > 0)
/*      */         {
/*  619 */           this.collapseBtn.setEnabled(true);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  626 */         javax.swing.tree.DefaultMutableTreeNode selectedNode = (javax.swing.tree.DefaultMutableTreeNode)this.pyramidTree.getLastSelectedPathComponent();
/*      */         
/*  628 */         if ((selectedNode != null) && (selectedNode.getLevel() == 2) && (selectedNode.getSiblingCount() > 1))
/*      */         {
/*      */ 
/*  631 */           this.pyramidTree.removeNodeFromParent(selectedNode);
/*  632 */           javax.swing.tree.DefaultMutableTreeNode newSCUNode = new javax.swing.tree.DefaultMutableTreeNode(new SCU(selectedNode.toString()));
/*      */           
/*  634 */           this.pyramidTree.insertNodeInto(newSCUNode, this.pyramidTree.getRootNode());
/*  635 */           this.pyramidTree.insertNodeInto(selectedNode, newSCUNode);
/*  636 */           this.pyramidTree.expandTree();
/*  637 */           this.pyramidTree.setSelectionPath(new javax.swing.tree.TreePath(newSCUNode.getPath()));
/*      */         }
/*      */         else {
/*  640 */           showError("No text selected", "You must select some text (or an SCU contributor) before creating an SCU");
/*      */         }
/*      */       }
/*  643 */     } else if (e.getActionCommand().equals("add"))
/*      */     {
/*  645 */       SCUTree tree = getTree();
/*  646 */       SCUTextPane textPane = getTextPane();
/*      */       
/*  648 */       if ((textPane.getSelectedText() != null) && (textPane.getSelectedText().length() > 0))
/*      */       {
/*      */ 
/*  651 */         javax.swing.tree.DefaultMutableTreeNode selectedNode = (javax.swing.tree.DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
/*      */         
/*      */ 
/*  654 */         if (selectedNode.getLevel() == 1)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*  659 */           if ((!this.isPeerLoaded) && (this.startDocumentIndexes != null))
/*      */           {
/*  661 */             int[] startDocumentIndexesTmp = new int[this.startDocumentIndexes.length + 1];
/*  662 */             for (int i = 0; i < this.startDocumentIndexes.length; i++)
/*  663 */               startDocumentIndexesTmp[i] = this.startDocumentIndexes[i];
/*  664 */             startDocumentIndexesTmp[(startDocumentIndexesTmp.length - 1)] = Integer.MAX_VALUE;
/*  665 */             int selectionStartIndex = textPane.getSelectionStartIndex();
/*  666 */             if (selectionStartIndex < startDocumentIndexesTmp[0])
/*      */             {
/*  668 */               showError("Not in document", "The text selection starts before the beginning of the first document");
/*  669 */               return;
/*      */             }
/*  671 */             for (int i = 0; i < startDocumentIndexesTmp.length - 1; i++)
/*      */             {
/*  673 */               if ((selectionStartIndex >= startDocumentIndexesTmp[i]) && (selectionStartIndex < startDocumentIndexesTmp[(i + 1)]))
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*  678 */                 java.util.Enumeration scuContribuorEnum = selectedNode.children();
/*  679 */                 while (scuContribuorEnum.hasMoreElements())
/*      */                 {
/*  681 */                   SCUContributor contributor = (SCUContributor)((javax.swing.tree.DefaultMutableTreeNode)scuContribuorEnum.nextElement()).getUserObject();
/*      */                   
/*      */ 
/*  684 */                   java.util.Iterator scuContribuorPartEnum = contributor.elements();
/*      */                   
/*  686 */                   while (scuContribuorPartEnum.hasNext())
/*      */                   {
/*  688 */                     SCUContributorPart scuContribuorPart = (SCUContributorPart)scuContribuorPartEnum.next();
/*      */                     
/*  690 */                     int partStartIndex = scuContribuorPart.getStartIndex();
/*  691 */                     if ((partStartIndex >= startDocumentIndexesTmp[i]) && (partStartIndex < startDocumentIndexesTmp[(i + 1)]))
/*      */                     {
/*      */ 
/*  694 */                       showError("Multiple contributor per SCU per document", "The current SCU already has a contributor from the selected document:\n" + scuContribuorPart.getText());
/*      */                       
/*      */ 
/*  697 */                       return;
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  706 */           msg("Adding SCU contributor \"" + textPane.getSelectedText() + "\" to SCU \"" + selectedNode.toString() + "\"");
/*      */           
/*      */ 
/*  709 */           javax.swing.tree.DefaultMutableTreeNode contributorNode = new javax.swing.tree.DefaultMutableTreeNode(new SCUContributor(new SCUContributorPart(textPane.getSelectionStartIndex(), textPane.getSelectionEndIndex(), textPane.getSelectedText())));
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  714 */           tree.insertNodeInto(contributorNode, selectedNode);
/*  715 */           textPane.modifyTextSelection(textPane.getSelectionStartIndex(), textPane.getSelectionEndIndex(), true);
/*      */ 
/*      */         }
/*  718 */         else if (selectedNode.getLevel() == 2)
/*      */         {
/*      */ 
/*  721 */           msg("Adding SCU contributor part \"" + textPane.getSelectedText() + "\" to SCU contributor \"" + selectedNode.toString() + "\" from SCU \"" + selectedNode.getParent().toString() + "\"");
/*      */           
/*      */ 
/*      */ 
/*  725 */           SCUContributor scuContributor = (SCUContributor)selectedNode.getUserObject();
/*  726 */           scuContributor.add(new SCUContributorPart(textPane.getSelectionStartIndex(), textPane.getSelectionEndIndex(), textPane.getSelectedText()));
/*      */           
/*      */ 
/*      */ 
/*  730 */           textPane.modifyTextSelection(textPane.getSelectionStartIndex(), textPane.getSelectionEndIndex(), true);
/*      */           
/*      */ 
/*  733 */           if (scuContributor.getNumParts() == 2)
/*      */           {
/*  735 */             tree.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(scuContributor.getSCUContributorPart(0)), selectedNode);
/*      */             
/*      */ 
/*  738 */             tree.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(scuContributor.getSCUContributorPart(1)), selectedNode);
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  743 */             tree.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(scuContributor.getSCUContributorPart(scuContributor.getNumParts() - 1)), selectedNode);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  750 */         tree.valueChanged(null);
/*  751 */         if (this.isPeerLoaded)
/*      */         {
/*  753 */           setPeerModified(true);
/*  754 */           this.scoreDlg.setText(getScore());
/*  755 */           this.orderBtn_peer.setEnabled(tree.getRootNode().getChildCount() > 1);
/*  756 */           this.collapseBtn_peer.setEnabled(tree.getRootNode().getChildCount() > 0);
/*      */         }
/*      */         else
/*      */         {
/*  760 */           setPyramidModified(true);
/*  761 */           this.orderBtn.setEnabled(tree.getRootNode().getChildCount() > 1);
/*  762 */           this.collapseBtn.setEnabled(tree.getRootNode().getChildCount() > 0);
/*      */         }
/*      */         
/*      */       }
/*      */       else
/*      */       {
/*  768 */         showError("No text selected", "You must select some text before adding a contributor");
/*      */       }
/*      */     }
/*  771 */     else if (e.getActionCommand().equals("rename"))
/*      */     {
/*      */ 
/*  774 */       javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode)this.pyramidTree.getLastSelectedPathComponent();
/*      */       
/*  776 */       SCU scu = (SCU)node.getUserObject();
/*  777 */       String scuLabel = scu.toString();
/*  778 */       String newSCULabel = (String)javax.swing.JOptionPane.showInputDialog(this, "Enter the label for the selected SCU", "Rename SCU", -1, null, null, scuLabel);
/*      */       
/*      */ 
/*  781 */       if ((newSCULabel != null) && (newSCULabel.trim().length() > 0))
/*      */       {
/*  783 */         scu.setLabel(newSCULabel);
/*  784 */         this.pyramidTree.nodeChanged(node);
/*  785 */         setPyramidModified(true);
/*      */       }
/*      */     }
/*  788 */     else if (e.getActionCommand().equals("setLabel"))
/*      */     {
/*  790 */       javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode)this.pyramidTree.getLastSelectedPathComponent();
/*      */       
/*  792 */       SCUContributor scuContributor = (SCUContributor)node.getUserObject();
/*  793 */       SCU scu = (SCU)((javax.swing.tree.DefaultMutableTreeNode)node.getParent()).getUserObject();
/*  794 */       scu.setLabel(scuContributor.toString().replaceAll("\\.\\.\\.", " "));
/*  795 */       this.pyramidTree.nodeChanged(node);
/*  796 */       setPyramidModified(true);
/*      */     }
/*  798 */     else if (e.getActionCommand().equals("remove"))
/*      */     {
/*  800 */       SCUTree tree = getTree();
/*  801 */       SCUTextPane textPane = getTextPane();
/*      */       
/*  803 */       javax.swing.tree.DefaultMutableTreeNode selectedNode = (javax.swing.tree.DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
/*      */       
/*      */ 
/*  806 */       if (selectedNode.getLevel() == 1)
/*      */       {
/*      */ 
/*      */ 
/*  810 */         tree.removeNodeFromParent(selectedNode);
/*  811 */         java.util.Enumeration nodeEnum = selectedNode.children();
/*  812 */         while (nodeEnum.hasMoreElements())
/*      */         {
/*  814 */           javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode)nodeEnum.nextElement();
/*  815 */           java.util.Iterator iterator = ((SCUContributor)node.getUserObject()).elements();
/*  816 */           while (iterator.hasNext())
/*      */           {
/*  818 */             SCUContributorPart scuContributorPart = (SCUContributorPart)iterator.next();
/*  819 */             textPane.modifyTextSelection(scuContributorPart.getStartIndex(), scuContributorPart.getEndIndex(), false);
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*  824 */       else if (selectedNode.getLevel() == 2)
/*      */       {
/*      */ 
/*  827 */         if ((selectedNode.getSiblingCount() == 1) && (!this.isPeerLoaded))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*  832 */           tree.removeNodeFromParent((javax.swing.tree.DefaultMutableTreeNode)selectedNode.getParent());
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  837 */           tree.removeNodeFromParent(selectedNode);
/*      */         }
/*  839 */         java.util.Iterator iterator = ((SCUContributor)selectedNode.getUserObject()).elements();
/*  840 */         while (iterator.hasNext())
/*      */         {
/*  842 */           SCUContributorPart scuContributorPart = (SCUContributorPart)iterator.next();
/*  843 */           textPane.modifyTextSelection(scuContributorPart.getStartIndex(), scuContributorPart.getEndIndex(), false);
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  850 */         javax.swing.tree.DefaultMutableTreeNode parent = (javax.swing.tree.DefaultMutableTreeNode)selectedNode.getParent();
/*      */         
/*  852 */         SCUContributorPart scuContributorPart = (SCUContributorPart)selectedNode.getUserObject();
/*      */         
/*  854 */         ((SCUContributor)parent.getUserObject()).removeSCUContributorPart(scuContributorPart);
/*      */         
/*  856 */         textPane.modifyTextSelection(scuContributorPart.getStartIndex(), scuContributorPart.getEndIndex(), false);
/*      */         
/*  858 */         tree.removeNodeFromParent(selectedNode);
/*      */         
/*  860 */         if (((SCUContributor)parent.getUserObject()).getNumParts() == 1)
/*      */         {
/*  862 */           tree.removeNodeFromParent((javax.swing.tree.DefaultMutableTreeNode)parent.getChildAt(0));
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  867 */       if (this.isPeerLoaded)
/*      */       {
/*  869 */         setPeerModified(true);
/*  870 */         this.scoreDlg.setText(getScore());
/*  871 */         this.orderBtn_peer.setEnabled(tree.getRootNode().getChildCount() > 1);
/*  872 */         this.collapseBtn_peer.setEnabled(tree.getRootNode().getChildCount() > 0);
/*      */       }
/*      */       else
/*      */       {
/*  876 */         setPyramidModified(true);
/*  877 */         this.orderBtn.setEnabled(tree.getRootNode().getChildCount() > 1);
/*  878 */         this.collapseBtn.setEnabled(tree.getRootNode().getChildCount() > 0);
/*      */       }
/*      */     }
/*  881 */     else if (e.getActionCommand().equals("order"))
/*      */     {
/*  883 */       if (this.isPeerLoaded)
/*      */       {
/*  885 */         this.peerTree.order();
/*  886 */         setPeerModified(true);
/*      */       }
/*      */       else
/*      */       {
/*  890 */         this.pyramidTree.order();
/*  891 */         setPyramidModified(true);
/*      */       }
/*      */     }
/*  894 */     else if (e.getActionCommand().equals("exit"))
/*      */     {
/*  896 */       java.awt.event.WindowListener[] listeners = getWindowListeners();
/*  897 */       for (int i = 0; i < listeners.length; i++)
/*      */       {
/*  899 */         listeners[i].windowClosing(null);
/*      */       }
/*      */     }
/*  902 */     else if (e.getActionCommand().startsWith("Text Size"))
/*      */     {
/*  904 */       float fontSize = Float.parseFloat(e.getActionCommand().substring(10));
/*  905 */       this.pyramidTextPane.setFont(this.pyramidTextPane.getFont().deriveFont(fontSize));
/*  906 */       this.pyramidTree.setFont(this.pyramidTree.getFont().deriveFont(fontSize));
/*  907 */       this.pyramidTree.revalidate();
/*  908 */       this.peerTextPane.setFont(this.peerTextPane.getFont().deriveFont(fontSize));
/*  909 */       this.peerTree.setFont(this.peerTree.getFont().deriveFont(fontSize));
/*  910 */       this.pyramidTree.revalidate();
/*  911 */       this.pyramidReferenceTextPane.setFont(this.pyramidReferenceTextPane.getFont().deriveFont(fontSize));
/*      */ 
/*      */     }
/*  914 */     else if (e.getActionCommand().startsWith("Look And Feel"))
/*      */     {
/*      */       try
/*      */       {
/*  918 */         javax.swing.UIManager.setLookAndFeel(e.getActionCommand().substring(14));
/*      */       }
/*      */       catch (Exception ex)
/*      */       {
/*  922 */         msg(ex.getMessage());
/*      */       }
/*  924 */       javax.swing.SwingUtilities.updateComponentTreeUI(this);
/*  925 */       this.pyramidTextPane.updateSelectedStyle();
/*      */     }
/*  927 */     else if (e.getActionCommand().equals("find"))
/*      */     {
/*  929 */       SCUTextPane textPane = getTextPane();
/*  930 */       SCUTree tree = getTree();
/*      */       
/*  932 */       String searchRegex = null;
/*      */       
/*      */       while(true)
/*      */       {
/*  936 */         searchRegex = this.searchDialog.getSearchString();
/*      */         
/*  938 */         if ((searchRegex == null) || (searchRegex.trim().length() == 0)) {
/*      */           break;
/*      */         }
/*      */         
/*      */         java.util.regex.Pattern pattern;
/*      */         try
/*      */         {
/*  945 */           pattern = java.util.regex.Pattern.compile(searchRegex, 2);
/*      */         }
/*      */         catch (java.util.regex.PatternSyntaxException ex)
/*      */         {
/*  949 */           showError("Regular Expression Error", "Regular expression syntax error:\n" + ex.getMessage()); continue; }
/*  950 */         //continue;
/*      */         
/*      */ 
/*  953 */         if (this.searchDialog.isSearchingText())
/*      */         {
/*  955 */           java.util.regex.Matcher matcher = pattern.matcher(textPane.getText());
/*  956 */           if (matcher.find())
/*      */           {
/*  958 */             textPane.modifyTextHighlight(0, textPane.getText().length() - 1, false);
/*      */             do
/*      */             {
/*  961 */               textPane.modifyTextHighlight(matcher.start(), matcher.end(), true);
/*      */             }
/*  963 */             while (matcher.find());
/*  964 */             break;
/*      */           }
/*      */           
/*      */ 
/*  968 */           javax.swing.JOptionPane.showMessageDialog(this, "Your search did not produce any results", "No results", 1);
/*      */ 
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  975 */           if (tree.highlightSCUsNodesWithLabelmatchingPattern(pattern) != 0)
/*      */             break;
/*  977 */           javax.swing.JOptionPane.showMessageDialog(this, "Your search did not produce any results", "No results", 1);
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */     }
/*  987 */     else if ((e.getActionCommand().equals("undo")) || (e.getActionCommand().equals("redo"))) {
/*      */       //UndoController undoController;
/*      */       SCUTree tree;
/*      */       SCUTextPane textPane;
/*      */       UndoController undoController;
/*  992 */       if (this.isPeerLoaded)
/*      */       {
/*  994 */         tree = this.peerTree;
/*  995 */         textPane = this.peerTextPane;
/*  996 */         undoController = this.peerUndoController;
/*      */       }
/*      */       else
/*      */       {
/* 1000 */         tree = this.pyramidTree;
/* 1001 */         textPane = this.pyramidTextPane;
/* 1002 */         undoController = this.pyramidUndoController;
/*      */       }
/*      */       //javax.swing.tree.DefaultMutableTreeNode rootNode;
/*      */       javax.swing.tree.DefaultMutableTreeNode rootNode;
/* 1006 */       if (e.getActionCommand().equals("undo")) {
/* 1007 */         rootNode = (javax.swing.tree.DefaultMutableTreeNode)undoController.undo();
/*      */       } else {
/* 1009 */         rootNode = (javax.swing.tree.DefaultMutableTreeNode)undoController.redo();
/*      */       }
/* 1011 */       tree.rebuildTree(rootNode);
/* 1012 */       textPane.loadText(textPane.getText());
/*      */       
/*      */ 
/* 1015 */       java.util.Enumeration scuNodeEnum = tree.getRootNode().children();
/* 1016 */       while (scuNodeEnum.hasMoreElements())
/*      */       {
/* 1018 */         javax.swing.tree.DefaultMutableTreeNode scuNode = (javax.swing.tree.DefaultMutableTreeNode)scuNodeEnum.nextElement();
/* 1019 */         java.util.Enumeration scuContributorNodeEnum = scuNode.children();
/*      */         
/* 1021 */         while (scuContributorNodeEnum.hasMoreElements())
/*      */         {
/* 1023 */           SCUContributor scuContributor = (SCUContributor)((javax.swing.tree.DefaultMutableTreeNode)scuContributorNodeEnum.nextElement()).getUserObject();
/*      */           
/*      */ 
/* 1026 */           for (int i = 0; i < scuContributor.getNumParts(); i++)
/*      */           {
/* 1028 */             SCUContributorPart scuContributorPart = scuContributor.getSCUContributorPart(i);
/* 1029 */             textPane.modifyTextSelection(scuContributorPart.getStartIndex(), scuContributorPart.getEndIndex(), true);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1034 */       if (this.isPeerLoaded)
/*      */       {
/* 1036 */         this.isPeerModified = true;
/* 1037 */         this.fileSavePeerAnnotationMenuItem.setEnabled(true);
/* 1038 */         this.fileSavePeerAnnotationAsMenuItem.setEnabled(true);
/*      */       }
/*      */       else
/*      */       {
/* 1042 */         this.isPyramidModified = true;
/* 1043 */         this.fileSavePyramidMenuItem.setEnabled(true);
/* 1044 */         this.fileSavePyramidAsMenuItem.setEnabled(true);
/*      */       }
/*      */     }
/* 1047 */     else if (e.getActionCommand().equals("print"))
/*      */     {
/* 1049 */       if (this.isPeerLoaded) {
/* 1050 */         this.peerTree.print();
/*      */       } else {
/* 1052 */         this.pyramidTree.print();
/*      */       }
/* 1054 */     } else if (e.getActionCommand().startsWith("autoannotate"))
/*      */     {
/*      */ 
/* 1057 */       java.util.Map contributorMap = new java.util.HashMap();
/*      */       
/* 1059 */       if ((e.getActionCommand().endsWith("pyramid")) || (e.getActionCommand().endsWith("both")))
/*      */       {
/* 1061 */         java.util.Enumeration scuNodeEnum = this.pyramidTree.getRootNode().children();
/* 1062 */         while (scuNodeEnum.hasMoreElements())
/*      */         {
/* 1064 */           javax.swing.tree.DefaultMutableTreeNode scuNode = (javax.swing.tree.DefaultMutableTreeNode)scuNodeEnum.nextElement();
/* 1065 */           Integer scuId = new Integer(((SCU)scuNode.getUserObject()).getId());
/* 1066 */           java.util.Enumeration scuContributorEnum = scuNode.children();
/* 1067 */           while (scuContributorEnum.hasMoreElements())
/*      */           {
/* 1069 */             SCUContributor scuContributor = (SCUContributor)((javax.swing.tree.DefaultMutableTreeNode)scuContributorEnum.nextElement()).getUserObject();
/*      */             
/* 1071 */             if (scuContributor.getNumParts() == 1)
/*      */             {
/* 1073 */               String label = scuContributor.getSCUContributorPart(0).getText();
/* 1074 */               if (!contributorMap.containsKey(label))
/* 1075 */                 contributorMap.put(label, new java.util.HashSet());
/* 1076 */               ((java.util.Set)contributorMap.get(label)).add(scuId);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */       
/* 1082 */       if ((e.getActionCommand().endsWith("peers")) || (e.getActionCommand().endsWith("both")))
/*      */       {
/* 1084 */         if (this.defaultPANFileLocation == null) {
/* 1085 */           this.defaultPANFileLocation = new java.io.File(this.defaultFilePath).getParent();
/*      */         }
/* 1087 */         String panDirectoryName = (String)javax.swing.JOptionPane.showInputDialog(this, "Specify the directory with completed peer annotations (*.pan files)", "Title", 3, null, null, this.defaultPANFileLocation);
/*      */         
/*      */ 
/* 1090 */         if (panDirectoryName == null) {
/* 1091 */           return;
/*      */         }
/* 1093 */         this.defaultPANFileLocation = panDirectoryName;
/*      */         
/* 1095 */         java.io.File panDirectory = new java.io.File(panDirectoryName);
/* 1096 */         if (!panDirectory.exists())
/*      */         {
/* 1098 */           showError("No such directory", "No directory " + panDirectoryName);
/* 1099 */           return;
/*      */         }
/*      */         
/* 1102 */         java.io.File[] panFiles = panDirectory.listFiles(new java.io.FilenameFilter()
/*      */         {
/*      */ 
/*      */           public boolean accept(java.io.File dir, String name)
/*      */           {
/* 1107 */             return name.endsWith(".pan");
/*      */           }
/*      */         });
/*      */         
/* 1111 */         if ((panFiles == null) || (panFiles.length == 0))
/*      */         {
/* 1113 */           showError("No *.pan files", "No *.pan files in " + panDirectoryName);
/* 1114 */           return;
/*      */         }
/*      */         
/* 1117 */         for (int i = 0; i < panFiles.length; i++)
/*      */         {
/*      */           try
/*      */           {
/* 1121 */             org.w3c.dom.Document doc = makeDocument(panFiles[i]);
/* 1122 */             org.w3c.dom.Element annotationElement = (org.w3c.dom.Element)doc.getElementsByTagName("annotation").item(0);
/*      */             
/* 1124 */             org.w3c.dom.NodeList peerSCUs = annotationElement.getElementsByTagName("peerscu");
/* 1125 */             for (int j = 0; j < peerSCUs.getLength(); j++)
/*      */             {
/* 1127 */               org.w3c.dom.Element peerSCU = (org.w3c.dom.Element)peerSCUs.item(j);
/* 1128 */               Integer scuId = new Integer(peerSCU.getAttributes().getNamedItem("uid").getNodeValue());
/*      */               
/* 1130 */               org.w3c.dom.NodeList contributors = peerSCU.getElementsByTagName("contributor");
/* 1131 */               for (int k = 0; k < contributors.getLength(); k++)
/*      */               {
/* 1133 */                 org.w3c.dom.Element contributor = (org.w3c.dom.Element)contributors.item(k);
/* 1134 */                 org.w3c.dom.NodeList contributorParts = contributor.getElementsByTagName("part");
/* 1135 */                 if (contributorParts.getLength() <= 1)
/*      */                 {
/* 1137 */                   String label = contributorParts.item(0).getAttributes().getNamedItem("label").getNodeValue();
/*      */                   
/* 1139 */                   if (!contributorMap.containsKey(label))
/* 1140 */                     contributorMap.put(label, new java.util.HashSet());
/* 1141 */                   ((java.util.Set)contributorMap.get(label)).add(scuId);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */           catch (Exception ex) {
/* 1147 */             showError(ex.getClass().toString(), ex.getMessage());
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1156 */       String text = this.peerTextPane.getText();
/* 1157 */       java.util.Iterator labelIterator = contributorMap.keySet().iterator();
/*      */       
/* 1159 */       while (labelIterator.hasNext())
/*      */       {
/* 1161 */         String label = (String)labelIterator.next();
/* 1162 */         int startIndex = 0;
/* 1163 */         while ((startIndex = text.indexOf(label, startIndex)) != -1)
/*      */         {
/* 1165 */           int endIndex = startIndex + label.length();
/* 1166 */           java.util.Set scuIds = (java.util.Set)contributorMap.get(label);
/*      */           
/* 1168 */           java.util.Enumeration scuNodeEnum = this.peerTree.getRootNode().children();
/* 1169 */           while (scuNodeEnum.hasMoreElements())
/*      */           {
/* 1171 */             javax.swing.tree.DefaultMutableTreeNode scuNode = (javax.swing.tree.DefaultMutableTreeNode)scuNodeEnum.nextElement();
/* 1172 */             if (scuIds.contains(new Integer(((SCU)scuNode.getUserObject()).getId())))
/*      */             {
/* 1174 */               this.peerTree.insertNodeInto(new javax.swing.tree.DefaultMutableTreeNode(new SCUContributor(new SCUContributorPart(startIndex, endIndex, label))), scuNode);
/*      */               
/* 1176 */               this.peerTextPane.modifyTextSelection(startIndex, endIndex, true);
/*      */             }
/*      */           }
/* 1179 */           startIndex = endIndex;
/*      */         }
/*      */       }
/* 1182 */       this.peerTree.expandTree();
/* 1183 */       setPeerModified(true);
/*      */     }
/* 1185 */     else if (e.getActionCommand().equals("collapse"))
/*      */     {
/* 1187 */       javax.swing.JButton btn = getCollapseBtn();
/* 1188 */       SCUTree tree = getTree();
/*      */       
/* 1190 */       tree.collapseTree();
/* 1191 */       btn.setText(" Expand ");
/* 1192 */       btn.setMnemonic('p');
/* 1193 */       btn.setActionCommand("expand");
/*      */     }
/* 1195 */     else if (e.getActionCommand().equals("expand"))
/*      */     {
/* 1197 */       javax.swing.JButton btn = getCollapseBtn();
/* 1198 */       SCUTree tree = getTree();
/*      */       
/* 1200 */       tree.expandTree();
/* 1201 */       btn.setText("Collapse");
/* 1202 */       btn.setMnemonic('l');
/* 1203 */       btn.setActionCommand("collapse");
/*      */     }
/* 1205 */     else if (e.getActionCommand().equals("comment"))
/*      */     {
/* 1207 */       javax.swing.tree.DefaultMutableTreeNode selectedNode = (javax.swing.tree.DefaultMutableTreeNode)getTree().getLastSelectedPathComponent();
/*      */       
/*      */ 
/* 1210 */       if (selectedNode.getLevel() == 1)
/*      */       {
/* 1212 */         SCU scu = (SCU)selectedNode.getUserObject();
/* 1213 */         String input = (String)javax.swing.JOptionPane.showInputDialog(this, "Enter the comment", "Comment", -1, null, null, scu.getComment());
/*      */         
/* 1215 */         if (input != null)
/*      */         {
/* 1217 */           scu.setComment(input.trim());
/* 1218 */           getTree().nodeChanged(selectedNode);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1223 */         SCUContributor scuContributor = (SCUContributor)selectedNode.getUserObject();
/* 1224 */         String input = (String)javax.swing.JOptionPane.showInputDialog(this, "Enter the comment", "Comment", -1, null, null, scuContributor.getComment());
/*      */         
/* 1226 */         if (input != null)
/*      */         {
/* 1228 */           scuContributor.setComment(input.trim());
/* 1229 */           getTree().nodeChanged(selectedNode);
/*      */         }
/*      */       }
/*      */     }
/* 1233 */     else if (e.getActionCommand().startsWith("dragScu"))
/*      */     {
/* 1235 */       this.draggingScuMove = e.getActionCommand().endsWith("Move");
/*      */     }
/* 1237 */     else if (e.getActionCommand().equals("regex"))
/*      */     {
/* 1239 */       String startDocumentPatternStrTmp = this.startDocumentPatternStr;
/*      */       
/*      */       do
/*      */       {
/* 1243 */         startDocumentPatternStrTmp = (String)javax.swing.JOptionPane.showInputDialog(this, "Enter the regular expression for the beginning of a new document", "Document Header RegEx", -1, null, null, startDocumentPatternStrTmp);
/*      */         
/*      */ 
/*      */ 
/* 1247 */         if (startDocumentPatternStrTmp == null) {
/*      */           break;
/*      */         }
/* 1250 */       } while (!initializeStartDocumentIndexes(startDocumentPatternStrTmp));
/*      */       
/* 1252 */       javax.swing.JOptionPane.showMessageDialog(this, "Your regular expression found " + this.startDocumentIndexes.length + " documents", "RegEx Result", -1);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private org.w3c.dom.Document makeDocument(java.io.File file)
/*      */     throws java.io.IOException, org.xml.sax.SAXException, javax.xml.parsers.ParserConfigurationException, javax.xml.parsers.FactoryConfigurationError
/*      */   {
/* 1263 */     javax.xml.parsers.DocumentBuilderFactory factory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
/* 1264 */     factory.setValidating(true);
/* 1265 */     factory.setIgnoringElementContentWhitespace(true);
/* 1266 */     factory.setIgnoringComments(true);
/* 1267 */     javax.xml.parsers.DocumentBuilder documdentBuilder = factory.newDocumentBuilder();
/* 1268 */     documdentBuilder.setErrorHandler(this);
/* 1269 */     return documdentBuilder.parse(file);
/*      */   }
/*      */   
/*      */   private void msg(String text)
/*      */   {
/* 1274 */     this.statusLbl.setText(text);
/*      */   }
/*      */   
/*      */   private String xmlize(String str)
/*      */   {
/* 1279 */     return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\\\"", "&quot;").replaceAll("\n", "");
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean writeout(java.io.File file, boolean writePeer)
/*      */   {
/* 1285 */     boolean success = true;
/*      */     try
/*      */     {
/* 1288 */       java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.BufferedWriter(new java.io.FileWriter(file)));
/*      */       
/* 1290 */       writer.println("<?xml version=\"1.0\"?>");
/* 1291 */       writer.println("<!DOCTYPE " + (writePeer ? "peerAnnotation" : "pyramid") + " [");
/*      */       
/* 1293 */       writer.println(writePeer ? getPeerDTD() : getPyramidDTD());
/* 1294 */       writer.println("]>");
/* 1295 */       writer.println();
/* 1296 */       if (writePeer)
/*      */       {
/* 1298 */         writer.println("<peerAnnotation>");
/* 1299 */         writer.println("<pyramid>");
/* 1300 */         writer.println(getXML(false));
/* 1301 */         writer.println("</pyramid>");
/* 1302 */         writer.println("<annotation>");
/* 1303 */         writer.println(getXML(true));
/* 1304 */         writer.println("</annotation>");
/* 1305 */         writer.println("</peerAnnotation>");
/* 1306 */         this.peerFile = file;
/* 1307 */         setPeerModified(false);
/*      */       }
/*      */       else
/*      */       {
/* 1311 */         writer.println("<pyramid>");
/* 1312 */         writer.println(getXML(false));
/* 1313 */         writer.println("</pyramid>");
/* 1314 */         this.pyramidFile = file;
/* 1315 */         setPyramidModified(false);
/*      */       }
/* 1317 */       writer.close();
/* 1318 */       msg("Saved " + file);
/*      */     }
/*      */     catch (java.io.IOException ex)
/*      */     {
/* 1322 */       ex.printStackTrace();
/* 1323 */       msg(ex.getMessage());
/* 1324 */       success = false;
/*      */     }
/* 1326 */     return success;
/*      */   }
/*      */   
/*      */   private String getPyramidDTD()
/*      */   {
/* 1331 */     StringBuffer buffer = new StringBuffer();
/*      */     
/* 1333 */     buffer.append(" <!ELEMENT pyramid (startDocumentRegEx?,text,scu*)>").append(eol);
/* 1334 */     buffer.append(" <!ELEMENT startDocumentRegEx (#PCDATA)>").append(eol);
/* 1335 */     buffer.append(" <!ELEMENT text (line*)>").append(eol);
/* 1336 */     buffer.append(" <!ELEMENT line (#PCDATA)>").append(eol);
/* 1337 */     buffer.append(" <!ELEMENT scu (contributor)+>").append(eol);
/* 1338 */     buffer.append(" <!ATTLIST scu uid CDATA #REQUIRED").append(eol);
/* 1339 */     buffer.append("               label CDATA #REQUIRED").append(eol);
/* 1340 */     buffer.append("               comment CDATA #IMPLIED>").append(eol);
/* 1341 */     buffer.append(" <!ELEMENT contributor (part)+>").append(eol);
/* 1342 */     buffer.append(" <!ATTLIST contributor label CDATA #REQUIRED").append(eol);
/* 1343 */     buffer.append("                       comment CDATA #IMPLIED>").append(eol);
/* 1344 */     buffer.append(" <!ELEMENT part EMPTY>").append(eol);
/* 1345 */     buffer.append(" <!ATTLIST part label CDATA #REQUIRED").append(eol);
/* 1346 */     buffer.append("                start CDATA #REQUIRED").append(eol);
/* 1347 */     buffer.append("                end   CDATA #REQUIRED>").append(eol);
/*      */     
/* 1349 */     return buffer.toString();
/*      */   }
/*      */   
/*      */   private String getPeerDTD()
/*      */   {
/* 1354 */     StringBuffer buffer = new StringBuffer();
/*      */     
/* 1356 */     buffer.append(" <!ELEMENT peerAnnotation (pyramid,annotation)>").append(eol);
/* 1357 */     buffer.append(" <!ELEMENT annotation (text,peerscu+)>").append(eol);
/* 1358 */     buffer.append(" <!ELEMENT peerscu (contributor)*>").append(eol);
/* 1359 */     buffer.append(" <!ATTLIST peerscu uid CDATA #REQUIRED").append(eol);
/* 1360 */     buffer.append("                   label CDATA #REQUIRED").append(eol);
/* 1361 */     buffer.append("                   comment CDATA #IMPLIED>").append(eol);
/* 1362 */     buffer.append(getPyramidDTD());
/*      */     
/* 1364 */     return buffer.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private String getXML(boolean getPeer)
/*      */   {
/* 1371 */     StringBuffer buffer = new StringBuffer();
/*      */     //SCUTree tree;
/* 1373 */     SCUTextPane textPane; SCUTree tree; if (getPeer)
/*      */     {
/* 1375 */       textPane = this.peerTextPane;
/* 1376 */       tree = this.peerTree;
/*      */     }
/*      */     else
/*      */     {
/* 1380 */       textPane = this.pyramidTextPane;
/* 1381 */       tree = this.pyramidTree;
/* 1382 */       if (this.startDocumentPatternStr != null) {
/* 1383 */         buffer.append("<startDocumentRegEx><![CDATA[").append(this.startDocumentPatternStr).append("]]></startDocumentRegEx>").append(eol);
/*      */       }
/*      */     }
/*      */     
/* 1387 */     buffer.append(" <text>").append(eol);
/* 1388 */     String[] lines = textPane.getText().split("\n");
/* 1389 */     for (int i = 0; i < lines.length; i++)
/*      */     {
/* 1391 */       buffer.append("  <line>").append(xmlize(lines[i])).append("</line>").append(eol);
/*      */     }
/* 1393 */     buffer.append(" </text>").append(eol);
/* 1394 */     java.util.Enumeration scuNodesEnum = tree.getRootNode().children();
/* 1395 */     boolean wroteSCU = false;
/* 1396 */     while (scuNodesEnum.hasMoreElements())
/*      */     {
/* 1398 */       javax.swing.tree.DefaultMutableTreeNode scuNode = (javax.swing.tree.DefaultMutableTreeNode)scuNodesEnum.nextElement();
/*      */       
/* 1400 */       SCU scu = (SCU)scuNode.getUserObject();
/*      */       
/* 1402 */       if (wroteSCU)
/*      */       {
/* 1404 */         buffer.append(eol);
/*      */       }
/* 1406 */       wroteSCU = true;
/*      */       
/* 1408 */       String scuComment = scu.getComment();
/* 1409 */       if ((scuComment != null) && (scuComment.length() > 0)) {
/* 1410 */         scuComment = " comment=\"" + xmlize(scuComment) + "\"";
/*      */       } else {
/* 1412 */         scuComment = "";
/*      */       }
/* 1414 */       buffer.append(" <").append(getPeer ? "peer" : "").append("scu uid=\"").append(scu.getId()).append("\" label=\"").append(xmlize(scu.toString())).append("\"").append(scuComment).append(">").append(eol);
/*      */       
/* 1416 */       java.util.Enumeration scuContributorEnum = scuNode.children();
/* 1417 */       while (scuContributorEnum.hasMoreElements())
/*      */       {
/* 1419 */         SCUContributor scuContributor = (SCUContributor)((javax.swing.tree.DefaultMutableTreeNode)scuContributorEnum.nextElement()).getUserObject();
/*      */         
/*      */ 
/*      */ 
/* 1423 */         String scuContributorComment = scuContributor.getComment();
/* 1424 */         if ((scuContributorComment != null) && (scuContributorComment.length() > 0)) {
/* 1425 */           scuContributorComment = " comment=\"" + xmlize(scuContributorComment) + "\"";
/*      */         } else {
/* 1427 */           scuContributorComment = "";
/*      */         }
/* 1429 */         buffer.append("  <contributor label=\"").append(xmlize(scuContributor.toString())).append("\"").append(scuContributorComment).append(">").append(eol);
/*      */         
/* 1431 */         java.util.Iterator scuContributorParts = scuContributor.elements();
/* 1432 */         while (scuContributorParts.hasNext())
/*      */         {
/* 1434 */           SCUContributorPart scuContributorPart = (SCUContributorPart)scuContributorParts.next();
/*      */           
/* 1436 */           buffer.append("   <part label=\"").append(xmlize(scuContributorPart.toString())).append("\" start=\"").append(scuContributorPart.getStartIndex()).append("\" end=\"").append(scuContributorPart.getEndIndex()).append("\"/>").append(eol);
/*      */         }
/*      */         
/*      */ 
/* 1440 */         buffer.append("  </contributor>").append(eol);
/*      */       }
/* 1442 */       buffer.append(" </").append(getPeer ? "peer" : "").append("scu>");
/*      */     }
/*      */     
/* 1445 */     return buffer.toString();
/*      */   }
/*      */   
/*      */   protected void setPyramidModified(boolean isModified)
/*      */   {
/* 1450 */     this.isPyramidModified = isModified;
/* 1451 */     this.fileSavePyramidMenuItem.setEnabled(isModified);
/* 1452 */     this.fileSavePyramidAsMenuItem.setEnabled(isModified);
/* 1453 */     this.pyramidUndoController.add(deepCopy(this.pyramidTree.getRootNode()));
/*      */   }
/*      */   
/*      */   protected void setPeerModified(boolean isModified)
/*      */   {
/* 1458 */     this.isPeerModified = isModified;
/* 1459 */     this.fileSavePeerAnnotationMenuItem.setEnabled(isModified);
/* 1460 */     this.fileSavePeerAnnotationAsMenuItem.setEnabled(isModified);
/* 1461 */     this.peerUndoController.add(deepCopy(this.peerTree.getRootNode()));
/*      */   }
/*      */   
/*      */   private void setPyramidLoaded(boolean isLoaded)
/*      */   {
/* 1466 */     this.isPyramidLoaded = isLoaded;
/* 1467 */     this.fileClosePyramidMenuItem.setEnabled(isLoaded);
/* 1468 */     this.fileNewPeerAnnotationMenuItem.setEnabled(isLoaded);
/* 1469 */     this.newBtn.setEnabled(isLoaded);
/* 1470 */     this.filePrintMenuItem.setEnabled(isLoaded);
/* 1471 */     this.dragScuMoveMenuItem.setEnabled(isLoaded);
/* 1472 */     this.dragScuMergeMenuItem.setEnabled(isLoaded);
/* 1473 */     this.documentStartRegexMenuItem.setEnabled(isLoaded);
/*      */     
/* 1475 */     if (isLoaded)
/*      */     {
/* 1477 */       showCard("pyramid");
/* 1478 */       this.pyramidUndoController.setActive(true);
/* 1479 */       this.peerUndoController.setActive(false);
/* 1480 */       this.pyramidUndoController.clear();
/* 1481 */       this.pyramidUndoController.add(deepCopy(this.pyramidTree.getRootNode()));
/*      */     }
/*      */     else
/*      */     {
/* 1485 */       this.pyramidUndoController.clear();
/* 1486 */       this.pyramidUndoController.setActive(false);
/* 1487 */       setPyramidModified(false);
/* 1488 */       this.orderBtn.setEnabled(false);
/* 1489 */       this.collapseBtn.setEnabled(false);
/*      */     }
/*      */   }
/*      */   
/*      */   private void setPeerLoaded(boolean isLoaded)
/*      */   {
/* 1495 */     this.isPeerLoaded = isLoaded;
/*      */     
/* 1497 */     this.fileNewPyramidMenuItem.setEnabled(!isLoaded);
/* 1498 */     this.fileLoadPyramidMenuItem.setEnabled(!isLoaded);
/* 1499 */     this.fileClosePyramidMenuItem.setEnabled(!isLoaded);
/* 1500 */     this.fileShowPeerAnnotationScoreMenuItem.setEnabled(isLoaded);
/* 1501 */     this.editAutoannotateFromPyramidMenuItem.setEnabled(isLoaded);
/* 1502 */     this.editAutoannotateFromPeersMenuItem.setEnabled(isLoaded);
/* 1503 */     this.editAutoannotateFromBothMenuItem.setEnabled(isLoaded);
/* 1504 */     this.dragScuMoveMenuItem.setEnabled((!isLoaded) && (this.isPyramidLoaded));
/* 1505 */     this.dragScuMergeMenuItem.setEnabled((!isLoaded) && (this.isPyramidLoaded));
/* 1506 */     this.documentStartRegexMenuItem.setEnabled((!isLoaded) && (this.isPyramidLoaded));
/*      */     
/* 1508 */     if (isLoaded)
/*      */     {
/* 1510 */       showCard("peer");
/* 1511 */       this.fileClosePeerAnnotationMenuItem.setEnabled(true);
/* 1512 */       this.scoreDlg.setText(getScore());
/* 1513 */       this.scoreDlg.pack();
/*      */       
/* 1515 */       this.pyramidUndoController.setActive(false);
/* 1516 */       this.peerUndoController.setActive(true);
/* 1517 */       this.peerUndoController.clear();
/* 1518 */       this.peerUndoController.add(deepCopy(this.peerTree.getRootNode()));
/*      */     }
/*      */     else
/*      */     {
/* 1522 */       showCard("pyramid");
/* 1523 */       this.fileClosePeerAnnotationMenuItem.setEnabled(false);
/* 1524 */       setPeerModified(false);
/* 1525 */       this.fileShowPeerAnnotationScoreMenuItem.setSelected(false);
/* 1526 */       this.scoreDlg.setVisible(false);
/*      */       
/* 1528 */       this.peerUndoController.clear();
/* 1529 */       this.peerUndoController.setActive(false);
/* 1530 */       this.pyramidUndoController.setActive(true);
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean saveModifiedPyramid()
/*      */   {
/* 1536 */     int choice = javax.swing.JOptionPane.showConfirmDialog(this, "Save changes to the loaded pyramid?", "Save changes", 1, 3);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1541 */     if (choice == 0)
/*      */     {
/* 1543 */       if (this.pyramidFile == null)
/*      */       {
/* 1545 */         return savePyramid(true);
/*      */       }
/*      */       
/*      */ 
/* 1549 */       return savePyramid(false);
/*      */     }
/*      */     
/* 1552 */     if (choice == 1)
/*      */     {
/* 1554 */       return true;
/*      */     }
/*      */     
/*      */ 
/* 1558 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean saveModifiedPeer()
/*      */   {
/* 1564 */     int choice = javax.swing.JOptionPane.showConfirmDialog(this, "Save changes to the loaded peer annotation?", "Save changes", 1, 3);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1569 */     if (choice == 0)
/*      */     {
/* 1571 */       if (this.peerFile == null)
/*      */       {
/* 1573 */         return savePeer(true);
/*      */       }
/*      */       
/*      */ 
/* 1577 */       return savePeer(false);
/*      */     }
/*      */     
/* 1580 */     if (choice == 1)
/*      */     {
/* 1582 */       return true;
/*      */     }
/*      */     
/*      */ 
/* 1586 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean savePyramid(boolean useNewFile)
/*      */   {
/* 1592 */     if (useNewFile)
/*      */     {
/* 1594 */       javax.swing.JFileChooser chooser = new DucViewFileChooser(this.defaultFilePath, true, true);
/* 1595 */       if (chooser.showSaveDialog(this) == 0)
/*      */       {
/* 1597 */         return writeout(chooser.getSelectedFile(), false);
/*      */       }
/*      */       
/*      */ 
/* 1601 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1606 */     return writeout(this.pyramidFile, false);
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean savePeer(boolean useNewFile)
/*      */   {
/* 1612 */     if (useNewFile)
/*      */     {
/* 1614 */       javax.swing.JFileChooser chooser = new DucViewFileChooser(this.defaultFilePath, true, false);
/* 1615 */       if (chooser.showSaveDialog(this) == 0)
/*      */       {
/* 1617 */         return writeout(chooser.getSelectedFile(), true);
/*      */       }
/*      */       
/*      */ 
/* 1621 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1626 */     return writeout(this.peerFile, true);
/*      */   }
/*      */   
/*      */ 
/*      */   private javax.swing.JMenuBar createMenuBar()
/*      */   {
/* 1632 */     javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
/*      */     
/* 1634 */     javax.swing.JMenu fileMenu = new javax.swing.JMenu("File");
/* 1635 */     fileMenu.setMnemonic('f');
/*      */     
/* 1637 */     javax.swing.JMenu pyramidMenu = new javax.swing.JMenu("Pyramid");
/* 1638 */     pyramidMenu.setMnemonic('y');
/*      */     
/* 1640 */     this.fileNewPyramidMenuItem = new JMenuItem("New...");
/* 1641 */     this.fileNewPyramidMenuItem.setMnemonic('n');
/* 1642 */     this.fileNewPyramidMenuItem.setActionCommand("fileNewPyramid");
/* 1643 */     this.fileNewPyramidMenuItem.addActionListener(this);
/* 1644 */     pyramidMenu.add(this.fileNewPyramidMenuItem);
/*      */     
/* 1646 */     this.fileLoadPyramidMenuItem = new JMenuItem("Load...");
/* 1647 */     this.fileLoadPyramidMenuItem.setMnemonic('l');
/* 1648 */     this.fileLoadPyramidMenuItem.setActionCommand("fileLoadPyramid");
/* 1649 */     this.fileLoadPyramidMenuItem.addActionListener(this);
/* 1650 */     pyramidMenu.add(this.fileLoadPyramidMenuItem);
/*      */     
/* 1652 */     this.fileSavePyramidMenuItem = new JMenuItem("Save");
/* 1653 */     this.fileSavePyramidMenuItem.setMnemonic('s');
/* 1654 */     this.fileSavePyramidMenuItem.setActionCommand("fileSavePyramid");
/* 1655 */     this.fileSavePyramidMenuItem.addActionListener(this);
/* 1656 */     this.fileSavePyramidMenuItem.setEnabled(false);
/* 1657 */     pyramidMenu.add(this.fileSavePyramidMenuItem);
/*      */     
/* 1659 */     this.fileSavePyramidAsMenuItem = new JMenuItem("Save As...");
/* 1660 */     this.fileSavePyramidAsMenuItem.setMnemonic('a');
/* 1661 */     this.fileSavePyramidAsMenuItem.setActionCommand("fileSavePyramidAs");
/* 1662 */     this.fileSavePyramidAsMenuItem.addActionListener(this);
/* 1663 */     this.fileSavePyramidAsMenuItem.setEnabled(false);
/* 1664 */     pyramidMenu.add(this.fileSavePyramidAsMenuItem);
/*      */     
/* 1666 */     this.fileClosePyramidMenuItem = new JMenuItem("Close");
/* 1667 */     this.fileClosePyramidMenuItem.setMnemonic('c');
/* 1668 */     this.fileClosePyramidMenuItem.setActionCommand("fileClosePyramid");
/* 1669 */     this.fileClosePyramidMenuItem.addActionListener(this);
/* 1670 */     this.fileClosePyramidMenuItem.setEnabled(false);
/* 1671 */     pyramidMenu.add(this.fileClosePyramidMenuItem);
/*      */     
/* 1673 */     fileMenu.add(pyramidMenu);
/*      */     
/* 1675 */     javax.swing.JMenu peerMenu = new javax.swing.JMenu("Peer Annotation");
/* 1676 */     peerMenu.setMnemonic('e');
/*      */     
/* 1678 */     this.fileNewPeerAnnotationMenuItem = new JMenuItem("New...");
/* 1679 */     this.fileNewPeerAnnotationMenuItem.setMnemonic('n');
/* 1680 */     this.fileNewPeerAnnotationMenuItem.setActionCommand("fileNewPeerAnnotation");
/* 1681 */     this.fileNewPeerAnnotationMenuItem.addActionListener(this);
/* 1682 */     this.fileNewPeerAnnotationMenuItem.setEnabled(false);
/* 1683 */     peerMenu.add(this.fileNewPeerAnnotationMenuItem);
/*      */     
/* 1685 */     JMenuItem fileLoadPeerAnnotationMenuItem = new JMenuItem("Load...");
/* 1686 */     fileLoadPeerAnnotationMenuItem.setMnemonic('l');
/* 1687 */     fileLoadPeerAnnotationMenuItem.setActionCommand("fileLoadPeerAnnotation");
/* 1688 */     fileLoadPeerAnnotationMenuItem.addActionListener(this);
/* 1689 */     peerMenu.add(fileLoadPeerAnnotationMenuItem);
/*      */     
/* 1691 */     this.fileSavePeerAnnotationMenuItem = new JMenuItem("Save");
/* 1692 */     this.fileSavePeerAnnotationMenuItem.setMnemonic('s');
/* 1693 */     this.fileSavePeerAnnotationMenuItem.setActionCommand("fileSavePeerAnnotation");
/* 1694 */     this.fileSavePeerAnnotationMenuItem.addActionListener(this);
/* 1695 */     this.fileSavePeerAnnotationMenuItem.setEnabled(false);
/* 1696 */     peerMenu.add(this.fileSavePeerAnnotationMenuItem);
/*      */     
/* 1698 */     this.fileSavePeerAnnotationAsMenuItem = new JMenuItem("Save As...");
/* 1699 */     this.fileSavePeerAnnotationAsMenuItem.setMnemonic('a');
/* 1700 */     this.fileSavePeerAnnotationAsMenuItem.setActionCommand("fileSavePeerAnnotationAs");
/* 1701 */     this.fileSavePeerAnnotationAsMenuItem.addActionListener(this);
/* 1702 */     this.fileSavePeerAnnotationAsMenuItem.setEnabled(false);
/* 1703 */     peerMenu.add(this.fileSavePeerAnnotationAsMenuItem);
/*      */     
/* 1705 */     this.fileShowPeerAnnotationScoreMenuItem = new javax.swing.JCheckBoxMenuItem("Show Score");
/* 1706 */     this.fileShowPeerAnnotationScoreMenuItem.setMnemonic('w');
/* 1707 */     this.fileShowPeerAnnotationScoreMenuItem.setActionCommand("fileShowPeerAnnotationScore");
/*      */     
/* 1709 */     this.fileShowPeerAnnotationScoreMenuItem.addActionListener(this);
/* 1710 */     this.fileShowPeerAnnotationScoreMenuItem.setEnabled(false);
/* 1711 */     peerMenu.add(this.fileShowPeerAnnotationScoreMenuItem);
/*      */     
/* 1713 */     this.fileClosePeerAnnotationMenuItem = new JMenuItem("Close");
/* 1714 */     this.fileClosePeerAnnotationMenuItem.setMnemonic('c');
/* 1715 */     this.fileClosePeerAnnotationMenuItem.setActionCommand("fileClosePeerAnnotation");
/* 1716 */     this.fileClosePeerAnnotationMenuItem.addActionListener(this);
/* 1717 */     this.fileClosePeerAnnotationMenuItem.setEnabled(false);
/* 1718 */     peerMenu.add(this.fileClosePeerAnnotationMenuItem);
/*      */     
/* 1720 */     fileMenu.add(peerMenu);
/*      */     
/* 1722 */     this.filePrintMenuItem = new JMenuItem("Print SCU Labels");
/* 1723 */     this.filePrintMenuItem.setMnemonic('p');
/* 1724 */     this.filePrintMenuItem.setActionCommand("print");
/* 1725 */     this.filePrintMenuItem.addActionListener(this);
/* 1726 */     this.filePrintMenuItem.setEnabled(false);
/* 1727 */     fileMenu.add(this.filePrintMenuItem);
/*      */     
/* 1729 */     JMenuItem fileExitMenuItem = new JMenuItem("Exit");
/* 1730 */     fileExitMenuItem.setMnemonic('x');
/* 1731 */     fileExitMenuItem.setActionCommand("exit");
/* 1732 */     fileExitMenuItem.addActionListener(this);
/* 1733 */     fileMenu.add(fileExitMenuItem);
/*      */     
/* 1735 */     menuBar.add(fileMenu);
/*      */     
/* 1737 */     javax.swing.JMenu editMenu = new javax.swing.JMenu("Edit");
/* 1738 */     editMenu.setMnemonic('e');
/*      */     
/* 1740 */     JMenuItem editFindMenuItem = new JMenuItem("Find...     Ctrl+F");
/* 1741 */     editFindMenuItem.setMnemonic('f');
/* 1742 */     editFindMenuItem.setActionCommand("find");
/* 1743 */     editFindMenuItem.addActionListener(this);
/* 1744 */     editMenu.add(editFindMenuItem);
/*      */     
/* 1746 */     this.editUndoMenuItem = new JMenuItem("Undo");
/* 1747 */     this.editUndoMenuItem.setMnemonic('u');
/* 1748 */     this.editUndoMenuItem.setActionCommand("undo");
/* 1749 */     this.editUndoMenuItem.addActionListener(this);
/* 1750 */     this.editUndoMenuItem.setEnabled(false);
/* 1751 */     editMenu.add(this.editUndoMenuItem);
/*      */     
/* 1753 */     this.editRedoMenuItem = new JMenuItem("Redo");
/* 1754 */     this.editRedoMenuItem.setMnemonic('r');
/* 1755 */     this.editRedoMenuItem.setActionCommand("redo");
/* 1756 */     this.editRedoMenuItem.addActionListener(this);
/* 1757 */     this.editRedoMenuItem.setEnabled(false);
/* 1758 */     editMenu.add(this.editRedoMenuItem);
/*      */     
/* 1760 */     javax.swing.JMenu autoannotateMenu = new javax.swing.JMenu("Autoannotate");
/* 1761 */     autoannotateMenu.setMnemonic('a');
/*      */     
/* 1763 */     this.editAutoannotateFromPyramidMenuItem = new JMenuItem("From local pyramid");
/* 1764 */     this.editAutoannotateFromPyramidMenuItem.setMnemonic('y');
/* 1765 */     this.editAutoannotateFromPyramidMenuItem.setActionCommand("autoannotate_from_pyramid");
/* 1766 */     this.editAutoannotateFromPyramidMenuItem.addActionListener(this);
/* 1767 */     this.editAutoannotateFromPyramidMenuItem.setEnabled(false);
/* 1768 */     autoannotateMenu.add(this.editAutoannotateFromPyramidMenuItem);
/*      */     
/* 1770 */     this.editAutoannotateFromPeersMenuItem = new JMenuItem("From annotated peers...");
/* 1771 */     this.editAutoannotateFromPeersMenuItem.setMnemonic('e');
/* 1772 */     this.editAutoannotateFromPeersMenuItem.setActionCommand("autoannotate_from_peers");
/* 1773 */     this.editAutoannotateFromPeersMenuItem.addActionListener(this);
/* 1774 */     this.editAutoannotateFromPeersMenuItem.setEnabled(false);
/* 1775 */     autoannotateMenu.add(this.editAutoannotateFromPeersMenuItem);
/*      */     
/* 1777 */     this.editAutoannotateFromBothMenuItem = new JMenuItem("From both...");
/* 1778 */     this.editAutoannotateFromBothMenuItem.setMnemonic('b');
/* 1779 */     this.editAutoannotateFromBothMenuItem.setActionCommand("autoannotate_from_both");
/* 1780 */     this.editAutoannotateFromBothMenuItem.addActionListener(this);
/* 1781 */     this.editAutoannotateFromBothMenuItem.setEnabled(false);
/* 1782 */     autoannotateMenu.add(this.editAutoannotateFromBothMenuItem);
/*      */     
/* 1784 */     editMenu.add(autoannotateMenu);
/*      */     
/* 1786 */     menuBar.add(editMenu);
/*      */     
/* 1788 */     javax.swing.JMenu optionsMenu = new javax.swing.JMenu("Options");
/* 1789 */     optionsMenu.setMnemonic('o');
/*      */     
/* 1791 */     javax.swing.JMenu textSizeSubmenu = new javax.swing.JMenu("Text Size");
/* 1792 */     textSizeSubmenu.setMnemonic('t');
/*      */     
/* 1794 */     int[] textSizes = { 6, 8, 10, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 44, 48, 52, 56, 60 };
/*      */     
/*      */ 
/* 1797 */     javax.swing.ButtonGroup sizeGroup = new javax.swing.ButtonGroup();
/* 1798 */     for (int i = 0; i < textSizes.length; i++)
/*      */     {
/* 1800 */       javax.swing.JRadioButtonMenuItem textSizeMenuItem = new javax.swing.JRadioButtonMenuItem(String.valueOf(textSizes[i]));
/*      */       
/* 1802 */       textSizeMenuItem.setActionCommand("Text Size " + String.valueOf(textSizes[i]));
/* 1803 */       textSizeMenuItem.addActionListener(this);
/* 1804 */       sizeGroup.add(textSizeMenuItem);
/* 1805 */       if (this.currentTextSize == textSizes[i])
/*      */       {
/* 1807 */         textSizeMenuItem.setSelected(true);
/*      */       }
/* 1809 */       textSizeSubmenu.add(textSizeMenuItem);
/*      */     }
/* 1811 */     optionsMenu.add(textSizeSubmenu);
/*      */     
/* 1813 */     javax.swing.JMenu lookAndFeelSubmenu = new javax.swing.JMenu("Look And Feel");
/* 1814 */     lookAndFeelSubmenu.setMnemonic(76);
/*      */     
/* 1816 */     javax.swing.UIManager.LookAndFeelInfo[] installedLooks = javax.swing.UIManager.getInstalledLookAndFeels();
/*      */     
/* 1818 */     javax.swing.ButtonGroup lookAndFeelGroup = new javax.swing.ButtonGroup();
/* 1819 */     for (int i = 0; i < installedLooks.length; i++)
/*      */     {
/* 1821 */       javax.swing.JRadioButtonMenuItem lookAndFeelMenuItem = new javax.swing.JRadioButtonMenuItem(installedLooks[i].getName());
/*      */       
/* 1823 */       lookAndFeelMenuItem.setActionCommand("Look And Feel " + installedLooks[i].getClassName());
/*      */       
/* 1825 */       lookAndFeelMenuItem.addActionListener(this);
/* 1826 */       lookAndFeelGroup.add(lookAndFeelMenuItem);
/* 1827 */       if (javax.swing.UIManager.getLookAndFeel().getName().equals(installedLooks[i].getName()))
/*      */       {
/* 1829 */         lookAndFeelMenuItem.setSelected(true);
/*      */       }
/* 1831 */       lookAndFeelSubmenu.add(lookAndFeelMenuItem);
/*      */     }
/*      */     
/* 1834 */     optionsMenu.add(lookAndFeelSubmenu);
/*      */     
/* 1836 */     javax.swing.JMenu draggingScuMenu = new javax.swing.JMenu("Dragging SCU");
/* 1837 */     draggingScuMenu.setMnemonic('d');
/*      */     
/* 1839 */     javax.swing.ButtonGroup dragScuGroup = new javax.swing.ButtonGroup();
/*      */     
/* 1841 */     this.dragScuMoveMenuItem = new javax.swing.JRadioButtonMenuItem("Moves it under target SCU");
/* 1842 */     this.dragScuMoveMenuItem.setMnemonic('o');
/* 1843 */     this.dragScuMoveMenuItem.setActionCommand("dragScuMove");
/* 1844 */     this.dragScuMoveMenuItem.addActionListener(this);
/* 1845 */     this.dragScuMoveMenuItem.setSelected(true);
/* 1846 */     this.dragScuMoveMenuItem.setEnabled(false);
/* 1847 */     dragScuGroup.add(this.dragScuMoveMenuItem);
/* 1848 */     draggingScuMenu.add(this.dragScuMoveMenuItem);
/*      */     
/* 1850 */     this.dragScuMergeMenuItem = new javax.swing.JRadioButtonMenuItem("Merges it with target SCU");
/* 1851 */     this.dragScuMergeMenuItem.setMnemonic('e');
/* 1852 */     this.dragScuMergeMenuItem.setActionCommand("dragScuMerge");
/* 1853 */     this.dragScuMergeMenuItem.addActionListener(this);
/* 1854 */     this.dragScuMergeMenuItem.setEnabled(false);
/* 1855 */     dragScuGroup.add(this.dragScuMergeMenuItem);
/* 1856 */     draggingScuMenu.add(this.dragScuMergeMenuItem);
/*      */     
/* 1858 */     optionsMenu.add(draggingScuMenu);
/*      */     
/* 1860 */     this.documentStartRegexMenuItem = new JMenuItem("Document Header RegEx...");
/* 1861 */     this.documentStartRegexMenuItem.setMnemonic('h');
/* 1862 */     this.documentStartRegexMenuItem.setActionCommand("regex");
/* 1863 */     this.documentStartRegexMenuItem.addActionListener(this);
/* 1864 */     this.documentStartRegexMenuItem.setEnabled(false);
/* 1865 */     optionsMenu.add(this.documentStartRegexMenuItem);
/*      */     
/* 1867 */     menuBar.add(optionsMenu);
/*      */     
/* 1869 */     javax.swing.JMenu helpMenu = new javax.swing.JMenu("Help");
/* 1870 */     helpMenu.setMnemonic(72);
/*      */     
/* 1872 */     JMenuItem helpAboutMenuItem = new JMenuItem("About...");
/* 1873 */     helpAboutMenuItem.setMnemonic(65);
/* 1874 */     helpAboutMenuItem.setActionCommand("helpAbout");
/* 1875 */     helpAboutMenuItem.addActionListener(this);
/* 1876 */     helpMenu.add(helpAboutMenuItem);
/*      */     
/* 1878 */     menuBar.add(helpMenu);
/*      */     
/* 1880 */     return menuBar;
/*      */   }
/*      */   
/*      */   public javax.swing.JButton getAddBtn()
/*      */   {
/* 1885 */     if (this.isPeerLoaded)
/*      */     {
/* 1887 */       return this.addBtn_peer;
/*      */     }
/*      */     
/*      */ 
/* 1891 */     return this.addBtn;
/*      */   }
/*      */   
/*      */ 
/*      */   public javax.swing.JButton getRemoveBtn()
/*      */   {
/* 1897 */     if (this.isPeerLoaded)
/*      */     {
/* 1899 */       return this.removeBtn_peer;
/*      */     }
/*      */     
/*      */ 
/* 1903 */     return this.removeBtn;
/*      */   }
/*      */   
/*      */ 
/*      */   public javax.swing.JButton getRenameBtn()
/*      */   {
/* 1909 */     if (this.isPeerLoaded)
/*      */     {
/* 1911 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 1915 */     return this.renameBtn;
/*      */   }
/*      */   
/*      */ 
/*      */   public javax.swing.JButton getSetLabelBtn()
/*      */   {
/* 1921 */     if (this.isPeerLoaded)
/*      */     {
/* 1923 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 1927 */     return this.setLabelbtn;
/*      */   }
/*      */   
/*      */ 
/*      */   public javax.swing.JButton getCollapseBtn()
/*      */   {
/* 1933 */     if (this.isPeerLoaded) {
/* 1934 */       return this.collapseBtn_peer;
/*      */     }
/* 1936 */     return this.collapseBtn;
/*      */   }
/*      */   
/*      */   private SCUTree getTree()
/*      */   {
/* 1941 */     if (this.isPeerLoaded) {
/* 1942 */       return this.peerTree;
/*      */     }
/* 1944 */     return this.pyramidTree;
/*      */   }
/*      */   
/*      */   private SCUTextPane getTextPane()
/*      */   {
/* 1949 */     if (this.isPeerLoaded) {
/* 1950 */       return this.peerTextPane;
/*      */     }
/* 1952 */     return this.pyramidTextPane;
/*      */   }
/*      */   
/*      */   public javax.swing.JButton getCommentBtn()
/*      */   {
/* 1957 */     if (this.isPeerLoaded) {
/* 1958 */       return this.commentBtn_peer;
/*      */     }
/* 1960 */     return this.commentBtn;
/*      */   }
/*      */   
/*      */   private void loadTree(org.w3c.dom.Element top, boolean loadPeer) {
/*      */     javax.swing.JButton collapseButton;
/*      */     SCUTextPane textPane;
/*      */     SCUTree tree;
/*      */     javax.swing.JButton orderButton;
/*      */     //javax.swing.JButton collapseButton;
/* 1969 */     if (loadPeer)
/*      */     {
/* 1971 */       textPane = this.peerTextPane;
/* 1972 */       tree = this.peerTree;
/* 1973 */       orderButton = this.orderBtn_peer;
/* 1974 */       collapseButton = this.collapseBtn_peer;
/*      */     }
/*      */     else
/*      */     {
/* 1978 */       textPane = this.pyramidTextPane;
/* 1979 */       tree = this.pyramidTree;
/* 1980 */       orderButton = this.orderBtn;
/* 1981 */       collapseButton = this.collapseBtn;
/*      */     }
/*      */     
/* 1984 */     org.w3c.dom.NodeList lineNodeList = top.getElementsByTagName("line");
/* 1985 */     StringBuffer buffer = new StringBuffer();
/* 1986 */     for (int i = 0; i < lineNodeList.getLength(); i++)
/*      */     {
/* 1988 */       if (lineNodeList.item(i).getFirstChild() != null)
/*      */       {
/* 1990 */         buffer.append(lineNodeList.item(i).getFirstChild().getNodeValue());
/*      */       }
/* 1992 */       buffer.append("\n");
/*      */     }
/*      */     
/* 1995 */     textPane.loadText(buffer.toString());
/*      */     
/* 1997 */     javax.swing.tree.DefaultMutableTreeNode rootNode = new javax.swing.tree.DefaultMutableTreeNode("Root Node");
/* 1998 */     org.w3c.dom.NodeList scuNodeList = top.getElementsByTagName((loadPeer ? "peer" : "") + "scu");
/*      */     
/* 2000 */     for (int scuCnt = 0; scuCnt < scuNodeList.getLength(); scuCnt++)
/*      */     {
/* 2002 */       org.w3c.dom.Element scuElement = (org.w3c.dom.Element)scuNodeList.item(scuCnt);
/* 2003 */       int id = Integer.parseInt(scuElement.getAttribute("uid"));
/* 2004 */       String scuLabel = scuElement.getAttribute("label");
/* 2005 */       String scuComment = scuElement.getAttribute("comment");
/* 2006 */       javax.swing.tree.DefaultMutableTreeNode scuNode = new javax.swing.tree.DefaultMutableTreeNode(new SCU(id, scuLabel, scuComment));
/*      */       
/*      */ 
/* 2009 */       org.w3c.dom.NodeList scuContributorNodeList = scuElement.getElementsByTagName("contributor");
/*      */       
/* 2011 */       for (int scuContributorCnt = 0; 
/* 2012 */           scuContributorCnt < scuContributorNodeList.getLength(); 
/* 2013 */           scuContributorCnt++)
/*      */       {
/* 2015 */         org.w3c.dom.Element scuContributorElement = (org.w3c.dom.Element)scuContributorNodeList.item(scuContributorCnt);
/*      */         
/* 2017 */         String scuContributorComment = scuContributorElement.getAttribute("comment");
/* 2018 */         org.w3c.dom.NodeList scuContributorPartNodeList = scuContributorElement.getElementsByTagName("part");
/*      */         
/* 2020 */         org.w3c.dom.Element scuContributorPartElement = (org.w3c.dom.Element)scuContributorPartNodeList.item(0);
/*      */         
/* 2022 */         int startIndex = Integer.parseInt(scuContributorPartElement.getAttribute("start"));
/*      */         
/* 2024 */         int endIndex = Integer.parseInt(scuContributorPartElement.getAttribute("end"));
/*      */         
/* 2026 */         String label = scuContributorPartElement.getAttribute("label");
/* 2027 */         SCUContributorPart scuContributorPart = new SCUContributorPart(startIndex, endIndex, label);
/*      */         
/* 2029 */         textPane.modifyTextSelection(scuContributorPart.getStartIndex(), scuContributorPart.getEndIndex(), true);
/*      */         
/* 2031 */         SCUContributor scuContributor = new SCUContributor(scuContributorPart, scuContributorComment);
/* 2032 */         javax.swing.tree.DefaultMutableTreeNode scuContributorNode = new javax.swing.tree.DefaultMutableTreeNode(scuContributor);
/*      */         
/* 2034 */         for (int scuContributorPartCnt = 1; 
/* 2035 */             scuContributorPartCnt < scuContributorPartNodeList.getLength(); 
/* 2036 */             scuContributorPartCnt++)
/*      */         {
/* 2038 */           if (scuContributorPartCnt == 1)
/*      */           {
/*      */ 
/* 2041 */             scuContributorNode.add(new javax.swing.tree.DefaultMutableTreeNode(scuContributorPart));
/*      */           }
/* 2043 */           scuContributorPartElement = (org.w3c.dom.Element)scuContributorPartNodeList.item(scuContributorPartCnt);
/*      */           
/* 2045 */           startIndex = Integer.parseInt(scuContributorPartElement.getAttribute("start"));
/*      */           
/* 2047 */           endIndex = Integer.parseInt(scuContributorPartElement.getAttribute("end"));
/*      */           
/* 2049 */           label = scuContributorPartElement.getAttribute("label");
/* 2050 */           scuContributorPart = new SCUContributorPart(startIndex, endIndex, label);
/*      */           
/* 2052 */           textPane.modifyTextSelection(scuContributorPart.getStartIndex(), scuContributorPart.getEndIndex(), true);
/*      */           
/* 2054 */           scuContributor.add(scuContributorPart);
/* 2055 */           scuContributorNode.add(new javax.swing.tree.DefaultMutableTreeNode(scuContributorPart));
/*      */         }
/* 2057 */         scuNode.add(scuContributorNode);
/*      */       }
/*      */       
/* 2060 */       rootNode.add(scuNode);
/*      */     }
/* 2062 */     tree.rebuildTree(rootNode);
/*      */     
/* 2064 */     orderButton.setEnabled(tree.getRootNode().getChildCount() > 1);
/* 2065 */     collapseButton.setEnabled(tree.getRootNode().getChildCount() > 0);
/*      */   }
/*      */   
/*      */   private void showCard(String cardname)
/*      */   {
/* 2070 */     ((java.awt.CardLayout)this.mainPanel.getLayout()).show(this.mainPanel, cardname);
/*      */     //String titleAddon;
/* 2072 */     String titleAddon; if (cardname.equals("peer"))
/*      */     {
/* 2074 */       titleAddon = " - Annnotating Peer";
/*      */     }
/*      */     else
/*      */     {
/* 2078 */       titleAddon = " - Creating pyramid";
/*      */     }
/* 2080 */     setTitle("DucView v. 1.4" + titleAddon);
/*      */   }
/*      */   
/*      */   public void showError(String title, String message)
/*      */   {
/* 2085 */     javax.swing.JOptionPane.showMessageDialog(this, message, title, 0);
/*      */   }
/*      */   
/*      */   protected String getScore()
/*      */   {
/* 2090 */     if (!this.isPeerLoaded)
/*      */     {
/* 2092 */       return "Error: peer annotation is not loaded\n";
/*      */     }
/*      */     
/* 2095 */     StringBuffer resultBuffer = new StringBuffer();
/*      */     
/* 2097 */     int totalPyramidSCUContributors = 0;
/*      */     
/* 2099 */     java.util.HashMap pyramidScuNumContributorsMap = new java.util.HashMap();
/* 2100 */     java.util.Enumeration pyramidScuNodesEnum = this.pyramidTree.getRootNode().children();
/* 2101 */     while (pyramidScuNodesEnum.hasMoreElements())
/*      */     {
/* 2103 */       javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode)pyramidScuNodesEnum.nextElement();
/*      */       
/* 2105 */       SCU scu = (SCU)node.getUserObject();
/* 2106 */       pyramidScuNumContributorsMap.put(new Integer(scu.getId()), new Integer(node.getChildCount()));
/*      */       
/* 2108 */       totalPyramidSCUContributors += node.getChildCount();
/*      */     }
/*      */     
/* 2111 */     java.util.ArrayList peerSCUIds = new java.util.ArrayList();
/* 2112 */     java.util.ArrayList peerSCUMultipleContributors = new java.util.ArrayList();
/*      */     
/* 2114 */     java.util.HashMap peerScuNumContributorsMap = new java.util.HashMap();
/* 2115 */     java.util.Enumeration peerScuNodesEnum = this.peerTree.getRootNode().children();
/*      */     
/* 2117 */     while (peerScuNodesEnum.hasMoreElements())
/*      */     {
/* 2119 */       javax.swing.tree.DefaultMutableTreeNode node = (javax.swing.tree.DefaultMutableTreeNode)peerScuNodesEnum.nextElement();
/*      */       
/* 2121 */       int childCount = node.getChildCount();
/* 2122 */       SCU scu = (SCU)node.getUserObject();
/* 2123 */       int scuId = scu.getId();
/* 2124 */       if ((scuId != 0) && (childCount > 0))
/*      */       {
/* 2126 */         peerSCUIds.add(new Integer(scuId));
/*      */       }
/* 2128 */       if ((scuId != 0) && (childCount > 1))
/*      */       {
/* 2130 */         peerSCUMultipleContributors.add(scu);
/*      */       }
/* 2132 */       peerScuNumContributorsMap.put(new Integer(scuId), new Integer(node.getChildCount()));
/*      */     }
/*      */     
/*      */ 
/* 2136 */     int numerator = 0;
/* 2137 */     java.util.Iterator peerSCUIdsIterator = peerSCUIds.iterator();
/* 2138 */     while (peerSCUIdsIterator.hasNext())
/*      */     {
/* 2140 */       numerator += ((Integer)pyramidScuNumContributorsMap.get(peerSCUIdsIterator.next())).intValue();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2145 */     resultBuffer.append("<table>");
/* 2146 */     resultBuffer.append("<tr><td>Number of unique contributing SCUs:</td> <td> <b>" + peerSCUIds.size() + "</b></td></tr>\n");
/*      */     
/*      */ 
/* 2149 */     resultBuffer.append("<tr><td>Number of SCUs not in the pyramid:</td> <td> <b>" + peerScuNumContributorsMap.get(new Integer(0)) + "</b></td></tr>\n");
/*      */     
/*      */ 
/* 2152 */     resultBuffer.append("<tr><td>Number of SCUs with multiple contributors:</td> <td> <b>" + peerSCUMultipleContributors.size() + "</b></td></tr>\n");
/*      */     
/*      */ 
/*      */ 
/* 2156 */     int totalRepetativeSCUs = 0;
/*      */     
/* 2158 */     if (peerSCUMultipleContributors.size() > 0)
/*      */     {
/* 2160 */       resultBuffer.append("<tr><td colspan='2'><table>");
/* 2161 */       java.util.Iterator peerSCUMultipleContributorsIterator = peerSCUMultipleContributors.iterator();
/*      */       
/* 2163 */       while (peerSCUMultipleContributorsIterator.hasNext())
/*      */       {
/* 2165 */         SCU scu = (SCU)peerSCUMultipleContributorsIterator.next();
/* 2166 */         int numRepetitions = ((Integer)peerScuNumContributorsMap.get(new Integer(scu.getId()))).intValue() - 1;
/*      */         
/* 2168 */         resultBuffer.append("<tr><td><i> " + scu.toString().substring(scu.toString().indexOf(')') + 2) + "</i></td> <td>" + numRepetitions + "</td></tr>\n");
/*      */         
/*      */ 
/*      */ 
/* 2172 */         totalRepetativeSCUs += numRepetitions;
/*      */       }
/* 2174 */       resultBuffer.append("<tr><td> total extra contributors: </td><td>" + totalRepetativeSCUs + "</td></tr></table></tr>\n");
/*      */     }
/*      */     
/*      */ 
/* 2178 */     int totalSCUsInPeer = peerSCUIds.size() + totalRepetativeSCUs + ((Integer)peerScuNumContributorsMap.get(new Integer(0))).intValue();
/*      */     
/*      */ 
/* 2181 */     resultBuffer.append("<tr><td>Total SCUs in peer:</td> <td><b>" + totalSCUsInPeer + "</b></td></tr>\n");
/*      */     
/* 2183 */     resultBuffer.append("<tr><td>Total peer SCU weight:</td> <td><b>" + numerator + "</b></td></tr>\n");
/*      */     
/*      */ 
/* 2186 */     Integer[] peerNumContributors = (Integer[])pyramidScuNumContributorsMap.values().toArray(new Integer[0]);
/*      */     
/* 2188 */     java.util.Arrays.sort(peerNumContributors);
/*      */     
/* 2190 */     int denominator = 0;
/*      */     
/* 2192 */     int peerIndex = peerNumContributors.length - 1; for (int numSCUs = 0; 
/* 2193 */         (peerIndex >= 0) && (numSCUs < totalSCUsInPeer); numSCUs++)
/*      */     {
/* 2195 */       denominator += peerNumContributors[peerIndex].intValue();peerIndex--;
/*      */     }
/*      */     
/* 2198 */     if (denominator == 0)
/*      */     {
/* 2200 */       resultBuffer.append("<tr><td>No SCUs were annotated</td></tr>\n");
/*      */     }
/*      */     else
/*      */     {
/* 2204 */       resultBuffer.append("<tr><td>Maximum attainable score with " + totalSCUsInPeer + " SCUs:</td> <td><b> " + denominator + "</b></td></tr>\n<tr><td>Score: </td> <td><b>" + new java.text.DecimalFormat("#.####").format(numerator / denominator) + "</b></td></tr>");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2213 */       if (this.startDocumentIndexes != null)
/*      */       {
/* 2215 */         int model_denominator = 0;
/* 2216 */         int num_model_scus = Math.round(totalPyramidSCUContributors / this.startDocumentIndexes.length);
/*      */         
/* 2218 */         resultBuffer.append("\n<tr><td>Average SCUs in Model summary:</td> <td><b>" + num_model_scus + "</b></td></tr>\n");
/*      */         
/*      */ 
/* 2221 */         peerIndex = peerNumContributors.length - 1; for (int numSCUs = 0; 
/* 2222 */             (peerIndex >= 0) && (numSCUs < num_model_scus); numSCUs++)
/*      */         {
/* 2224 */           model_denominator += peerNumContributors[peerIndex].intValue();peerIndex--;
/*      */         }
/*      */         
/* 2227 */         resultBuffer.append("<tr><td>Maximum attainable score with " + num_model_scus + " SCUs:</td> <td><b> " + model_denominator + "</b></td></tr>\n<tr><td>Score using " + num_model_scus + " SCUs: </td> <td><b>" + new java.text.DecimalFormat("#.####").format(numerator / model_denominator) + "</b></td></tr>");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2236 */     resultBuffer.append("</table>");
/*      */     
/* 2238 */     return resultBuffer.toString();
/*      */   }
/*      */   
/*      */ 
/*      */   public void error(org.xml.sax.SAXParseException ex)
/*      */   {
/* 2244 */     String message = "XML Parsing error: " + ex.getMessage() + " at line " + ex.getLineNumber() + " col " + ex.getColumnNumber();
/*      */     
/* 2246 */     System.err.println(message);
/* 2247 */     msg(message);
/* 2248 */     showError("Error", message);
/*      */   }
/*      */   
/*      */ 
/*      */   public void fatalError(org.xml.sax.SAXParseException ex)
/*      */   {
/* 2254 */     String message = "XML Parsing fatal error: " + ex.getMessage() + " at line " + ex.getLineNumber() + " col " + ex.getColumnNumber();
/*      */     
/* 2256 */     System.err.println(message);
/* 2257 */     msg(message);
/* 2258 */     showError("Error", message);
/*      */   }
/*      */   
/*      */ 
/*      */   public void warning(org.xml.sax.SAXParseException ex)
/*      */   {
/* 2264 */     String message = "XML Parsing warning: " + ex.getMessage() + " at line " + ex.getLineNumber() + " col " + ex.getColumnNumber();
/*      */     
/* 2266 */     System.err.println(message);
/* 2267 */     msg(message);
/*      */   }
/*      */   
/*      */   public static Object deepCopy(Object orig)
/*      */   {
/* 2272 */     Object obj = null;
/*      */     
/*      */     try
/*      */     {
/* 2276 */       java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
/* 2277 */       java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(bos);
/* 2278 */       out.writeObject(orig);
/* 2279 */       out.flush();
/* 2280 */       out.close();
/*      */       
/*      */ 
/*      */ 
/* 2284 */       java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.ByteArrayInputStream(bos.toByteArray()));
/*      */       
/* 2286 */       obj = in.readObject();
/*      */     }
/*      */     catch (Exception ex)
/*      */     {
/* 2290 */       ex.printStackTrace();
/*      */     }
/* 2292 */     return obj;
/*      */   }
/*      */   
/*      */   private boolean initializeStartDocumentIndexes(String regexStr)
/*      */   {
/* 2297 */     if (regexStr.trim().length() == 0)
/*      */     {
/* 2299 */       showError("Regular Expression Error", "The regular expression is empty");
/* 2300 */       return false;
/*      */     }
/*      */     
/*      */     java.util.regex.Pattern p;
/*      */     try
/*      */     {
/* 2306 */       p = java.util.regex.Pattern.compile(regexStr);
/*      */     }
/*      */     catch (java.util.regex.PatternSyntaxException ex)
/*      */     {
/* 2310 */       showError("Regular Expression Error", "The regular expression is invalid:\n" + ex.getMessage());
/* 2311 */       return false;
/*      */     }
/*      */     
/* 2314 */     java.util.regex.Matcher m = p.matcher(this.pyramidTextPane.getText());
/* 2315 */     java.util.ArrayList indexes = new java.util.ArrayList();
/* 2316 */     while (m.find())
/*      */     {
/* 2318 */       indexes.add(new Integer(m.start()));
/*      */     }
/* 2320 */     if (indexes.isEmpty())
/*      */     {
/* 2322 */       showError("Regular Expression Error", "The regular expression did not match any text");
/* 2323 */       return false;
/*      */     }
/* 2325 */     if (indexes.size() == 1)
/*      */     {
/* 2327 */       showError("Regular Expression Error", "The regular expression only found one document");
/* 2328 */       return false;
/*      */     }
/*      */     
/* 2331 */     this.startDocumentIndexes = new int[indexes.size()];
/* 2332 */     for (int i = 0; i < indexes.size(); i++) {
/* 2333 */       this.startDocumentIndexes[i] = ((Integer)indexes.get(i)).intValue();
/*      */     }
/* 2335 */     this.startDocumentPatternStr = regexStr;
/* 2336 */     return true;
/*      */   }
/*      */   
/*      */   private class DucViewWindowAdapter
/*      */     extends java.awt.event.WindowAdapter
/*      */   {
/*      */     private DucView ducView;
/*      */     
/*      */     public DucViewWindowAdapter(DucView ducView)
/*      */     {
/* 2346 */       this.ducView = ducView;
/*      */     }
/*      */     
/*      */     public void windowClosing(java.awt.event.WindowEvent e)
/*      */     {
/* 2351 */       if (((DucView.this.isPeerModified) && (!DucView.this.saveModifiedPeer())) || ((!DucView.this.isPyramidModified) || (DucView.this.saveModifiedPyramid())))
/*      */       {
/*      */ 
/* 2354 */         this.ducView.dispose();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private class DucViewFileChooser extends javax.swing.JFileChooser
/*      */   {
/*      */     private boolean isSavingFile;
/*      */     private boolean isPyramid;
/*      */     
/*      */     public DucViewFileChooser(String currentDirectory, boolean isSavingFile, boolean isPyramid)
/*      */     {
/* 2366 */       super();
/* 2367 */       this.isSavingFile = isSavingFile;
/* 2368 */       this.isPyramid = isPyramid;
/*      */       
/* 2370 */       String defaultName = isPyramid ? DucView.this.pyramidInputTextFile : DucView.this.peerInputTextFile;
/* 2371 */       if ((defaultName == null) || (defaultName.trim().length() == 0))
/* 2372 */         defaultName = "untitled";
/* 2373 */       defaultName = defaultName.replaceFirst("\\.txt$", "");
/* 2374 */       defaultName = defaultName + (isPyramid ? ".pyr" : ".pan");
/*      */       
/* 2376 */       if (isSavingFile) {
/* 2377 */         setSelectedFile(new java.io.File(defaultName));
/*      */       }
/* 2379 */       this.setFileFilter(new DucViewFileFilter());
                 
/*      */     }
/*      */     
/*      */     public void approveSelection()
/*      */     {
/* 2384 */       if ((this.isSavingFile) && (getSelectedFile().exists()) && (javax.swing.JOptionPane.showConfirmDialog(this, "The file " + getSelectedFile().getName() + " already exists, would you like to overwrite it?", "Overwrite?", 0, 2) != 0))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2394 */         return;
/*      */       }
/* 2396 */       super.approveSelection();
/*      */     }
/*      */     
/* 2399 */     private class DucViewFileFilter extends javax.swing.filechooser.FileFilter { 
/*      */       
/*      */       public boolean accept(java.io.File file)
/*      */       {
/* 2403 */         if (file.isDirectory())
/*      */         {
/* 2405 */           return true;
/*      */         }
/* 2407 */         if (DucView.DucViewFileChooser.this.isPyramid)
/*      */         {
/* 2409 */           return file.getName().endsWith(".pyr");
/*      */         }
/*      */         
/*      */ 
/* 2413 */         return file.getName().endsWith(".pan");
/*      */       }
/*      */       
/*      */ 
/*      */       public String getDescription()
/*      */       {
/* 2419 */         if (DucView.DucViewFileChooser.this.isPyramid)
/*      */         {
/* 2421 */           return "Pyramid Files (*.pyr)";
/*      */         }
/*      */         
/*      */ 
/* 2425 */         return "Peer Annotation Files (*.pan)";
/*      */       }
/*      */       
/*      */       private DucViewFileFilter() {}
/*      */     } }
/*      */   
/* 2431 */   private class UndoController { 
/*      */     
/* 2433 */     private java.util.Vector states = new java.util.Vector();
/*      */     
/* 2435 */     private boolean isUndoEnabled = false; private boolean isRedoEnabled = false; private boolean isActive = false;
/*      */     
/* 2437 */     private int undoIndex = -1;
/*      */     
/*      */     private void expressGUI()
/*      */     {
/* 2441 */       if (this.isActive)
/*      */       {
/* 2443 */         DucView.this.editUndoMenuItem.setEnabled(this.isUndoEnabled);
/* 2444 */         DucView.this.editRedoMenuItem.setEnabled(this.isRedoEnabled);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void setActive(boolean isActive)
/*      */     {
/* 2470 */       this.isActive = isActive;
/* 2471 */       expressGUI();
/*      */     }
/*      */     
/*      */     public void clear()
/*      */     {
/* 2476 */       this.states.clear();
/* 2477 */       this.undoIndex = -1;
/* 2478 */       this.isUndoEnabled = (this.isRedoEnabled = false);
/* 2479 */       expressGUI();
/*      */     }
/*      */     
/*      */     public void add(Object state)
/*      */     {
/* 2484 */       this.undoIndex += 1;
/* 2485 */       if (this.undoIndex < this.states.size())
/* 2486 */         this.states.setSize(this.undoIndex);
/* 2487 */       this.states.add(state);
/* 2488 */       this.isUndoEnabled = (this.undoIndex > 0);
/* 2489 */       this.isRedoEnabled = false;
/* 2490 */       expressGUI();
/*      */     }
/*      */     
/*      */ 
/*      */     public Object undo()
/*      */     {
/* 2496 */       if (this.isUndoEnabled)
/*      */       {
/* 2498 */         this.undoIndex -= 1;
/* 2499 */         this.isUndoEnabled = (this.undoIndex > 0);
/* 2500 */         this.isRedoEnabled = true;
/* 2501 */         expressGUI();
/*      */         
/* 2503 */         return DucView.deepCopy(this.states.get(this.undoIndex));
/*      */       }
/*      */       
/*      */ 
/* 2507 */       return null;
/*      */     }
/*      */     
/*      */ 
/*      */     public Object redo()
/*      */     {
/* 2513 */       if (this.isRedoEnabled)
/*      */       {
/* 2515 */         this.undoIndex += 1;
/* 2516 */         this.isUndoEnabled = true;
/* 2517 */         this.isRedoEnabled = (this.states.size() > this.undoIndex + 1);
/* 2518 */         expressGUI();
/*      */         
/* 2520 */         return DucView.deepCopy(this.states.get(this.undoIndex));
/*      */       }
/*      */       
/*      */ 
/* 2524 */       return null;
/*      */     }
/*      */     
/*      */     private UndoController() {}
/*      */   }
/*      */ }


/* Location:              /home/grads/szb754/Downloads/DUCView-1.4.jar!/ducview/DucView.class
 * Java compiler version: 2 (46.0)
 * JD-Core Version:       0.7.1
 */