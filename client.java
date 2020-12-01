import java.net.*;
// import jdk.internal.org.jline.utils.InputStreamReader;
import java.util.Scanner;
import java.io.*;

public class client {
	public static final int PORT = 10000;

    
    public static void main(String[] args) throws java.io.IOException {
		
		Socket server = null;
        BufferedReader fromServer = null;
    	BufferedOutputStream os = null;

		DataOutputStream toServer = null;
		String username = null;
		Scanner scanner;
		boolean exitFlag = false;
		String message = null;
		int count = 0;
        
        try {
			server = new Socket(args[0],PORT);
			os=new BufferedOutputStream(server.getOutputStream());
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));

            scanner = new Scanner(System.in);
            System.out.println("Please enter a unique username: ");

            while(!exitFlag){

	            while(username == null){
	         	username = scanner.nextLine();
	         	}

		    	// System.out.println("Hello, " + username);
		        os.write((username + "\r\n").getBytes());
				os.flush();


				 
				/*
				*
				*Here we need to read from the ChatScreen instead of the Scanner!
				*
				*/
				message = scanner.nextLine();
				while(message.length() > 1){
				os.write(message.getBytes());
				os.flush();
				}
				

				// String line;
				// line = fromServer.readLine();
				// System.out.println("server: " + line);
		
			}
			

			
		} catch (java.io.IOException ioe) {
			String line = "unknown host";
			System.out.println(line);
			System.err.println(ioe);
		}
		finally {
			// let's close streams and sockets
			// closing the input stream closes the socket as well
			// if (fromServer!= null)
			// 	fromServer.close();
			if (server != null)
				server.close();
		}
	}
}

