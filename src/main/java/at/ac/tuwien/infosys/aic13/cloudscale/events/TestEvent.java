package at.ac.tuwien.infosys.aic13.cloudscale.events;

import at.ac.tuwien.infosys.cloudscale.messaging.objects.monitoring.Event;

/**
 * 
 * @author e0756024 <e0756024@student.tuwien.ac.at>
 */
public class TestEvent extends Event {

    private static final long serialVersionUID = 1L;

    private String hostId;
    
    public TestEvent(String hostID) {
	this.hostId = hostID;
    }
    
    public String getHostId() {
        return hostId;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("TestEvent [hostId=");
	builder.append(hostId);
	builder.append(", timestamp=");
	builder.append(timestamp);
	builder.append(", eventId=");
	builder.append(eventId);
	builder.append("]");
	return builder.toString();
    }    
    
}
