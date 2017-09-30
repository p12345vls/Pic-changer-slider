
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextArea;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicMenuBarUI;

/**
 * Defines a menu interface for choosing files, contains main()
 *
 * @author pp
 */
public class Driver {

    static JFrame frame;

    private JMenuBar menu;
    private JMenu fileMenu;
    private JMenu helpMenu;

    private JMenuItem exit;
    private JMenuItem userManual;
    private JMenuItem openFile;
    private JMenuItem about;

    private JPanel openPanel;
    protected JPanel exitPanel;
    private JPanel aboutPanel;
    private JPanel userManualPanel;
    private JPanel filesPanel;
    private JPanel homePanel;

    static CardLayout cardView;
    static JPanel cardPanel;
    private JLabel label;

    private JFileChooser chooser;
    private JTextArea tarea;
    private JScrollPane scroll;
    private JButton okButton;
    static Icon home;
    static JLabel homeLabel;

    //constructor
    public Driver() {

        frame = new JFrame("Picture Slider");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents(frame);
        frame.setSize(480, 350);
        frame.setResizable(false);
      //  frame.setLocationRelativeTo(null);

        homePanel = new JPanel();
        homePanel.setLayout(new BorderLayout());
        homePanel.add(homeLabel, BorderLayout.SOUTH);
        frame.add(homePanel, BorderLayout.SOUTH);
        homeLabel.setVisible(false);
        frame.setVisible(true);
    }

    //components of the class
    private void initComponents(JFrame frame) {

        home = new ImageIcon("src/images/home2.png");
        homeLabel = new JLabel(home);
        tarea = new JTextArea(10, 38);
        scroll = new JScrollPane(tarea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tarea.setEditable(false);

        okButton = new JButton("OK");
        label = new JLabel();
        tarea.setEditable(false);

        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));

