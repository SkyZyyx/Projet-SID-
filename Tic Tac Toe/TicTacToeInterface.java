import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;


public class TicTacToeInterface extends JFrame {
    private JLabel scoreLabel;
    private JButton[][] buttons;
    private String currentPlayer;
    private static String player1Name;
    private static String player2Name;
    private int playerXScore;
    private int playerOScore;
    TicTacToeBean ticTacToeBean = new TicTacToeBean();
    int scoreX = ticTacToeBean.getPlayerXScore();
        int scoreO = ticTacToeBean.getPlayerOScore();
    public TicTacToeInterface(String player1,String player2) {
        
        player1Name= player1;
        player2Name = player2;
        initializeFrame();
        initializeComponents();
    }

    private void initializeFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Tic Tac Toe");
       setBounds(180,0,1080,720);        
    }

    private void initializeComponents() {
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());

        JPanel northPanel = createNorthPanel();
        JPanel panel3 = createPanel3();
        JPanel buttonPanel = createButtonPanel();

        main.add(northPanel, BorderLayout.NORTH);
        main.add(panel3, BorderLayout.CENTER);
        main.add(buttonPanel, BorderLayout.SOUTH);
        add(main);
        setVisible(true);
    }

    private JPanel createNorthPanel() {
        JPanel northPanel = new JPanel();
        northPanel.setBackground(new Color(42, 43, 46));
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.X_AXIS));
        ImageIcon icon = new ImageIcon("./images/tictactoe.png");
        JLabel logoLabel = new JLabel(icon);
        logoLabel.setPreferredSize(new Dimension(70, 70));
        northPanel.add(Box.createHorizontalGlue());
        northPanel.add(logoLabel);

        JLabel titleLabel = new JLabel("<html><span style='color: white; font-size:22px;'>TY</span><span style='color: #FF725E;'>-TicTacToe</span></html>");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setPreferredSize(new Dimension(200, 30));
        northPanel.add(titleLabel);

        JPanel scorePanel = createScorePanel();
        northPanel.add(scorePanel);

        northPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        northPanel.setPreferredSize(new Dimension(800, 100));

        return northPanel;
    }

    private JPanel createScorePanel() {
        JPanel scorePanel = new JPanel();
        scorePanel.setBackground(new Color(42, 43, 46));
        scoreLabel = new JLabel("<html><span style='color: #FF725E; font-size:16px;'>  "+player1Name+" - </span><span style='color: white; font-size:16px;'>" + playerXScore + "   |</span><span style='color: #FF725E; font-size:16px;'>  "+player2Name+" - </span><span style='color: white; font-size:16px;'>" + playerOScore + "</span></html>");
        scorePanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        scorePanel.add(scoreLabel);

        return scorePanel;
    }

    private JPanel createPanel3() {
        JPanel panel3 = new JPanel(new FlowLayout());

        
        String gifPath = "./images/Gaming.gif";
        ImageIcon gifIcon = new ImageIcon(gifPath);

        createGamePanel(panel3, gifIcon);
        return panel3;
    }

    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);

        return button;
    }

    private void createGamePanel(JPanel panel3, ImageIcon gifIcon) {
        int labelWidth = 400;
        int labelHeight = 400;

        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setSize(labelWidth, labelHeight);

        JPanel gamePanel = new JPanel();
        gamePanel.setLayout(new GridLayout(3, 3));
        gamePanel.setPreferredSize(new Dimension(labelWidth, labelHeight));
        buttons = new JButton[3][3];
        currentPlayer = "X";
        initializeButtons(gamePanel);

        panel3.add(gifLabel);
        panel3.add(gamePanel);
    }
    private void updateScore() {
        
        
        ticTacToeBean.setPlayer1Name(player1Name);
        ticTacToeBean.setPlayer2Name(player2Name);
    
        // Mettre à jour les scores en fonction du joueur actuel
        if (ticTacToeBean.getCurrentPlayer().equals("X")) {
            scoreX++;
            ticTacToeBean.setPlayerXScore(scoreX);
        } else {
            scoreO++;
            ticTacToeBean.setPlayerOScore(scoreO);
        }
        

    
        // Mettre à jour l'étiquette des scores
        updateScoreLabel();
    }

    private JPanel createButtonPanel() {
        JButton resetButton = createStyledButton("End session", new Color(255, 114, 94));
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt){
              SwingUtilities.invokeLater(() -> {
                try {
                    new Main(player1Name,player2Name,scoreX,scoreO);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
            // Fermez la fenêtre actuelle
            dispose();
        }
    });
        JButton cancel = createStyledButton("Close", Color.WHITE);
        
        cancel.setForeground(Color.BLACK);
        cancel.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent evt){
                //Fermer la page en cours 
                dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(resetButton);
        buttonPanel.add(cancel);
        buttonPanel.setPreferredSize(new Dimension(800, 70));

        return buttonPanel;
    }

    // Utiliser ticTacToeBean pour accéder et modifier l'état du jeu
    
    private void updateScoreLabel() {
        // Utiliser les méthodes de TicTacToeBean pour obtenir les scores et les noms des joueurs
         player1Name = ticTacToeBean.getPlayer1Name();
         player2Name = ticTacToeBean.getPlayer2Name();
       
    
        scoreLabel.setText("<html><span style='color: #FF725E; font-size:16px;'>  " +
        player1Name + " -</span>- </span><span style='color: white; font-size:16px;'>" +
        ticTacToeBean.getPlayerXScore() + "   |</span><span style='color: #FF725E; font-size:16px;'>  " +
        player2Name + " - </span><span style='color: white; font-size:16px;'>" +
        ticTacToeBean.getPlayerOScore() + "</span></html>");

    }
    

    private void initializeButtons(JPanel panel) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                styleButton(buttons[i][j]);

                final int row = i;
                final int col = j;

                buttons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        buttonClicked(row, col);
                    }
                });

                panel.add(buttons[i][j]);
            }
        }
    }

    private void buttonClicked(int row, int col) {
        if (buttons[row][col].getText().equals("")) {
            if (ticTacToeBean.getCurrentPlayer().equals("X")) {
                buttons[row][col].setForeground(new Color(42, 43, 46));
            } else {
                buttons[row][col].setForeground(new Color(255, 114, 94));
            }
            buttons[row][col].setText(ticTacToeBean.getCurrentPlayer());
    
            if (ticTacToeBean.checkWin(buttons, row, col)) {
                JOptionPane.showMessageDialog(this, "Joueur " + ticTacToeBean.getCurrentPlayer() + " a gagné!");
                updateScore();
                resetGame();
            } else if (checkDraw()) {
                JOptionPane.showMessageDialog(this, "Match nul!");
                resetGame();
            } else {
                ticTacToeBean.switchPlayer();
            }
        }
    }
    
    

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }

  

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        currentPlayer = "X";
    }


    private void styleButton(JButton button){
        button.setFont(new Font("Arial", Font.PLAIN, 40));
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
    }
}
