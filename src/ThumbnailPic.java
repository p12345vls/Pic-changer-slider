
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.AbstractAction;
import static javax.swing.Action.LARGE_ICON_KEY;
import static javax.swing.Action.SHORT_DESCRIPTION;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;

/**
 * Class for displaying thumbnail pictures
 *
 * @author pp
 */
public class ThumbnailPic extends JPanel {

    /**
     * Default constructor
     */
    public ThumbnailPic() {
        picDescription = new JLabel();
        home = new ImageIcon("src/images/home.png");
        homeLabel = new JLabel(home);
        tumbPicture = new ImageIcon("src/images/thambnail.png");
        tumbLabel = new JLabel(tumbPicture);
        picPanel = new JPanel();
        photographLabel = new JLabel();
        buttonBar = new JToolBar();
        blankImage = new BlankIcon();
        southPanel = new JPanel(new BorderLayout());
        JPanel southCenter = new JPanel();
        southCenter.setBackground(Color.WHITE);
        southCenter.add(homeLabel);
        southCenter.add(tumbLabel);
        southPanel.add(southCenter, BorderLayout.CENTER);

        buttonBar.setLayout(new GridLayout(5, 3, 30, 30));

        scroll = new JScrollPane(buttonBar,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        picPanel.setLayout(new BorderLayout());
        picDescription.setFont(new Font("Serif", Font.BOLD, 20));
        picDescription.setBorder(new EmptyBorder(10, 250, 0, 0));
        picDescription.setForeground(Color.BLUE);

        photographLabel.setBorder(new EmptyBorder(0, 100, 0, 0));
        picPanel.add(photographLabel, BorderLayout.CENTER);
        picPanel.add(picDescription, BorderLayout.NORTH);

        this.setLayout(new BorderLayout());

        add(scroll, BorderLayout.CENTER);
        add(picPanel, BorderLayout.NORTH);
        add(southPanel, BorderLayout.SOUTH);

        tumbLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                picPanel.setVisible(false);
                scroll.setVisible(true);
                homeLabel.setVisible(true);
            }

        });

        homeLabel.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                homeLabel.setVisible(true);
                Driver.cardView.show(Driver.cardPanel, "exitPanel");
               Driver.frame.setSize(480, 350);

            }

        });

        loadimages.execute();
    }

    /**
     * Loads the images and calls publish when a new one is ready to be
     * displayed.
     */
    private SwingWorker<Void, ThumbnailAction> loadimages = new SwingWorker<Void, ThumbnailAction>() {

        @Override
        protected Void doInBackground() throws Exception {
            for (int i = 0; i < imageTitles.length; i++) {
                ImageIcon icon;
                icon = createImageIcon(dir + fileNames[i], imageTitles[i]);

                ThumbnailAction action;
                if (icon != null) {

                    ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(icon.getImage(), 150, 150));

                    action = new ThumbnailAction(icon, thumbnailIcon, imageTitles[i]);

                } else {

                    action = new ThumbnailAction(blankImage, blankImage, imageTitles[i]);
                }
                publish(action);
            }

            return null;
        }

        @Override
        protected void process(List<ThumbnailAction> chunks) {
            for (ThumbnailAction action : chunks) {
                JButton thumbButton = new JButton(action);
                buttonBar.add(thumbButton, buttonBar.getComponentCount() - 1);
            }
        }
    };

    /**
     * Creates an ImageIcon if the path is valid.
     *
     * @param path resource path
     * @param description description of the file
     */
    protected ImageIcon createImageIcon(String path,
            String description) {
        ImageIcon icon;
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            icon = new ImageIcon(imgURL, description);

        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
        return new ImageIcon(getScaledImage(icon.getImage(), 500, 500));
    }

    /**
     * Resizes an image using a Graphics2D object backed by a BufferedImage.
     *
     * @param srcImg source image to scale
     * @param w width
     * @param h height
     * @return resized image
     */
    protected Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    /**
     * Action class that shows the image specified in it's constructor.
     */
    private class ThumbnailAction extends AbstractAction {

        /**
         * The icon if the full image we want to display.
         */
        private Icon displayPhoto;

        /**
         * @param Icon - The full size photo to show in the button.
         * @param Icon - The thumbnail to show in the button.
         * @param desc - The description of the icon.
         */
        public ThumbnailAction(Icon photo, Icon thumb, String desc) {
            displayPhoto = photo;
            putValue(SHORT_DESCRIPTION, desc);
            putValue(LARGE_ICON_KEY, thumb);
        }

        /**
         * Shows the full image
         */
        public void actionPerformed(ActionEvent e) {
            photographLabel.setIcon(displayPhoto);
            Driver.frame.setSize(700, 620);
            picDescription.setText(getValue(SHORT_DESCRIPTION).toString());
            picPanel.setVisible(true);
            scroll.setVisible(false);

        }

    }
    private JPanel southPanel;
    private JLabel picDescription;
    private Icon home;
    private JLabel homeLabel;
    private Icon tumbPicture;
    private JLabel tumbLabel;
    protected JPanel picPanel;
    private JLabel photographLabel;
    private JToolBar buttonBar;
    protected JScrollPane scroll;
    private String dir = "images/";
    private BlankIcon blankImage;

    private String[] imageTitles = {"\"Rose made of galaxies\"",
        "\"Identified in the center of NGC\"",
        "\"Black hole outflows\"",
        "\"Hubble’s 25th anniversary image\"", "\"New view of the Pillars"
        + " of Creation\"", "\"The Bubble Nebula\"",
        "\"The Hubble\"",
        "\"The Grab\"",
        "\"The Merging\"",
        "\"The Butterfly\"",
        "\"The Pillar\"",
        "\"Whirlpool\"",
        "\"Sagittarius\"",
        "\"Light\"",
        "\"Saturn\""};

    private String[] fileNames = {"heig1107a.jpg", "opo0321b.jpg",
        "ann1121b.jpg", "heic1509a.jpg", "heic1501a.jpg", "heic1608a.jpg",
        "humble.jpg",
        "grabNubula.jpg",
        "merging.jpg",
        "butterfly.jpg",
        "pillar.jpg",
        "whirpool.jpg",
        "sagittarius.jpg",
        "light.jpg",
        "saturn.jpg"};

}
