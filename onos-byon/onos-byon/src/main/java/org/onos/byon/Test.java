package org.onos.byon;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import org.apache.felix.scr.annotations.*;
import org.onlab.packet.Ip4Address;
import org.onlab.packet.MacAddress;
import org.onosproject.net.*;
import org.onosproject.net.provider.ProviderId;
import org.onosproject.net.topology.DefaultTopologyVertex;
import org.onosproject.net.topology.TopologyEdge;
import org.onosproject.net.topology.TopologyGraph;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.onlab.graph.Graph;
import org.onosproject.net.*;
import org.onosproject.net.provider.ProviderId;
import org.onosproject.net.topology.*;

import java.util.*;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;
import static org.onlab.packet.MacAddress.valueOf;

/**
 * Created by tsf on 4/15/17.
 */
@Component(immediate = true)
public class Test {
    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected NetworkService service;


    protected RouteCalculation routeCalculation = new RouteCalculation();
    private Logger log = LoggerFactory.getLogger(getClass());

    @Activate
    public void activate(){
        // ================= test event post end process =========
//        NetworkEvent event = new NetworkEvent(NetworkEvent.Type.NETWORK_UPDATED, "test-network");
//        log.info("{} in Test", event.type().toString());
//        service.post(event);
//        log.info("event: iflytang@{} in Test",event.subject().toString());
        NetworkEvent event1 = new NetworkEvent(NetworkEvent.Type.NETWORK_UPDATED, "test-network", "USTC");
        log.info("{} in Test", event1.type().toString());
        log.info("{} in Test", event1.getName());
        service.post(event1);
        log.info("event1: iflytang@{} in Test",event1.subject().toString());


        // ===================== test IP =======================
        /*log.info("IP-getIPv4: {}", Ip4Address.valueOf("192.168.109.172").getIp4Address());
        log.info("IP-IPPrefix: {}", Ip4Address.valueOf("192.168.109.172").toIpPrefix());
        log.info("IP-toInt: {}", Ip4Address.valueOf("192.168.109.172").toInt());
        log.info("IP-toOctets: {}", Ip4Address.valueOf("192.168.109.172").toOctets());
        log.info("IP-toString: {}", Ip4Address.valueOf("192.168.109.172").toString());*/


        //=================== test MAC ====================
       /* log.info("MAC- {}", MacAddress.valueOf("4f:4f:4f:4f:4f:4f"));
        log.info("MAC-Long {}", MacAddress.valueOf("4f:4f:4f:4f:4f:4f").toLong());
        log.info("MAC-Bytes {}", MacAddress.valueOf("4f:4f:4f:4f:4f:4f").toBytes());
        log.info("MAC-StringNoColon {}", MacAddress.valueOf("4f:4f:4f:4f:4f:4f").toStringNoColon());
        log.info("MAC-HashCode {}", MacAddress.valueOf("4f:4f:4f:4f:4f:4f").hashCode());
*/
        // ================= test set links/vertices/Topo ===============
/*        log.info("setLinks in Test.");
        routeCalculation.setLinks();
        log.info("setVertexs in Test");
        routeCalculation.setVertexs();
        log.info("====== set deviceId in Test ======");
        routeCalculation.setDeviceId();
        log.info("====== set topo in Test =======");
        routeCalculation.setTopo();*/


    }

    @Deactivate
    public void deactivate(){

    }



    /**
     * Created by tsf on 4/17/17.
     *
     * @Description store self-defined topology, and return calculated path for request from src to dst
     */

//    @Component(immediate = true)
    public class RouteCalculation {
        public RouteCalculation(){}

        protected String[] deviceId = {"pof:0000000000000001", "pof:0000000000000002", "pof:0000000000000003",
                "pof:0000000000000004", "pof:0000000000000005", "pof:0000000000000006"
        };

        // set iterable deviceId
        public Set<DeviceId> setDeviceId() {
            Set<DeviceId> devices = new HashSet<DeviceId>();
            for(String device : deviceId)
                devices.add(DeviceId.deviceId(device));
            return devices;
        }


