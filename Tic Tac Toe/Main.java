import java.io.*;
import java.net.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main extends JFrame{
    public String player1,player2;
    private JPanel panel3, panel1, panel2,mainPanel;
    private JLabel imageLabel;
    private static String name1,name2,Wname;
    private static int score1,score2,Wscore;
    private static List<PlayerInterface> winnersList;
    public static List<PlayerInterface> playersList = new ArrayList<>();
    TicTacToeBean ticTacToeBean = new TicTacToeBean();
    PlayerImplementation playerImplementation = new PlayerImplementation();
    Main(String player1N, String player2N, int player1S, int player2S) throws RemoteException{
        name1 = player1N;
         name2 = player2N;
            score1 = player1S;
                score2 = player2S;
                
        
        try {
            Socket socket = new Socket("127.0.0.1", 12346);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Send the first number to the server
            writer.println(score1);

            // Send the second number to the server
            writer.println(score2);

            // Receive and print the server's response
            String serverResponse = reader.readLine();
            checkWhich(serverResponse);

            // Close the connections
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
          Registry registry = LocateRegistry.getRegistry("localhost");
          PlayerInterface Winner = (PlayerInterface) registry.lookup("RemoteObject");
         // List<PlayerInterface> playersList = new ArrayList<>();
            winnersList = new ArrayList<>();

          

          Winner.setName(Wname);
          Winner.setScore(Wscore);
          
          playersList.add(Winner);
          
          playersList = readWinnersFromFile();

          //saveWinnerToFile(Wname,Wscore);

         /*  for(PlayerInterface PlayerInterface :playersList ){
            System.out.println("Winner: " + Winner.getName());
            System.out.println("Score: " + Winner.getScore());
            System.out.println("Rank Difference: " + Winner.getRankDifference());
            System.out.println("---------------");
          }*/
          

          
        } catch (Exception e) {
            System.err.println("Client exception: "+e.getMessage());
            e.printStackTrace();
        }




        
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Login");
        setBounds(300, 90, 900, 600);
        mainPanel= new JPanel();
        panel3 = new JPanel(new FlowLayout());  
        setContentPane(mainPanel);
       // mainPanel.setLayout(new BorderLayout());

        
        // Panel 1
        panel1 = new JPanel(null);  // Use null layout for precise component placement
        panel1.setBackground(Color.WHITE);

        JLabel lUserName = new JLabel("Le vainqueur est :"+Wname);
        lUserName.setFont(new Font("Monospaced", Font.PLAIN, 20)); // Utilisez Monospaced pour un aspect pixelisé
        lUserName.setSize(300, 80);
        lUserName.setForeground(Color.BLACK);
        lUserName.setLocation(50, 40);
        panel1.add(lUserName);
       
       JLabel scoreL = new JLabel("avec un score de "+Wscore);
        scoreL.setFont(new Font("Monospaced", Font.PLAIN, 25)); // Utilisez Monospaced pour un aspect pixelisé
        scoreL.setSize(300, 80);
        scoreL.setLocation(50,80);
        scoreL.setForeground(Color.BLACK);
        panel1.add(scoreL);

        // Save the Winner name and score to winners.txt file 
        playerImplementation.saveWinnerToFile(Wname, Wscore);
        JButton scorebutton = new JButton("Reveal Scoreboard");
        
       

        // Add the button to the SOUTH position of the main panel
        
        
        
        // Add the main panel to the frame
        scorebutton.setFont(new Font("Arial", Font.PLAIN, 25));
        scorebutton.setBackground(Color.WHITE);
        scorebutton.setForeground(Color.BLACK);
        scorebutton.setFocusPainted(false);
        scorebutton.setBounds(50, 170, 250, 80); // Set bounds (x, y, width, height)
        panel1.add(scorebutton);
        add(panel1);
        
        scorebutton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent evt){
                try {
                    
                    displayScoreBoard(winnersList);
                    dispose();
                    
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
                }
            }

            
        });

        panel1.setPreferredSize(new Dimension(500, 270));

        panel3.add(panel1);

        // Panel 2
        panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);

        

        ImageIcon imageIcon = new ImageIcon("./images/winner.png");
        Image image = imageIcon.getImage();
        int labelWidth = 400;
        int labelHeight = 400;
        Image scaledImage = image.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon = new ImageIcon(scaledImage);
        imageLabel = new JLabel(scaledImageIcon);
        imageLabel.setSize(labelWidth, labelHeight);


        panel2.add(imageLabel);
        panel3.add(panel2);
        panel3.setLocation(400,200);
        panel3.setBackground(Color.WHITE);
        int topMargin = 80;
        mainPanel.setBorder(BorderFactory.createEmptyBorder(topMargin, topMargin, topMargin, topMargin));
        mainPanel.add(panel3);
        mainPanel.setBackground(Color.WHITE);

        setVisible(true);
    }
    
    public static void checkWhich(String x) {
        int parsedValue = Integer.parseInt(x);
    
        if (parsedValue == score1) {
            Wname = name1;
            Wscore = score1;
        } else {
            Wname = name2;
            Wscore = score2;
        }
    }
   
    

    
   
    private static List<PlayerInterface> readWinnersFromFile() {
        List<PlayerInterface> playersList = new ArrayList<>();
    
        try (BufferedReader reader = new BufferedReader(new FileReader("winners.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
    
                    // Create PlayerInterface objects or use your existing implementation
                    PlayerInterface player = new PlayerImplementation();
                    player.setName(name);
                    player.setScore(score);
    
                    playersList.add(player);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Sort playersList based on scores in descending order
        playersList.sort(Comparator.comparingInt(value -> {
            try {
                return ((PlayerInterface) value).getScore();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return 0;
        }).reversed());
    
        return playersList;
    }
    
    
    private static void displayScoreBoard(List<PlayerInterface> winnersList) throws RemoteException {
        List<PlayerInterface> playersList = readWinnersFromFile();
    
        // Create a new JFrame to display the scoreboard
        JFrame frameScore = new JFrame("Scoreboard");
    
        // Create a two-dimensional array to hold data for the table
        Object[][] data = new Object[playersList.size()][2];
    
        // Populate the array with data from playersList
        for (int i = 0; i < playersList.size(); i++) {
            PlayerInterface player = playersList.get(i);
            data[i][0] = player.getName();
            data[i][1] = player.getScore();
        }
    
        // Define column names
        String[] columnNames = {"Name", "Score"};
    
        // Create a table model with the data and column names
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
    
        // Create a JTable with the model
        JTable table = new JTable(model);
    
        // Create a scroll pane to hold the table if there are many entries
        JScrollPane scrollPane = new JScrollPane(table);
    
        // Create a panel for the image
        JPanel imagePanel = new JPanel();
        
        // Load and display the image on the left side
        ImageIcon imageIcon = new ImageIcon("./images/Goal.gif");
        JLabel imageLabel = new JLabel(imageIcon);
        imagePanel.add(imageLabel);
        // Create a split pane with the table on the right and the image on the left
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, imagePanel, scrollPane);
        splitPane.setResizeWeight(0.1); // Adjust the ratio of the split
    
        // Add the split pane to the frameScore's content pane
        frameScore.add(splitPane);
    
        // Set frameScore properties
        frameScore.setSize(800, 600); // Set an appropriate size
        frameScore.setLocationRelativeTo(null); // Center the frameScore on the screen
        frameScore.setVisible(true);
    }
}
