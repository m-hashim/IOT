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
	public String[] topicName = {"TEMPERATURE",
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
	ArrayList<IotClient> Publishers; 
	ArrayList<Topic> Topics;
	
 	String iotBroker = "tcp://iot.eclipse.org:1883";
    String localBroker = "tcp://localhost:1883";
	String broker       = localBroker;
	
	public Broker() {
		try {
			
			Subscribers = new ArrayList<ClientInfo>();
			Publishers = new ArrayList<IotClient>();
			Topics = new ArrayList<Topic>();
			MqttClient dummy = new MqttClient(broker,"dummy",new MemoryPersistence());
			MqttConnectOptions connOpts = new MqttConnectOptions();
		    connOpts.setCleanSession(true);
			dummy.connect(connOpts);
			for(String topic : topicName)	{
				dummy.subscribe(topic);
				Topics.add(new Topic(topic,10.0f));
			}
		} catch (MqttException e) {
			e.printStackTrace();
		} 
	}
	public int TopicId(String topicName) {
		for (int i = 0;i<this.topicName.length;i++) {
			if(topicName.equals(this.topicName[i])) {
				return i;
			}
		}
		System.out.println("Error -----------------------------------------------------------"+topicName);
		
		return -1;
	}
	public void RegisterPublisher(IotClient client, String topicName) {
		Topic topic = Topics.get(TopicId(topicName));
	
		topic.messageCount.put(client.getClientId(),0);
		
		client.topics.add(topicName);
		
	}
	public void RegisterSubscriber(IotClient client, String topicName) {
		try {
			Subscribers.add(new ClientInfo(client,topicName));
			
			Topic topic = Topics.get(TopicId(topicName));
			topic.collection+=topic.cost;
			client.topics.add(topicName);
			
			client.subscribe(topicName);
			
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
	
	public void PaymentSummary() {
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println();
		System.out.println("Payment Summary");
		for(Topic topic: Topics) {
			System.out.println(topic.name+"\t\t collects \t\t"+topic.collection+" \twith Total Message\t"+topic.totalMessage);
			
		}
		
		
		for(IotClient publisher: Publishers) {
			int sum =0;
			for(Topic topic: Topics) {
				if(topic.messageCount.containsKey(publisher.getClientId())){
					sum+= ((float)topic.messageCount.get(publisher.getClientId())/topic.totalMessage)*(float)topic.collection;
					System.out.print(publisher.getClientId()+" Publishes "+topic.messageCount.get(publisher.getClientId())+ " of "+ topic.totalMessage);
					System.out.println(" of topic "+ topic.name);
				}
			}	
			System.out.println(publisher.getClientId()+"\t earns \t"+ sum);
		}
	}
}


