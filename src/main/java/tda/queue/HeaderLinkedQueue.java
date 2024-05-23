/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tda.queue;

import tda.help.Utility;

/**
 *
 * @author Profesor Lic. Gilberth Chaves A.
 * Cola enlazada con nodo cabecera (nodo vacio)
 */
public class HeaderLinkedQueue implements Queue {
    private Node front; //anterior //Front.next tira el primer elemento
    private Node end; //posterior  //End.next tira el ultimo elemento
    private int count; //control de elementos encolados
    
    //Constructor
    public HeaderLinkedQueue(){
        //inicializo ant y post en un nodo cabecera
        front= end =new Node(); //Son el mismo nodo
        count=0;
    }

    @Override
    public int size() {
        return count;
    } //Devuelve el tamaño

    @Override
    public void clear() {
        front= end =new Node();
        count=0;
    }

    @Override
    public boolean isEmpty() {
        return front== end;
    }//Si ambos son el mismo nodo, esta vacio

    @Override
    public int indexOf(Object element) throws QueueException {
        if(isEmpty())
            throw new QueueException("Header Linked Queue is Empty");
        HeaderLinkedQueue aux = new HeaderLinkedQueue(); //Crea una lista auxiliar
        int pos1=1; //Guarda indices
        int pos2=-1; //si es -1 no existe
        while(!isEmpty()){
            //Si es una pila o una cola, se llega hasta que este vacia
            //Si es una lista simple o doble, se llega hasta que sea null
                //Adicionalmente, si se busca llegar al ultimo elemento sin saltarlo, se tiene que llegar a aux.next!=null
            //Para las circulares, se tiene que llegar a last para recorrer todo el arreglo
            if(Utility.compare(front(), element)==0){
                pos2 = pos1; //Si son iguales, guarda el indice
            }
            aux.enQueue(deQueue());//Encolar lo desencolado en la lista auxiliar
            pos1++;//Se sube el indice
        }//while
        //al final dejamos la cola en su estado original
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());//Encolar en la original lo desencolado en la auxiliar hasta que este vacía
        }
        return pos2;
    }

    @Override
    public void enQueue(Object element) throws QueueException {
        Node newNode = new Node(element);
        end.next = newNode; //posterior.sgte=nuevoNodo
        end = newNode; //posterior=nuevoNodo
        //al final actualizo el contador
        count++;
    }
    
    @Override
    public void enQueue(Object element, Integer priority) throws QueueException {
        Node newNode = new Node(element, priority);
        if(isEmpty()){ //la cola no existe
            end.next = newNode;
            //garantizo que anterior quede apuntando al primer nodo
            end = newNode;
        }else{ //que pasa si ya hay elementos encolados
            Node aux = front.next;
            Node prev = front.next;
            while(aux!=null&&aux.priority>=priority){
                prev = aux; //dejo un rastro
                aux = aux.next;
            }
            //se sale cuando alcanza nulo o la prioridad del nuevo elemento
            //es mayor
            if(aux==front.next){
                newNode.next = front.next;
                front.next = newNode;
            }else
                if(aux==null){
                    prev.next = newNode;
                    end = newNode;
                }else{ //en cualquier otro caso
                    prev.next = newNode;
                    newNode.next = aux;
                }
        }    
    }

    @Override
    public Object deQueue() throws QueueException {
        if(isEmpty())
            throw new QueueException("Header Linked Queue is Empty");
        Object element = front.next.data;
        //caso 1. cuando solo hay un elemento
        if(front.next== end){
            clear(); //elimino la cola
        }else{ //caso 2. caso contrario
            front.next = front.next.next;
        }
        //actualizo el contador de elementos encolados
        count--;
        return element;
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if(isEmpty())
            throw new QueueException("Header Linked Queue is Empty");
        HeaderLinkedQueue aux = new HeaderLinkedQueue();
        boolean found = false;
        while(!isEmpty()){
            if(Utility.compare(front(), element)==0){
                found = true;
            }
            aux.enQueue(deQueue());
        }//while
        //al final dejamos la cola en su estado original
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        return found;
    }

    @Override
    public Object peek() throws QueueException {
        if(isEmpty())
            throw new QueueException("Header Linked Queue is Empty");
        return front.next.data;
    }

    @Override
    public Object front() throws QueueException {
        if(isEmpty())
            throw new QueueException("Header Linked Queue is Empty");
        return front.next.data;
    }
    
    @Override
    public String toString(){
        if(isEmpty()) return "Header Linked Queue is Empty";
        String result = "\n";
        try {
            HeaderLinkedQueue aux = new HeaderLinkedQueue();
            while(!isEmpty()){
                result+=front()+"\n";
                aux.enQueue(deQueue());
            }
            //al final dejamos la cola en su estado original
            while(!aux.isEmpty()){
                enQueue(aux.deQueue());
            }
        }catch (QueueException ex) {
                System.out.println(ex.getMessage());
        }
        return result;
    }
}
