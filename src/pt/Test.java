package pt;

import org.eclipse.paho.client.mqttv3.MqttException;

public class Test {
	public static void main(String args[]) {
		//System.out.println(IotClient.MyModPow(2, 4, 5));
		try {
			Broker.Instance = new Broker();
		    
			IotClient a = new IotClient(Broker.Instance.broker,"1",ClientType.Publisher);
			IotClient b = new IotClient(Broker.Instance.broker,"2",ClientType.Subscriber);
			new PersonalConnection(a,b);
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
	}
}
