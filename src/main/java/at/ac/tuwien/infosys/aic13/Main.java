package at.ac.tuwien.infosys.aic13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.infosys.aic13.cloudscale.service.SentimentAnalysisService;
import at.ac.tuwien.infosys.aic13.cloudscale.service.impl.SentimentAnalysisServiceCloudScaleImpl;
import at.ac.tuwien.infosys.aic13.cloudscale.service.impl.SentimentAnalysisServiceDummyImpl;
import at.ac.tuwien.infosys.cloudscale.annotations.CloudScaleShutdown;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);
	private Properties properties;
	public static void main(String[] args){
		Main main = new Main();
		main.runServerLoop();
	}
	
	private Properties getProperties(){
		if(properties == null){
			properties = new Properties();
			InputStream in = getClass().getClassLoader().getResourceAsStream("aic13g3p3.properties");
			try {
				properties.load(in);
				in.close();
			} catch (IOException e) {
				logger.error("Error while loading file 'aic13g3p3.properties' (6FNHAP).", e);
			}
		}
		return properties;
	}
	
	@CloudScaleShutdown
	public void runServerLoop(){
		SentimentAnalysisService service = createAndRunSentimentAnalysisService();
		
		//This runs in an endless loop until "end" is typed into the java-console.
		runConsoleReader();
		
		service.stopService();
		
	}

	private void runConsoleReader(){
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		try {
			line = bufferRead.readLine();
		} catch (IOException e) {
			logger.error("Error while reading from console (E8J353).", e);
		}
		while(!"end".equalsIgnoreCase(line)){
			try {
				line = bufferRead.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			logger.info("Type \"end\" in console to stop CloudScale server (JS8340).");
		}
	}
	
	public SentimentAnalysisService createAndRunSentimentAnalysisService(){
		String className = (String) getProperties().get("g3p3.sentimentAnalysisServiceClass");
		SentimentAnalysisService service = null;
		if(className != null){
			logger.info("Loading SentimentServiceClass: "+className);
			try {
				@SuppressWarnings("unchecked")
				Class<SentimentAnalysisService> c = (Class<SentimentAnalysisService>) Class.forName(className);
				service = c.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				logger.error("Failed to create SentimentAnalysisServiceClass from property '"+className+"' Defaulting to Dummy-Service (PKSZNR).", e);
			}
		}
		if(service == null){
			logger.warn("Defaulting to Dummy-SentimanetAnalysisService (A1FF8Z).");
			service = new SentimentAnalysisServiceDummyImpl();
		}
		Thread t = new Thread(service);
		t.start();
		return service;
	}
	
}
