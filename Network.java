import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Network implements Closeable {
    DataOutputStream out;
    DataInputStream in;
    Scanner scanner = new Scanner(System.in);
    private final Socket socket;
    private MessageSender messageSender;

    public  Network(String hostname, int port, MessageSender messageSender) throws IOException {

        this.socket = new Socket(hostname,port);
        this.out = new DataOutputStream(socket.getOutputStream());
        this.messageSender = messageSender;
        this.in = new DataInputStream(socket.getInputStream());


        new Thread(new Runnable() {
            @Override
            public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String msg = in.readUTF();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            messageSender.sendMessangeers("Server", msg);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }
        }).start();
    }

    public void sendMessage(String msg){
        try {
        out.writeUTF(msg);
        out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
