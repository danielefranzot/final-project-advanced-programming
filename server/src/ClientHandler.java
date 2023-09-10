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

    private long currentRequestResponseTime;

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

                long startTime = System.currentTimeMillis();
                System.out.println("start time request: " + startTime);

                System.out.println("printo request dopo averla ricevuta " + this.request);
                String response = "default response";

                if (this.request != null) {
                    if (this.request.equals(server.getQuitCommand())) {
                        socket.close();
                        break;
                    } else {
                        if (!manageRequest().contains("ERR")) {
                            System.out.println("toh comando giusto " + this.request);

                            response = this.manageRequest();
                            System.out.println(response);
                        }
                    }
                    System.out.println("invio response");


                    if (!response.contains("ERR")) {//if OK type response update response time data
                        server.incrementOkResponseCounter();
                        long currentResponseTime = System.currentTimeMillis() - startTime;
                        server.addResponseTime(currentResponseTime);     //addTotalResponse time add the response time of the current response and updates the avarega response time
                        System.out.println("current response time: " + currentResponseTime +
                                " total response time: " + server.getTotalResponseTimes() +
                                " max response time: " + server.getMaxResponseTime() +
                                " avg response time: " + server.getAverageResponseTime());
                    }
                    bw.write(process(this.request) + ": " + response + System.lineSeparator());
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

    private String manageRequest() {
        String response = "";
        if (this.request == null) {
            response = "ERR; null request received";
        }
        //formato request  tipoRequest;variableValuesFunction;expression


        String[] stringRequestArr = this.request.split(";");
        String[] requestType = stringRequestArr[0].split("_");

        System.out.println("in manageRequest: " + this.request);

        if (stringRequestArr.length == 1) {
            //TODO siamo in una richiesta di tipo stat visto che sono composte solo da  STAT_REQS  STAT_AVG_TIME   STAT_MAX_TIME
            System.out.println("stringrequestarray: " + stringRequestArr[0]);

            switch (stringRequestArr[0]) {
                case "STAT_REQS" -> {//return number of ok response
                    System.out.println("stat_reqs");
                    response = "OK;" + this.server.getOkResponseCounter();
                }
                case "STAT_AVG_TIME" -> {//return avg response time of all ok response
                    response = "OK;" + server.getAverageResponseTime();
                }
                case "STAT_MAX_TIME" -> {
                    response = "OK;" + server.getMaxResponseTime();
                }
                default -> {
                    System.out.println("default");
                    response = "ERR; invalid request";
                }
            }

        } else if (requestType.length == 3) {
            //TODO  siamo in una computarional request a questo punto da decidere se trasformare questa nella funzione che elabora anche la richiesta e via
            //      così faccio un escalation di errori per generare response di tipo ERR

        } else {
            //in tutti gli altri casi se i segmenti delimitati da ; sono più di 3 allora la request non sta rispettando il formato corretto
            response = "ERR;request non formattata correttamente";
        }

        try {
            Thread.sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return response;
    }

    protected String process(String input) {
        return input.toUpperCase();
    }

}



