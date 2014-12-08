import static org.junit.Assert.*;
import obfuscations.IObfuscation;
import obfuscations.ObfuscationFactory;
import obfuscations.abstractinterfacemodification.AbstractInterfaceModificationManager;
import obfuscations.accessmodification.AccessModificationManager;
import obfuscations.enums.SupportedObfuscations;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//Creates 2 objects and tests if they are of the same type.
//One object is created using the factory while the other one is created using "new".
//The 2 objects are compared, using reflection(.getClass()), to determine if they are of the same type or not.
public class ObfuscationFactoryTest {

	IObfuscation obfFromFactory;
	IObfuscation obfFromNew;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		obfFromFactory = null;
		obfFromNew = null;
	}

	@Test
	public void AccessModificationPublicTest() {
		obfFromNew = new AccessModificationManager();
		assertNotNull(obfFromNew);
		
		obfFromFactory = ObfuscationFactory.buildObfuscation(SupportedObfuscations.ACCESS_MODIFICATION_PUBLIC);
		assertNotNull(obfFromFactory);
	
		assertEquals(obfFromNew.getClass(), obfFromFactory.getClass());
	}
	
	@Test
	public void AbstractInterfaceModificationTest() {
		obfFromNew = new AbstractInterfaceModificationManager();
		assertNotNull(obfFromNew);
		
		obfFromFactory = ObfuscationFactory.buildObfuscation(SupportedObfuscations.ABSTRACT_INTERFACE_MODIFICATION);
		assertNotNull(obfFromFactory);
	
		assertEquals(obfFromNew.getClass(), obfFromFactory.getClass());
	}
}
