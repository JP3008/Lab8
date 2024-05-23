package domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {

    @Test
    public void Test() throws TreeException {
        BTree bt = new BTree();
        bt.add(20);
        bt.add(30);
        bt.add(40);
        bt.add(45);
        bt.add(55);
        bt.add(60);

        System.out.println(bt.preOrder());
        System.out.println(bt.size());
    }

}