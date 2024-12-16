import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PlayerImplementation extends UnicastRemoteObject implements PlayerInterface{
    private String name;
    private int score;
    TicTacToeBean ticTacToeBean = new TicTacToeBean();

    public PlayerImplementation() throws RemoteException {
        super();
    }

    public void setName(String name) throws RemoteException {
        this.name = name;
    }

    public void setScore(int score) throws RemoteException {
        this.score = score;
    }

    public String getName() throws RemoteException {
        return name;
    }

    public int getScore() throws RemoteException {
        return score;
    }
   
   
    // Method to save winner information to a file
    public void saveWinnerToFile(String winnerName, int winnerScore) {
        try {
            String filePath = "C:\\Users\\akram\\UNIV\\Tic Tac Toe\\winners.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
            writer.write(winnerName + "," + winnerScore);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<PlayerInterface> readWinnersFromFile() {
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
    

}
