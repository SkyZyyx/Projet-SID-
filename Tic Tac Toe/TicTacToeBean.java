import java.io.Serializable;

import javax.swing.JButton;

public class TicTacToeBean implements Serializable {
    private int playerXScore;
    private int playerOScore;
    private String currentPlayer;
    private String player1Name;
    private String player2Name;
    

    // Constructeur et autres méthodes
    public TicTacToeBean() {
        // Initialisez currentPlayer à une valeur par défaut, par exemple "X" ou "O"
        currentPlayer = "X";
    }

    // Getters et Setters
    public int getPlayerXScore() {
        return playerXScore;
    }

    public void setPlayerXScore(int score) {
        this.playerXScore = score;
    }

    public int getPlayerOScore() {
        return playerOScore;
    }

    public void setPlayerOScore(int Score) {
        this.playerOScore = Score;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    public String getPlayer1Name() {
        return player1Name;
    }
    public void setPlayer1Name(String player1Name) {
        this.player1Name = player1Name; 
    }
    public String getPlayer2Name(){
        return player2Name;
    }
    public void setPlayer2Name(String player2Name){
        this.player2Name= player2Name;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
    }
    public boolean checkWin(JButton[][] buttons, int row, int col) {
        // Vérifie la ligne
        String currentPlayer = getCurrentPlayer();
        if (buttons[row][(col + 1) % 3].getText().equals(currentPlayer) &&
            buttons[row][(col + 2) % 3].getText().equals(currentPlayer)) {
            return true;
        }
    
        // Vérifie la colonne
        if (buttons[(row + 1) % 3][col].getText().equals(currentPlayer) &&
            buttons[(row + 2) % 3][col].getText().equals(currentPlayer)) {
            return true;
        }
    
        // Vérifie la diagonale principale
        if (row == col && buttons[(row + 1) % 3][(col + 1) % 3].getText().equals(currentPlayer) &&
            buttons[(row + 2) % 3][(col + 2) % 3].getText().equals(currentPlayer)) {
            return true;
        }
    
        // Vérifie la diagonale inverse
        if (row + col == 2 && buttons[(row + 1) % 3][(col + 2) % 3].getText().equals(currentPlayer) &&
            buttons[(row + 2) % 3][(col + 1) % 3].getText().equals(currentPlayer)) {
            return true;
        }
    
        return false;
    }
    public void saveWinner(PlayerInterface winner) {
        Main.playersList.add(winner);
    }

    
    
}
