import modules.CommandsProvider;
import CollectionObjects.*;

import java.io.*;
import java.net.*;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Timestamp;

import modules.commands.Show;
import modules.XmlParser;
import network.Request;
import network.Response;

import static CollectionObjects.Collectionss.stringCollection;

public class Server extends Thread {
    public static int port = 9999;
    public static CommandsProvider commandsProvider;
    public static Collectionss collectionss;
    public static String filename;


    public void run() {
        try (ServerSocketChannel channel = ServerSocketChannel.open()) {
            Server server = new Server();
            channel.socket().bind(new InetSocketAddress(port));
            channel.configureBlocking(false);
            server.setCommandsProvider(new CommandsProvider());
            server.setCollectionss(new Collectionss());
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println(stringCollection);
                XmlParser.saveToXml(stringCollection, filename);
                System.out.println("Server stopped. Collection saved.");
            }));
            System.out.println("Ожидание клиента на порт " + channel.socket().getLocalPort() + "...");
            while (true) {
                try (SocketChannel client = channel.accept()) {
                    if (client != null) {
                        while (true) {
                            try {
                                SocketAddress IPAddress = client.socket().getRemoteSocketAddress();
                                int port = client.socket().getPort();
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                System.out.println("[" + timestamp + ", IP: " + IPAddress + ", Port: " + port + "] ");
                                int bytesRead = -1;
                                bytesRead = client.read(buffer);
                                //Thread.sleep(1000);
                                if (bytesRead == -1){
                                    System.out.println("|X| Разорвано соединение с клиентом ("
                                            + client.getRemoteAddress() + ").");
                                    client.close();
                                    return;
                                }
                                else {
                                    buffer.flip();
                                    try(ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
                                    ObjectInputStream ois = new ObjectInputStream(bais)) {
                                        Object object = ois.readObject();
                                        Request request = (Request) object;
                                        String command = request.getCommandName();
                                        System.out.println("|R| Получен запрос на выполнение команды /" + command
                                                + " от клиента (" + client.getRemoteAddress() + ").");
                                        buffer.clear();
                                        Response result = CommandsProvider.call(request);
                                        Thread.sleep(1000);

                                        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                             ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                                            oos.writeObject(result.getMessage());
                                            byte[] responseData = baos.toByteArray();

                                            ByteBuffer responseBuffer = ByteBuffer.wrap(responseData);
                                            while (responseBuffer.hasRemaining()) {
                                                client.write(responseBuffer);
                                            }
                                        }
                                        buffer.clear();
                                    }
                                    }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }else{
                        Thread.sleep(100);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }  catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }


            } catch(IOException e){
                throw new RuntimeException(e);
            }
        }


        public CommandsProvider getCommandsProvider (CommandsProvider commandsProvider){
            return commandsProvider;
        }

        public Collectionss getCollections () {
            return collectionss;
        }
        public void setCollectionss (Collectionss collectionss){
            Server.collectionss = collectionss;
        }

        public void setCommandsProvider (CommandsProvider commandsProvider){
            Server.commandsProvider = commandsProvider;
        }

        public String getFilename () {
            return filename;
        }

        public static void setFilename (String filename){
            Server.filename = filename;
        }

        public static void main (String[] args) throws IOException {
            // Проверка наличия аргумента командной строки
            if (args.length == 0) {
                System.out.println("Ошибка: Не указано имя файла в аргументах командной строки.");
                return;
            }
                setFilename(args[0]);
                Show.inputFileName = args[0];
            // Сохранение имени файла из аргументов командной строки
            Thread t = new Server();
            t.start();
        }
}





