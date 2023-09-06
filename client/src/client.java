import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class client {
	 public static void main(String[] args) throws IOException {
		    InetAddress serverInetAddress;
		    if (args.length > 1) {
		      serverInetAddress = InetAddress.getByName(args[0]);
		    } else {
		      serverInetAddress = InetAddress.getLocalHost();
		    }
		    Socket socket = new Socket(serverInetAddress, 10000);
		    BufferedReader br = new BufferedReader(
		      new InputStreamReader(socket.getInputStream())
		    );
		    BufferedWriter bw = new BufferedWriter(
		      new OutputStreamWriter(socket.getOutputStream())
		    );
			boolean stop = true;

		    while (stop) {
				Scanner keyboard = new Scanner(System.in);
				String stringToSend = keyboard.nextLine();


				bw.write(stringToSend + System.lineSeparator());
				bw.flush();
				System.out.println("messaggio inviato");
				String received = br.readLine();
				System.out.printf("Sent: %s%nReceived: %s%n",
						stringToSend, received
				);
				System.out.println("controllo BYE");
				stop = !stringToSend.equals("BYE");
			}
		 System.out.println("chiudo socket");
		    socket.close();
		  }
}
