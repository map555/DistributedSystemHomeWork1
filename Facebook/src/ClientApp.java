import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class ClientApp {
    Socket clientSocket;
    Socket chatClientSocket;
    ServerSocket chatServer;
    ObjectOutputStream out;
    ObjectInputStream in;

    private void connectToServer() {
        try {
            clientSocket = new Socket(
                    InetAddress.getByName("127.0.0.1"), 5432);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectToChatServer(SocketAddress socketAddress) {
        chatClientSocket = new Socket();
        try {
            chatClientSocket.connect(socketAddress);
            System.out.println("Connection successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createChatServer(){
        try {
            chatServer = new ServerSocket(0);
            out.writeObject(chatServer.getLocalSocketAddress());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerClient() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter your name");
        String name = sc.nextLine();

        System.out.println("Please enter your age");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.println("Please enter your e-mail address");
        String email = sc.nextLine();

        try {
            in = new ObjectInputStream(clientSocket.getInputStream());
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            out.writeObject("Name:" + name + ";Age:" + age + ";email:" + email);
            out.flush();

            Thread listenerThread = new Thread(() -> {
                Object message = new Object();
                while (true) {
                    try {
                        message = in.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    if (message instanceof SocketAddress) {
                        connectToChatServer((SocketAddress) message);
                    } else if(message instanceof String) {
                        System.out.println((String) message);
                        if(((String) message).startsWith("WAITING")) {
                            createChatServer();
                            try {
                                new ChatHandler(chatServer.accept()).start();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
            listenerThread.start();


            String answer = "";
            while (true) {
                answer = sc.nextLine();
                out.writeObject(answer);
                out.flush();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ChatHandler extends Thread {
        Socket socket;

        public ChatHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Sockcet: " + socket.getRemoteSocketAddress());
        }
    }


    public static void main(String args[]) {

        ClientApp app = new ClientApp();

        app.connectToServer();

        app.registerClient();

    }
}
