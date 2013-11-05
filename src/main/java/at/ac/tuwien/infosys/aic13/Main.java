package at.ac.tuwien.infosys.aic13;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

import at.ac.tuwien.infosys.cloudscale.annotations.CloudScaleShutdown;

public class Main {

	@CloudScaleShutdown
	public static void main(String[] args){

		Server server = new Server( 8080 );
		WebAppContext root = new WebAppContext();
		root.setWar("../../target/G3P3-0.0.1-SNAPSHOT.war");
		root.setContextPath("/G3P3");
		server.setHandler(root);
		server.setStopAtShutdown(true);
		try {
			server.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
