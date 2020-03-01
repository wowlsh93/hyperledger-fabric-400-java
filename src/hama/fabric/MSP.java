package hama.fabric;

import java.util.ArrayList;

public class MSP {

    public String pubKey;
    public String priKey;
    public String id;


    public boolean validating(String _id){
        return id == _id;
    }
};

