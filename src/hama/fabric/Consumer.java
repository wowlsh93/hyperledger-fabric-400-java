package hama.fabric;

import java.util.ArrayList;

public class Consumer implements Runnable {

    boolean isStop = false;
    public Orderer orderer;
    public Consumer(Orderer _orderer){
        orderer = _orderer;
    }
    @Override
    public void run() {

        while (!isStop) {
            //Fix me!! more efficient way
            ArrayList<RWSet> rwsets = new ArrayList<>();
            RWSet rwset1 = orderer.kafka.poll();
            RWSet rwset2 = orderer.kafka.poll();
            RWSet rwset3 = orderer.kafka.poll();

            Block newBlock = orderer.createBlock(rwsets);
            for(Peer peer : orderer.committer){
                peer.addBlock(newBlock);
            }
        }
    }
}
