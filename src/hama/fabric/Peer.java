package hama.fabric;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;

public class Peer {

    public Peer(int _peer_type, MSP _msp, Fabric  _fabric, Ledger _ledger){
        peer_type = _peer_type;
        msp = _msp;
        fabric = _fabric;
        ledger = _ledger;
    }

    public int peer_type; // 0 commit only peer, 1 endorse + commit peer
    public Ledger ledger;
    public Fabric fabric;
    public MSP msp;

    public ConcurrentLinkedDeque<Transaction> _transactionList = new ConcurrentLinkedDeque<>();
    public ConcurrentLinkedDeque<RWSet> _rwsetList = new ConcurrentLinkedDeque<>();
    public ConcurrentLinkedDeque<Block> _blockList = new ConcurrentLinkedDeque<>();

    ArrayList<Thread> threads = new ArrayList<Thread>();
    private boolean _stop;

    public void start(){

        ledger.createGenesisBlock();
        if (peer_type == 1){
            Thread _endorser = new Thread(new Endorser(this));
            _endorser.start();
            threads.add(_endorser);

        }
        Thread _committer = new Thread(new Committer(this));
        _committer.start();
        threads.add(_committer);
    }

    public RWSet addTrans(Transaction trans){
        _transactionList.addLast(trans);
        RWSet rwset = _rwsetList.getFirst();
        return rwset;
    }

    public void addBlock(Block block){
        _blockList.addLast(block);
    }

    public boolean validating(Block block){

        if (block.endorsers.get(0) == fabric.MSP_peer1 &&
                block.endorsers.get(1) == fabric.MSP_peer2) {
            return true;
        }

        return false;
    }

    public String getData(String key){
        return ledger.stateDB.getValue(key);
    }
}
