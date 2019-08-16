package pt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber extends MqttClient {
	public Subscriber(String Broker, String Id) throws MqttException {
		super(Broker,Id);
	}
}
