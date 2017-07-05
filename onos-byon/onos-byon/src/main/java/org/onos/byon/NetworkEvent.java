package org.onos.byon;

import org.onosproject.event.AbstractEvent;

/**
 * Created by sdn on 16-12-2.
 */


public class NetworkEvent extends AbstractEvent<NetworkEvent.Type, String> {

    enum Type {
        NETWORK_ADDED,
        NETWORK_REMOVED,
        NETWORK_UPDATED
    }

    protected String name;

    public NetworkEvent(Type type, String subject) {
        super(type, subject);
    }

    public NetworkEvent(Type type, String subject, String name) {
        super(type, subject);
        this.name = name;
    }

    public String getName() {
        return name;
    }

}