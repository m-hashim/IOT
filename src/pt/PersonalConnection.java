package pt;

import org.eclipse.paho.client.mqttv3.MqttException;

public class PersonalConnection {
	String ChannelId;
	public PersonalConnection() {}
	
	public PersonalConnection(IotClient sender, IotClient receiver) {
		
		ChannelId = sender.getClientId()+"-"+receiver.getClientId();
		receiver.ChannelId = sender.ChannelId = ChannelId;
		
		CheckForKeys(sender,receiver);
		
		while(!(sender.HasKeyFor(receiver.getClientId())&&receiver.HasKeyFor(sender.getClientId()))) {
			try {
				//System.out.println("Keys not transferred ");
				
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		receiver.keyInventory.PrintKeys(sender.getClientId());

		sender.keyInventory.PrintKeys(receiver.getClientId());
		receiver.WaitForMessage();
		sender.SendMessage(receiver.getClientId());
		
	}
	
	public void CheckForKeys(IotClient sender, IotClient receiver) {
		if(sender.HasKeyFor(receiver.getClientId())&&receiver.HasKeyFor(sender.getClientId())) {
			System.out.println("Found Keys");
		}
		else {
			
			//First key is cipher-technique
			CreateNewKey(sender,receiver);
			try {
				
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Second key is cipher-key
			CreateNewKey(sender,receiver);
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//third key is another cipher-key
			CreateNewKey(sender,receiver);
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void CreateNewKey(IotClient sender, IotClient receiver) {
		
		sender.GeneratePrivateKey();
		receiver.GeneratePrivateKey();
			
		receiver.WaitForOtherGeneratedKey(sender.getClientId());
		sender.WaitForOtherGeneratedKey(receiver.getClientId());
	
		receiver.TransferOwnGeneratedKey();
		sender.TransferOwnGeneratedKey();	
		
	}
}
