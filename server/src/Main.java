import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		Server s = new Server(10000);
	
		try {
			s.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
