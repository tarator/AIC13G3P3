package at.ac.tuwien.infosys.aic13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.infosys.cloudscale.annotations.CloudScaleShutdown;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	public static void main(String[] args){
		Main main = new Main();
		
		
		final Server server = new Server( 8080 );
		WebAppContext root = new WebAppContext();
		root.setWar("../../target/G3P3-0.0.1-SNAPSHOT.war");
		root.setContextPath("/G3P3");
		server.setHandler(root);
		server.setStopAtShutdown(true);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("Server started...");
			
		
		logger.info("Starting server loop..");
		main.runServerLoop();
		logger.info("Stopped server loop... shutting down...");
		
		try {
			server.setGracefulShutdown(1);
			server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("Shutdown complete...");
	}
	
	@CloudScaleShutdown
	public void runServerLoop(){
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		try {
			line = bufferRead.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while(!"end".equalsIgnoreCase(line)){
			try {
				line = bufferRead.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Type \"end\" in console to stop server.");
		}
	}
	
}
