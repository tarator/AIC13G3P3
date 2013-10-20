package at.ac.tuwien.aic13.g3p3;

import at.ac.tuwien.infosys.cloudscale.annotations.CloudObject;
import at.ac.tuwien.infosys.cloudscale.annotations.DestructCloudObject;

@CloudObject
public class MyCloudObject {

  public String doThings() {
	  String x = "";
	  for(int i = 97; i<150; i++){
		  x += String.valueOf((char)(i%200));
	  }
	  return x;
  }

  @DestructCloudObject
  public void iAmDone() { 

  }

}
