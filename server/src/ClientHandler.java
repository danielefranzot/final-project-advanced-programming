import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler extends Thread{

	private Server server;
	private Socket socket;

	public ClientHandler(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
	}
	
	public void run() {
		try {
		    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		    while (true) {
		      String line = br.readLine();
		      if (line.equals(server.getQuitCommand())) {
		        socket.close();
		        break;
		      }
		      bw.write("hey questo è quello che hai inserito: " + process(line) + System.lineSeparator());
		      bw.flush();
		    }
		  } catch (IOException e) {
		    e.printStackTrace();
		  } finally {
		    try {
		      socket.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		  }
		  }
	
	protected String process(String input) {
		    return input;
		  }
	

}
