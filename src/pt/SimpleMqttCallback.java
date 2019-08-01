package pt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SimpleMqttCallback implements MqttCallback {

	String id;
	public SimpleMqttCallback(String id) {
		this.id = id;
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
		String msg = new String(arg1.getPayload());
		System.out.println("Message received by "+id);
		
		//Message Decryption
		CipherManager.Instance.Decryption(msg);
	}

}