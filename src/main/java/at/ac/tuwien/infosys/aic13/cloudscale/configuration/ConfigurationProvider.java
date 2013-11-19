package at.ac.tuwien.infosys.aic13.cloudscale.configuration;

import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.infosys.aic13.cloudscale.events.TestEvent;
import at.ac.tuwien.infosys.cloudscale.annotations.CloudScaleConfigurationProvider;
import at.ac.tuwien.infosys.cloudscale.configuration.CloudScaleConfiguration;
import at.ac.tuwien.infosys.cloudscale.configuration.CloudScaleConfigurationBuilder;

/**
 * 
 * @author e0756024 <e0756024@student.tuwien.ac.at>
 */
public class ConfigurationProvider {
    
    private static final Logger log = LoggerFactory.getLogger(ConfigurationProvider.class);

    @CloudScaleConfigurationProvider
    public static CloudScaleConfiguration getConfiguration() {
	log.info("-------------------------------------------------------------");
	log.info("returning new local config");
	return localConfig();
    }

    /**
     * @return config for local testing
     */
    private static CloudScaleConfiguration localConfig() {

	CloudScaleConfiguration cfg = CloudScaleConfigurationBuilder
		.createLocalConfigurationBuilder()
		.withGlobalLoggingLevel(Level.INFO) 
		.with(new ScalingPolicy())
		.withMonitoring(true)
		.withMonitoringEvents(TestEvent.class)
		.build();
	
	//TODO e0756024: check every x time units? 
	cfg.common().setScaleDownIntervalInSec(60);

	return cfg;

    }
    
    /**
     * @return config for Amazons EC2
     */
    @SuppressWarnings("unused")
    private static CloudScaleConfiguration remoteConfig() {
	throw new UnsupportedOperationException("Not Yet Implemented!");
	
//	IScalingPolicy scalingPolicy = new ScalingPolicy();
//	Level defaultLoggingLevel = Level.ALL;
//	String mqServerAddress = "";
//	int mqServerPort = 0;
//	boolean isMonitoringEnabled = true;
//	String identityPublicURL = "";
//	String login = "";
//	String password = "";
//	String tenantName = "";
//	String imageName = "";
//	String identityUrl = "";
//
//	CloudScaleConfiguration cfg = CloudScaleConfigurationBuilder
//		.createOpenStackConfigurationBuilder(
//			scalingPolicy, 
//			defaultLoggingLevel, 
//			mqServerAddress, 
//			mqServerPort, 
//			identityPublicURL, 
//			tenantName, 
//			imageName, 
//			login, 
//			password)
//		.build();
//	
//	//TODO e0756024: check every x time units? 
//	cfg.common().setScaleDownIntervalInSec(60);
//		
//	return cfg;

    }
}
