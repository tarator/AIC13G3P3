package at.ac.tuwien.infosys.aic13.cloudscale.utils;

import at.ac.tuwien.infosys.cloudscale.vm.ClientCloudObject;
import at.ac.tuwien.infosys.cloudscale.vm.IHost;
import at.ac.tuwien.infosys.cloudscale.vm.IHostPool;

/**
 * 
 * @author e0756024 <e0756024@student.tuwien.ac.at>
 */
public class CloudScaleUtils {

    /**
     *
     * @param host
     * @return toDebugString of the given host
     */
    public static String logHost(IHost host) {
	StringBuilder builder = new StringBuilder();
	
	builder.append("Host[getId=");
	builder.append(host.getId());
	builder.append(", getIpAddress=");
	builder.append(host.getIpAddress());
	builder.append(", isOnline=");
	builder.append(host.isOnline());
	builder.append(", getCurrentCPULoad=");
	builder.append(host.getCurrentCPULoad());
	builder.append(", getCurrentRAMUsage=");
	builder.append(host.getCurrentRAMUsage());
	builder.append(", getLastRequestTime=");
	builder.append(host.getLastRequestTime());
	builder.append(", getStartupTime=");
	builder.append(host.getStartupTime());
	builder.append(", getCloudObjectsCount=");
	builder.append(host.getCloudObjectsCount());
	builder.append("]");
	
	return builder.toString();
    }
    
    /**
     * 
     * @param hostPool
     * @return toDebugString of the given hostPool
     */
    public static String logHostPool(IHostPool hostPool) {
	StringBuilder builder = new StringBuilder();
	
	builder.append("HostPool [getHostsCount=");
	builder.append(hostPool.getHostsCount());
	builder.append("]");
	
	return builder.toString();
    }
    
    /**
     * 
     * @param cloudObject
     * @return toDebugString of the given cloudObject
     */
    public static String logClientCloudObject(ClientCloudObject cloudObject) {
	StringBuilder builder = new StringBuilder();
	
	builder.append("ClientCloudObject [getHostsCount=");
	builder.append(cloudObject.getId());
	builder.append(", =getState");
	builder.append(cloudObject.getState());
	builder.append("]");
	
	return builder.toString();
    }
}
