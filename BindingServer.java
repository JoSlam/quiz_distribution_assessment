/* 
* Name: Joshua Lambert
* ID: 815007658
* Course: COMP 6601
* Final assessment
*/

/* 
    * Documentation
    * Handles binding of QuizServer to QuizServer name for lookup
*/

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BindingServer {
    private static Registry registry = null;

    public static void main(String[] args) {
        try {
            registry = LocateRegistry.getRegistry();

            RMIQuizServer quizServer = new RMIQuizServer();
            bindServer("QuizServer", quizServer, registry);
        } catch (RemoteException e) {
            System.out.println("Remote Exception: " + e);
        }
    }


    // Binds server to name given and prints output
    public static void bindServer(String name, Remote server, Registry registry) {
        try {
            registry.rebind(name, server);
            System.out.println("Bound server: " + server.getClass().getName() + " to server name: " + name);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
