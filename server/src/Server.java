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
    private final String requestCommand;

    public Server(int port, String requestCommand){
        this.port = port;
        this.requestCommand = requestCommand;
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
      return requestCommand;
    }

    
    /*public void main(String[] args) {
		Server s = new Server(10000, "QUIT");
	
		try {
			s.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

    
}