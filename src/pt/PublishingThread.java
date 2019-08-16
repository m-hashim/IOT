package pt;
import java.util.ArrayList;
import java.util.Random;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PublishingThread extends Thread {
	int duration = IotNetwork.SimulationDuration;
	int qos = 2;
	MqttClient client ;
	int id;
	public PublishingThread(MqttClient client,int id) {
		this.client = client;
		this.id = id;
		usedContent = CreateContent();
		start();
	}
	
	@Override
	public void run() {
		
		try {
			for(int i=0;i<duration;i++) {
				String content = usedContent;
				
				MqttMessage message = new MqttMessage(CipherManager.Instance.Encryption(content).getBytes());
				String Topic = Broker.Instance.GetRandomTopic();
				
				ArrayList<ClientInfo> subscribers = Broker.Instance.SubscribersByTopic(Topic);
				
				for(ClientInfo cl : subscribers) {
					
				}
				
				/*
				message.setQos(qos);
		        client.publish("Topic"+"\\"+(id), message);
		        */
				
				
				Thread.sleep(1000);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	String usedContent;
	String sample = "The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog The quick brown fox jumps over the lazy dog";

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
