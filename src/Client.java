import java.net.*;
import java.io.*;
import java.util.Scanner;
public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Scanner scan = new Scanner(System.in);
            String msg = (String) in.readObject();
            System.out.println(msg);
            while (true) {
                System.out.print("Nombre 1: ");
                double n1 = scan.nextDouble();
                System.out.print("Opérateur: ");
                String op = scan.next();
                if (op.equals("quit")) {
                    out.writeObject(new Operation(0, 0, "quit"));
                    break;
                }
                System.out.print("Nombre 2: ");
                double n2 = scan.nextDouble();
                
                out.writeObject(new Operation(n1, n2, op));
                Double resultat = (Double) in.readObject();
                System.out.println("Résultat: " + resultat);
            }
            scan.close();
            socket.close();
        } catch (Exception e) {
            System.out.println("Problème de connexion");
        }
    }
}