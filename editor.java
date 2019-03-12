import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;

import static javafx.application.Platform.exit;

public class editor extends JFrame implements ActionListener, ItemListener,MouseListener ,MouseMotionListener{



    int character_count=0;
    int wordcount=0;


    JTextPane area = new JTextPane();
    JFrame findFrame=new JFrame();                           //creating separate frame to find
    JFrame replaceFrame=new JFrame();
    JScrollBar scrollbar=new JScrollBar();
    Choice ch=new Choice();
    Choice ch2=new Choice();
    JButton lower=new JButton("a");                            //buttons to change case
    JButton upper=new JButton("A");
    JLabel ch_w_count=new JLabel();







    String fontName="";
    int fontSize;
    JPanel bottom=new JPanel();

    public editor() throws HeadlessException {

        lower.setBackground(new Color(191,255,128));
        upper.setBackground(new Color(191,255,128));                       //setting the color of background and buttons
        ch2.setBackground(new Color(191,255,128));
        ch.setBackground(new Color(191,255,128));
        area.setBackground(new Color(255,255,230));



        ch_w_count.setText("select the text to count number of characters and the words");

        addMouseListener(this);

        JScrollPane scroll=new JScrollPane(area);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        JFrame drawFrame=new JFrame();
        JButton clear=new JButton("Clear the screen");
        draw obj=new draw();
        drawFrame.add(obj,BorderLayout.CENTER);
        drawFrame.setSize(1000,800);


        area.addMouseListener(this);







        int wc=0;

        JLabel wordcount=new JLabel();
        this.add(bottom,BorderLayout.SOUTH);
        Font f=new Font("Calibri",Font.PLAIN,20);

        JPanel findpanel=new JPanel(new GridLayout(3,1));
        JLabel findlabel=new JLabel("enter the text you want to find");
        JTextField findfield=new JTextField();
        JButton findbt=new JButton("Find");
        findpanel.add(findlabel);
        findpanel.add(findfield);
        findpanel.add(findbt);
        findFrame.add(findpanel);
        findFrame.setVisible(false);
        findFrame.setSize(200,200);
        JToolBar tb=new JToolBar();
        JPanel tbpanel=new JPanel(new FlowLayout(0,0,0));
        JMenuItem drawShapes=new JMenuItem("Draw Shapes");

        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();                 //getting the family of fonts
        String[] fontnames =   e.getAvailableFontFamilyNames();
        for (int i = 0; i < fontnames.length; i++)
            ch.add(fontnames[i]);
        for(int i=8;i<=96;i=i+2)
        {
            String temp=""+i;
            ch2.add(temp);
        }
        JPanel mpanel=new JPanel(new FlowLayout(0,0,0));

             JLabel l1=new JLabel(" Size ");
             JLabel l2=new JLabel(" Style ");

             tbpanel.add(l1);

        tbpanel.add(ch2);
            tbpanel.add(l2);

        tbpanel.add(ch);
        JLabel c1=new JLabel(" Case ");
        tbpanel.add(c1);


        tbpanel.add(upper);

        tbpanel.add(lower);


        drawShapes.addActionListener(new ActionListener() {                             //making draw shape fraame visible
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                drawFrame.setVisible(true);


            }
        });
        bottom.add(ch_w_count);

        tbpanel.setPreferredSize(new Dimension(500,30));
        tb.add(tbpanel);
        JPanel replacePanel=new JPanel(new GridLayout(5,1));
        JLabel replace1=new JLabel("find text");                                           //creating frame for the replace functions
        JLabel replace2=new JLabel("replace with");
        JTextField field1=new JTextField();
        JTextField replacetext=new JTextField();
        JButton replace_bt=new JButton("replace");
        JButton replaceall_bt=new JButton("replace all");
        JPanel btpanel=new JPanel(new GridLayout(1,2));
        btpanel.add(replace_bt);
        btpanel.add(replaceall_bt);
        replacePanel.add(replace1);
        replacePanel.add(field1);
        replacePanel.add(replace2);
        replacePanel.add(replacetext);
        replacePanel.add(btpanel);
        replaceFrame.setVisible(false);
        replaceFrame.setSize(250,250);
        replaceFrame.add(replacePanel);
        replace_bt.addActionListener(new ActionListener() {                                      //adding action listner to replace
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                StringBuffer s=new StringBuffer(area.getText());
                int start=area.getText().indexOf(field1.getText(),0);
                int end=start+field1.getText().length();
                s.replace(start,end,replacetext.getText());
                area.setText(s.toString());
            }
        });
        replaceall_bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
