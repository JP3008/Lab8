package domain;

import util.Utility;

import java.util.IllegalFormatCodePointException;

public class BTree implements Tree {
    private BTreeNode root; //unica entrada al arbol

    public BTree(){
        this.root = null;
    }
    public BTreeNode getRoot(){
        return this.root;
    }

    @Override
    public int size() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return size(root);
    }

    private int size(BTreeNode node){
        if(node==null)
            return 0;
        else
            return 1+size(node.left)+size(node.right);
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return binarySearch(root, element);
    }

    //método interno
    private boolean binarySearch(BTreeNode node, Object element){
        if(node==null)
            return false;
        else
        if(util.Utility.compare(node.data, element)==0)
            return true; //ya lo encontro
        else
            return binarySearch(node.left, element)
                    || binarySearch(node.right, element);
    }


    private BTreeNode contains2(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return binarySearch2(root, element);
    }

    //método interno
    private BTreeNode binarySearch2(BTreeNode node, Object element){
        if(node==null)
            return null;

        if(util.Utility.compare(node.data, element)==0)
            return node;

        if (binarySearch2(node.left, element) != null)
            return binarySearch2(node.left,element);

        return binarySearch2(node.right, element);

    }

    @Override
    public void add(Object element) {
        //this.root = add(root, element);
        this.root = add(root, element, "root");
    }

    /**
     * else if(node.left==null){
     *    node.left = add(node.left, element);
     * }else if(node.right==null){
     *     node.right = add(node.right, element);
     * }
     * */
    private BTreeNode add(BTreeNode node, Object element){
        if(node==null){ //si el arbol esta vacio
            node = new BTreeNode(element);
        }else{
            //debemos establecer algun criterio de insercion
            int value = util.Utility.getRandom(10);
            if(value%2==0) //si es par inserte por la izq
                node.left = add(node.left, element);
            else //si es impart inserte por la der
                node.right = add(node.right, element);
        }
        return node;
    }

    private BTreeNode add(BTreeNode node, Object element, String path){
        if(node==null){ //si el arbol esta vacio
            node = new BTreeNode(element, path);
        }else{
            //debemos establecer algun criterio de insercion
            int value = util.Utility.getRandom(10);
            if(value%2==0) //si es par inserte por la izq
                node.left = add(node.left, element, path+"/left");
            else //si es impart inserte por la der
                node.right = add(node.right, element, path+"/right");
        }
        return node;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("Binary Tree is empty");
        root = remove(root, element);
        if (root != null) {
            updatePaths(root, "root");
        }
    }

    private void updatePaths(BTreeNode node, String path){
        if (node != null) {
        node.path = path;
        updatePaths(node.left, path + "/left");
        updatePaths(node.right, path + "/right");
    }
    }


    private BTreeNode remove(BTreeNode node, Object element) {
        if (node != null) {
            if (util.Utility.compare(element, node.data) == 0) {
                if (node.left == null && node.right == null) {
                    return null; // Caso 1: sin hijos
                } else if (node.left == null) {
                    //node.right = newPath(node.right,node.path);
                    return node.right; // Caso 2: un hijo (derecha)
                } else if (node.right == null) {
                    //node.left = newPath(node.left,node.path);
                    return node.left; // Caso 2: un hijo (izquierda)
                } else {
                    // Caso 3: dos hijos
                    Object value = getLeaf(node.right);
                    node.data = value;
                    node.right = removeLeaf(node.right, value);
                }
            } else {
                node.left = remove(node.left, element);
                node.right = remove(node.right, element);
            }
        }
        return node;
    }

    private BTreeNode removeLeaf(BTreeNode node, Object value) {
        if (node == null) {
            return null;
        } else if (node.left == null && node.right == null && util.Utility.compare(node.data, value) == 0) {
            return null;
        } else {
            node.left = removeLeaf(node.left, value);
            node.right = removeLeaf(node.right, value);
            return node;
        }
    }

    private Object getLeaf(BTreeNode node) {
        Object aux;
        if (node == null) {
            return null;
        } else if (node.left == null && node.right == null) {
            return node.data;
        } else {
            aux = getLeaf(node.left);
            if (aux == null) {
                aux = getLeaf(node.right);
            }
            return aux;
        }
    }


    @Override
    public int height(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int counter){
        if (node==null){
            return 0;
        }else if (Utility.compare(node.data,element)==0){
            return counter;
        }else{
            return Math.max(height(node.left,element,++counter),height(node.right,element,counter));
        }
    }

    @Override
    public int height() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return height(root)-1;
    }

    private int height(BTreeNode node){
        if (node==null) {
            return 0;
        }
        return Math.max(height(node.left),height(node.right))+1;
    }

    @Override
    public Object min() throws TreeException {
        return null;
    }

    @Override
    public Object max() throws TreeException {
        return null;
    }

    @Override
    public String preOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return preOrder(root)+"\n";
    }


    private String InOrder() throws TreeException {
        return null;
    }

    //node-left-right
    public String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result =  node.data+"("+node.path+")"+"\n";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return "InOrder Tour: \n" + inOrder(root);
    }

    //left-node-right
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+"("+node.path+") "+"\n";
            result += inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return "PostOrder Tour: \n" + postOrder(root);
    }

    //left-right-node
    private String postOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data+"("+node.path+")"+"\n";

        }
        return result;
    }

    public int heigth(Object obj) throws TreeException {

        BTreeNode node = contains2(obj);
        String str = node.path;
        int counter = 0;
        for (int i = 0; i <str.length() ; i++) {
            if (str.charAt(i)== '/'){
                counter++;
            }

        }
        return counter;
    }


    public String preorderTour(BTreeNode node) {
        String result="";
        if (node!=null){
            result="(" + node.path + ") ";
        }
        return result;

    }

    public String BtreeTour() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("El árbol está vacío");
        }
        return "PreOrder Tour: \n" + BtreeTour(root);

    }

    private String BtreeTour(BTreeNode node) {
        String result="";
        if (node!=null){
            result=node.data + " ";
            result+=BtreeTour(node.left);
            result+=BtreeTour(node.right);
        }
        return result;

    }

    //preOrder: recorre el árbol de la forma: nodo-izq-der
    //inOrder: recorre el árbol de la forma: izq-nodo-der
    //postOrder: recorre el árbol de la forma: izq-der-nodo
    @Override
    public String toString() {
        if(isEmpty())
            return "Binary Tree is empty";
        String result = "BINARY TREE TOUR...\n";
        result+="o---o---o---o---o---o---o---o---o---o---o\n";
        result+=" PreOrder: \n"+preOrder(root)+"\n";
        result+="o---o---o---o---o---o---o---o---o---o---o\n";
        result+=" InOrder: \n"+inOrder(root)+"\n";
        result+="o---o---o---o---o---o---o---o---o---o---o\n";
        result+=" PostOrder: \n"+postOrder(root)+"\n";
        return result;
    }


}
