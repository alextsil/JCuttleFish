import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class Main {
//.

	private final static Logger logger = LoggerFactory.getLogger( Main.class );
	
	public static void main(String[] args) {
		troll();
		logger.error("TEST LOGGER");
//		String userGivenPath = args[0];
//		AccessModificationManager aModMan = new AccessModificationManager();
//		aModMan.setParameters(userGivenPath);
//		aModMan.obfuscate();
	}
	public static void troll()
	{
		logger.error("TEST LOGGER");
	}
	
}
