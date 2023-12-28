import javax.swing.*;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NameMenu extends JFrame implements MouseListener {
    private JTextField namefield;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JPanel topPanel;

    NameMenu() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(500, 500);

        topPanel = new JPanel(new GridLayout());
        topPanel.setPreferredSize(new Dimension(200, 200));

        namefield = new JTextField("Please enter your name here");
        namefield.setLayout(new GridLayout());
        namefield.setFont(new Font("Dubai", Font.BOLD, 26));
        namefield.setBorder(new StrokeBorder(new BasicStroke(2)));
        namefield.setHorizontalAlignment(0);
        namefield.setCaretPosition(namefield.getText().length());
        namefield.addMouseListener(this);
        namefield.setPreferredSize(new Dimension(200, 200));


        this.add(namefield, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == namefield) {
            namefield.setText("");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
