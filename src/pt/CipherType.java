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
			return CipherType.Affine;
		case 2: 
			return CipherType.RailFence;
		default:
			return null;
		}
	}
	
}
