package pt;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import java.util.Random;


public class IotNetwork {

	public static int MessageLength = 10000;
	public static int SimulationDuration = 30;
	
	public static void main(String[] args) 
	{
		
		
		
		int noOfSub=1, noOfPub=10;
	    float connectionPercent = 30f;
		
	    
	    
	    
	    
	    connectionPercent/=100f;

	    Broker.Instance = new Broker();
	    CipherManager.Instance = new CipherManager(CipherType.RailFence);
	    
	    
	    MqttClient[] Subscribers = new MqttClient[noOfSub];
	    MqttClient[] Publishers = new MqttClient[noOfPub];
	    
	    
	    try {
	        System.out.println("Connecting to broker");
	        
	    	for(int i=0;i<noOfPub;i++) {
	    		Publishers[i]= CreateClient(ClientType.Publisher,i+1);
	    	}
	        
	    	Random rand = new Random();        
	    	
	    	for(int i=0;i<noOfSub;i++) {
	    		Subscribers[i]= CreateClient(ClientType.Subscriber,i+1);
	    	
	    		for(String topic : Broker.Instance.Topics) {
	    			if(rand.nextFloat()<=connectionPercent) {
	    				//Subscribers[i].subscribe(topic);
	    				Broker.Instance.RegisterSubscriber(Subscribers[i], topic);
	    			}
	    		}
	    	}
	    	
	    	
	    	System.out.println("Connected");
	        
	    	PublishingThread Pt[] = new PublishingThread[noOfPub];
	        for(int i=0;i<noOfPub;i++) {
	    		Pt[i] = new PublishingThread(Publishers[i],i+1);
	    		
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

	    	System.out.println("Total Encryption Time:"+CipherManager.Instance.totalEncryptionTime);
	    	System.out.println("Total Decryption Time:"+CipherManager.Instance.totalDecryptionTime);
	    	
	    	//Not giving required values
	    	System.out.println("Total Encryption Memory:"+CipherManager.Instance.totalEncryptionMemory);
	    	System.out.println("Total Decryption Memory:"+CipherManager.Instance.totalDecryptionMemory);
	    	
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

	public static MqttClient CreateClient(ClientType ct, int id) throws MqttException {
		String broker = Broker.Instance.broker;
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

