package ca.mcgill.ecse211.team11;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * 
 * The Logger class's purpose is to log any data
 * passed to it into a file to be later used for
 * debugging purposes. All methods and 
 * 
 * @author	Justin Szeto
 * @version	1.0
 * @since	1.0
 *
 */

public class Logger {

	private static PrintStream writer = System.out;
	private static long startTime = 0;
	
	/**
	 * Log the time and the data passed as parameter.
	 * <p>
	 * Calculates the current time relative to the start time 
	 * (if it has been set) and prints the current time and the 
	 * data passed as message to file or to System.out. 
	 * 
	 * @param message
	 */
	public static void logData(String message) {
		
		long currentTime = System.currentTimeMillis()-startTime;
		writer.println("Time in ms: " + currentTime + ", " + message);
	}
	
	/**
	 * Set the logger to write to a file.
	 * <p>
	 * When this method is called, it will create a new file with the name filename 
	 * and set the logger to write data into the new file. By default, if this method 
	 * is not called, then the logger will print out data to System.out.
	 * 
	 * @param filename name of the file to print log data into
	 * @throws FileNotFoundException
	 */
	public static void setLogWriter(String filename) throws FileNotFoundException {
		
		writer = new PrintStream(new File(filename));
	}
	
	/**
	 * Store the time when logging starts.
	 * <p>
	 * This method can only be used once, preferably when the program 
	 * starts running. Any subsequent calls after the first one will
	 * not have any effect. If this method is not called, then the
	 * start time is set to 0.
	 */
	public static void setLogStartTime() {
		
		if (startTime == 0) {
			startTime = System.currentTimeMillis();
		}
	}
}
