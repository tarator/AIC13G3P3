package at.ac.tuwien.infosys.aic13.cloudscale.configuration;

import java.util.logging.Level;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.infosys.aic13.cloudscale.events.TestEvent;
import at.ac.tuwien.infosys.cloudscale.annotations.CloudScaleConfigurationProvider;
import at.ac.tuwien.infosys.cloudscale.configuration.CloudScaleConfiguration;
import at.ac.tuwien.infosys.cloudscale.configuration.CloudScaleConfigurationBuilder;
import at.ac.tuwien.infosys.cloudscale.vm.ec2.EC2CloudPlatformConfiguration;

/**
 * 
 * @author e0756024 <e0756024@student.tuwien.ac.at>
 */
public class ConfigurationProvider {
    
    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(ConfigurationProvider.class);

    @CloudScaleConfigurationProvider
    public static CloudScaleConfiguration getConfiguration() {
//	return localConfig();
	return getEC2Config();
    }

    /**
     * @return config for local testing
     */
    @SuppressWarnings("unused")
    private static CloudScaleConfiguration localConfig() {
	
	CloudScaleConfiguration cfg = new CloudScaleConfigurationBuilder()
		.withLogging(Level.WARNING) 
		.with(new ScalingPolicyAlternative())
		.withMonitoring(true)
		.withMonitoringEvents(TestEvent.class)
		.build();
	
	//check every 60 seconds
	cfg.common().setScaleDownIntervalInSec(60);

	return cfg;

    }
    
    private static CloudScaleConfiguration getEC2Config() {
	
	final String AWS_CONFIG = "aws.properties";
	final String AWS_ENDPOINT = "ec2.ap-southeast-1.amazonaws.com";
	final String AWS_INSTANCE_TYPE = "t1.micro";
	final String AWS_KEY = "awscs";
	final String AWS_IMAGE_NAME = "CloudScale_v0.3.2 G3P3";
	
	// CHANGE ME
	final String MQ_HOSTNAME = "ec2-54-251-13-247.ap-southeast-1.compute.amazonaws.com";
	final int MQ_PORT = 61616;
	
	EC2CloudPlatformConfiguration ec2Config = new EC2CloudPlatformConfiguration();
	ec2Config.setAwsConfigFile(ClassLoader.getSystemResource(AWS_CONFIG).getPath());
	ec2Config.setAwsEndpoint(AWS_ENDPOINT);
        ec2Config.setInstanceType(AWS_INSTANCE_TYPE);
        ec2Config.setSshKey(AWS_KEY);
        ec2Config.setImageName(AWS_IMAGE_NAME);
        
        CloudScaleConfiguration config = new CloudScaleConfigurationBuilder()
        	.with(ec2Config)
        	.with(new ScalingPolicyAlternative())
        	.withMQServer(MQ_HOSTNAME, MQ_PORT)
        	.withMonitoring(true)
        	.withLogging(Level.OFF)
        	.build();

        config.common().setScaleDownIntervalInSec(60);
        
        return config;
    }
}
