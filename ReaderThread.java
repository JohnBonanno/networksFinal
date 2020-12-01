/**
 * This thread is passed a socket that it reads from. Whenever it gets input
 * it writes it to the ChatScreen text area using the displayMessage() method.
 */

import java.io.*;
import java.net.*;
import javax.swing.*;

public class ReaderThread implements Runnable
{
	Socket server;
	BufferedReader fromServer;
	ChatScreen screen;

	public ReaderThread(Socket server, ChatScreen screen) {
		this.server = server;
		this.screen = screen;
	}

	public void run() {
		try {
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));
			//pass in the stripped message
			while (true) {
				String message = fromServer.readLine();
				//parse for message
				//printing message

				//message = ;
				if (message != null)
				screen.displayMessage(message);
				// now display it on the display area
				
			}
		}
		catch (IOException ioe) { System.out.println(ioe); }

    }
    
}