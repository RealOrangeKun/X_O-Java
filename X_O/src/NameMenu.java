import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.StrokeBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;

public class NameMenu extends JFrame implements MouseListener, ActionListener {
    private JTextField namefield;
    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    private JButton submit;

    private JPanel topPanel, bottomPanel;
    private boolean button_pressed= false;

    private String Username;

    NameMenu() {
        Image icon = new ImageIcon("icon.png").getImage().getScaledInstance(300, 300, 900);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(500, 500);
        this.setIconImage(new ImageIcon(icon).getImage());
        this.addMouseListener(this);

        topPanel = new JPanel(new GridLayout());
        JLabel top_panel_label = new JLabel("Please enter your name:");
        topPanel.add(top_panel_label);
        top_panel_label.setVerticalAlignment(0);
        top_panel_label.setHorizontalAlignment(0);
        top_panel_label.setFont(new Font("Dubai", Font.BOLD, 30));
        topPanel.setPreferredSize(new Dimension(100, 300));

        submit = new JButton("Submit");
        submit.setPreferredSize(new Dimension(100,200));
        submit.setFocusable(false);
        submit.setBackground(Color.LIGHT_GRAY);
        submit.setBorder(new StrokeBorder(new BasicStroke(1)));
        submit.setForeground(Color.white);
        submit.addMouseListener(this);
        submit.addActionListener(this);

        namefield = new JTextField("Please enter your name here");
        namefield.setLayout(new GridLayout());
        namefield.setFont(new Font("Dubai", Font.BOLD, 26));
        namefield.setForeground(Color.LIGHT_GRAY);
        namefield.setBorder(new StrokeBorder(new BasicStroke(2)));
        namefield.setHorizontalAlignment(0);
        namefield.setCaretPosition(namefield.getText().length());
        namefield.addMouseListener(this);
        namefield.setPreferredSize(new Dimension(this.getWidth()-submit.getWidth(), 200));
        namefield.setEnabled(false);


        bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(namefield, BorderLayout.CENTER);
        bottomPanel.add(submit, BorderLayout.EAST);

        this.add(bottomPanel, BorderLayout.SOUTH);
        this.add(topPanel, BorderLayout.NORTH);
        this.setLocation((int) (screenSize.getWidth() - this.getWidth()) / 2,
                (int) (screenSize.getHeight() - this.getHeight()) / 2);
        this.setVisible(true);
    }

    public boolean Userinput(){
        return button_pressed;
    }
    public String getUsername(){
        if(Username == null) return "";
        return Username;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource()==this){
            namefield.setEnabled(false);
            namefield.setForeground(Color.lightGray);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == namefield) {
            namefield.setEnabled(true);
            if(Objects.equals(namefield.getText(), "Please enter your name here")) namefield.setText("");
            namefield.setForeground(Color.BLACK);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if(e.getSource()==submit){
            submit.setBorder(new LineBorder(Color.BLACK));
            submit.setForeground(Color.BLACK);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(e.getSource()==submit){
            submit.setBorder(new StrokeBorder(new BasicStroke(1)));
            submit.setForeground(Color.white);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submit){
            Username = namefield.getText();
            namefield.setEnabled(false);
            button_pressed = true;
            this.setEnabled(false);
        }
    }
}
