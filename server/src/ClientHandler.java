import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Server server;
    private Socket socket;

    private String request;

    public ClientHandler(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while (true) {
                System.out.println("leggo da socket");
                this.request = br.readLine();
                System.out.println("printo request dopo averla ricevuta "+ this.request);
                String response = "default response";
                if(this.request != null) {
                    if (this.request.equals(server.getQuitCommand())) {
                        socket.close();
                        break;
                    } else {
                        if (!checkRequest().contains("ERR")) {
                            System.out.println("toh comando giusto " + this.request);
                            server.incrementOkResponseCounter();
                            response = this.checkRequest();//TODO valutare se usare campo o una stringa e via
                            System.out.println(response);
                        }
                    }
                    System.out.println("invio response");
                    bw.write(process(this.request) + ": " + response + System.lineSeparator() );
                    bw.flush();
                    System.out.println("ho inviato response");
                }
                System.out.println("fine whie");
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

    private  String checkRequest() {
        //TODO controllo stringa not null
        //formato rquest  tipoRequest;variableValuesFunction;expression

        //prendo prima occorrenza
        String[] stringRequestArr = this.request.split(";");
        String[] requestType = stringRequestArr[0].split("_");
        System.out.println("in checkRequest: "+ this.request);
        if (stringRequestArr.length == 1) {
            //TODO siamo in una richiesta di tipo stat visto che sono composte solo da  STAT_REQS  STAT_AVG_TIME   STAT_MAX_TIME
            System.out.println("stringrequestarray: "+ stringRequestArr[0]);

            switch (stringRequestArr[0]) {
                case "STAT_REQS" -> {
                    System.out.println("stat_reqs");
                    return  "OK;" + this.server.getOkResponseCounter();
                }
                case "roba", "altra_roba" -> {
                    return "altra request";
                }
                default -> {
                    System.out.println("default");
                    return "ERR; formato request non corretto";
                }
            }

        } else if (requestType.length == 3) {
            //TODO  siamo in una computarional request a questo punto da decidere se trasformare questa nella funzione che elabora anche la richiesta e via
            //      così faccio un escalation di errori per generare response di tipo ERR

        }

        //in tutti gli altri casi se i segmenti delimitati da ; sono più di 3 allora la request non sta rispettando il formato corretto
        return "ERR;request non formattata correttamente";


    }



    protected String process(String input) {
        return input.toUpperCase();
    }


}

//TODO rifare ritorni stringa con ERR