//
String s = new String(area.getText());
                String temp=  s.replaceAll(field1.getText(),replacetext.getText());
                area.setText(temp);
            }
        });
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File options");
        menubar.setForeground(Color.GREEN);
        JMenu edit = new JMenu("Edit Options");
        JMenuItem close=new JMenuItem("exit") ;

        JMenu font_options=new JMenu("Font options");
        JMenuItem font_type=new JMenuItem("Font Type");
        JMenuItem font_size=new JMenuItem("Font Size");
        JMenuItem cahange_case=new JMenuItem("Change Case");
        font_options.add(font_type);
        font_options.add(font_size);
        font_options.add(cahange_case);
        JMenuItem open = new JMenuItem("open a file");
        JMenuItem save = new JMenuItem("save the file");
        JMenuItem newfile = new JMenuItem("Create a new file");

        file.add(newfile);
        newfile.addActionListener(this);
        file.add(open);
        file.add(save);
        file.add(close);
        JMenuItem cut = new JMenuItem("cut");
        JMenuItem copy = new JMenuItem("copy");
        JMenuItem paste = new JMenuItem("paste");
        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        JMenuItem find = new JMenuItem("Find");
        JMenuItem replace = new JMenuItem("Replace");
        edit.add(find);
        edit.add(replace);
        edit.add(drawShapes);

        menubar.add(file);
        menubar.add(edit);
        menubar.add(drawShapes);

        mpanel.add(tbpanel);
        menubar.add(mpanel);
        this.setJMenuBar(menubar);


        ch.addItemListener(new ItemListener() {                            //code to change style of the selected text
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                String fn=itemEvent.getItem().toString();
                StyledDocument doc = (StyledDocument) area.getDocument();
                int selectionEnd = area.getSelectionEnd();
                int selectionStart = area.getSelectionStart();
                if (selectionStart == selectionEnd) {
                    return;

                }Element element = doc.getCharacterElement(selectionStart);
                AttributeSet as = element.getAttributes();
                MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
                StyleConstants.setFontFamily(asNew, itemEvent.getItem().toString());
                doc.setCharacterAttributes(selectionStart, area.getSelectedText().length(), asNew, true);
                String text = (StyleConstants.isBold(as) ? "Cancel Bold" : "Bold");
            }
        });

        area.setFont(new Font("Brush Script Std",Font.PLAIN,12));
        ch2.addItemListener(new ItemListener() {                                            //code for changing the font size
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                String fn=itemEvent.getItem().toString();
                StyledDocument doc = (StyledDocument) area.getDocument();
                int selectionEnd = area.getSelectionEnd();
                int selectionStart = area.getSelectionStart();
                if (selectionStart == selectionEnd) {
                    return;
                }
                Element element = doc.getCharacterElement(selectionStart);
                AttributeSet as = element.getAttributes();
                MutableAttributeSet asNew = new SimpleAttributeSet(as.copyAttributes());
                StyleConstants.setFontSize(asNew, Integer.parseInt(fn));
                doc.setCharacterAttributes(selectionStart, area.getSelectedText().length(), asNew, true);
                String text = (StyleConstants.isBold(as) ? "Cancel Bold" : "Bold");
            }
        });
        this.add(scroll);
        open.addActionListener(this);
        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);                                           //adding action listner
        save.addActionListener(this);
        close.addActionListener(this);
        find.addActionListener(this);
        replace.addActionListener(this);
        upper.addActionListener(this);
        lower.addActionListener(this);


        findbt.addActionListener(new ActionListener() {                                      //code for finding the text
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(area.getText().length()==0)
                {
                    JOptionPane.showMessageDialog(null,"Not found!");


                }
                else {
                    String text = findfield.getText();
                    String tmp = area.getText();
                    int count=0;
                    int start = 0;
                    Highlighter.HighlightPainter ob = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
                    int again = 0;
                    Highlighter high = area.getHighlighter();
                    do {

                        start = tmp.indexOf(text, again);
                        int end = start + text.length();
                        if (start != -1) {
                            try {
                                count++;
                                high.addHighlight(start, end, ob);
                            } catch (BadLocationException e) {
                                e.printStackTrace();
                            }
                            again = end;


                        }
                        again = again + 1;

                    }
                    while (start!=-1);

                    if(count==0)
                    {
                        JOptionPane.showMessageDialog(null,"Not found!");
                    }
                } }
        });
    }





    public static void main(String[] args) {
        editor obj=new editor();
        obj.setVisible(true);
        obj.setSize(1366,768);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }






    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("open a file"))
        {
            JFileChooser f=new JFileChooser("D:");                                 //code for loading a file
            int var=  f.showOpenDialog(null);
            if(var==JFileChooser.APPROVE_OPTION) {
                File file = new File(f.getSelectedFile().getAbsolutePath());
                try {

                    String s1 = "", sl = "";
                    FileReader file_reader=new FileReader(file);
                    BufferedReader buffer_reader=new BufferedReader(file_reader);
                    sl=buffer_reader.readLine();
                    while((s1=buffer_reader.readLine())!=null)
                    {
                        sl=sl+"\n"+s1;
                    }
                    area.setText(sl);

                } catch (Exception e) {

                }
            }
        }
        if(ae.getActionCommand().equals("cut"))                                                       //cut
        {
            area.cut();
        }
        if(ae.getActionCommand().equals("copy"))                                              //copy
        {
            area.copy();
        }
        if(ae.getActionCommand().equals("paste"))                                              //paste
        {
            area.paste();
        }
        if(ae.getActionCommand().equals("Create a new file"))                              //creatig a new file
        {

            area.setText("");
        }
        if(ae.getActionCommand().equals("save the file"))                              //saving the file
        {
            JFileChooser f=new JFileChooser("D:");
            int var=f.showSaveDialog(null);
            if(var==JFileChooser.APPROVE_OPTION) {
                File file = new File(f.getSelectedFile().getAbsolutePath());
                try {
                    FileWriter wr = new FileWriter(file, false);

                    BufferedWriter w = new BufferedWriter(wr);


                    w.write(area.getText());

                    w.flush();
                    w.close();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            }
            else

                JOptionPane.showMessageDialog(this, "cancelled");
        }
        if(ae.getActionCommand().equals("print the document")) {
            try {
                area.print();
            } catch (PrinterException e) {
                JOptionPane.showMessageDialog(this, "cant print");
            }
        }
        if(ae.getActionCommand().equals("exit"))
        {
            this.dispose();
        }
        if(ae.getActionCommand().equals("Find"))
        {
            findFrame.setVisible(true);

        }
        if(ae.getActionCommand().equals("Replace"))
        {
            replaceFrame.setVisible(true);


        }
        if(ae.getActionCommand().equals("A"))                                //changing case of the selected text
        {
           String s=area.getSelectedText();
           String temp=s.toUpperCase();
           area.replaceSelection(temp);

        }
        if(ae.getActionCommand().equals("a"))
        {
            String s=area.getSelectedText();
            String temp=s.toLowerCase();
            area.replaceSelection(temp);

        }

    }
    @Override
    public void itemStateChanged(ItemEvent itemEvent) {
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        ch_w_count.setText("");


    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        String s=area.getSelectedText();
                 character_count=0;
        for(int i=0;i<s.length();i++)                                                   //counting the number of characters and words
        {
            character_count++;



        }
        String[] temp=s.split(" ");
        ch_w_count.setText("number characters in the selected text "+character_count+"             number of words int the selected text "+temp.length);





        bottom.add(ch_w_count);






    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }
}











