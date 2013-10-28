/**
 * @author e0756024 <e0756024@student.tuwien.ac.at>
 * @version 5:48:00 PM
 */
package at.ac.tuwien.infosys.aic13.cloudscale.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.infosys.aic13.cloudscale.utils.CloudScaleUtils;
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
    
    @Override
    public boolean scaleDown(IHost host, IHostPool hostPool) {
	// TODO Auto-generated method stub
	
	log.debug(CloudScaleUtils.logHost(host));
	log.debug(CloudScaleUtils.logHostPool(hostPool));
	
	return false;
    }

    @Override
    public IHost selectHost(ClientCloudObject cloudObject, IHostPool hostPool) {
	// TODO Auto-generated method stub
	
	log.debug(CloudScaleUtils.logClientCloudObject(cloudObject));
	
	return null;
    }

}
