package pt;
import java.util.ArrayList;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PublishingThread extends Thread {
	int duration = IotNetwork.SimulationDuration;
	int qos = 2;
	IotClient client ;
	int id;
	public PublishingThread(IotClient client,int id) {
		this.client = client;
		this.id = id;
		start();
	}
	
	@Override
	public void run() {
		
		try {
			for(int i=0;i<duration;i++) {
				
				String Topic = Broker.Instance.GetRandomTopic();
				
				ArrayList<ClientInfo> subscribers = Broker.Instance.SubscribersByTopic(Topic);
			
				PersonalConnection pc;
				for(ClientInfo cl : subscribers) {
					pc = new PersonalConnection(client,cl.client);
				}
				
				
				
				Thread.sleep(1000);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	 
}