class draw extends JPanel implements MouseListener,MouseMotionListener {

    JRadioButton b1, b2, b3,b4,b5;
    int x;
    int x1,x2,y1,y2;
    public draw() {
        JLabel label=new JLabel("click and move mouse to resize the shape of the geomentric object");


        this.setBackground(new Color(255,255,230));




        this.setPreferredSize(new Dimension(1000, 800));
        b1 = new JRadioButton("Recangle");
        b2 = new JRadioButton("Oval");                                      //creating button for drawing shapes
        b3 = new JRadioButton("Round Rectangle");
        b4=new JRadioButton("square");
        b5=new JRadioButton("circle");
        addMouseListener(this);
        addMouseMotionListener(this);
        JPanel btpanel=new JPanel(new GridLayout(6,1));
         btpanel.add(label);

        btpanel.add(b1);

        btpanel.add(b4);
        btpanel.add(b2);

        btpanel.add(b5);
        btpanel.add(b3);

        this.add(btpanel,BorderLayout.SOUTH);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b2.setSelected(false);
                b3.setSelected(false);
                b4.setSelected(false);
                b5.setSelected(false);

                x = 1;
                drawing();
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b1.setSelected(false);
                b3.setSelected(false);
                b4.setSelected(false);
                b5.setSelected(false);
                x = 2;
                drawing();
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                b1.setSelected(false);
                b2.setSelected(false);
                b4.setSelected(false);
                b5.setSelected(false);
                x = 3;
                drawing();
            }
        });
        b4.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                b1.setSelected(false);
                b2.setSelected(false);
                b3.setSelected(false);
                b5.setSelected(false);
                x=4;
                drawing();
            }
        }));
        b5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                b1.setSelected(false);
                b2.setSelected(false);
                b3.setSelected(false);
                b4.setSelected(false);
                x=5;
                drawing();

            }
        });
    }
    public void drawing() {
        repaint();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);                                                            //creating the shapes
        if (x == 1) {

                 g.setColor(new Color(184,0,230));

                     g.fillRect(x1, y1, Math.abs(x2-x1), Math.abs(y2-y1));
        }
        if (x == 2) {
            g.setColor(new Color(184,0,230));

            g.fillOval(x1, y1,x2-x1, y2-y1);
        }
        if (x == 3) {
            g.setColor(new Color(184,0,230));

            g.fillRoundRect(x1,y1,x2-x1,y2-y1,50,50);
        }
        if(x==4)
        {                 g.setColor(new Color(184,0,230));

            g.fillRect(x1,y1,x2-x1,x2-x1);
        }
        if(x==5) {
            g.setColor(new Color(184,0,230));

            g.fillOval(x1, y1, x2 - x1, x2 - x1);
        }
    }@Override
    public void mouseClicked(MouseEvent mouseEvent) {                              //getting initial co-ordinates
        x1=mouseEvent.getX();
        y1=mouseEvent.getY();
    }
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        x1=mouseEvent.getX();
        y1=mouseEvent.getY();

    }@Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }
    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        x2=mouseEvent.getX();
        y2=mouseEvent.getY();                                                  //getting co-ordinates

        repaint();
    }@Override
    public void mouseMoved(MouseEvent mouseEvent) {
    }
}
