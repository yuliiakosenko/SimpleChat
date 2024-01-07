package ie.atu.sw;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;





public class ChatServer {

    private static final int PORT = 13;
 

    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            while (true) {
                Socket clientSocket = server.accept();
                System.out.println("Client connected");

                ChatHandler handler = new ChatHandler(clientSocket);
                pool.submit(handler);
            }
        }
    }
}

class ChatHandler implements Runnable {
    private final Socket clientSocket;
	private Object out;
    private static List<ChatHandler> ChatHandler = new CopyOnWriteArrayList<>();
    

    ChatHandler(Socket socket) {
        this.clientSocket = socket;
    }
    private void broadcastMessage(String message) {
        for (ChatHandler clientHandler : ChatHandler) {
            if (clientHandler != this) { // Don't send the message back to the sender
                ((PrintStream) clientHandler.out).println(message); // Send the message to each client
            }
        }
    }
    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if ("\\q".equals(inputLine)) {
                    break;
                }
                System.out.println(inputLine);

                System.out.print("Server: ");
                String response = console.readLine();
                out.println(response);
            }
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error closing socket: " + e.getMessage());
            }
        }
  }
    
  
} 
