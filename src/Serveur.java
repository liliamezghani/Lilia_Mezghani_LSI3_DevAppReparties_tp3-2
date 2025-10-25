import java.net.*;
import java.io.*;
public class Serveur {
    static int totalOps = 0;
    static int nbClients = 0;
    public static void main(String[] args) throws IOException {
        ServerSocket serveur = new ServerSocket(1234);
        System.out.println("Serveur démarre");
        while (true) {
            Socket client = serveur.accept();
            nbClients++;
            new Thread(new GestionClient(client, nbClients)).start();
        }
    }
    static synchronized void ajouterOperation() {
        totalOps++;
        System.out.println("Total operations: " + totalOps);
    }
    static class GestionClient implements Runnable {
        Socket client;
        int numClient;
        public GestionClient(Socket client, int num) {
            this.client = client;
            this.numClient = num;
        }
        public void run() {
            try {
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(client.getInputStream());
                
                String ip = client.getInetAddress().getHostAddress();
                System.out.println("Client " + numClient + " connecté depuis " + ip);
                
                out.writeObject("Bonjour client " + numClient);
                
                while (true) {
                    Operation op = (Operation) in.readObject();
                    
                    if (op.op.equals("quit")) break;
                    
                    double resultat = calculer(op);
                    out.writeObject(resultat);
                    
                    ajouterOperation();
                } 
                client.close();
            } catch (Exception e) {
                System.out.println("Client " + numClient + " parti");
            }
        }
        double calculer(Operation op) {
            switch (op.op) {
                case "+": return op.n1 + op.n2;
                case "-": return op.n1 - op.n2;
                case "*": return op.n1 * op.n2;
                case "/": return op.n2 != 0 ? op.n1 / op.n2 : 0;
                default: return 0;
            }
        }
    }
}