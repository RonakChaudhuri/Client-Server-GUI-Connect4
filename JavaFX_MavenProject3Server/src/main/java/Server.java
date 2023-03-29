import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

import javafx.application.Platform;
import javafx.scene.control.ListView;

public class Server{

    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    int port = 0;
    private Consumer<Serializable> callback;
    C4Info info = new C4Info();


    Server(Consumer<Serializable> call, int port){

        callback = call;
        server = new TheServer();
        server.start();
        this.port = port;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void removeClients() {
        clients.remove(clients.get(1));
        setCount(getCount()-1);
        clients.remove(clients.get(0));
        setCount(getCount()-1);
    }

    public class TheServer extends Thread{

        public void run() {

            try(ServerSocket mysocket = new ServerSocket(port);){
                System.out.println("Server is waiting for a client!");


                while(true) {

                    ClientThread c = new ClientThread(mysocket.accept(), count);
                    callback.accept("client has connected to server: " + "client #" + count);
                    clients.add(c);
                    c.start();

                    count++;

                }
            }//end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        }//end of while
    }


    class ClientThread extends Thread{


        Socket connection;
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
        }

        public void updateClients(String message) {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.writeObject(message);
                }
                catch(Exception e) {}
            }
        }



        public void updateClients(String message, int player) {
            ClientThread t = clients.get(player-1);
            try {
                t.out.writeObject(message);
            }
            catch(Exception e) {}
        }

        public void run(){

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            if(count == 1) {
                updateClients("New client on server: client #"+count + "\nWait for client #2");
            }
            if(count == 2) {
                updateClients("Tnew client on server: client #"+count+"\nYour Turn",1);
                updateClients("Xnew client on server: client #"+count+"\nClient One Turn",2);
                info = new C4Info();
            }


            while(true) {
                try {
                    String data = in.readObject().toString();
                    int i = Integer.parseInt(data.substring(0,1));
                    int j = Integer.parseInt(data.substring(1,2));
                    if(count == 1) {
                        info.updateBoard(i,j,1);
                        if(info.checkForWinner(1) != 0){
                            if(info.checkForWinner(1) != 3) {
                                updateClients("PLAYER ONE WINS");
                                callback.accept("PLAYER ONE WINS");
                                removeClients();
                            }
                            else{
                                updateClients("TIE GAME");
                                callback.accept("TIE GAME");
                                removeClients();
                            }
                        }
                        else {
                            updateClients("T" + count + data + "client #"+count+" played: "+data
                                    +"\nYour Turn", 2);
                            updateClients("X" + count + data + "client #"+count+" played: "+data
                                    +"\nClient Two Turn", 1);
                        }
                    }
                    if(count == 2) {
                        info.updateBoard(i,j,2);
                        if(info.checkForWinner(2) != 0){
                            if(info.checkForWinner(2) != 3) {
                                updateClients("PLAYER TWO WINS");
                                callback.accept("PLAYER TWO WINS");
                                removeClients();
                            }
                            else{
                                updateClients("TIE GAME");
                                callback.accept("TIE GAME");
                                removeClients();
                            }
                        }
                        else {
                            updateClients("T" + count + data + "client #"+count+" played: "+data
                                    +"\nYour Turn", 1);
                            updateClients("X" + count + data + "client #"+count+" played: "+data
                                    +"\nClient One Turn", 2);
                        }
                    }
                    callback.accept("client #" + count + " played: " + data);
                    //updateClients(count + data + "client #"+count+" played: "+data);

                }
                catch(Exception e) {
                    callback.accept("NOTICE!!! Something wrong with the socket from client: " + count + "....closing down!");
                    updateClients("Client #"+count+" has left the server!");
                    clients.remove(this);
                    if(count>1) {
                        setCount(getCount()-1);
                    }
                    break;
                }

            }
        }//end of run



    }//end of client thread

}






