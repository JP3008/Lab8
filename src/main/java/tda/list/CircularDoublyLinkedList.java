package tda.list;

import tda.help.Utility;

public class CircularDoublyLinkedList implements List {
    private Node first; //apuntador al inicio de la lista
    private Node last; //apuntador al final de la lista

    public CircularDoublyLinkedList() {
        this.first = null; //la lista no existe
        this.last = null;
    }

    @Override
    public int size() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        Node aux = first;
        int count=0;
        while(aux!=last){
            count++;
            aux = aux.next; //lo movemos al sgte nodo
        }
        return count +1;
    }

    @Override
    public void clear() {
        this.first = null; //anulamos la lista
        this.last = null;
    }

    @Override
    public boolean isEmpty() {
        return this.first == null; //si es nulo está vacía
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        Node aux = first;
        if (size()==1){
            if(Utility.compare(aux.data, element)==0){
                return true;
            }else
                return false;
        }


        while(aux!=last){
            if(Utility.compare(aux.data, element)==0){
                return true;
            }
            aux = aux.next; //lo movemos al sgte nodo
        }
        //Se sale del while cuando aux es igual a last entonces solo nos queda verificar
        //Si el elemento a buscar esta en el último nodo
        //Si en el último nodo es igual retorna true sino false
        return Utility.compare(aux.data, element)==0;
    }

    public boolean containsTwo(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        Node aux = first;
        if (size()==1){
            if(Utility.compareTwo(aux.data, element)==0){
                return true;
            }else
                return false;
        }
        while(aux!=last){
            if(Utility.compareTwo(aux.data, element)==0){
                return true;
            }
            aux = aux.next; //lo movemos al sgte nodo
        }
        return Utility.compare(aux.data, element)==0;
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = last = newNode;
        }else {
            last.next = newNode;
            //Pones last a apuntar al nuevo nodo
            last = newNode;
        }
        //Se hace el enlace circular
        //Poniendo al siguiente de last a apuntar al primero para que sea circular
        last.next = first;
        //Hacemos el enlace circular y doble
        first.prev = last;
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = last = newNode;
        }else{
            newNode.next = first;
            first.prev = newNode;
            first = newNode;
        }
        //Garantizo siempre el enlace circular y doble
        last.next = first;
        first.prev = last;
    }

    @Override
    public void addLast(Object element) {
        add(element);
    }

    @Override
    public void addInSortedList(Object element) {
        Node newNode = new Node(element);
        Node auxiliar = first;
        Node positionOldNode;
        if (isEmpty()) {
            first = newNode;
            first.prev = first;
            first.next = first;
        } else {
            if (Utility.compare(first.data, newNode.data) > 0) {
                newNode.next = first;
                newNode.prev = first.prev;
                first.prev = newNode;
                first = newNode;
            } else {
                while (auxiliar.next != first && Utility.compare(auxiliar.next.data, newNode.data) < 0) {
                    auxiliar = auxiliar.next;
                }
                positionOldNode = auxiliar.next;
                auxiliar.next = newNode;
                newNode.prev = auxiliar;
                newNode.next = positionOldNode;
                positionOldNode.prev = newNode;
            }
        }
    }

    @Override
    public void remove(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        //Caso 1. El elemento a suprimir esta al inicio
        if(Utility.compareTwo(first.data, element)==0){
            first = first.next; //saltamos el primer nodo
        }else{  //Caso 2. El elemento a suprimir puede estar al medio o final
            Node prev = first; //dejo un apuntador al nodo anterior
            Node aux = first.next;
            while(aux!=last && !(Utility.compareTwo(aux.data, element)==0)){
                prev = aux;
                aux = aux.next;
            }
            //se sale cuando auxiliar es igual a last o cuando encuentra el elemento
            if(Utility.compareTwo(aux.data, element)==0){
                //ya lo encontro, procedo a desenlazar el nodo
                prev.next = aux.next;
                //mantengo el doble enlace
                aux.next.prev = prev;
            }
            //Que pasa si el elemento a suprimir esta en el último nodo
            if (aux == last && Utility.compareTwo(aux.data, element)==0){
                last = prev;//desenlaza el último nodo

            }
        }
        //Mantengo el enlace circular y doble
        last.next = first;
        first.prev = last;

        //Otro caso
        //Que pasa si solo queda un nodo y es el que quiero eliminar
        if (first == last && Utility.compare(first.data,element)==0)
            clear();//Anulo la lista
    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty())
            throw new ListException("Circular Doubly Linked List is Empty");

        Node removedNode = first;
        if (first.next == first) {
            // Caso especial: solo hay un nodo en la lista
            first = last = null;
        } else {
            // Actualizar el primer nodo y los enlaces previos y siguientes
            first = first.next;
            first.prev = last;
            last.next = first;
        }

        // Devolver el elemento del nodo eliminado
        return removedNode.data;
    }



    @Override
    public Object removeLast() throws ListException {
        if(isEmpty() || size()==1){//Verification
            throw new ListException("Circular Linked List is Empty or only has one element ");
        }

        Node auxNode = first;
        Node save = last; //Saving the removed value
        while (auxNode.next!=last){
            auxNode=auxNode.next;
        }

        auxNode.next=first;//Link del antipenultimo al primer
        last=auxNode;//Definimos ultimo como auxnode    
        first.prev=auxNode;//Link de vuelta al previo del primero



        return save;
    }
    //Burbuja que ordena de acuerdo a los nombres
    @Override
    public void sort() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is Empty");
        }
        for (int i = 1; i <= size() ; i++) {
            for (int j = i+1; j <= size() ; j++) {
                if(Utility.compare(getNode(j).data, getNode(i).data)<0){
                    Object aux = getNode(i).data;
                    getNode(i).data = getNode(j).data;
                    getNode(j).data = aux;
                }
            }
        }
    }
    //Burbuja que ordena segun el id
    public void sort2() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is Empty");
        }
        for (int i = 1; i <= size() ; i++) {
            for (int j = i+1; j <= size() ; j++) {
                if(Utility.compareTwo(getNode(j).data, getNode(i).data)<0){
                    Object aux = getNode(i).data;
                    getNode(i).data = getNode(j).data;
                    getNode(j).data = aux;
                }
            }
        }
    }
    @Override
    public int indexOf(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        Node aux = first;
        int index=1; //la lista inicia en 1
        while(aux!=last){
            if(Utility.compareTwo(aux.data, element)==0){
                return index;
            }
            index++; //incremento el indice
            aux=aux.next; //muevo aux al sgte nodo
        }
        //Se sale cuando alcanza last
        if(Utility.compareTwo(aux.data, element)==0){
            return index;
        }
        return -1; //indica q el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        return last.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
        }
        if(Utility.compare(first.data, element)==0){
            return "It's the first, it has no previous";
        }
        Node aux = first;
        //mientras no llegue al ult nodo
        while(aux.next!=last){
            if(Utility.compare(aux.next.data, element)==0){
                return aux.data; //retornamos la data del nodo actual
            }
            aux=aux.next;
        }
        //se sale cuando aux.next == last
        if(Utility.compare(aux.next.data, element)==0){
            return aux.data; //retornamos la data del nodo actual
        }
        return "Does not exist in Circular Doubly Linked List";
    }

    //Gets the nextValue
    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()){
            throw new ListException("There's no such element");
        }
        Node aux = first;
        while (aux!=last){
            if(Utility.compare(aux.next.data,element)==0) {
                aux = aux.next;
                aux = aux.next;
                return aux.data;
            }
            aux = aux.next;
        }
        if (Utility.compare(aux.next.data,element)==0){
            aux = aux.next;
            return aux;
        }
        return "Non existant in this Circular Doubly Linked List";
    }
    @Override
    public Node getNode(int index) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node aux = first;
        int i = 1; // pos del primer nodo
        while(aux!=last){
            if(Utility.compare(i, index)==0) {  //ya encontro el indice
                return aux;
            }
            i++; //incremento la var local
            aux = aux.next; //muevo aux al sgte nodo
        }
        if(Utility.compare(i, index)==0) {  //ya encontro el indice
            return aux;
        }
        return null; //si llega aqui es xq no encontro el index
    }

    @Override
    public String toString() {
        String result = "Circular Doubly Linked List Content\n\n";
        Node aux = first;
        while(aux!=last){
            result+= aux.data+"\n";
            aux = aux.next;
        }
        return result + "\n" + aux.data;//agrega la data del ultimo nodo
    }
}
