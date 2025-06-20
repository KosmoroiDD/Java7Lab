import modules.CommandsProvider;
import CollectionObjects.*;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Timestamp;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import modules.commands.Show;
import network.Request;
import network.Response;
import modules.commands.Add;

import static CollectionObjects.Collectionss.stringCollection;

public class Server extends Thread {
    static Logger log = Logger.getLogger(Server.class.getName());
    public static int port = 9999;
    public static CommandsProvider commandsProvider;
    public static Collectionss collectionss;
    public static String filename;
    static FileHandler fileHandler;

    static {
        try {
            fileHandler = new FileHandler("server.log");
            // Устанавливаем формат вывода (можно использовать SimpleFormatter для более читаемого формата)
            fileHandler.setFormatter(new SimpleFormatter());

            // Добавляем обработчик к логгеру
            log.addHandler(fileHandler);

            // Отключаем вывод логов в консоль (если нужно только в файл)
            log.setUseParentHandlers(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


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
                log.info("Server stopped. Collection saved.");
                System.out.println("Server stopped. Collection saved.");
            }));
            System.out.println("Ожидание клиента на порт " + channel.socket().getLocalPort() + "...");
            log.info("Server started at " + channel.socket().getLocalPort());
            log.info("Server waiting for connection...");
            while (true) {
                try (SocketChannel client = channel.accept()) {
                    if (client != null) {
                        while (true) {
                            try {
                                SocketAddress IPAddress = client.socket().getRemoteSocketAddress();
                                int port = client.socket().getPort();
                                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                                System.out.println("[" + timestamp + ", IP: " + IPAddress + ", Port: " + port + "] ");
                                log.info("Client connected: "+"[" + timestamp + ", IP: "
                                        + IPAddress + ", Port: " + port + "] ");
                                int bytesRead = -1;
                                bytesRead = client.read(buffer);
                                if (bytesRead == -1){
                                    log.info("|X| Разорвано соединение с клиентом ("
                                            + client.getRemoteAddress() + ").");
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
                                        log.info("Server received command: " + command);
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
                                            log.info("Server sent command: " + command);
                                        }
                                        buffer.clear();
                                    }
                                    }
                            } catch (IOException e) {
                                log.severe("Buffer overflow.");
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                log.severe("Client disconnected.");
                                throw new RuntimeException(e);
                            }
                        }
                    }

                } catch (IOException e) {
                    log.severe("Server stopped.");
                    e.printStackTrace();
                }  catch (InterruptedException e) {
                    log.severe("Client stopped.");
                    throw new RuntimeException(e);
                }
            }


            } catch(IOException e){
                log.info("Server stopped.");
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
                log.warning("Invalid arguments.");
                return;
            }
                setFilename(args[0]);
                Show.inputFileName = args[0];
                Add.filename = args[0];
            // Сохранение имени файла из аргументов командной строки
            Thread t = new Server();
            t.start();
        }
}





