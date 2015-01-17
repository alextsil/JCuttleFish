import obfuscations.accessmodification.AccessModificationManager;



public class Main {

	public static void main(String[] args) {
		String userGivenPath = args[0];
		
		AccessModificationManager aModMan = new AccessModificationManager();
		aModMan.setParameters(userGivenPath);
		aModMan.obfuscate();
	}
	
}
