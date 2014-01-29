/**
 * @author e0756024 <e0756024@student.tuwien.ac.at>
 * @version 5:48:00 PM
 */
package at.ac.tuwien.infosys.aic13.cloudscale.configuration;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ac.tuwien.infosys.cloudscale.monitoring.EventCorrelationEngine;
import at.ac.tuwien.infosys.cloudscale.monitoring.MonitoringMetric;
import at.ac.tuwien.infosys.cloudscale.policy.AbstractScalingPolicy;
import at.ac.tuwien.infosys.cloudscale.vm.ClientCloudObject;
import at.ac.tuwien.infosys.cloudscale.vm.IHost;
import at.ac.tuwien.infosys.cloudscale.vm.IHostPool;

/**
 * 
 * @author e0756024 <e0756024@student.tuwien.ac.at>
 */
public class ScalingPolicyAlternative extends AbstractScalingPolicy {

	private static final Logger log = LoggerFactory.getLogger(ScalingPolicyAlternative.class);

	private Object lock = new Object();

	private final double MAX_CPU_LOAD_PERCENTAGE = 0.90;
	private final double MAX_RAM_USE_PERCENTAGE = 0.90;
	private final int MAX_CLOUD_OBJECTS_PER_HOST = 25;
//	private DecimalFormat doubleFormatter = new DecimalFormat("#0.000");

	

	@Override
	public boolean scaleDown(IHost host, IHostPool hostPool) {

		synchronized (lock) {
			
			log.debug("-------------------------------------------------------------");

			log.debug("Checking whether to scale down host " + host.getId().toString());

			if (!host.isOnline()) {
				log.debug("Not scaling down. Host is either just starting up or going offline");
				return false;
			}

			if (host.getCloudObjectsCount() > 0) {
				log.debug("Not scaling down. Host is in use");
				return false;
			}

			if (!otherUnusedHost(hostPool, host.getId())) {
				log.debug("Not scaling down. Host is the last unused host");
				return false;
			}

			try {
				long startupTime = host.getStartupTime().getTime();
				long currentTime = System.currentTimeMillis();
				long upTime = TimeUnit.MILLISECONDS.toMinutes(currentTime - startupTime);
				long upTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(currentTime - startupTime);
				// check if hour is running out - possible shutdown candidate
				log.debug("Uptime is " + upTime + " minutes (" + upTimeSeconds + "seconds)!");
				if ((upTime >= 0 && (upTime % 60) <57)) {
					log.debug("Not scaling down. Host has not reached end of full hour)");
					return false;
				}
			} catch (Exception e) {
				log.error("Error", e);
				return false;
			}
			log.debug("##############################################################");
			log.debug("Scaling down host " + host.getId().toString());
			log.debug("##############################################################");

			
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

		

		log.debug("#######################");
		log.debug("Starting to select host");
		log.debug("#######################\n");
		
		IHost selectedHost = null;
		IHost backupHost = null;

		int cloubObjectsCount = 0;

		synchronized (lock) {

			for (IHost currentHost : hostPool.getHosts()) {
				

				log.debug("-------------------------------------------------------------");

				log.debug("Checking host " + currentHost.getId());
				cloubObjectsCount += currentHost.getCloudObjectsCount();
				// log.debug(CloudScaleUtils.logHost(currentHost));
				boolean newhost = false;

				// if host is not online, it's starting up or shutting down
				if (!currentHost.isOnline()) {
					log.debug("Host is not online");
					// if starttime is positive then the host is shutting down
					if (currentHost.getStartupTime() != null
							&& currentHost.getStartupTime().getTime() > 0) {

						continue;
					} else {
						newhost = true;
					}
				} else {
					log.debug("Host is online");
				}


				//check CPU Load
				if (currentHost.getCurrentCPULoad() != null) {
					log.debug("-------------------------------------------------------------");
					// log.debug("Host has a CPU Load of "
					// +
					// doubleFormatter.format(currentHost.getCurrentCPULoad().getCpuLoad())
					// + " on " +
					// currentHost.getCurrentCPULoad().getProcessors()
					// + "processors");
					if (currentHost.getCurrentCPULoad().getCpuLoad() > MAX_CPU_LOAD_PERCENTAGE) {
						log.debug("Host" + currentHost.getId() + "is busy - looking for next one");
						continue;
					} else {
						log.debug("CPU Load is ok");
					}
				} 

				// check RAM Usage
				if (currentHost.getCurrentRAMUsage() != null) {
					double ramUse = currentHost.getCurrentRAMUsage().getUsedMemory()
							/ currentHost.getCurrentRAMUsage().getMaxMemory();
					// log.debug("Host has a Memory Usage of " +
					// doubleFormatter.format(ramUse) + "%");
					if (ramUse > MAX_RAM_USE_PERCENTAGE) {
						log.debug("Host " + currentHost.getId()
								+ "is at nearly full capacity - looking for next one");
						continue;
					} else {
						log.debug("RAM Usage is ok");
					}
				}

				if (newhost) {
					if (currentHost.getCloudObjectsCount() < MAX_CLOUD_OBJECTS_PER_HOST) {
						selectedHost = currentHost;
					} else {
						continue;
					}
				} else {
					long startupTime = currentHost.getStartupTime().getTime();
					long currentTime = System.currentTimeMillis();
					long upTime = TimeUnit.MILLISECONDS.toMinutes(currentTime - startupTime);
					long upTimeSeconds = TimeUnit.MILLISECONDS.toSeconds(currentTime - startupTime);
					// check if hour is running out - possible shutdown
					// candidate
					log.debug("Uptime is " + upTime + " minutes (" + upTimeSeconds + "seconds)!");
					log.debug("Cloud Object on this Host: " + currentHost.getCloudObjectsCount());
					if (currentHost.getCloudObjectsCount() < MAX_CLOUD_OBJECTS_PER_HOST) {
						if (upTime >= 0 && (upTime % 60) < 57) {
							selectedHost = currentHost;
						} else {
							backupHost = currentHost;
							log.debug("Selected Backup Host");
						}

					}
				}
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}

			// end of loop

			// first check if we have selected a host - if not, replace it by
			// backup host if it exists
			// try {
			if (selectedHost == null && backupHost != null) {
				selectedHost = backupHost;
			}

			// if no host from hostpool satisfied the criteria, start another
			// one
			if (selectedHost == null) {
				log.debug("##################################");
				log.debug("Found no suitable host, scaling up");
				log.debug("##################################");
				selectedHost = hostPool.startNewHost();
				// registerHostCpuEventMetric(selectedHost);
			} else {
				log.debug("######################################");
				log.debug("Deploying new cloud object "
				// + cloudObject.getCloudObjectClass().getName()
				);
				log.debug(selectedHost.getId() != null ? "Using host "
						+ selectedHost.getId().toString() : "Using host in startup progress");
			}

			log.debug("######################################\n");
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			return selectedHost;

		}
	}

	public void registerHostCpuEventMetric(IHost host) {

		MonitoringMetric metric = new MonitoringMetric();
		metric.setName("TestMetric" + host.getId().toString());
		metric.setEpl(String
				.format("select cpuLoad as avg_load from CPUEvent(hostId.toString() = '%s').win:time(10 sec)",
						host.getId().toString()));
		metric.setResultField("avg_load");
		EventCorrelationEngine.getInstance().registerMetric(metric);
	}

}