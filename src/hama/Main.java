package hama;

import hama.fabric.ClientSDK;

public class Main {

    public static void main(String[] args) {
        System.out.println("Start 1");

        ClientSDK sdk = new ClientSDK();
        sdk.startFabric();

        System.out.println("Start 2");

        long startTime = System.currentTimeMillis();


        for (int i = 0 ; i < 1000000 ; i++) {
            sdk.writeTrans(String.valueOf(i), String.valueOf(i));
        }

        System.out.println("Start 3");

//        while(true) {
//            String result = sdk.getTrans("1000000");
//            if (result != null && !result.equals("")) {
//                break;
//            }
//        }
        System.out.println("Start 4");

        long estimatedTime = System.currentTimeMillis() - startTime;
        System.out.println("took " + estimatedTime + " ms");


        try {
            Thread.sleep(1000 * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
