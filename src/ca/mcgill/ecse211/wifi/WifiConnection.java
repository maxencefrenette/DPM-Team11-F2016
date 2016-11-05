/*
* @author Sean Lawlor
* @date November 3, 2011
* @class ECSE 211 - Design Principle and Methods
*
* Modified by F.P. Ferrie
* February 28, 2014
* Changed parameters for W2014 competition
* 
* Modified by Francois OD
* November 11, 2015
* Ported to EV3 and wifi (from NXT and bluetooth)
* Changed parameters for F2015 competition
* 
* Modified by Michael Smith
* November 1, 2016
* Removed LCD printing, added optional debug print statements
*/
package wifi;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/*
 * This class opens a wifi connection, waits for the data
 * and then allows access to the data after closing the wifi socket.
 * 
 * It should be used by calling the constructor which will automatically wait for
 * data without any further user command
 * 
 * Then, once completed, it will allow access to an instance of the Transmission
 * class which has access to all of the data needed
 */
public class WifiConnection {

	public HashMap<String, Integer> StartData;

	public WifiConnection(String serverIP, int teamNumber) throws IOException {
		this(serverIP, teamNumber, true);
	}

	public WifiConnection(String serverIP, int teamNumber, boolean debugPrint) throws IOException {

		// Open connection to the server and data streams
		int port = 2000 + teamNumber; // semi-abritrary port number"
		Socket socketClient = new Socket(serverIP, port);

		DataOutputStream dos = new DataOutputStream(socketClient.getOutputStream());
		DataInputStream dis = new DataInputStream(socketClient.getInputStream());

		if (debugPrint) {
			System.out.println("Connected\nWaiting for data");
		}

		// Wait for the server transmission to arrive
		while (dis.available() <= 0)
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
			}

		// Parse transmission
		this.StartData = ParseTransmission.parseData(dis);

		if (debugPrint) {
			System.out.println("Data received");
		}

		// End the wifi connection
		dis.close();
		dos.close();
		socketClient.close();

	}

}
