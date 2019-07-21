package pt;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.Random;


public class PubSub {

	public static int msgNumber=0;
    
public static void main(String[] args) 
{
	int noOfTopics=5, noOfSub=5, noOfPub=5;
    float subPercent = 0.5f;
	int qos             = 2;
    String iotBroker = "tcp://iot.eclipse.org:1883";
    String localBroker = "tcp://localhost:1883";
	String broker       = localBroker;
    MqttClient[] Subscribers = new MqttClient[noOfSub];
    MqttClient[] Publishers = new MqttClient[noOfPub];
    
    try {
        System.out.println("Connecting to broker: "+broker);
        Random rand = new Random();
        
    	for(int i=0;i<noOfPub;i++) {
    		Publishers[i]= CreateClient(ClientType.Publisher,i+1,broker);
    	}
    	
    	for(int i=0;i<noOfSub;i++) {
    		Subscribers[i]= CreateClient(ClientType.Subscriber,i+1,broker);
    		for(int j=0;j<noOfTopics;j++) {
    			if(rand.nextFloat()<=subPercent) {
    				Subscribers[i].subscribe("Topic\\"+(j+1));
    			}
    		}
    	}
    	System.out.println("Connected");
        
    	for(int i=0;i<noOfPub;i++) {
    		PublishingThread Pt = new PublishingThread(Publishers[i],i+1);
    	}
    	try {
    	Thread.sleep(12000);
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	System.out.println("Message published");
        for(int i=0;i<noOfPub;i++)	Publishers[i].disconnect();
    	for(int i=0;i<noOfSub;i++) Subscribers[i].disconnect();
    	
    	
        System.out.println("Disconnected");
        System.exit(0);
        
    } catch(MqttException me) {
        System.out.println("reason "+me.getReasonCode());
        System.out.println("msg "+me.getMessage());
        System.out.println("loc "+me.getLocalizedMessage());
        System.out.println("cause "+me.getCause());
        System.out.println("excep "+me);
        me.printStackTrace();
    }
}

public static MqttClient CreateClient(ClientType ct, int id, String broker) throws MqttException {
	MqttClient client;
	MqttConnectOptions connOpts = new MqttConnectOptions();
    connOpts.setCleanSession(true);
    
	if(ct == ClientType.Publisher) {
		
		client = new MqttClient(broker,"Pub"+id,new MemoryPersistence());
		
	}else {
		client = new MqttClient(broker,"Sub"+id,new MemoryPersistence());
		client.setCallback(new SimpleMqttCallback(client.getClientId()));
	}
	client.connect(connOpts);
    return client;
}


}

