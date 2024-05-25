package domain;

import org.junit.jupiter.api.Test;
import tda.help.Utility;
import tda.list.CircularDoublyLinkedList;
import tda.list.ListException;
import tda.list.SinglyLinkedList;
import tda.queue.LinkedQueue;
import tda.queue.QueueException;
import tda.stack.LinkedStack;
import tda.stack.StackException;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BTreeTest {

    @Test
    public void Test() throws TreeException, StackException, QueueException, ListException {

        //Declaración de arbol
    BTree bt = new BTree();

//Rellana la lista
        SinglyLinkedList sl = new SinglyLinkedList();
        for (int i = 0; i < 20; i++) {
            sl.add(Utility.getRandom(50));
        }

        bt.add(sl);

        //Declara y crea un arreglo de nombres en una circularlinkedlist
        CircularDoublyLinkedList cdl = new CircularDoublyLinkedList();

        String[] names = {
                "Carlos", "María", "Juan", "Ana", "Luis", "Elena", "José", "Laura",
                "Miguel", "Carmen", "Pedro", "Lucía", "Jorge", "Sofía", "Manuel", "Rosa",
                "Antonio", "Clara", "Andrés", "Paula"
        };

        for (int i = 0; i <20; i++) {
            cdl.add(names[i]);
        }

        bt.add(cdl);

        //Stack de países en un array
        LinkedStack ls = new LinkedStack();
        String[] countries = {
                "Argentina", "Brasil", "Canadá", "Costa Rica", "Dinamarca", "Egipto", "Francia", "Alemania", "Japón", "México"
        };

        for (int i = 0; i <10; i++) {
            ls.push(countries[i]);
        }

        bt.add(ls);

        //LinkedQueue de numeros aleatorios
        LinkedQueue lq = new LinkedQueue();

        for (int i = 0; i <100; i++) {
            lq.enQueue(new Random().nextInt(5000-1000)+1000);
        }
        bt.add(lq);


        //Arbolbinario de letras
        BTree btreeChars = new BTree();

        char[] alpha = {
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N','Ñ', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        for (int i = 0; i <27 ; i++) {
            btreeChars.add(alpha[i]);
        }

        //Puntos adicionales
        System.out.println("///////////////////////////");
        bt.add(btreeChars);

        System.out.println("Original tree: \n"+bt.toString2());
        System.out.println("///////////////////////////");
        System.out.println("Size of tree "+bt.size());
        System.out.println("///////////////////////////");
        System.out.println("Height of tree "+bt.height());
        System.out.println("///////////////////////////");
        System.out.println("Height of the LinkedQueue "+bt.height(lq));
        System.out.println("///////////////////////////");
        System.out.println("Btree countains Binary Tree of chars "+(bt.contains(btreeChars)?" True!":" False."));
        System.out.println("///////////////////////////");
        //Se rmueve la variable
        bt.remove(lq);

        for (int i = 0; i < 5; i++) {
            int ramval = Utility.getRandom(50);
            if (sl.contains(ramval)){
                sl.remove(ramval);
            }else{
                i--;
            }
        }

        //Quita los nombres en orden
        for (int i = 0; i <5 ; i++) {
            cdl.remove(names[i]);
            }

        //Los saca del stack correctamente
        for (int i = 0; i <5 ; i++) {
            ls.pop();
        }

        //Remueve una letra aleatoria del arreglo
        for (int i = 0; i <5 ; i++) {
            btreeChars.remove(alpha[Utility.getRandom(26)]);
        }


        //Resultados
        System.out.println("Modified tree: \n"+bt.toString2());
        System.out.println("///////////////////////////");

        System.out.println("Height of SinglyLinkedList: "+bt.height(sl));
        System.out.println("///////////////////////////");

        System.out.println("Height of CircularDoublyLinkedList: "+(bt.height(cdl)));
        System.out.println("///////////////////////////");

        System.out.println("Height of LinkedStack "+bt.height(ls));
        System.out.println("///////////////////////////");

        System.out.println("Height of BinaryTree for Chars "+bt.height(btreeChars));





    }

}