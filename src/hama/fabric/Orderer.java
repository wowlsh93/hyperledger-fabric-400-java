package hama.fabric;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Orderer {

      public  Orderer(MSP _msp,  Fabric _fabric) {
        this.msp = _msp;
        this.fabric = _fabric;
      }

      public Thread consumer;
      public MSP msp;
      public Fabric fabric;
      public ArrayList<Peer> committer = new ArrayList<>();

      public  ConcurrentLinkedQueue<RWSet> kafka = new ConcurrentLinkedQueue<RWSet>();

      public void addCommitter(Peer peer){
        committer.add(peer);
      }

      void start(){

          this.consumer =  new Thread(new Consumer(this));
          consumer.start();
      }

      void addRWSet(RWSet rwset){
          kafka.add(rwset);
      }

      Block createBlock(ArrayList<RWSet> _rwsets){
          Block newBlock = new Block();
          for (RWSet rwset : _rwsets){
              _Transaction _trans = new  _Transaction(rwset.key, rwset.value);
              newBlock.trans.add(_trans);
              newBlock.endorsers.add(rwset.peers_msp.get(0));
              newBlock.endorsers.add(rwset.peers_msp.get(1));
          }
          return newBlock;
      }
}
