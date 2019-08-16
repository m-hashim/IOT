package pt;

public class PersonalConnection {
	public PersonalConnection() {}
	public PersonalConnection(IotClient sender, IotClient receiver) {
		sender.ClearKeys();
		receiver.ClearKeys();
		
		//First key is cipher-technique
		CreateNewKey(sender,receiver);
		
		//Second key is cipher-key
		CreateNewKey(sender,receiver);
		
		//third key is another cipher-key
		CreateNewKey(sender,receiver);

		
	}
	
	public void CreateNewKey(IotClient sender, IotClient receiver) {
		sender.SetPrivateKey();
		receiver.SetPrivateKey();
		
		receiver.SetOtherPersonGeneratedKey(sender.GetGeneratedKey());
		sender.SetOtherPersonGeneratedKey(receiver.GetGeneratedKey());
		
		sender.CreateKey();
		receiver.CreateKey();
		
	}
}
