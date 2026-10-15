package it.calcolatrice;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORTA = 12345;

    public static void main(String[] args) {
        try (Scanner tastiera = new Scanner(System.in)) {
            System.out.print("Inserisci il primo numero: ");
            String primo = tastiera.nextLine();

            System.out.print("Inserisci l'operatore (+, -, *, /): ");
            String operatore = tastiera.nextLine();

            System.out.print("Inserisci il secondo numero: ");
            String secondo = tastiera.nextLine();

            try (
                Socket socket = new Socket(HOST, PORTA);
                PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true
                );
                BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                )
            ) {
                output.println(primo + " " + operatore + " " + secondo);

                String risposta = input.readLine();
                System.out.println("Risposta del server: " + risposta);
            }
        } catch (Exception e) {
            System.err.println("Errore di connessione: " + e.getMessage());
        }
    }
}
