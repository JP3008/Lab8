package tda.list;

import tda.help.Utility;

public class SinglyLinkedList implements List {
    private Node first; //apuntador al inicio de la lista

    public SinglyLinkedList() {
        this.first = null; //la lista no existe
    }

    @Override
    public int size() throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        int count=0;
        while(aux!=null){
            count++;
            aux = aux.next; //lo movemos al sgte nodo
        }
        return count;
    }

    @Override
    public void clear() {
        this.first = null; //anulamos la lista
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
        while(aux!=null){
            if(Utility.compare(aux.data, element)==0){
                return true;
            }
            aux = aux.next; //lo movemos al sgte nodo
        }
        return false; //indica q el elemento no existe
    }

    public boolean containsTwo(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is empty");
        }
        Node aux = first;
        while(aux!=null){
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
            first = newNode;
        }else{
            Node aux = first;
            //mientras no llegue al ult nodo
            while(aux.next!=null){
                aux=aux.next;
            }
            //una vez que se sale del while, quiere decir q
            //aux esta en el ult nodo, por lo q lo podemos enlazar
            //con el nuevo nodo
            aux.next = newNode;
        }

    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = newNode;
        }else{
            newNode.next = first;
            first = newNode;
        }

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
        //Si la lista esta vacía lo agrega en el primero
        if (isEmpty()) {
            first = newNode;
        } else {
            //Sino si el que esta en el primer nodo es mayor a el nuevo, este pasa a ser el primero
            if (Utility.compare(first.data, newNode.data) > 0) {
                newNode.next = first;
                first = newNode;
            } else {
                //Sino mientras el auxiliar sea diferente de null y los nodos sean menores que el, los dos avanzan
                while (auxiliar.next != null && Utility.compare(auxiliar.next.data, newNode.data) < 0) {
                    auxiliar = auxiliar.next;
                }
                //Cuando llega al nodo que es mayor que el, el nuevo toma su posición y el nodo mayor pasa a ser el siguiente
                positionOldNode = auxiliar.next;
                auxiliar.next = newNode;
                newNode.next = positionOldNode;
            }
        }
    }

    @Override
    public void remove(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
        }
        //Caso 1. El elemento a suprimir esta al inicio
        if(Utility.compare(first.data, element)==0){
            first = first.next; //saltamos el primer nodo
        }else{  //Caso 2. El elemento a suprimir puede estar al medio o final
            Node prev = first; //dejo un apuntador al nodo anterior
            Node aux = first.next;
            while(aux!=null && !(Utility.compare(aux.data, element)==0)){
                prev = aux;
                aux = aux.next;
            }
            //se sale cuando alcanza nulo o cuando encuentra el elemento
            if(aux!=null && Utility.compare(aux.data, element)==0){
                //ya lo encontro, procedo a desenlazar el nodo
                prev.next = aux.next;
            }
        }
    }

    @Override
    public Object removeFirst() throws ListException {

        Node aux;
        if (isEmpty() || size() < 2) {
            throw new ListException("Singly Linked List must have at least two nodes");
        }

        aux = first.next;

        first=aux;

        return aux;
    }
    @Override
    public Object removeLast() throws ListException {

        Node aux = first;

        if (isEmpty() || size() < 2) {
            throw new ListException("Singly Linked List must have at least two nodes");
        }

        while (aux.next != null) {
            aux = aux.next;
        }//end while

        aux.next = null;

        return aux;
    }
    @Override
    public void sort() throws ListException {
        //Validar si la lista esta vacía o tiene solo un número ya que no se podría acomodar
        if (isEmpty() || size() < 2) {
            throw new ListException("Singly Linked List must have at least two nodes");
        }
        //Nodos temporales para realizar un ordenamiento burbuja
        Node auxiliar = first, temp1, temp2;
        Object temp3;//Objeto que pueda almacenar cualquier valor de un nodo
        // Mientras no llegue al penúltimo nodo
        while (auxiliar.next != null && auxiliar.next.next != null) {
            //Se incia en el primer nodo y en el segundo
            temp1 = auxiliar;
            temp2 = auxiliar.next;
            while (temp2 != null) {
                //Se recorre todos los nodos comparandolo con el temp1 para verificar si este es mayor a los nodos
                if (Utility.compare(temp1.data, temp2.data) > 0) {
                    //Si este mayor al nodo en el que esta temp2 se cambian lugar usando el ordenamiento burbuja
                    temp3 = temp1.data;
                    temp1.data = temp2.data;
                    temp2.data = temp3;
                }
                temp1 = temp1.next;
                temp2 = temp2.next;
                if (temp1 == auxiliar.next) {
                    //Se compara que se haya hecho la modificación
                    //Y temp1 vuelve a tomar el valor de auxiliar para seguir comparando con los demás nodos
                    temp1 = auxiliar;
                }
            }//Se finaliza cuando temp1 se comparo con todos los nodos y tomo su posición de menor a mayor
            //Se sigue con el siguiente nodo
            auxiliar = auxiliar.next;
        }
    }

    @Override
    public int indexOf(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
        }
        Node aux = first;
        int index=1; //la lista inicia en 1
        while(aux!=null){
            if(Utility.compareTwo(aux.data, element)==0){
                return index;
            }
            index++; //incremento el indice
            aux=aux.next; //muevo aux al sgte nodo
        }
        return -1; //indica q el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
        }
        Node aux = first;
        //mientras no llegue al ult nodo
        while(aux.next!=null){
            aux=aux.next;
        }
        //se sale del while cuando aux esta en el ult nodo
        return aux.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
        }
        if(Utility.compare(first.data, element)==0){
            return "It's the first, it has no previous";
        }
        Node aux = first;
        //mientras no llegue al ult nodo
        while(aux.next!=null){
            if(Utility.compare(aux.next.data, element)==0){
                return aux.data; //retornamos la data del nodo actual
            }
            aux=aux.next;
        }
        return "Does not exist in Single Linked List";
    }

    //Gets the nextValue
    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()){
            throw new ListException("There's no such element");
        }

        Node aux = first;
        while (aux.next!=null){
            if (aux.next.next==null){
                return "It's the last element. data not retrievable";
            }

            if(Utility.compare(aux.next.data,element)==0) {
                aux = aux.next;
                aux = aux.next;
                return aux.data;
            }

            aux = aux.next;
        }
        return "Non existant in this Singly  Linked list";
    }
    @Override
    public Node getNode(int index) throws ListException {
        if(isEmpty()){
            throw new ListException("Singly Linked List is Empty");
        }
        Node aux = first;
        int i = 1; // pos del primer nodo
        while(aux!=null){
            if(Utility.compare(i, index)==0) {  //ya encontro el indice
                return aux;
            }
            i++; //incremento la var local
            aux = aux.next; //muevo aux al sgte nodo
        }
        return null; //si llega aqui es xq no encontro el index
    }

    @Override
    public String toString() {
        String result = "Singly Linked List Content\n\n";
        Node aux = first;
        while(aux!=null){
            result+= aux.data+"\n";
            aux = aux.next;
        }
        return result;
    }
}
