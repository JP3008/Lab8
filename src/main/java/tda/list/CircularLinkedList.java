package tda.list;

import tda.help.Utility;

public class CircularLinkedList implements List {
    private Node first; //apuntador al inicio de la lista
    private Node last; //apuntador al final de la lista

    public CircularLinkedList() {
        this.first = null; //la lista no existe
        this.last = null;
    }

    @Override
    public int size() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is empty");
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
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
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
        Node aux = first;
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }

        if (size()==1){
            if(Utility.compareTwo(aux.data, element)==0){
                return true;
            }else {
                return false;
            }
        }


        while(aux!=last){
            if(Utility.compareTwo(aux.data, element)==0){
                return true;
            }
            aux = aux.next; //lo movemos al sgte nodo
        }
        return false; //indica q el elemento no existe
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
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = last = newNode;
        }else{
            newNode.next = first;
            first = newNode;
        }
        //Se hace el enlace circular
        //Poniendo al siguiente de last a apuntar al primero para que sea circular
        last.next = first;
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
        } else {
            if (Utility.compare(first.data, newNode.data) > 0) {
                newNode.next = first;
                first = newNode;
                last.next = first;
            } else {
                while (auxiliar.next != first && Utility.compare(auxiliar.next.data, newNode.data) < 0) {
                    auxiliar = auxiliar.next;
                }
                positionOldNode = auxiliar.next;
                auxiliar.next = newNode;
                newNode.next = positionOldNode;
                if (newNode.next == first) {
                    last = newNode;
                }
            }
        }
    }
    @Override
    public void remove(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
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
            }
            //Que pasa si el elemento a suprimir esta en el último nodo
            if (aux == last && Utility.compare(aux.data, element)==0){
                last = prev;//desenlaza el último nodo
            }
        }
        //Mantengo el enlace circular
        last.next = first;

        //Otro caso
        //Que pasa si solo queda un nodo y es el que quiero eliminar
        if (first == last && Utility.compare(first.data,element)==0)
            clear();//Anulo la lista
    }

    @Override
    public Object removeFirst() throws ListException {
        if(isEmpty() || size()==1){//Verification
            throw new ListException("Circular Linked List is Empty or only has one element ");
        }
        Node auxNode;//Aux node
        auxNode = first; //Assign removed node
        first= first.next;//First node becomes the node next to it
        last.next=first;//Then we make the next node to last first, circling back

        return auxNode;
    }
    @Override
    public Object removeLast() throws ListException {

        if(isEmpty() || size()==1){//Verification
            throw new ListException("Circular Linked List is Empty or only has one element ");
        }

        Node auxNode = null;

        auxNode = last; //Saving the removed value
        last=last.prev;//We define the last node as the node right before it
        last.next=first;//And the we connect this new node to the first, cicling back

        return auxNode;
    }

    //Burbuja que ordena de acuerdo al nombre
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
    //Burbuja que ordena de acuerdo al id
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
            throw new ListException("Circular Linked List is Empty");
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
            throw new ListException("Circular Linked List is Empty");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is Empty");
        }
        return last.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }
        if (Utility.compare(first.data, element) == 0) {
            return "It's the first, it has no previous";
        }
        Node aux = first;
        while (aux.next != last) {
            if (Utility.compare(aux.next.data, element) == 0) {
                return aux.data; // return the data of the current node
            }
            aux = aux.next;
        }
        // check if the element is found in the last node
        if (Utility.compare(aux.next.data, element) == 0) {
            return aux.data; // return the data of the current node
        }
        return "Does not exist in Circular Linked List";
    }

    //Gets the nextValue
    @Override
    public Object getNext(Object element) throws ListException {
        //Hacerlo nosotros
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
        return "Non existant in this Circular Linked List";
    }
    @Override
    public Node getNode(int index) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
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
        String result = "Singly Linked List Content\n\n";
        Node aux = first;
        while(aux!=last){
            result+= aux.data+"\n";
            aux = aux.next;
        }
        return result + "\n" + aux.data;//agrega la data del ultimo nodo
    }
}
