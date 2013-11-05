package at.ac.tuwien.infosys.aic13;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandler;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.webapp.WebAppContext;
import org.mortbay.xml.XmlConfiguration;
import org.springframework.web.context.ContextLoaderListener;

import at.ac.tuwien.infosys.aic13.cloudscale.workers.DummyWorker;
import at.ac.tuwien.infosys.aic13.publicdto.PublicCompany;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQuery;
import at.ac.tuwien.infosys.aic13.publicdto.PublicSentimentQueryResult;
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
