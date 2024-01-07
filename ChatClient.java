package ie.atu.sw;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 13;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to server");
            
            
            
            System.out.println("Write your username: ");
            String username = console.readLine();
            
            String userInput;
            while (true) {
            	
                System.out.print(username + ": ");
                userInput = console.readLine();
                out.println(username + ": " +userInput);
                String serverResponse = in.readLine();
                System.out.println("Server: " + serverResponse);

                if ("\\q".equals(userInput)) {
                    out.println(userInput);
                    break;
                }

                
            }

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + SERVER_ADDRESS);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + SERVER_ADDRESS);
        }
    }
}

