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
            Transaction trans = _peer._transactionList.getFirst();

            if (trans.client_msp == _peer.fabric.MSP_org1) {
                //
                //execute chain code !!!
                //
                RWSet rwset = new RWSet(trans.key,trans.value, _peer.msp.id, null );
                _peer._rwsetList.addLast(rwset);
            }
        }

    }
}
