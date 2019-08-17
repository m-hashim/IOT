package pt;
import java.util.*;

public class KeyInventory {
	Map<String,ArrayList<Integer>> keysInfo;
	IotClient client;
	public KeyInventory() {
		keysInfo = new HashMap<String,ArrayList<Integer>>();
	}
	
	public KeyInventory(IotClient client) {
		this();
		this.client = client;
		
	}
	public void AddKey(String clientId, int key) {
		
		ArrayList<Integer> keys = keysInfo.get(clientId);
		if(keys==null) {
			keys = new ArrayList<Integer>();
		}
		keys.add(key);	
		keysInfo.put(clientId, keys);
		
	}
	public boolean HasKeysFor(String clientId) {
		return GetKeysFor(clientId)!=null ? true:false;
	}
	
	public void ClearKeys(String clientId) {
		keysInfo.put(clientId, new ArrayList<Integer>());
	}
	
	public ArrayList<Integer> GetKeysFor(String clientId){
		return keysInfo.get(clientId);	
	}	
	
}
