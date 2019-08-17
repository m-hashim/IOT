package pt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.math.*;
import java.util.*;
import org.json.simple.*;  

public class IotClient extends MqttClient {
	
	public String ChannelId;
	public SimpleMqttCallback callBack;
	public KeyInventory keyInventory;
	public IotClient(String serverURI, String clientId) throws MqttException {
		super(serverURI, clientId, new MemoryPersistence());	
		keyInventory = new KeyInventory(this);
	}
	
	public boolean HasKeyFor(String clientId) {
		return keyInventory.HasKeysFor(clientId);
	}
	
	public void SendMessage(String clientId) {
		try {
			Thread.currentThread().sleep(200);
        	CipherManager cm = GetCipherManager(clientId);
        	String encryptedMessage = cm.Encryption(CreateContent());
        	
        	JSONObject jobj = new JSONObject();
        	jobj.put("ClientId", getClientId());
        	jobj.put("Contains","Message");
        	jobj.put("Message",encryptedMessage);
        	
    		MqttMessage message = new MqttMessage(jobj.toJSONString().getBytes());
       		message.setQos(2);

       		this.publish("Channel"+"\\"+(ChannelId)+"\\"+"Message", message);
        	/*MqttMessage keyMessage = new MqttMessage(("Keys10").getBytes());
       		message.setQos(2);
        	this.publish("Channel-"+ChannelId+"-Keys-From"+this.getClientId(), keyMessage);
        	 */
		} catch (Exception e) {
			System.out.println(this.getClientId());
			e.printStackTrace();
		}
        
	}
	
	public void WaitForMessage() {
		try {
			this.subscribe("Channel"+"\\"+(ChannelId)+"\\"+"Message");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void MessageArrived(String message) {
		JSONObject jobj = (JSONObject) JSONValue.parse(message);
		if(getClientId().equalsIgnoreCase((String)jobj.get("ClientId"))) return ;
		
		if("Key".equalsIgnoreCase( (String)jobj.get("Contains"))){
			System.out.println(getClientId()+" "+(String)jobj.get("ClientId"));
			ReceiveKey((String)jobj.get("ClientId"), Integer.parseInt((String)jobj.get("Key")));
			
		}
		else {
			System.out.println("Message received by "+getClientId() + "from" + jobj.get("ClientId"));
			CipherManager  cm = GetCipherManager((String)jobj.get("ClientId"));
			//Message Decryption
			System.out.println("\t"+cm.Decryption((String)jobj.get("Message")));
			cm.Decryption((String)jobj.get("Message"));
		}
	}
	
	public CipherManager GetCipherManager(String clientId) {
		
		ArrayList<Integer> keys = keyInventory.GetKeysFor(clientId);
		System.out.println("keys check for "+clientId+" ---- "+keyInventory.HasKeysFor(clientId) );
		CipherManager cm = new CipherManager(CipherType.fromInteger(keys.get(0)%3));
		cm.SetKey1(keys.get(1));
		cm.SetKey2(keys.get(2));
		return cm;
	}
	
		//Below this : Diffie-Hellman Key Exchange Algorithm
	private int p =5, g = 23;

	//private key is randomly generated key
	//otherPersonGeneratedKey is key given by other person
	//myGeneratedKey is key generated by me
	
	private int privateKey, myGeneratedKey ,otherPersonGeneratedKey;

	
	//randomly generate private key
	//and generate my key
	public void GeneratePrivateKey() {
		Random rand = new Random();
		privateKey = rand.nextInt(1000);
		myGeneratedKey = MyModPow(p,privateKey,g);
	}
	
	
	public void TransferOwnGeneratedKey() {
		try {
			
	   		JSONObject jobj = new JSONObject();
        	jobj.put("ClientId", getClientId());
        	jobj.put("Contains","Key");
        	jobj.put("Key",""+myGeneratedKey);
        	
        	MqttMessage keyMessage = new MqttMessage(jobj.toJSONString().getBytes());
	   		keyMessage.setQos(2);
	   		
	   		String Topic ="Channel\\"+ChannelId+"\\Keys"; 
	   		
	   		this.publish(Topic, keyMessage);
	   		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void WaitForOtherGeneratedKey(String id) {
		try {
			String Topic ="Channel\\"+ChannelId+"\\Keys"; 
	   		this.subscribe(Topic);
	    } catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void ReceiveKey(String clientId, int key) {
		otherPersonGeneratedKey = key;
		keyInventory.AddKey(clientId, MyModPow(otherPersonGeneratedKey,privateKey,g));
	
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
	// Below this: Message Generation	
	String sample = "abcde";//"The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog";

	public String CreateContent() {
		
		String result = new String();
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<=IotNetwork.MessageLength/sample.length();i++) {
			sb.append(sample);
		}
		result =sb.toString();
		return result;
	}


}

