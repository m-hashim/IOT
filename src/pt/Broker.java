package pt;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.*;
class ClientInfo {
	IotClient client;
	String topic;
	public ClientInfo() {}
	public ClientInfo(IotClient client,String topic) {
		this.client = client;
		this.topic = topic;
	}
} 

public class Broker {
	public static Broker Instance;
	public String[] Topics = {"TEMPERATURE",
			"RELATIVE_HUMIDITY",
			"SEA_LVL_PRESSURE",
			"TOTAL_PRECIPITATION",
			"TOTAL_CLOUD_COVER",
			"SUNSHINE_DURATION",
			"SHORTWAVE_RADIATION",
			"WIND_SPEED",
			"WIND_DIRECTION",
			"WIND_GUST"};
	
	ArrayList<ClientInfo> Subscribers;
	
 	String iotBroker = "tcp://iot.eclipse.org:1883";
    String localBroker = "tcp://localhost:1883";
	String broker       = localBroker;
	
	public Broker() {
		try {
			Subscribers = new ArrayList<ClientInfo>();
			MqttClient dummy = new MqttClient(broker,"dummy",new MemoryPersistence());
			MqttConnectOptions connOpts = new MqttConnectOptions();
		    connOpts.setCleanSession(true);
			dummy.connect(connOpts);
			for(String topic : Topics)	dummy.subscribe(topic);
		} catch (MqttException e) {
			e.printStackTrace();
		} 
	}
	
	public void RegisterSubscriber(IotClient client, String topic) {
		try {
			Subscribers.add(new ClientInfo(client,topic));
			client.subscribe(topic);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
	}
	
	public ArrayList<ClientInfo> SubscribersByTopic(String topic){
		ArrayList<ClientInfo> result = new ArrayList<ClientInfo>();
		
		for(ClientInfo cl : Subscribers ) {
			if(cl.topic.equals(topic)) {
				result.add(cl);
			}
		}
		return result;
	}
	
	/*
	public String GetRandomTopic() {
		try {
			return Topics[new Random().nextInt(Topics.length)];
		}catch(Exception e) {
			e.printStackTrace();
			return Topics[0];
		}
	}
	*/
	
	
	public void CheckForKeys(IotClient sender, IotClient receiver) {
		if(!(sender.HasKeyFor(receiver.getClientId())&&receiver.HasKeyFor(sender.getClientId()))) {
			//First key is cipher-technique
			CreateNewKey(sender,receiver);
					
			//Second key is cipher-key
			CreateNewKey(sender,receiver);
		
			//third key is another cipher-key
			CreateNewKey(sender,receiver);
			
		}
	}
	
	public void CreateNewKey(IotClient sender, IotClient receiver) {
		try {
			sender.GeneratePrivateKey();
			receiver.GeneratePrivateKey();

			receiver.TransferOwnGeneratedKey(sender.getClientId());
			sender.TransferOwnGeneratedKey(receiver.getClientId());	
	
			Thread.currentThread().sleep(10);
		}catch (InterruptedException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}


