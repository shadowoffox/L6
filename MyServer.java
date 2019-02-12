import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(7777)) {
            System.out.println("Server started!");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected!");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try (DataInputStream inp = new DataInputStream(socket.getInputStream())
                            ) {

                            while (true) {
                                String msg = inp.readUTF();
                                System.out.println("Message: " + msg);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                       try( DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in))){
                           String line =null;

                           while (true){
                               System.out.println("ВВедите сообщение для клиента!");
                               line = keyboard.readLine();
                               out.writeUTF(line);
                               out.flush();
                           }
                       }catch (IOException e) {
                           e.printStackTrace();
                       }

                    }
                }).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

