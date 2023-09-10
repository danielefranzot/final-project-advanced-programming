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

    private long averageResponseTime;

    private long totalResponseTimes;

    private long maxResponseTime;

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

    public long getAverageResponseTime() {
        return averageResponseTime;
    }

    private void updateAverageTime() {
        this.averageResponseTime = this.totalResponseTimes / this.okResponseCounter;
    }


    public long getTotalResponseTimes() {
        return totalResponseTimes;
    }

    public long getMaxResponseTime() {
        return maxResponseTime;
    }

    public synchronized void addResponseTime(long newResponseTime) {//TODO aggiungere synchronized anche su update avg time?
        this.totalResponseTimes = newResponseTime;
        if(this.okResponseCounter == 0){
            this.averageResponseTime = newResponseTime;
            this.maxResponseTime = newResponseTime;
        }else{
            this.updateAverageTime();
            this.maxResponseTime = Math.max(this.maxResponseTime, newResponseTime);
        }

    }
}
//TODO quando un client si disconnette (solo nel caso in cui si disconnette  interrompendo l'esecuzione e non con BYE) il while entra in loop e non si riferma allo start()
