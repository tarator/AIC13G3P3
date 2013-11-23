/**
 * @author e0756024 <e0756024@student.tuwien.ac.at>
 * @version 5:48:00 PM
 */
package at.ac.tuwien.infosys.aic13.cloudscale.configuration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
public class ScalingPolicyAlternative implements IScalingPolicy {

	private static final Logger log = LoggerFactory.getLogger(ScalingPolicyAlternative.class);

	private Object lock = new Object();

	private final double MAX_CPU_LOAD_PERCENTAGE = 0.95;
	private final double MAX_RAM_USE_PERCENTAGE = 0.95;

	@Override
	public boolean scaleDown(IHost host, IHostPool hostPool) {

		synchronized (lock) {
			log.info("-------------------------------------------------------------");
			log.info("Checking whether to scale down host " + host.getId().toString());

			if (!host.isOnline()) {
				log.info("Not scaling down. Host is either just starting up or going offline");
				return false;
			}

			if (host.getCloudObjectsCount() > 0) {
				log.info("Not scaling down. Host is in use");
				return false;
			}

			if (!otherUnusedHost(hostPool, host.getId())) {
				log.info("Not scaling down. Host is the last unused host");
				return false;
			}

			log.info("Scaling down host " + host.getId().toString());

			return true;
		}
	}

	/**
	 * 
	 * @param hostPool
	 * @param id
	 *            id of host to check if it is the last one
	 * @return true if there is at least one other unused host in the hostpool
	 */
	private boolean otherUnusedHost(IHostPool hostPool, UUID id) {
		for (IHost host : hostPool.getHosts()) {
			if (!host.getId().equals(id) && host.getCloudObjectsCount() == 0)
				return true;
		}

		return false;
	}

	@Override
	public IHost selectHost(ClientCloudObject cloudObject, IHostPool hostPool) {
		
		log.info("-------------------------------------------------------------");
		log.info("Starting to select host");
		IHost selectedHost = null;

		synchronized (lock) {

			for (IHost currentHost : hostPool.getHosts()) {
				log.info(CloudScaleUtils.logHost(currentHost));
				boolean newhost = false;

				// if host is not online, it's either starting up or shutting
				// down
				if (!currentHost.isOnline()) {
					// if starttime is positive then the host is shutting down
					if (currentHost.getStartupTime() != null
							&& currentHost.getStartupTime().getTime() > 0) {
						// TODO is this necessary to remove host from hostpool ?
						// hostPool.shutdownHostAsync(currentHost);
						continue;
					}
					// no startup time, therefore the host is just starting
					else {
						newhost = true;
					}
				}

				// check CPU Load
				if (currentHost.getCurrentCPULoad() != null) {
					log.debug("-------------------------------------------------------------");
					log.debug("Host has a CPU Load of "
							+ currentHost.getCurrentCPULoad().getCpuLoad());
					if (currentHost.getCurrentCPULoad().getCpuLoad() > MAX_CPU_LOAD_PERCENTAGE) {
						log.debug("Host {} is busy - looking for next one", currentHost.getId());
						continue;
					}
					log.debug("OK");
				}

				// check RAM Usage
				if (currentHost.getCurrentRAMUsage() != null) {
					double ramUse = currentHost.getCurrentRAMUsage().getUsedMemory()
							/ currentHost.getCurrentRAMUsage().getMaxMemory();
					log.debug("-------------------------------------------------------------");
					log.debug("Host has a Memory Usage of " + ramUse);
					if (ramUse > MAX_RAM_USE_PERCENTAGE) {
						log.debug("Host {} is at nearly full capacity - looking for next one",
								currentHost.getId());
						continue;
					}
					log.debug("OK");

				}

				if (newhost) {
					if (currentHost.getCloudObjectsCount() < 5) {
						selectedHost = currentHost;
					} else {
						continue;
					}
				} else {
					long startupTime = currentHost.getStartupTime().getTime();
					long currentTime = System.currentTimeMillis();
					long upTime = TimeUnit.MILLISECONDS.toMinutes(currentTime - startupTime);
					// check if hour is running out - possible shutdown
					// candidate
					log.debug("-------------------------------------------------------------");
					log.debug("Uptime is" + upTime);
					if ((upTime >= 0 && (upTime % 60) < 55)
							|| currentHost.getCloudObjectsCount() < 5) {
						selectedHost = currentHost;
					}
				}

			}

			// if no host from hostpool satisfies the criteria start another one

			log.info("-------------------------------------------------------------");

			if (selectedHost == null) {
				log.info("Found no suitable host, scaling up");
				selectedHost = hostPool.startNewHost();
			} else {
				log.info(selectedHost.getId() != null ? "Using host "
						+ selectedHost.getId().toString() : "Using host in startup progress");
			}

			return selectedHost;

		}
	}
}