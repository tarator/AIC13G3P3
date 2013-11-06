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
		main.runServerLoop();
		logger.info("Stopped server loop... shutting down...");
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
