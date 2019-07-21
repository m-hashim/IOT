package pt;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PublishingThread extends Thread {
	int duration = 30;
	int qos = 2;
	MqttClient client ;
	int id;
	public PublishingThread(MqttClient client,int id) {
		this.client = client;
		this.id = id;
		start();
	}
	
	@Override
	public void run() {
		
		try {
			for(int i=0;i<duration/(id*3);i++) {
				PubSub.msgNumber++;
				String content = "Message from "+client.getClientId()+" with MsgNo: " +PubSub.msgNumber;
				MqttMessage message = new MqttMessage(CaesarCipher.encrypt(content,2).toString().getBytes());
				
		        message.setQos(qos);
		        client.publish("Topic"+"\\"+(id), message);
		        
				Thread.sleep(id*1000);
			}		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	 
}
