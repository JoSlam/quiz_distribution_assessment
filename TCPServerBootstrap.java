import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/*
    Name: Joshua Lambert
    ID: 815007658
    Course: COMP 6601
*/

/*
    Documentation => TCPServerBootstrap

    * Server accepts connections on port 12009

    The TCPServerBootstrap performs the following roles:
    * Opens connection on port 12009 to accept requests from client machines
    * On receipt of a connection it creates a new instance of the TCPQuizServer class
        => The TCPServerBootstrap class extends the Thread super class to enable multi-threaded workloads

    For more information on server functionality see documentation at => TCPQuizServer.java

 */
public class TCPServerBootstrap {

    public static void main(String[] args) {
        try {
            int serverPort = 12009;
            ServerSocket listenSocket = new ServerSocket(serverPort);

            System.out.println("Name: Joshua Lambert");
            System.out.println("ID: 815007658");
            System.out.println("Quiz Server running on port: " + serverPort);
            System.out.println("Listening for connections...");

            // listen for connections
            while (true) {
                // create new quizServer thread on receipt of client message
                Socket clientSocket = listenSocket.accept();
                TCPQuizServer quizServer = new TCPQuizServer(clientSocket);
            }
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
            System.out.println(e.getStackTrace());
        }
    }
}
