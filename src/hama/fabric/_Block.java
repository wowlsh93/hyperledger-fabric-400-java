package hama.fabric;

import java.util.ArrayList;

public class _Block {

    int index;
    long  timestamp;
    String hash;
    String prevHash;
    public ArrayList<_Transaction> trans;

    public _Block(){}
    public _Block(
            int _index,
            long  _timestamp,
            String _hash,
            String _prevHash,
            ArrayList<_Transaction> _trans) {

        this.index = _index;
        this.timestamp = _timestamp;
        this.hash = _hash;
        this.prevHash = _prevHash;
        this.trans = _trans;
    }
}

