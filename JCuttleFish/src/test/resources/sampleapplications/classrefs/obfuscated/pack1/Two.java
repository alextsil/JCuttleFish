package pack1;

import java.util.ArrayList;
import java.util.List;

import pack2.One;

public class Two {
	
	private One one;
	private List<One> ones;

	
	public Two() {
		One newOne = new One();
		List<One> newOnes = new ArrayList<One>();
		newOne.funct1();
		One.class.getClass();
		String static1Str = One.static1;
		One.staticFunct();
		pack2.One.staticFunct();
	}
}
