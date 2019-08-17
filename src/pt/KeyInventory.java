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
	public void PrintKeys(String clientId) {
		String str="";
		ArrayList<Integer> keys = keysInfo.get(clientId);
		
		for(int k : keys) {
			str+=k+"\t";
		}
		System.out.println("Keys by "+client.getClientId()+" for "+clientId+" is "+str);
	}
	public void AddKey(String clientId, int key) {
		
		ArrayList<Integer> keys = keysInfo.get(clientId);
		if(keys==null) {
			keys = new ArrayList<Integer>();
		}
		keys.add(key);
		System.out.println("Num of keys by "+client.getClientId()+" for "+clientId+" is "+keys.size());
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
