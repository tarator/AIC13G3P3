package at.ac.tuwien.aic13.g3p3;

import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.infosys.cloudscale.annotations.CloudScaleShutdown;

public class MyRunner {

	@CloudScaleShutdown
	public static void main(String[] args) {
		List<MyCloudObject> cloudObjects = new ArrayList<MyCloudObject>();
		for(int i = 0; i<10; i++){
			MyCloudObject o = new MyCloudObject();
			cloudObjects.add(o);
			System.out.println(o.doThings());
			o.iAmDone();
		}


	}

}
