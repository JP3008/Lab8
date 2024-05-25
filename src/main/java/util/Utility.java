package util;

import domain.BTree;
import tda.list.CircularDoublyLinkedList;
import tda.list.SinglyLinkedList;
import tda.queue.LinkedQueue;
import tda.stack.LinkedStack;

import java.text.DecimalFormat;
import java.util.Random;

public class Utility {

    //static init
    static {
    }

    public static String format(double value){
        return new DecimalFormat("###,###,###.##").format(value);
    }
    public static String $format(double value){
        return new DecimalFormat("$###,###,###.##").format(value);
    }
    public static String show(int[] a, int size) {
        String result="";
        for (int i = 0; i < size; i++) {
            result+= a[i] + " ";
        }
        return result;
    }

    public static void fill(int[] a, int bound) {
        for (int i = 0; i < a.length; i++) {
            a[i] = new Random().nextInt(bound);
        }
    }

    public static int getRandom(int bound) {
        return new Random().nextInt(bound)+1;
    }

    public static int compare(Object a, Object b) {
        switch (instanceOf(a, b)){
            case "Integer":
                Integer int1 = (Integer)a; Integer int2 = (Integer)b;
                return int1 < int2 ? -1 : int1 > int2 ? 1 : 0; //0 == equal
            case "String":
                String st1 = (String)a; String st2 = (String)b;
                return st1.compareTo(st2)<0 ? -1 : st1.compareTo(st2) > 0 ? 1 : 0;
            case "Character":
                Character c1 = (Character)a; Character c2 = (Character)b;
                return c1.compareTo(c2)<0 ? -1 : c1.compareTo(c2)>0 ? 1 : 0;
            case "LinkedStack":
                LinkedStack ls1 = (LinkedStack) a; LinkedStack ls2 = (LinkedStack) b;
                return ls1.toString().compareTo(ls2.toString())<0? -1 : ls1.toString().compareTo(ls2.toString())>0 ? 1 : 0;
            case "Btree":
                BTree b1 = (BTree) a; BTree b2 = (BTree) b;
                return b1.toString().compareTo(b2.toString())<0? -1 : b1.toString().compareTo(b2.toString())>0 ? 1 : 0;
            case "SinglyLinkedList":
                SinglyLinkedList sl1 = (SinglyLinkedList) a; SinglyLinkedList sl2 = (SinglyLinkedList) b;
                return sl1.toString().compareTo(sl2.toString())<0? -1 : sl1.toString().compareTo(sl2.toString())>0 ? 1 : 0;
            case "CircularDoublyLinkedList":
                CircularDoublyLinkedList cdl1 = (CircularDoublyLinkedList) a; CircularDoublyLinkedList cdl2 = (CircularDoublyLinkedList) b;
                return cdl1.toString().compareTo(cdl2.toString())<0? -1 : cdl1.toString().compareTo(cdl2.toString())>0 ? 1 : 0;
            case "LinkedQueue":
                LinkedQueue lq1 = (LinkedQueue) a; LinkedQueue lq2 = (LinkedQueue) b;
                return lq1.toString().compareTo(lq2.toString())<0? -1 : lq1.toString().compareTo(lq2.toString())>0 ? 1 : 0;
        }
        return 2; //Unknown
    }

    private static String instanceOf(Object a, Object b) {
        if(a instanceof Integer && b instanceof Integer) return "Integer";
        if(a instanceof String && b instanceof String) return "String";
        if(a instanceof Character && b instanceof Character) return "Character";
        if(a instanceof LinkedStack && b instanceof LinkedStack) return "LinkedStack";
        if(a instanceof BTree && b instanceof BTree) return "Btree";
        if(a instanceof SinglyLinkedList && b instanceof SinglyLinkedList) return "SinglyLinkedList";
        if(a instanceof CircularDoublyLinkedList && b instanceof CircularDoublyLinkedList) return "CircularDoublyLinkedList";
        if(a instanceof LinkedQueue && b instanceof LinkedQueue) return "LinkedQueue";
        return "Unknown";
    }
}
