
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

/**
 * PicChanger contains the panel for the image slider
 *
 * @author pp
 */
public class PicChanger extends JPanel {

    JLabel descriptionlabel;
    JLabel imagelabel;
    JPanel upperPanel;
    private int count = 0;
    JPanel southPanel;

    //constructor
    public PicChanger() {

        JPanel panel = new JPanel(new BorderLayout());
        southPanel = new JPanel(new BorderLayout());
        upperPanel = new JPanel(new BorderLayout());

        Icon next = new ImageIcon("src/images/next.jpeg");
        JLabel nextLabel = new JLabel(next);
        Icon previous = new ImageIcon("src/images/previous.jpeg");
        JLabel previousLabel = new JLabel(previous);
        Icon last = new ImageIcon("src/images/last.jpeg");
        JLabel lastLabel = new JLabel(last);
        Icon home = new ImageIcon("src/images/home.png");
        JLabel homeLabel = new JLabel(home);
        Icon playOff = new ImageIcon("src/images/offButton.png");
        Icon play = new ImageIcon("src/images/onButton.png");
        JLabel playLabel = new JLabel(playOff);
        Icon tumbPicture = new ImageIcon("src/images/thambnail.png");
        JLabel tumbLabel = new JLabel(tumbPicture);

        String[] title = {"\"Rose made of galaxies\"",
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

        imagelabel = new JLabel();
        descriptionlabel = new JLabel();

        ImageIcon[] image = {new ImageIcon("src/images/heig1107a.jpg"),
            new ImageIcon("src/images/opo0321b.jpg"),
            new ImageIcon("src/images/ann1121b.jpg"),
            new ImageIcon("src/images/heic1509a.jpg"),
            new ImageIcon("src/images/heic1501a.jpg"),
            new ImageIcon("src/images/heic1608a.jpg"),
            new ImageIcon("src/images/humble.jpg"),
            new ImageIcon("src/images/grabNubula.jpg"),
            new ImageIcon("src/images/merging.jpg"),
            new ImageIcon("src/images/butterfly.jpg"),
            new ImageIcon("src/images/pillar.jpg"),
            new ImageIcon("src/images/whirpool.jpg"),
            new ImageIcon("src/images/sagittarius.jpg"),
            new ImageIcon("src/images/light.jpg"),
            new ImageIcon("src/images/saturn.jpg")

        };

        for (int i = 0; i < image.length; i++) {
            ThumbnailPic pic = new ThumbnailPic();
            image[i] = new ImageIcon(pic.getScaledImage(image[i].getImage(), 500, 500));
        }

        imagelabel.setIcon(image[0]);
        descriptionlabel.setFont(new Font("Serif", Font.BOLD, 20));
        descriptionlabel.setForeground(Color.BLUE);
        descriptionlabel.setText(title[count]);

        nextLabel.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (count < image.length - 1) {
                    ++count;
                    imagelabel.setIcon(image[count]);
                    descriptionlabel.setText(title[count]);
                }
            }

        });

        previousLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (count > 0) {
                    count--;
                    imagelabel.setIcon(image[count]);
                    descriptionlabel.setText(title[count]);
                }
            }

        });

        playLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                if (changer % 2 == 0) {
                    playLabel.setIcon(play);
                    timer.start();
                } else {
                    playLabel.setIcon(playOff);
                    timer.stop();
                }
                changer++;
            }

        });

        timer = new Timer(2000, (ActionEvent e) -> {
            if (count < image.length - 1) {

                imagelabel.setIcon(image[count]);
                descriptionlabel.setText(title[count]);
            } else if (count == image.length) {
                count = 0;
                imagelabel.setIcon(image[count]);
                descriptionlabel.setText(title[count]);
            }
            ++count;
        });

        tumbLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {

                Driver.cardView.show(Driver.cardPanel, "thumpPanel");

            }

        });

        lastLabel.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                count = image.length - 1;

                imagelabel.setIcon(image[count]);
                descriptionlabel.setText(title[count]);

            }

        });

        homeLabel.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Driver.homeLabel.setVisible(false);
                Driver.cardView.show(Driver.cardPanel, "exitPanel");
                Driver.frame.setSize(480, 350);
            }

        });

        upperPanel.add(descriptionlabel);

        southPanel.setBackground(Color.WHITE);
        descriptionlabel.setBorder(new EmptyBorder(0, 150, 0, 0));
        JPanel southCenter = new JPanel();
        southCenter.setBackground(Color.WHITE);

        southCenter.add(playLabel);
        southCenter.add(homeLabel);
        southCenter.add(previousLabel);
        southCenter.add(nextLabel);
        southCenter.add(lastLabel);
        southCenter.add(tumbLabel);

        southPanel.add(southCenter, BorderLayout.CENTER);

        panel.add(upperPanel, BorderLayout.NORTH);
        panel.add(imagelabel, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);
        add(panel);

    }

    public static void main(String[] args) {

        //  new PicChanger();
    }
    private Timer timer;
    private int changer = 0;
}
