package hama.fabric;

public class Endorser implements Runnable {

    public boolean _stop;
    public Peer _peer;
    public  Endorser (Peer peer){
        _peer = peer;
    }

    @Override
    public void run() {

        while (!_stop) {
            Transaction trans = _peer._transactionList.poll();
            if (trans == null)
                continue;

            if (trans.client_msp == _peer.fabric.MSP_org1) {
                //
                //execute chain code !!!
                //

                _peer.rwlock.lock();
                try {
                    //_peer._rwset = new RWSet( _peer.msp.id, trans.key,trans.value, null );
                    RWSet rwset = new RWSet( _peer.msp.id, trans.key,trans.value, null );
                    _peer._rwsetList.add(rwset);
                    _peer.rwsetok.signal();
                } finally {
                    _peer.rwlock.unlock();
                }
            }
        }

    }
}
