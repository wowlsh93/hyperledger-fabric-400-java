
package hama.fabric;

import java.util.ArrayList;

public class RWSet {
    public String msp;
    public String key;
    public String value;
    public ArrayList<String> peers_msp;

    public RWSet(
            String _msp,
            String _key,
            String _value,
            ArrayList<String> _peers_msp) {

        this.msp = _msp;
        this.key = _key;
        this.value = _value;
        this.peers_msp = _peers_msp;
    }
}


