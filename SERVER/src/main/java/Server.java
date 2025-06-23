import modules.CommandsProvider;
import CollectionObjects.*;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.*;
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

    // User authentication fields
    private static final Map<String, String> registeredUsers = new ConcurrentHashMap<>();
    private static final Map<SocketChannel, String> authenticatedClients = new ConcurrentHashMap<>();
    private static final String USER_DATA_FILE = "users.dat";

    // Thread pools
    private static final ExecutorService requestReadingPool = Executors.newFixedThreadPool(10);
    private static final ExecutorService requestProcessingPool = Executors.newCachedThreadPool();
    public static ForkJoinPool forkJoinPool = new ForkJoinPool();

    // Synchronized collection
    private static Collection<Object> synchronizedCollection;

    static {
        try {
            fileHandler = new FileHandler("server.log");
            fileHandler.setFormatter(new SimpleFormatter());
            log.addHandler(fileHandler);
            log.setUseParentHandlers(false);

            // Load registered users from file
            loadUsers();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try (ServerSocketChannel serverChannel = ServerSocketChannel.open();
             Selector selector = Selector.open()) {

            // Initialize synchronized collection
            synchronizedCollection = Collections.synchronizedCollection(new ArrayList<>());

            serverChannel.socket().bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);

            setCommandsProvider(new CommandsProvider());
            setCollectionss(new Collectionss());

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println(stringCollection);
                log.info("Server stopped. Collection saved.");
                System.out.println("Server stopped. Collection saved.");
                saveUsers(); // Save user data on shutdown
                requestReadingPool.shutdown();
                requestProcessingPool.shutdown();
            }));

            System.out.println("Ожидание клиента на порт " + serverChannel.socket().getLocalPort() + "...");
            log.info("Server started at " + serverChannel.socket().getLocalPort());
            log.info("Server waiting for connection...");

            while (true) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    keys.remove();

                    if (!key.isValid()) continue;

                    if (key.isAcceptable()) {
                        acceptClient(serverChannel, selector);
                    } else if (key.isReadable()) {
                        requestReadingPool.submit(() -> handleRead(key));
                    }
                }
            }
        } catch (IOException e) {
            log.severe("Server stopped.");
            throw new RuntimeException(e);
        }
    }

    private void acceptClient(ServerSocketChannel serverChannel, Selector selector) throws IOException {
        SocketChannel client = serverChannel.accept();
        if (client != null) {
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);

            SocketAddress IPAddress = client.socket().getRemoteSocketAddress();
            int port = client.socket().getPort();
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            System.out.println("[" + timestamp + ", IP: " + IPAddress + ", Port: " + port + "] "
                    + " In Thread: " +  Thread.currentThread().getName());
            log.info("Client connected: "+"[" + timestamp + ", IP: "
                    + IPAddress + ", Port: " + port + "] " + " In Thread: " +  Thread.currentThread().getName());
        }
    }

    private void handleRead(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(4096);

        try {
            int bytesRead = client.read(buffer);
            if (bytesRead == -1) {
                log.info("|X| Разорвано соединение с клиентом ("
                        + client.getRemoteAddress() + ")." + " In Thread: " +  Thread.currentThread().getName());
                System.out.println("|X| Разорвано соединение с клиентом ("
                        + client.getRemoteAddress() + ")." + " In Thread: " +  Thread.currentThread().getName());
                authenticatedClients.remove(client);
                client.close();
                return;
            }

            buffer.flip();
            requestProcessingPool.submit(() -> processRequest(buffer, client));
        } catch (IOException e) {
            try {
                authenticatedClients.remove(client);
                client.close();
            } catch (IOException ex) {
                log.severe("Error closing client connection: " + ex.getMessage() + " In Thread: "
                        +  Thread.currentThread().getName());
            }
            log.severe("Error reading from client: " + e.getMessage() + " In Thread: "
                    +  Thread.currentThread().getName());
        }
    }

    private void processRequest(ByteBuffer buffer, SocketChannel client) {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(buffer.array());
             ObjectInputStream ois = new ObjectInputStream(bais)) {

            Object object = ois.readObject();
            Request request = (Request) object;
            String command = request.getCommandName();

            // Check if client is authenticated
            if (!isAuthenticated(client) && !command.equals("login") && !command.equals("register")) {
                sendResponse(new Response("ERROR: You need to login first"), client);
                return;
            }

            System.out.println("|R| Получен запрос на выполнение команды /" + command
                    + " от клиента (" + client.getRemoteAddress() + ")." + " In Thread: "
                    +  Thread.currentThread().getName());
            log.info("Server received command: " + command + " In Thread: " +  Thread.currentThread().getName());

            Response result;
            if (command.equals("login")) {
                result = handleLogin(request, client);
            } else if (command.equals("register")) {
                result = handleRegistration(request);
            } else {
                result = CommandsProvider.call(request);
            }

            new Thread(() -> sendResponse(result, client)).start();

        } catch (IOException | ClassNotFoundException e) {
            log.severe("Error processing request: " + e.getMessage()
                    + " In Thread: " +  Thread.currentThread().getName());
        }
    }

    private Response handleLogin(Request request, SocketChannel client) {
        String[] credentials = request.getCommandStrArg().split(":");
        if (credentials.length != 2) {
            return new Response("ERROR: Invalid login format. Use username:password");
        }

        String username = credentials[0];
        String password = credentials[1];

        if (!registeredUsers.containsKey(username)) {
            return new Response("ERROR: User not found");
        }

        if (!registeredUsers.get(username).equals(password)) {
            return new Response("ERROR: Invalid password");
        }

        authenticatedClients.put(client, username);
        return new Response("SUCCESS: Logged in as " + username);
    }

    private Response handleRegistration(Request request) {
        String[] credentials = request.getCommandStrArg().split(":");
        if (credentials.length != 2) {
            return new Response("ERROR: Invalid registration format. Use username:password");
        }

        String username = credentials[0];
        String password = credentials[1];

        if (registeredUsers.containsKey(username)) {
            return new Response("ERROR: Username already exists");
        }

        registeredUsers.put(username, password);
        saveUsers();
        return new Response("SUCCESS: Registered successfully. Now you can login.");
    }

    private boolean isAuthenticated(SocketChannel client) {
        return authenticatedClients.containsKey(client);
    }

    private static void loadUsers() {
        File file = new File(USER_DATA_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Map<String, String> loadedUsers = (Map<String, String>) ois.readObject();
            registeredUsers.putAll(loadedUsers);
            log.info("Loaded " + loadedUsers.size() + " registered users");
        } catch (IOException | ClassNotFoundException e) {
            log.warning("Failed to load user data: " + e.getMessage());
        }
    }

    private static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_DATA_FILE))) {
            oos.writeObject(registeredUsers);
            log.info("Saved " + registeredUsers.size() + " registered users");
        } catch (IOException e) {
            log.warning("Failed to save user data: " + e.getMessage());
        }
    }

    private void sendResponse(Response result, SocketChannel client) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(result.getMessage());
            byte[] responseData = baos.toByteArray();
            ByteBuffer responseBuffer = ByteBuffer.wrap(responseData);

            while (responseBuffer.hasRemaining()) {
                client.write(responseBuffer);
            }

            log.info("Server sent response for command" + " In Thread: " +  Thread.currentThread().getName());
        } catch (IOException e) {
            log.severe("Error sending response: " + e.getMessage()
                    + " In Thread: " +  Thread.currentThread().getName());
        }
    }

    public CommandsProvider getCommandsProvider(CommandsProvider commandsProvider) {
        return commandsProvider;
    }

    public Collectionss getCollections() {
        return collectionss;
    }

    public void setCollectionss(Collectionss collectionss) {
        Server.collectionss = collectionss;
    }

    public void setCommandsProvider(CommandsProvider commandsProvider) {
        Server.commandsProvider = commandsProvider;
    }

    public String getFilename() {
        return filename;
    }

    public static void setFilename(String filename) {
        Server.filename = filename;
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Ошибка: Не указано имя файла в аргументах командной строки.");
            log.warning("Invalid arguments.");
            return;
        }
        setFilename(args[0]);
        Show.inputFileName = args[0];
        Add.filename = args[0];

        PostgresSQL.initializeDatabaseConnection();

        Thread t = new Server();
        t.start();
    }
}