import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * server
 * 
 * v0.1 ricevi una richiesta e chiudi la connessione se la richiesta contiene QUIT
 * 
 * 
 */
public class Server {
    private final int port;
    private String requestCommand;

    private final String quitCommand = "BYE";
    private int okResponseCounter = 0;

    public Server(int port){
        this.port = port;
    }

    public void run() throws IOException{
        ServerSocket serverSocket = new ServerSocket(this.port);
        
        while(true){
        	System.out.println("server up");
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket, this);
            clientHandler.start();
        }
    }
    

    public String process(String input) {
      return input;
    }
    public String getQuitCommand() {
        return quitCommand;
    }

    public String getRequestCommand() {
        return requestCommand;
    }

    public int getOkResponseCounter(){return this.okResponseCounter;}

    public void incrementOkResponseCounter(){this.okResponseCounter++;}

}