        // define vertex, six nodes
        public Set<DefaultTopologyVertex> setVertexs() {
            log.info("Start set vertexs");
            Set<DefaultTopologyVertex> vertexs = new HashSet<DefaultTopologyVertex>();
            DefaultTopologyVertex vertex1 = new DefaultTopologyVertex(DeviceId.deviceId(deviceId[0]));
            DefaultTopologyVertex vertex2 = new DefaultTopologyVertex(DeviceId.deviceId(deviceId[1]));
            DefaultTopologyVertex vertex3 = new DefaultTopologyVertex(DeviceId.deviceId(deviceId[2]));
            DefaultTopologyVertex vertex4 = new DefaultTopologyVertex(DeviceId.deviceId(deviceId[3]));
            DefaultTopologyVertex vertex5 = new DefaultTopologyVertex(DeviceId.deviceId(deviceId[4]));
            DefaultTopologyVertex vertex6 = new DefaultTopologyVertex(DeviceId.deviceId(deviceId[5]));
            log.info("add vertexs to set.");
            vertexs.add(vertex1);vertexs.add(vertex2);vertexs.add(vertex3);
            vertexs.add(vertex4);vertexs.add(vertex5);vertexs.add(vertex6);
            //System.out.println(vertex1);
            log.info("{}",vertexs.iterator().next());
            return vertexs;
        }


        // define bidirectional links, total 12 links
        public Set<Link> setLinks() {
            PortNumber port1 = PortNumber.portNumber((long) 1);
            PortNumber port2 = PortNumber.portNumber((long) 2);
            PortNumber port3 = PortNumber.portNumber((long) 3);
            ConnectPoint device1_4 = new ConnectPoint(DeviceId.deviceId(deviceId[0]), port2); // 1 -> 4
            ConnectPoint device4_1 = new ConnectPoint(DeviceId.deviceId(deviceId[3]), port3); // 4 -> 1
            ConnectPoint device4_3 = new ConnectPoint(DeviceId.deviceId(deviceId[3]), port2); // 4 -> 3
            ConnectPoint device3_4 = new ConnectPoint(DeviceId.deviceId(deviceId[2]), port3); // 3 -> 4
            ConnectPoint device3_2 = new ConnectPoint(DeviceId.deviceId(deviceId[2]), port1); // 3 -> 2
            ConnectPoint device2_3 = new ConnectPoint(DeviceId.deviceId(deviceId[1]), port2); // 2 -> 3
            ConnectPoint device3_5 = new ConnectPoint(DeviceId.deviceId(deviceId[2]), port2); // 3 -> 5
            ConnectPoint device5_3 = new ConnectPoint(DeviceId.deviceId(deviceId[4]), port2); // 5 -> 3
            ConnectPoint device5_4 = new ConnectPoint(DeviceId.deviceId(deviceId[4]), port1); // 5 -> 4
            ConnectPoint device4_5 = new ConnectPoint(DeviceId.deviceId(deviceId[3]), port1); // 4 -> 5
            ConnectPoint device5_6 = new ConnectPoint(DeviceId.deviceId(deviceId[4]), port3); // 5 -> 6
            ConnectPoint device6_5 = new ConnectPoint(DeviceId.deviceId(deviceId[5]), port1);  // 6 -> 5

            ProviderId providerId = new ProviderId("pof", "mobility management topology");
            Link link1 = DefaultLink.builder().providerId(providerId).src(device1_4).dst(device4_1)
                    .type(Link.Type.EDGE).build();
            Link link2 = DefaultLink.builder().providerId(providerId).src(device4_1).dst(device1_4)
                    .type(Link.Type.EDGE).build();

            Link link3 = DefaultLink.builder().providerId(providerId).src(device4_3).dst(device3_4)
                    .type(Link.Type.EDGE).build();
            Link link4 = DefaultLink.builder().providerId(providerId).src(device3_4).dst(device4_1)
                    .type(Link.Type.EDGE).build();

            Link link5 = DefaultLink.builder().providerId(providerId).src(device3_2).dst(device1_4)
                    .type(Link.Type.EDGE).build();
            Link link6 = DefaultLink.builder().providerId(providerId).src(device2_3).dst(device3_4)
                    .type(Link.Type.EDGE).build();
            Link link7 = DefaultLink.builder().providerId(providerId).src(device3_5).dst(device4_1)
                    .type(Link.Type.EDGE).build();
            Link link8 = DefaultLink.builder().providerId(providerId).src(device5_3).dst(device1_4)
                    .type(Link.Type.EDGE).build();
            Link link9 = DefaultLink.builder().providerId(providerId).src(device5_4).dst(device3_4)
                    .type(Link.Type.EDGE).build();
            Link link10 = DefaultLink.builder().providerId(providerId).src(device4_5).dst(device4_1)
                    .type(Link.Type.EDGE).build();
            Link link11 = DefaultLink.builder().providerId(providerId).src(device5_6).dst(device1_4)
                    .type(Link.Type.EDGE).build();
            Link link12 = DefaultLink.builder().providerId(providerId).src(device6_5).dst(device3_4)
                    .type(Link.Type.EDGE).build();

            Set<Link> links = new HashSet<Link>();
            links.add(link1);links.add(link2);links.add(link3);links.add(link4);links.add(link5);links.add(link6);
            links.add(link7);links.add(link8);links.add(link9);links.add(link10);links.add(link11);links.add(link12);

            //System.out.println(link1);
            log.info("{}",links.iterator().next());
            log.info("{}",links.iterator().next().src());
            log.info("{}",links.iterator().next().dst());
            log.info("{}",links.toArray());
            return links;
        }


