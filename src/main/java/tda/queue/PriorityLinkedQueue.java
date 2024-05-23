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
 * Cola enlazada
 */
public class PriorityLinkedQueue implements Queue {
    private Node front; //anterior
    private Node end; //posterior
    private int count; //control de elementos encolados

    //Constructor
    public PriorityLinkedQueue(){
        front= end =null;
        count=0;
    }
    
    public Node getFront(){
        return this.front;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public void clear() {
        front= end =null;
        count=0;
    }

    @Override
    public boolean isEmpty() {
        return front==null;
    }

    @Override
    public int indexOf(Object element) throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        PriorityLinkedQueue aux = new PriorityLinkedQueue();
        int pos1=1;
        int pos2=-1; //si es -1 no existe
        while(!isEmpty()){
            if(Utility.compare(front(), element)==0){
                pos2 = pos1;
            }
            aux.enQueue(deQueue());
            pos1++;
        }//while
       //al final dejamos la cola en su estado original
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        return pos2;
    }

    @Override
    public void enQueue(Object element) throws QueueException {
        Node newNode = new Node(element);
        if(isEmpty()){ //la cola no existe
            end = newNode;
            //garantizo q anterior quede apuntando al primer nodo
            front= end; //anterior=posterior
        }else{ //significa q al menos hay un elemento en la cola
            end.next = newNode; //posterior.sgte = nuevoNodo
            end = newNode; //posterior = nuevoNodo
        }
        //al final actualizo el contador
        count++;
    }
    
    @Override
    public void enQueue(Object element, Integer priority) throws QueueException {
        Node newNode = new Node(element, priority);
        if(isEmpty()){ //la cola no existe
            end = newNode;
            //garantizo que anterior quede apuntando al primer nodo
            front = end;
        }else{ //que pasa si ya hay elementos encolados
            Node aux = front;
            Node prev = front;
            while(aux!=null&&aux.priority<=priority){
                prev = aux; //dejo un rastro
                aux = aux.next;
            }
            //se sale cuando alcanza nulo o la prioridad del nuevo elemento
            //es mayor
            if(aux==front){
                newNode.next = front;
                front = newNode;
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
            throw new QueueException("Linked Queue is Empty");
        Object element = front.data;
        //caso 1. cuando solo hay un elemento
        //cuando estan apuntando al mismo nodo
        if(front== end){
            clear(); //elimino la cola
        }else{ //caso 2. caso contrario
            front = front.next; //anterior=anterior.sgte
        }
        //actualizo el contador de elementos encolados
        count--;
        return element;
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        PriorityLinkedQueue aux = new PriorityLinkedQueue();
        boolean finded = false;
        while(!isEmpty()){
            if(Utility.compare(front(), element)==0){
                finded = true;
            }
            aux.enQueue(deQueue());
        }//while
        //al final dejamos la cola en su estado original
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        return finded;
    }

    @Override
    public Object peek() throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        return front.data;
    }

    @Override
    public Object front() throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        return front.data;
    }
    
    public Integer frontPriority() throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty");
        return front.priority;
    }
    
    @Override
    public String toString(){
        if(isEmpty()) return "Linked Queue is Empty";
        String result = "\n";
        try {
            PriorityLinkedQueue aux = new PriorityLinkedQueue();
            while(!isEmpty()){
                result+=front()+"\n";
                aux.enQueue(deQueue());
            }
            //al final dejamos la cola en su estado original
            while(!aux.isEmpty()){
                enQueue(aux.deQueue());
            }
        }catch(QueueException ex){
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
