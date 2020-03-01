package hama;

import hama.fabric.ClientSDK;

public class Main {

    public static void main(String[] args) {
        System.out.println("Start !!!");

        ClientSDK sdk = new ClientSDK();
        sdk.startFabric();

        sdk.writeTrans("1", "1");
        String result = sdk.getTrans("1000000");

    }
}
