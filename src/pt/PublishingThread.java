package pt;
import java.util.ArrayList;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PublishingThread extends Thread {
	int duration = IotNetwork.SimulationDuration;
	IotClient client ;

	public PublishingThread(IotClient client) {
		this.client = client;
		//run();
		start();
	}
	
	@Override
	public void run() {
		try {		
			
			for(int i=0;i<duration;i++) {
				String Topic = Broker.Instance.Topics[client.Id]; 
				
				ArrayList<ClientInfo> subscribers = Broker.Instance.SubscribersByTopic(Topic);
				client.SendTo(subscribers.size());
				for(ClientInfo cl : subscribers) {
					client.SendMessage(cl.client.getClientId());
				}
				Thread.sleep(1000);
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	 
}
