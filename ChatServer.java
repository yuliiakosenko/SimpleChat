package ie.atu.sw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ChatServer {

	// Port number on which the server will listen
	public static final int PORT = 13;

	// Main method - entry point of the program
	public static void main(String[] args) {
		// Creating a thread pool to handle multiple client connections
		ExecutorService pool = Executors.newFixedThreadPool(50);

		// Try-with-resources statement to ensure proper closure of resources
		try (ServerSocket server = new ServerSocket(PORT)) {
			// Inform that the server is listening on the specified port
			System.out.println("Listening for connection on port " + PORT);

			// Infinite loop to keep the server running
			while (true) {
				try {
					// Accept an incoming client connection
					Socket connection = server.accept();

					// Log the connection details
					System.out.println(
							"Client connected from " + connection.getInetAddress() + ":" + connection.getPort());

					// Create a new task for handling the client connection
					ChatTask task = new ChatTask(connection);

					// Submit the task to the thread pool for execution
					pool.submit(task);
				} catch (IOException ex) {
					// Log any IOException that occurs when accepting a connection
					System.err.println("Error accepting connection: " + ex.getMessage());
				}
			}
		} catch (IOException ex) {
			// Log any IOException that occurs when setting up the server
			System.err.println("Couldn't start server: " + ex.getMessage());
		}
	}
}

// Class for handling individual client connections
class ChatTask implements Callable<Void> {
	// Socket representing the client connection
	private Socket connection;

	// Constructor for ChatTask
	ChatTask(Socket connection) {
		this.connection = connection;
	}

	// The call method that will be executed by the thread pool
	 @Override
	    public Void call() {
	        try {
	            // Create a BufferedReader to read data from the client
	            BufferedReader clientInput = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            // Create a Writer to send data back to the client (if needed)
	            Writer out = new OutputStreamWriter(connection.getOutputStream());

	            String clientMessage;
	            // Infinite loop for continuously reading client input
	            while ((clientMessage = clientInput.readLine()) != null) {
	                // Print the message received from the client
	                System.out.println("Client says: " + clientMessage);

	              
	            }
	        } catch (IOException e) {
	            // Log any IOException that occurs during communication
	            System.err.println("Error in communication: " + e.getMessage());
	        } finally {
	            try {
	                // Close the client connection
	                connection.close();
	            } catch (IOException e) {
	                // Log any IOException that occurs when closing the connection
	                System.err.println("Error closing connection: " + e.getMessage());
	            }
	        }
	        return null;
	    }
	}