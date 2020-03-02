package hama.fabric;

import java.util.AbstractMap;
import java.util.ArrayList;

public class Fabric {

    public Orderer  orderer1;
    public Orderer  orderer2;
    public Peer endorser1;
    public Peer endorser2;
    public Peer committer;

    boolean roundrobin;

    public String MSP_org1;
    public String  MSP_peer1;
    public String  MSP_peer2;
    public String  MSP_peer3;
    public String  MSP_orderer1;
    public String  MSP_orderer2;

    public void start(){

        Ledger ledger1 =  new Ledger();
        Ledger ledger2 =  new Ledger();
        Ledger ledger3 =  new Ledger();

        this.MSP_org1 = "org1";
        this.MSP_peer1 = "peer1";
        this.MSP_peer2 = "peer2";
        this.MSP_peer3 = "peer3";
        this.MSP_orderer1 = "orderer1";
        this.MSP_orderer2 = "orderer2";


        MSP msp_peer1 = new MSP();
        msp_peer1.id = MSP_peer1;

        endorser1 = new Peer(1, msp_peer1, this, ledger1);
        endorser1.start();

        MSP msp_peer2 = new MSP();
        msp_peer2.id = MSP_peer2;

        endorser2 = new Peer(1, msp_peer2, this, ledger2);
        endorser2.start();

        MSP msp_peer3 = new MSP();
        msp_peer3.id = MSP_peer3;

        committer = new Peer(0, msp_peer3, this, ledger3);
        committer.start();

        // 2. kafka simulator start
        //kafka = std::make_shared<Kafaka>();

        // 3. two orderer simulator start (first is input, second is ordering)
        MSP msp_orderer1 = new MSP();
        msp_orderer1.id = MSP_orderer1;

        orderer1 = new Orderer(msp_orderer1, this);
        orderer1.addCommitter(endorser1);
        orderer1.addCommitter(endorser2);
        orderer1.addCommitter(committer);

        orderer1.start();
    }

    public void stop(){

    }

    public AbstractMap.SimpleEntry<RWSet, RWSet>  writeTransaction(String key , String value, String auth){
        Transaction t = new Transaction(auth,key,value);

        RWSet rwset1 = endorser1.addTrans(t);
        RWSet rwset2 = endorser2.addTrans(t);

        AbstractMap.SimpleEntry<RWSet, RWSet> result = new AbstractMap.SimpleEntry<>(rwset1,rwset2);
        return result;

    }

    public String readTranaction( String key , String auth ){
        return committer.getData(key);
    }
    public void sendToOrderer(RWSet rwset){
    // if (roundrobin) {
    //    //        orderer1->addRWSet(rwset);
    //    //        roundrobin = false;
    //    //    } else {
    //    //        orderer2->addRWSet(rwset);
    //    //        roundrobin = true;
    //    //    }

        orderer1.addRWSet(rwset);
    }

}
