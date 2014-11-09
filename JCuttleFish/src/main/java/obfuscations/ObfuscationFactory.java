package obfuscations;

import obfuscations.accessmodification.AccessModificationManager;

public class ObfuscationFactory {

	public static IObfuscation buildObfuscation(SupportedObfuscations type) {
		IObfuscation obfuscation = null;

		switch (type) {

		case ACCESS_MODIFICATION_PUBLIC:
			obfuscation = new AccessModificationManager();
			break;

		default:
			break;

		}
		return obfuscation;
	}
}
