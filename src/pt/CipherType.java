package pt;

public enum CipherType {
	Caesar,
	Affine,
	RailFence;
	
	public static CipherType fromInteger(int x) {
		switch(x) {
		case 0: 
			return CipherType.Caesar;
		case 1: 
			return CipherType.RailFence;
		case 2: 
			return CipherType.Affine;
		default:
			return null;
		}
	}
	public static int NoOfCipherTechniques= 2;
	
}
