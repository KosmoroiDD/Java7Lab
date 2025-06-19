import CollectionObjects.Product;
import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import network.Request;
import network.Response;

public class Client {
    private static Client client;
    Scanner scanner;
    static Scanner tempScanner;
    private static final Deque<File> scriptsStack = new ArrayDeque<>();
    static int port = 5745;
    static String name = "localhost";
    public static SocketChannel chnl;
    Response response;

    private Client(InetAddress host, int port) {
    }
    public static Client getInstance(){
        if(client == null){
            try {
                client = new Client(InetAddress.getByName(name), port);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }
        return client;
    }

    public void run() throws IOException {
        try (SocketChannel channel = SocketChannel.open()) {
            channel.configureBlocking(false);
            channel.connect(new InetSocketAddress(name, 9999));
            chnl = channel;
            while (!channel.finishConnect()) {
                System.out.println("waiting to finish connection");
            }
            System.out.println("Подключение к серверу (" + channel.getRemoteAddress() + ") успешно.");
            System.out.print("> ");
            try {
                while (true) {
                    String text = scanner.nextLine();
                    String[] request = text.split(" ");
                    String arguments = "";
                    if (request.length > 1) {
                        arguments = request[1].trim();
                    }
                    if (text.trim().isEmpty()) {
                        System.out.print("> ");
                        continue;
                    }
                    System.out.println(text);
                    processUserPrompt(request[0], arguments);
                    System.out.print("> ");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("IOException occurred or class not found.");
            }
        }
    }


    public void processUserPrompt(String command, String arguments) throws IOException, ClassNotFoundException {
        if (command.equalsIgnoreCase("add") || command.equalsIgnoreCase("update_id")){
            Product objArgument = Filler.createProduct();
            if (objArgument != null) {
                send(new Request(command, arguments, (Serializable) objArgument));
            }
        }
        else if (command.equalsIgnoreCase("exit")){
            System.out.println("bye-bye🤫🧏");
            System.exit(0);
        }
        else if (command.equalsIgnoreCase("execute")){
            this.executeScript(arguments);
        }
        else {
            Request request = new Request(command, arguments);
            System.out.println(request);
            send(request);
        }
    }

    public void send(Request request) throws IOException{
        try{
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(request);
            buffer.put(baos.toByteArray());
            buffer.flip();
            while (buffer.hasRemaining()){
                chnl.write(buffer);
            }
            oos.close();
            buffer.clear();
            int bytesRead = chnl.read(buffer);
            if (bytesRead == -1){
                System.out.println("Возникла проблема: получено пустое сообщение от сервера (" + chnl.getRemoteAddress() + ").");
                return;
            }
            buffer.flip();
            try(ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
            ObjectInputStream ois = new ObjectInputStream(bais)){
            Object answer = ois.readObject();
                receive(answer);
                ois.close();
                buffer.clear();
            }
        } catch (IOException e) {
            System.out.println("the server is not reachable as of now, try again later...");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void receive(Object answer) throws IOException{
        System.out.println("Получено сообщение от сервера (" + chnl.getRemoteAddress() + "):\n" + answer);
    }

    public void executeScript(String filepath){
        File file = new File(filepath);
        if(getScriptsStack().contains(file)){
            System.out.println("Recursion detected");
            return;
        }
        try {
            tempScanner = Client.getInstance().scanner;
            Scanner newScanner = new Scanner(file);
            getScriptsStack().add(file);
            setScanner(newScanner);
            while (newScanner.hasNextLine()){
                String line = newScanner.nextLine();
                var command = "";
                var arguments = "";

                String[] prompt = (line + " ").trim().split(" ", 2);
                if (prompt.length == 2) {
                    arguments = prompt[1].trim();
                }
                command = prompt[0].trim();
                if (command.equals("execute")){
                    executeScript(arguments);
                    continue;

                }
                processUserPrompt(command, arguments);
            }
            setScanner(tempScanner);
            getScriptsStack().removeLast();
        } catch (FileNotFoundException e) {
            System.out.println("didn't find the desired script file");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Deque<File> getScriptsStack() {
        return scriptsStack;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}