        // set topo and return topo
        public void setTopo() {
            log.info("be in setTopo");
            long nanos = 5;   // time in nanos of when the topology description was created
            long mills = 1;   // time in millis of when the topology description was created
            Set<DeviceId> devices = setDeviceId();
            Set<Link> links = setLinks();
            SparseAnnotations annotations = DefaultAnnotations.builder().set("defaultTopo","1").build();
            log.info("======================");
            TopologyGraphDescription topoGraph = new TopologyGraphDescription(nanos, mills, (Iterable<DeviceId>) devices,
                    links, annotations);
            log.info("{} in topoGraph",topoGraph.edges().iterator().next());
            //return (Topology) topoGraph;


        }


        private class TopologyGraphDescription extends AbstractDescription
                implements GraphDescription {

//        private static final Logger log = getLogger(getClass());

            private final long nanos;
            private final long creationTime;
            private final ImmutableSet<TopologyVertex> vertexes;
            private final ImmutableSet<TopologyEdge> edges;

            private final Map<DeviceId, TopologyVertex> vertexesById = Maps.newHashMap();

            /**
             * Creates a minimal topology graph description to allow core to construct
             * and process the topology graph.
             *
             * @param nanos       time in nanos of when the topology description was created
             * @param millis      time in millis of when the topology description was created
             * @param devices     collection of infrastructure devices
             * @param links       collection of infrastructure links
             * @param annotations optional key/value annotations map
             */
            public TopologyGraphDescription(long nanos, long millis,
                                            Iterable<DeviceId> devices,
                                            Iterable<Link> links,
                                            SparseAnnotations... annotations) {
                super(annotations);
                this.nanos = nanos;
                this.creationTime = millis;
                this.vertexes = buildVertexes(devices);
                this.edges = buildEdges(links);
                vertexesById.clear();
            }

            @Override
            public long timestamp() {
                return nanos;
            }

            @Override
            public long creationTime() {
                return creationTime;
            }

            @Override
            public ImmutableSet<TopologyVertex> vertexes() {
                return vertexes;
            }

            @Override
            public ImmutableSet<TopologyEdge> edges() {
                return edges;
            }

            // Builds a set of topology vertexes from the specified list of devices
            private ImmutableSet<TopologyVertex> buildVertexes(Iterable<DeviceId> devices) {
                ImmutableSet.Builder<TopologyVertex> vertexes = ImmutableSet.builder();
                for (DeviceId device : devices) {
                    TopologyVertex vertex = new DefaultTopologyVertex(device);
                    vertexes.add(vertex);
                    vertexesById.put(vertex.deviceId(), vertex);
                }
                return vertexes.build();
            }

            // Builds a set of topology vertexes from the specified list of links
            private ImmutableSet<TopologyEdge> buildEdges(Iterable<Link> links) {
                ImmutableSet.Builder<TopologyEdge> edges = ImmutableSet.builder();
                for (Link link : links) {
                    try {
                        edges.add(new DefaultTopologyEdge(vertexOf(link.src()),
                                vertexOf(link.dst()),
                                link));
                    } catch (IllegalArgumentException e) {
//                    log.debug("Ignoring {}, missing vertex", link);
                    }
                }
                return edges.build();
            }

            // Fetches a vertex corresponding to the given connection point device.
            private TopologyVertex vertexOf(ConnectPoint connectPoint) {
                DeviceId id = connectPoint.deviceId();
                TopologyVertex vertex = vertexesById.get(id);
                checkArgument(vertex != null, "Vertex missing for %s", id);
                return vertex;
            }


        }





































    }

}
