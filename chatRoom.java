/**
 * An chat server listening on port 10,000
 *
 * @author - JB.
 */

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;


public class  chatRoom
{
    public static final int DEFAULT_PORT = 10000;

    // construct a thread pool for concurrency
    private static final Executor exec = Executors.newCachedThreadPool();
    
    
    public static void main(String[] args) throws IOException {
        ServerSocket sock = null;
        
        HashMap<String,BufferedOutputStream> map = new HashMap<String,BufferedOutputStream>(25);
        try {
            // establish the socket
            sock = new ServerSocket(DEFAULT_PORT);

            while (true) {
                /**
                 * now listen for connections
                 * and service the connection in a separate thread.
                 */
                Runnable task = new Connection(sock.accept(),map);
                exec.execute(task);
            }
        }
        catch (IOException ioe) { System.err.println(ioe); }
        finally {
            if (sock != null)
                sock.close();
        }
    }
}

class Handler
{

    /**
     * this method is invoked by a separate thread
     */
    public void process(Socket client, HashMap<String,BufferedOutputStream> map) throws java.io.IOException {
        BufferedOutputStream toClient = null;
        BufferedReader fromClient = null;
        String userName = null;
        String message;
        byte[] buffer = new byte[10000];
        

        try { 
            fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            toClient = new BufferedOutputStream(client.getOutputStream());
            // while (true) {
                //System.out.println("enter while loop");
                message = fromClient.readLine() + "\r\n";
                System.out.println(message.split(" ")[1]); //prints without header
                System.out.println(map);
                System.out.println(map.get(userName));

                if (message.split(" ")[0].equals("JOIN")){
                    userName = fromClient.readLine();
                    System.out.println(userName);
                    map.put(userName, toClient);
                    System.out.println(map);
                }
                
                //System.out.println(userName + client);
               // System.out.println(map.get(userName));
                //System.out.println(map);
                
                else{
                System.out.println(message);
                String msg = message.split(" ")[1];
                // BufferedOutputStream ppl = null;
                    for (String user : map.keySet()){
                        BufferedOutputStream users = map.get(user);
                        users.write(msg.getBytes());
                        users.flush();
                    }
                }
                //     ppl.write(message.getBytes());
                //     System.out.println(user + ": " + message);
                //     ppl.flush();
                // }
                
            // }
        }
        catch (IOException ioe) {
            System.err.println(ioe);
        }
        finally {
            // close streams and socket
            if (toClient != null)
                toClient.close();
        }
    }
}

class Connection implements Runnable
{
    private Socket client;
    private static Handler handler = new Handler();
    private HashMap<String,BufferedOutputStream> map = null;
    public Connection(Socket client,HashMap<String,BufferedOutputStream> map) {
        this.client = client;
        this.map = map;
    }

    /**
     * This method runs in a separate thread.
     */
    public void run() {
        try {
            handler.process(client,map);
        }
        catch (java.io.IOException ioe) {
            System.err.println(ioe);
        }
    }
}



