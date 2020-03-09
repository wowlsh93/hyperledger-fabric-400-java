package hama.fabric;

import java.util.AbstractMap;
import java.util.ArrayList;

public class ClientSDK {

    public Fabric fab  = new Fabric();
    public void startFabric(){
        this.fab.start();
    }

    public String writeTrans(String key, String value){
        AbstractMap.SimpleEntry<RWSet, RWSet> rwsets = fab.writeTransaction(key, value, fab.MSP_org1);

//        if ( rwsets.getKey().msp.equals(fab.MSP_peer1) && rwsets.getValue().msp.equals(fab.MSP_peer2) ) {
//            ArrayList<String> msps = new ArrayList<>();
//            msps.add(fab.MSP_peer1);
//            msps.add(fab.MSP_peer2);
//
//            RWSet rwset = new RWSet("", key, value, msps);
//            fab.sendToOrderer(rwset);
//            return "ok";
//        }

        return "failed";
    }

    public String getTrans(String key){
        String rwset = fab.readTranaction(key, fab.MSP_org1);
        return rwset;
    }
}
