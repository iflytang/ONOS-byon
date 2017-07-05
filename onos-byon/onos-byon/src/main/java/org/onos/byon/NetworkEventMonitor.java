package org.onos.byon;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.onosproject.event.ListenerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs network events.
 */
@Component(immediate = true)
public class NetworkEventMonitor {
    private static Logger log = LoggerFactory.getLogger(NetworkEventMonitor.class);

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected NetworkService service;

    private final Listener listener = new Listener();

    @Activate
    protected void activate() {
        service.addListener(listener);
        log.info("Monitor Started");
        NetworkEvent event = new NetworkEvent(NetworkEvent.Type.NETWORK_ADDED, "test-network");
        log.info("{} in NetworkEventMonitor", event.type().toString());
        service.post(event);
        log.info("iflytang@{} in NetworkEventMonitor",event.subject().toString());
    }

    @Deactivate
    protected void deactivate() {
        service.removeListener(listener);
        log.info("Stopped");
    }

    private class Listener implements NetworkListener {
        @Override
        public void event(NetworkEvent event) {
            if ( event.type().equals(NetworkEvent.Type.NETWORK_ADDED)) {
                log.info("O, my God.");
                log.info("{}", event.type());
                log.info("O, my God.");
            }
            if ( event.type().equals(NetworkEvent.Type.NETWORK_REMOVED)) {
                log.info("iflytang@Networkevent: {}", event.type());
                log.info("iflytang@Networkevent: {}", event.subject().toString());
            }
            if ( event.type().equals(NetworkEvent.Type.NETWORK_UPDATED)) {
                log.info("iflytang@Networkevent: {}", event.type());
                log.info("iflytang@Networkevent: {}", event.subject().toString());
            }

        }
    }
}