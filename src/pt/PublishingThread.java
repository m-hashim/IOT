package pt;
import java.util.ArrayList;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PublishingThread extends Thread {
	int duration = IotNetwork.SimulationDuration;
	IotClient client ;
	int accessCount = 0;

	public PublishingThread(IotClient client) {
		this.client = client;
		//run();
		start();
	}
	
	@Override
	public void run() {
		try {		
			
			for(int i=0;i<duration;i++) {
				
				for(String Topic: client.topics) {
					ArrayList<ClientInfo> subscribers = Broker.Instance.SubscribersByTopic(Topic);
					int topicId = Broker.Instance.TopicId(Topic);
					int value = Broker.Instance.Topics.get(topicId).messageCount.get(client.getClientId())+1;
					 
					Broker.Instance.Topics.get(topicId).messageCount.replace(client.getClientId(),value);
					Broker.Instance.Topics.get(topicId).totalMessage+=1;	
					client.SetMessage(topicId, accessCount);
					accessCount++;
					
					for(ClientInfo cl : subscribers) {
						client.SendMessage(cl.client.getClientId());
					}
						
				}
				//String Topic = Broker.Instance.topicName[client.Id]; 
				System.out.println("working....");
				Thread.sleep(1000);
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	 
}
