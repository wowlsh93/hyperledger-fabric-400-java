package hama.fabric;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    public ConcurrentLinkedQueue<Transaction> _transactionList = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<RWSet> _rwsetList = new ConcurrentLinkedQueue<>();
    public RWSet _rwset = null;
    public ConcurrentLinkedQueue<Block> _blockList = new ConcurrentLinkedQueue<>();
    public Block _block = null;
    public final Lock rwlock = new ReentrantLock();
    public final Condition rwsetok = rwlock.newCondition();

    public final Lock bclock = new ReentrantLock();
    public final Condition blockok = bclock.newCondition();


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
        _transactionList.add(trans);

//        lock.lock();
//        RWSet rwset = null;
//        try {
//            rwsetok.await();
//            rwset = this._rwset;
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        finally {
//            lock.unlock();
//        }
//
//        return rwset;

        RWSet rwset = null;
        while (rwset == null) {
            rwset = _rwsetList.poll();
        }
        return rwset;
    }

    public void addBlock(Block block){
        //this.bclock.lock();
        _blockList.add(block);
        //_block = block;
        //this.blockok.signal();
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
