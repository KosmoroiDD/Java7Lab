import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Client client = Client.getInstance();
        Client.getInstance().setScanner(new Scanner(System.in));
        client.run();
    }
}
