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
        ArrayList<RWSet> rwsets = new ArrayList<>();
        while (!isStop) {
            //Fix me!! more efficient way
            RWSet rwset = orderer.kafka.poll();
            if (rwset == null)
                continue;

            rwsets.add( rwset );
            if (rwsets.size() != 3)
                continue;

            Block newBlock = orderer.createBlock(rwsets);
            rwsets.clear();
            for(Peer peer : orderer.committer){
                peer.addBlock(newBlock);
            }
        }
    }
}
