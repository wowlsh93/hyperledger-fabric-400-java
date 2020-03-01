package hama.fabric;

public class Transaction {
    public String client_msp;
    public String key;
    public String value;

    public Transaction(String _auth, String _key,
                       String _value) {
        this.client_msp = _auth;
        this.key = _key;
        this.value = _value;
    }

//    public String getAuth() {
//        return auth;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public String getValue() {
//        return value;
//    }
}

