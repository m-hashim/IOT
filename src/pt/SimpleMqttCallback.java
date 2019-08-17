package pt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SimpleMqttCallback implements MqttCallback {

	private String id;
	private IotClient client;
	public SimpleMqttCallback(IotClient client) {
		id = client.getClientId();
		this.client = client;
	}
	
	
	@Override
	public void connectionLost(Throwable arg0) {
		System.out.println("Connection lost to broker");
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
	
	}

	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		client.MessageArrived(new String(arg1.getPayload()));
		
	}
	
	

}
