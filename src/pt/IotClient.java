package pt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.*;
import java.util.*;
import org.json.simple.*;  

public class IotClient extends MqttClient {
	public static ArrayList<String[]> WeatherMessage ;
	public int Id;
	public int AccessCount=0;
	
	public SimpleMqttCallback callBack;
	public KeyInventory keyInventory;
	
	public IotClient(String serverURI, String clientId) throws MqttException {
		super(serverURI, clientId, new MemoryPersistence());	
		keyInventory = new KeyInventory(this);
		
	}
	
	public boolean HasKeyFor(String clientId) {
		return keyInventory.HasKeysFor(clientId);
	}

	// Message Generation	
	public static void CreateMessage() {
		String row;
		String PathToCsv="E:\\Projects\\Eclipse\\IOT\\bin\\WeatherData.csv";
		WeatherMessage = new ArrayList<String[]>();
		BufferedReader csvReader;
		try {
			csvReader = new BufferedReader(new FileReader(PathToCsv));
			
			while ((row = csvReader.readLine()) != null) {
				//System.out.println(row);
				String[] data = row.split(";");
				//System.out.println(data[0]);
				if(data!=null) WeatherMessage.add(data);
				// do something with the data
				//	
			}
			csvReader.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// Message Accessing
	int subscribersCount;
	String Message;
	public void SendTo(int subscribersCount) {
		this.subscribersCount = subscribersCount;
		Message= WeatherMessage.get(AccessCount)[Id+5];
		AccessCount++;
		//System.out.println(Message);
	}
	
	public void SendMessage(String clientId) {
		try {
			CipherManager cm = GetCipherManager(clientId);
			String encryptedMessage = cm.Encryption(Message);
        	
        	JSONObject jobj = new JSONObject();
        	jobj.put("SenderId", getClientId());
        	jobj.put("ReceiverId",clientId);
        	jobj.put("Contains","Message");
        	jobj.put("Message",encryptedMessage);
        	
    		MqttMessage message = new MqttMessage(jobj.toJSONString().getBytes());
       		message.setQos(2);

       		this.publish("Channel\\Message", message);
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void MessageArrived(String message) {
		JSONObject jobj = (JSONObject) JSONValue.parse(message);
		
		if(getClientId().equalsIgnoreCase((String)jobj.get("SenderId"))) return ;
		if(!(getClientId().equalsIgnoreCase((String)jobj.get("ReceiverId")))) return;
		
		if("Key".equalsIgnoreCase((String)jobj.get("Contains"))){
			ReceiveKey((String)jobj.get("SenderId"), Integer.parseInt((String)jobj.get("Key")));
		}
		else {
			CipherManager  cm = GetCipherManager((String)jobj.get("SenderId"));
			//Message Decryption		
			System.out.println("Message received by "+getClientId() + " from " + jobj.get("SenderId"));
			cm.Decryption((String)jobj.get("Message"))
			//System.out.println(cm.Decryption((String)jobj.get("Message")));
		}
	}
	
	public CipherManager GetCipherManager(String clientId) {
		try {
		ArrayList<Integer> keys = keyInventory.GetKeysFor(clientId);
		CipherManager cm = new CipherManager(CipherType.fromInteger(keys.get(0)%3));
		cm.SetKey1(keys.get(1));
		cm.SetKey2(keys.get(2));
		return cm;
		}catch(Exception e) {
			e.printStackTrace();
			CipherManager cm = new CipherManager(CipherType.Affine);
			cm.SetKey1(5);
			cm.SetKey2(5);
			return cm;
		}
	}
	
	//Diffie-Hellman Key Exchange Algorithm
	private int p =5, g = 23;
	private int privateKey, myGeneratedKey ,otherPersonGeneratedKey;

	//randomly generate private key
	//and generate my key
	public void GeneratePrivateKey() {
		Random rand = new Random();
		privateKey = rand.nextInt(1000);
		myGeneratedKey = MyModPow(p,privateKey,g);
	}
	
	
	public void TransferOwnGeneratedKey(String clientId) {
		try {
			
	   		JSONObject jobj = new JSONObject();
    
	   		jobj.put("SenderId", getClientId());
        	jobj.put("ReceiverId",clientId);
        	jobj.put("Contains","Key");
        	jobj.put("Key",""+myGeneratedKey);
        	
        	MqttMessage keyMessage = new MqttMessage(jobj.toJSONString().getBytes());
	   		keyMessage.setQos(2);
	   		
	   		String Topic ="Channel\\Keys"; 
	   		
	   		this.publish(Topic, keyMessage);
	   		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void ReceiveKey(String clientId, int key) {
		keyInventory.AddKey(clientId, MyModPow(key,privateKey,g));
	
	}
	
		
	public int MyModPow(int base, int power, int mod) {
		try {
			BigInteger ans = new BigInteger(base+"");
			ans = ans.modPow(new BigInteger(power+""), new BigInteger(mod+""));
		
			return Integer.parseInt(ans.toString());
		}catch(Exception e) {
			e.printStackTrace();
			return 1;
		}
	}
	
}
