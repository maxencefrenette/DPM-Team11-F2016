package ca.mcgill.ecse211.team11;

import java.io.IOException;
import java.util.HashMap;

import ca.mcgill.ecse211.wifi.WifiConnection;

/**
 * Connects to competition server and retrieve data
 * 
 * @author Justin Szeto
 * @version 1.0
 * @since 1.0
 */
public class WifiClient {

  private static WifiConnection conn;
  
  /**
   * 
   * @return true if successful connection to server. false if connection failed
   */
  public static boolean connectToServer() {
    boolean isConnected;
    
    try {
      Logger.logData("Wifi: Connecting...");
      conn = new WifiConnection(Constants.SERVER_IP, Constants.TEAM_NUMBER);
      isConnected = true;
    } catch (IOException e) {
      Logger.logData("Wifi: Connection failed");
      isConnected = false;
    }
    return isConnected;
  }
  
  /**
   * Retrieves data from the the server
   * <p>
   * The data is returned in a HashMap of String keys and Integer values.
   * The keys and their meaning are as followed:
   * Key: "BTN", Meaning: Builder team number
   * Key: "BSC", Meaning: Builder team start corner
   * Key: "CTN", Meaning: Garbage collector team number
   * Key: "CSC", Meaning: Garbage collector team start corner
   * Keys: "LRZx", "LRZy", Meaning: Red zone lower left corner x and y coordinates in tiles
   * Keys: "URZx", "URZy", Meaning: Red zone upper right corner x and y coordinates in tiles
   * Keys: "LGZx", "LGZy", Meaning: Green zone lower left corner x and y coordinates in tiles
   * Keys: "UGZx", "UGZy", Meaning: Green zone upper right corner x and y coordinates in tiles
   * <p>
   * Refer to project description pdf for more information about the values.
   * 
   * @return HashMap with String keys and Integer values retrieved from server. null if no data.
   */
  public static HashMap<String, Integer> retrieveDataFromServer() {
    HashMap<String, Integer> data = null;
    
    if (conn != null) {
      data = conn.StartData;
      
      if (data == null) {
        Logger.logData("Wifi: Failed to receive transmission");
      } else {
        Logger.logData("Wifi: Transmission data received");
      }
      
    } else {
      Logger.logData("Wifi: No connection established. Cannot retrieve data");
    }
    
    return data;
  }
  
}
