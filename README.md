<h1>Chat Application</h1>

<p>This is a simple multi-client chat application implemented in Java. It consists of two main components:</p>

<ul>
  <li><strong>ChatServer</strong>: The server-side program that listens for incoming client connections and facilitates communication between connected clients.</li>
  <li><strong>ChatClient</strong>: The client-side program that connects to the server and allows users to send and receive messages.</li>
</ul>

<h2>Features</h2>

<ul>
  <li><strong>Multi-client support</strong>: The server can handle multiple clients simultaneously, allowing for group chats.</li>
  <li><strong>Message Broadcasting</strong>: Messages sent by one client are broadcasted to all other connected clients.</li>
  <li><strong>Graceful Shutdown</strong>: Clients can disconnect from the server using the command <code>\q</code>.</li>
</ul>

<h2>How to Run</h2>

<h3>Prerequisites</h3>

<p>Java Development Kit (JDK) installed.</p>

<h3>Running the Server</h3>

<ol>
  <li>Compile the <code>ChatServer.java</code> file:</li>
</ol>

<pre><code>javac ie/atu/sw/ChatServer.java</code></pre>

<ol start="2">
  <li>Run the server:</li>
</ol>

<pre><code>java ie.atu.sw.ChatServer</code></pre>

<p>The server will start listening for client connections on port <strong>13</strong>.</p>

<h3>Running the Client</h3>

<ol>
  <li>Compile the <code>ChatClient.java</code> file:</li>
</ol>

<pre><code>javac ie/atu/sw/ChatClient.java</code></pre>

<ol start="2">
  <li>Run the client:</li>
</ol>

<pre><code>java ie.atu.sw.ChatClient</code></pre>

<p>The client will attempt to connect to the server running on <code>localhost</code> at port <strong>13</strong>.</p>

<p>Once connected, the client will prompt you to enter your username. After entering your username, you can start sending messages.</p>

<h3>Example Interaction</h3>

<pre><code>Server: Client connected
Client1: Hello, everyone!
Server: Hello, Client1!</code></pre>

<h2>Code Overview</h2>

<h3>ChatServer.java</h3>

<ul>
  <li><strong>Port</strong>: The server listens on port <strong>13</strong>.</li>
  <li><strong>ExecutorService</strong>: A cached thread pool is used to handle multiple client connections concurrently.</li>
  <li><strong>ChatHandler</strong>: A separate <code>Runnable</code> class that handles communication with each connected client.</li>
</ul>

<h3>ChatClient.java</h3>

<ul>
  <li><strong>Server Address</strong>: The client connects to the server running on <code>localhost</code>.</li>
  <li><strong>Port</strong>: The client connects to port <strong>13</strong>.</li>
  <li><strong>User Input</strong>: The client reads user input from the console and sends it to the server.</li>
  <li><strong>Exit Command</strong>: The user can exit the chat by typing <code>\q</code>.</li>
</ul>

<h2>Improvements</h2>

<p><strong>Broadcast Functionality</strong>: The server can be enhanced to broadcast messages from one client to all other connected clients. The current implementation reads a server response from the console instead of automatically broadcasting client messages.</p>

<h2>License</h2>

<p>This project is open-source and available under the <strong>MIT License</strong>.</p>
