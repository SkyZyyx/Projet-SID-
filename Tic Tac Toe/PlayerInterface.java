
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
public interface PlayerInterface extends Remote{
   void setName(String name) throws RemoteException;
   void setScore(int score) throws RemoteException;
   String getName() throws RemoteException;
   int getScore() throws RemoteException;
   public List<PlayerInterface> readWinnersFromFile() throws RemoteException;
   public void saveWinnerToFile(String winnerName, int winnerScore) throws RemoteException;
}
