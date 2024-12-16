import java.io.*;
import java.net.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MaxNumberServer {
    public static void main(String[] args) {
        
        try {
            PlayerInterface remoteObject = new PlayerImplementation();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("RemoteObject", remoteObject);
            System.out.println("Remote Object bound");
        } catch (Exception e) {
           e.printStackTrace();
        }
        try {
            ServerSocket serverSocket = new ServerSocket(12346);
            System.out.println("Server waiting for client on port 12346...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Read the first number from the client
            int num1 = Integer.parseInt(reader.readLine());
            System.out.println("Received number 1: " + num1);

            // Read the second number from the client
            int num2 = Integer.parseInt(reader.readLine());
            System.out.println("Received number 2: " + num2);

            // Calculate the max number
            int maxNumber = Math.max(num1, num2);

            // Send the max number and a quote back to the client
            writer.println(maxNumber);

            // Close the connections
            reader.close();
            writer.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}