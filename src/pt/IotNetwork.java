package pt;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.Random;


public class IotNetwork {

	public static int SimulationDuration = 10;//360;
	
	public static void main(String[] args) 
	{
		
		int noOfSub=10, noOfPub=2;
	    float connectionPercent = 70f;
	
	    connectionPercent/=100f;

	    Broker.Instance = new Broker();
	    IotClient.CreateMessage();
	    
	    
	    IotClient[] Subscribers = new IotClient[noOfSub];
	    IotClient[] Publishers = new IotClient[noOfPub];
	    
	    try {
	        System.out.println("Connecting to broker");
	        Random rand = new Random();       
	    	for(int i=0;i<noOfPub;i++) {
	    		
	    		Publishers[i]= CreateClient(ClientType.Publisher,i+1);
	    		Broker.Instance.Publishers.add(Publishers[i]);
	    		for(String topic: Broker.Instance.topicName) {
	    			if(rand.nextFloat()<=connectionPercent) {
	    				Broker.Instance.RegisterPublisher(Publishers[i], topic);
	    			}
	    		}
	    	}
	        
	    	for(int i=0;i<noOfSub;i++) {
	    		Subscribers[i]= CreateClient(ClientType.Subscriber,i+1);
	    	
	    		for(String topic: Broker.Instance.topicName) {
	    			if(rand.nextFloat()<=connectionPercent) {
	    				Broker.Instance.RegisterSubscriber(Subscribers[i], topic);
	    			}
	    		}
	    	}
	    	
	    	
	    	System.out.println("Connected");
	 
	    	for(int i=0;i<noOfPub;i++) {
	    		for(int j=0;j<noOfSub;j++) {
	    			Broker.Instance.CheckForKeys(Subscribers[j],Publishers[i]);
	    		}
	    	}
	    	
	    	PublishingThread Pt[] = new PublishingThread[noOfPub];
	        for(int i=0;i<noOfPub;i++) {
	    		Pt[i] = new PublishingThread(Publishers[i]);
	    	}
	        
	        //Waits for thread to finish
	    	try {
	    		for(int i=0;i<noOfPub;i++) {
	    			Pt[i].join();
	    		}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	    	
	    	for(int i=0;i<noOfPub;i++)	Publishers[i].disconnect();
	    	for(int i=0;i<noOfSub;i++) 	Subscribers[i].disconnect();
	    	
	    	
	    	
	    	System.out.println("Disconnected");
	    	Broker.Instance.PaymentSummary();
	    	
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

	
	public static IotClient CreateClient(ClientType ct, int id) throws MqttException {
		String broker = Broker.Instance.broker;
		IotClient client;
		MqttConnectOptions connOpts = new MqttConnectOptions();
		connOpts.setCleanSession(true);
		//connOpts.setAutomaticReconnect(true);
		//connOpts.setKeepAliveInterval(1);
		if(ct == ClientType.Publisher) {
			client = new IotClient(broker,"Pub"+id);
			client.Id = id-1;
		}else {
			client = new IotClient(broker,"Sub"+id);
		}
		client.callBack = new SimpleMqttCallback(client);
		client.setCallback(client.callBack);
		
		client.connect(connOpts);
		client.subscribe("Channel\\Keys");
		client.subscribe("Channel\\Message");
		return client;
	}

	
}

