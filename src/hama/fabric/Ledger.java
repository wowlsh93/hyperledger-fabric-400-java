package hama.fabric;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Ledger{

    public LevelDB stateDB = new LevelDB();
    public ArrayList<_Block> blockChain = new ArrayList<>();

    public  void createGenesisBlock(){

        _Block genesisBlock = new _Block();
        genesisBlock.index = 0;
        genesisBlock.timestamp = 0;
        ArrayList<_Transaction> genesis =  new ArrayList<>();
        genesis.add(new _Transaction("genesis_key","3 organization"));
        genesisBlock.trans = genesis;
        genesisBlock.hash = calculateHash(genesisBlock);
        genesisBlock.prevHash = "";

        blockChain.add(genesisBlock);
    }

    public void addBlock(Block block){
        _Block prevBlock = blockChain.get(blockChain.size() - 1);
        _Block newBlock = generateBlock(prevBlock, block);
        blockChain.add(newBlock);
    }

    public _Block generateBlock(_Block  oldBlock, Block block ){
        _Block newBlock = new _Block();
        newBlock.index = oldBlock.index + 1;
        newBlock.timestamp = System.currentTimeMillis() / 1000;
        newBlock.prevHash = oldBlock.hash;
        newBlock.trans = block.trans;
        newBlock.hash = calculateHash(newBlock);

        return newBlock;
    }

    public void  setState(_Transaction trans ){
        stateDB.setValue(trans.key, trans.value);
    }

    public String getState(Transaction trans ){
        return stateDB.getValue(trans.key);

    }

    public String calculateHash(_Block block){
        StringBuilder trans_concated = new StringBuilder();
        for (_Transaction trans : block.trans ) {
            trans_concated.append(trans.value);
        }
        //string record = hama::string_format("%d%lf%s%s\n", block->Index, block->Timestamp, trans_concated, block->PrevHash);
        String record = "afefwfwef2323f2332f23f23wfwefweref23423423sdvsvsr423423432432432f23f23f23432234322f";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(record.getBytes());
        byte[] hashedmsg = md.digest();
        StringBuilder builder = new StringBuilder();
        for (byte b: hashedmsg) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();

    }
}
