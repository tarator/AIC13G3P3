/**
 * @author e0756024 <e0756024@student.tuwien.ac.at>
 * @version 5:48:00 PM
 */
package at.ac.tuwien.infosys.aic13.cloudscale.configuration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.infosys.cloudscale.policy.IScalingPolicy;
import at.ac.tuwien.infosys.cloudscale.vm.ClientCloudObject;
import at.ac.tuwien.infosys.cloudscale.vm.IHost;
import at.ac.tuwien.infosys.cloudscale.vm.IHostPool;

/**
 * 
 * @author e0756024 <e0756024@student.tuwien.ac.at>
 */
public class ScalingPolicy implements IScalingPolicy {

    private static final Logger log = LoggerFactory.getLogger(ScalingPolicy.class);
    
    private Object lock = new Object();
    
    @Override
    public boolean scaleDown(IHost host, IHostPool hostPool) {
	//log.debug(CloudScaleUtils.logHost(host));
	//log.debug(CloudScaleUtils.logHostPool(hostPool));
	
	//scale down if
	// host is online
	// currently unused
	// no cloudObjects are online on this host
	// not the last one
	
	boolean result = true;
	
	synchronized (lock) {
	    log.info("-------------------------------------------------------------");
	    log.info("Checking whether to scale down host "+host.getId().toString());
	    
	    if(!host.isOnline()) {
		result = false;
		log.info("Not scaling down. Host is offline");
	    }
	    
	    if(host.getCloudObjectsCount() > 0) {
		result = false;
		log.info("Not scaling down. Host is in use");
	    }
	    
	    if(!otherUnusedHost(hostPool, host.getId())) {
		result = false;
		log.info("Not scaling down. Host is the last unused host");
	    }
	}
	if(result) {
		log.info("Scaling down host "+host.getId().toString());
	}
	
	return result;
    }

    /**
     * 
     * @param hostPool
     * @param id	id of host to check if it is the last one
     * @return 		true if there is at least one other unused 
     * 			host in the hostpool 
     */
    private boolean otherUnusedHost(IHostPool hostPool, UUID id) {
	for(IHost host : hostPool.getHosts()) {
		if(!host.getId().equals(id) && host.getCloudObjectsCount() == 0)
			return true;
	}

	return false;
    }

    @Override
    public IHost selectHost(ClientCloudObject cloudObject, IHostPool hostPool) {
	//log.debug(CloudScaleUtils.logClientCloudObject(cloudObject));
	
	log.info("Starting to select host");
	
	IHost selected = null;
	
	// lock the policy so that we do not concurrently fire up a bunch of hosts
	synchronized (lock) {
		
		log.info("-------------------------------------------------------------");

		// for each host, check if the avg processing time for the host is below 50ms
		// (unused hosts we are always using)
		for (IHost host : hostPool.getHosts()) {
			if(host.getId() == null)
				continue;
			long startupTime = host.getStartupTime().getTime();
			long currentTime = System.currentTimeMillis();
			long upTime = TimeUnit.MILLISECONDS.toMinutes(currentTime-startupTime);
			log.info(String.format(
				"Host %s (%s) has started before %i minutes",
				host.getId().toString(), host.getIpAddress(), upTime
			));
			if((upTime >= 0 && upTime < 55) || host.getCloudObjectsCount() == 0) {
				selected = host;
				break;
			}
		}
		log.info("-------------------------------------------------------------");
		if(selected == null)
			log.info("Found no suitable host, scaling up");
		else
			log.info("Using host "+selected.getId().toString());
		log.info("-------------------------------------------------------------");

	}
		
	// no suitable host found, start a new one
	if(selected == null) {
		selected = hostPool.startNewHost();
	}
	
	return selected;
    }
    
}
