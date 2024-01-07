package ie.atu.sw;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;


public class ChatClient {
    // Define the port number to connect to (should match the server's port)
    public static final int DAYTIME_PORT = 13;

    // Main method - entry point of the program
    public static void main(String[] args) {
        // Determine the hostname to connect to (default to "localhost" if no argument is provided)
        String hostname = args.length > 0 ? args[0] : "localhost";

        // Try to establish a socket connection and handle communication
        try (Socket socket = new Socket(hostname, DAYTIME_PORT);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             Writer writer = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
             InputStream input = socket.getInputStream()) {

            // Notify that connection is successful
            System.out.println("Connected to Chat Server on host " + hostname);

            // Start a new thread to handle reading messages from the server
            Thread readerThread = new Thread(() -> {
                try {
                    // Create a BufferedReader to read data coming from the server
                    BufferedReader serverReader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                    String serverMessage;
                    // Continuously read messages from the server and print them
                    while ((serverMessage = serverReader.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    // Print an error message if there's an issue with the server connection
                    System.out.println("Connection to server lost.");
                }
            });
            // Start the thread
            readerThread.start();

            // Main thread continues here, handling user input
            System.out.println("Enter your messages (type '\\q' to quit):");
            String userInput;
            // Infinite loop to read user input and send it to the server
            while (true) {
                // Read user input from the console
                userInput = consoleReader.readLine();

                // Check if the user wants to quit
                if ("\\q".equals(userInput)) {
                    break;
                }

                // Send the user input to the server
                writer.write(userInput + "\n");
                // Flush the writer to ensure the data is sent immediately
                writer.flush();
            }

            // Close the socket, which also interrupts the reader thread
            socket.close();
        } catch (UnknownHostException e) {
            // Handle exceptions if the host is unknown
            System.out.println("Can't connect to host: " + hostname);
            e.printStackTrace();
        } catch (IOException e) {
            // Handle general I/O exceptions
            System.out.println("An I/O error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

