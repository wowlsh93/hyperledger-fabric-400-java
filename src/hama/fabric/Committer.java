package hama.fabric;

public class Committer implements Runnable {

    public boolean _stop;
    public Peer _peer;
    public  Committer (Peer peer){
        _peer = peer;
    }

    @Override
    public void run() {

        while (!_stop) {

            Block block = _peer._blockList.getFirst();
            boolean ok = _peer.validating(block);
            if (ok == false) {
                continue;
            }

            for (_Transaction trans : block.trans) {
                _peer.ledger.setState(trans);
            }

            _peer.ledger.addBlock(block);
        }

    }
}
