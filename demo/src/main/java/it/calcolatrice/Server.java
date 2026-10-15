package it.calcolatrice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORTA)) {
            System.out.println("Server in ascolto sulla porta " + PORTA);

            while (true) {
                try (
                    Socket clientSocket = serverSocket.accept();
                    BufferedReader input = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream())
                    );
                    PrintWriter output = new PrintWriter(
                        clientSocket.getOutputStream(), true
                    )
                ) {
                    System.out.println("Client connesso: "
                            + clientSocket.getInetAddress());

                    String richiesta = input.readLine();
                    output.println(calcola(richiesta));

                } catch (Exception e) {
                    System.err.println("Errore nella connessione: "
                            + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.err.println("Impossibile avviare il server: "
                    + e.getMessage());
        }
    }

    private static String calcola(String richiesta) {
        if (richiesta == null) {
            return "Errore: richiesta vuota.";
        }

        String[] parti = richiesta.trim().split("\\s+");
        if (parti.length != 3) {
            return "Errore: formato non valido. Usa: numero operatore numero";
        }

        try {
            double primo = Double.parseDouble(parti[0]);
            String operatore = parti[1];
            double secondo = Double.parseDouble(parti[2]);
            double risultato;

            switch (operatore) {
                case "+":
                    risultato = primo + secondo;
                    break;
                case "-":
                    risultato = primo - secondo;
                    break;
                case "*":
                    risultato = primo * secondo;
                    break;
                case "/":
                    if (secondo == 0) {
                        return "Errore: divisione per zero.";
                    }
                    risultato = primo / secondo;
                    break;
                default:
                    return "Errore: operatore non riconosciuto.";
            }

            return "Risultato: " + risultato;
        } catch (NumberFormatException e) {
            return "Errore: uno o entrambi gli operandi non sono numeri validi.";
        }
    }
}
