import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class pageLogin extends JFrame implements ActionListener {
    public String player1,player2;
    private JPanel panel3, panel1, panel2,mainPanel;
    private JTextField tPlayer1,tPlayer2;
    private JLabel lUserName, imageLabel,title,lPlayer;
    private JButton login, cancel;
    private int borderRadius = 25;
    private MatteBorder customBorder = new MatteBorder(
            2, 2, 2, 2, // Border thickness
            new Color(255, 114, 94)); // Color: #3498db

    private CompoundBorder compoundBorder = new CompoundBorder(
            customBorder,
            new EmptyBorder(borderRadius, borderRadius, borderRadius, borderRadius)
    );

    public pageLogin() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Login");
        setBounds(300, 90, 900, 600);
        mainPanel= new JPanel();
        panel3 = new JPanel(new FlowLayout());  
        setContentPane(mainPanel);

        // Panel 1
        panel1 = new JPanel(null);  // Use null layout for precise component placement
        panel1.setBackground(Color.WHITE);

        title = new JLabel("<html><span style='color: #FF725E; font-size:22px;'>Log</span>-in</html>");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 40);
        title.setLocation(10, 10);
        title.setForeground(Color.BLACK);
        panel1.add(title);

        lUserName = new JLabel("Player1:");
        lUserName.setFont(new Font("Arial", Font.PLAIN, 20));
        lUserName.setSize(100, 30);
        lUserName.setForeground(Color.BLACK);
        lUserName.setLocation(20, 80);
        panel1.add(lUserName);

        lPlayer = new JLabel("Player2:");
        lPlayer.setFont(new Font("Arial", Font.PLAIN, 20));
        lPlayer.setSize(100, 30);
        lPlayer.setForeground(Color.BLACK);
        lPlayer.setLocation(20,120);
        panel1.add(lPlayer);


        tPlayer2 = new JTextField();
        tPlayer2.setFont(new Font("Arial", Font.PLAIN, 20));
        tPlayer2.setSize(220, 30);
        tPlayer2.setLocation(120, 120);
        panel1.add(tPlayer2);

        tPlayer1 = new JTextField();
        tPlayer1.setFont(new Font("Arial", Font.PLAIN, 20));
        tPlayer1.setSize(220, 30);
        tPlayer1.setLocation(120, 80);
        panel1.add(tPlayer1);

       
        login = new JButton("Login");  // Fix button text
        login.setFont(new Font("Arial", Font.PLAIN, 15));
        login.setSize(150, 30);
        login.setLocation(20, 180);
        login.setBackground(new Color(255, 114, 94));
        login.setForeground(Color.WHITE);
        login.setFocusPainted(false);
        panel1.add(login);
        //Action performed
        login.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent evt){
         player1 = tPlayer1.getText();
         player2 = tPlayer2.getText();
         if (player1.isEmpty() || player2.isEmpty()) {
            JOptionPane.showMessageDialog(panel1, "Veillez entrer le nom des joueurs!");
         }else{

             SwingUtilities.invokeLater(() -> {
               new TicTacToeInterface(player1,player2);
               // Fermez la fenÃªtre actuelle
               dispose();
            });
         }
    }
});
      
        cancel = new JButton("Cancel");  // Fix button text
        cancel.setFont(new Font("Arial", Font.PLAIN, 15));
        cancel.setSize(150, 30);
        cancel.setLocation(200, 180);  // Adjust button location
        cancel.setBackground(Color.WHITE);
        cancel.setForeground(Color.BLACK);
        cancel.setFocusPainted(false);
        panel1.add(cancel);
         cancel.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent evt){
                //Fermer la page en cours 
                dispose();
            }
        });

        panel1.setBorder(compoundBorder);
        panel1.setPreferredSize(new Dimension(500, 270));

        panel3.add(panel1);

        // Panel 2
        panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);

        ImageIcon imageIcon = new ImageIcon("./images/login.png");
        Image image = imageIcon.getImage();
        int labelWidth = 200;
        int labelHeight = 200;
        Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setSize(labelWidth, labelHeight);
        panel2.setBorder(compoundBorder);

        panel2.add(imageLabel);
        panel3.add(panel2);
        panel3.setLocation(400,200);
        panel3.setBackground(new Color(255, 114, 94));
        int topMargin = 80;
        mainPanel.setBorder(BorderFactory.createEmptyBorder(topMargin, topMargin, topMargin, topMargin));
        mainPanel.add(panel3);
        mainPanel.setBackground(Color.WHITE);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}

public class Login{
    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new pageLogin();
        });
    }
}