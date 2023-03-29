import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{


    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;
    C4Info info = new C4Info();
    int port = 0;

    private Consumer<Serializable> callback;

    Client(Consumer<Serializable> call, int port){

        callback = call;
        this.port = port;
    }

    public void run() {

        try {
            socketClient= new Socket("127.0.0.1",port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        catch(Exception e) {}

        while(true) {

            try {
                String message = in.readObject().toString();
                if(message.equals("PLAYER ONE WINS")) {
                    info.setWinner(1);
                }
                if(message.equals("PLAYER TWO WINS")) {
                    info.setWinner(2);
                }
                if(message.equals("TIE GAME")) {
                    info.setWinner(3);
                }
                if(message.charAt(0) == 'N') {
                    callback.accept(message);
                }
                if(message.charAt(0) == 'T') {
                    info.setTurn(true);
                    if(message.charAt(1) == 'n') {
                        callback.accept(message.substring(1));
                    }
                }
                else if(message.charAt(0) == 'X') {
                    info.setTurn(false);
                    if(message.charAt(1) == 'n') {
                        callback.accept(message.substring(1));
                    }
                }
                Integer number = Integer.parseInt(message.substring(1,2));
                int i = Integer.parseInt(message.substring(2,3));
                int j = Integer.parseInt(message.substring(3,4));
                if(number == 1) {
                    info.setP1Plays(message.substring(2,4));
                    info.setP2Plays("");
                    info.setP1turn(false);
                    info.setP2turn(true);
                    info.updateBoard(i,j,1);
                }
                if(number == 2) {
                    info.setP1Plays("");
                    info.setP2Plays(message.substring(2,4));
                    info.setP1turn(true);
                    info.setP2turn(false);
                    info.updateBoard(i,j,2);
                }
                callback.accept(message.substring(4));
            }
            catch(Exception e) {}
        }

    }

    public void send(String data) {

        try {
            out.writeObject(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void output(String data) {
        callback.accept(data);
    }

    public C4Info getC4Info() {
        return info;
    }



}