        menu = new JMenuBar();
        menu.setForeground(Color.BLUE);
        menu.setUI(new BasicMenuBarUI() {
            public void paint(Graphics g, JComponent c) {
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, c.getWidth(), c.getHeight());
            }
        });
        frame.setJMenuBar(menu);

        fileMenu = new JMenu("File");

        openFile = new JMenuItem("Open");

        exit = new JMenuItem("Exit");
        exit.setForeground(Color.RED);
        fileMenu.add(openFile);
        fileMenu.add(exit);

        helpMenu = new JMenu("Help");
        about = new JMenuItem("About");
        userManual = new JMenuItem("Help/User Manual");
        helpMenu.add(about);
        helpMenu.add(userManual);

        menu.add(fileMenu);
        menu.add(helpMenu);

        cardView = new CardLayout();
        cardPanel = new JPanel(cardView);

        openPanel = new JPanel();
        openPanel.setLayout(new BorderLayout());
        openPanel.add(scroll,BorderLayout.CENTER);
        openPanel.add(okButton, BorderLayout.SOUTH);

        exitPanel = new JPanel();
        JLabel firstLabel = new JLabel("Use The Menu To Navigate");
        firstLabel.setForeground(Color.BLUE);
        firstLabel.setFont(new Font("Serif", Font.ITALIC, 20));
        firstLabel.setBorder(new EmptyBorder(56, 0, 0, 0));
        exitPanel.add(firstLabel, BorderLayout.CENTER);

        filesPanel = new JPanel();
        filesPanel.add(label);

        aboutPanel = new JPanel();
        aboutTextArea.setBackground(Color.GRAY);
        aboutTextArea.setForeground(Color.white);
        aboutTextArea.setEditable(false);
        JLabel aboutTextLabel = new JLabel("About this Software");
        aboutTextLabel.setForeground(Color.BLUE);
        aboutTextLabel.setFont(new Font("Serif", Font.BOLD, 20));
        aboutTextLabel.setBorder(new EmptyBorder(0, 120, 0, 0));

        aboutPanel.setLayout(new BorderLayout());
        aboutPanel.add(aboutTextLabel, BorderLayout.NORTH);
        aboutPanel.add(aboutTextArea, BorderLayout.CENTER);

        userManualPanel = new JPanel();
        userManualPanel.setLayout(new BorderLayout());

        JLabel helpTextLabel = new JLabel("Help/User Manual");
        helpTextLabel.setForeground(Color.BLUE);
        helpTextLabel.setFont(new Font("Serif", Font.BOLD, 20));
        helpTextLabel.setBorder(new EmptyBorder(0, 125, 0, 0));
        textHelpManual.setBackground(Color.GRAY);
        textHelpManual.setForeground(Color.white);
        textHelpManual.setEditable(false);
        userManualPanel.add(helpTextLabel, BorderLayout.NORTH);
        userManualPanel.add(textHelpManual, BorderLayout.CENTER);

        cardPanel.add("exitPanel", exitPanel);
        cardPanel.add("aboutPanel", aboutPanel);
        cardPanel.add("userManualPanel", userManualPanel);
        cardPanel.add("imagePanel", new PicChanger());
        cardPanel.add("filesPanel", filesPanel);
        cardPanel.add("thumpPanel", new ThumbnailPic());

        openFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        about.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutActionPerformed(evt);
            }
        });
        userManual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userHelpActionPerformed(evt);
            }
        });

        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                okButtonActionPerformed(evt);
            }
        });

        homeLabel.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeLabel.setVisible(false);
                cardView.show(cardPanel, "exitPanel");

            }

        });

        frame.getContentPane().add(cardPanel, BorderLayout.CENTER);
    }

    //action performed methods
    //========================================================
    private void openActionPerformed(java.awt.event.ActionEvent evt) {

        // show file chooser dialog
        int r = chooser.showOpenDialog(null);

        // if file selected, set it as icon of the label
        if (r == JFileChooser.APPROVE_OPTION) {
            if (chooser.getSelectedFile().getName().equals("ALBUM.txt")) {

                cardPanel.add("openPanel", openPanel);
                cardView.show(cardPanel, "openPanel");
                frame.setSize(480, 350);

                File file = chooser.getSelectedFile();
                try {
                    BufferedReader input = new BufferedReader(new InputStreamReader(
                            new FileInputStream(file)));
                    tarea.read(input, "");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                frame.setSize(480, 350);
                homeLabel.setVisible(true);
                cardView.show(cardPanel, "filesPanel");

                String name
                        = chooser.getSelectedFile().getPath();
                label.setIcon(new ImageIcon(name));
            }
        }
    }

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {

        System.exit(0);
    }

    private void aboutActionPerformed(java.awt.event.ActionEvent evt) {
        frame.setSize(480, 350);
        homeLabel.setVisible(true);

        cardView.show(cardPanel, "aboutPanel");
    }

    private void userHelpActionPerformed(java.awt.event.ActionEvent evt) {
        frame.setSize(480, 350);
        homeLabel.setVisible(true);

        cardView.show(cardPanel, "userManualPanel");
    }

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {
        homeLabel.setVisible(false);
        frame.setSize(700, 620);
        cardView.show(cardPanel, "imagePanel");
        new ThumbnailPic().setVisible(false);
         
    }

    //main method
    //==================================================
    public static void main(String... args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

//                try {
//                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//                } catch (ClassNotFoundException
//                        | InstantiationException
//                        | IllegalAccessException
//                        | UnsupportedLookAndFeelException e) {
//                }
//
//                UIManager.put("swing.boldMetal", Boolean.TRUE);
                Driver a = new Driver();
            }
        });
    }
    private TextArea aboutTextArea = new TextArea("This Software provides the user a GUI interface"
            + "to navigate through files.\n"
            + "Specificaly,File choosers provide a GUI for navigating the file system,\n"
            + "and then either choosing a file or directory from a list, or entering\n"
            + "the name of a file or directory. To display a file chooser,\n"
            + "choose File > Open."
            + "For Displaying the Pictures Slider choose \nFile > Open > src > ALBUM.txt\n"
            + "to read the contents of the slider.\n"
            + "For more info please choose Help > Help/User manual "
            + "\n", 10, 40);

    private TextArea textHelpManual = new TextArea("1.System Requirements\n\n Windows , 10 64 bits.\n"
            + "CPU quad-core or hexa-core Intel i7/Xeon.\n"
            + "GeForce GPU compatible with OpenGL 3.2 and 2 GB RAM.\n"
            + "Hard disk: SSD.\n"
            + "\nMac, An Intel Core 2 Duo, Core i3, Core i5, Core i7, or Xeon processor\n"
            + "Mac OS X v10.6.6 or later to install via the Mac App Store (v10.6.8 recommended)\n"
            + "7 GB of available disk space\n"
            + "2 GB of RAM\n\nSmall projects (under 100 images at 14 MP): 8 GB RAM, 15 GB SSD Free Space.\n"
            + "Medium projects (between 100 and 500 images at 14 MP): 16GB RAM, 30 GB SSD Free Space.\n"
            + "Large projects (over 500 images at 14 MP): 32 GB RAM, 60 GB SSD Free Space.\n"
            + "Very Large projects (over 2000 images at 14 MP): 64 GB RAM, 120 GB SSD Free Space.\n"
            + "\n\n2. Slide Format File\n"
            + "PC & Mac Compatibility: If you are using Mac or PC, or constantly shifting from\n"
            + " one to another, JPEG is the best image format for PC and Mac Compatibility. "
            + "\nThis software supports auto image resizing to fit specific size.\n\n"
            + "3. Starting The System\n"
            + "Please use the File Menu to Choose a file to display.\n"
            + "For pictures choose src/ALBUM.txt. ",
            10, 40);

}
