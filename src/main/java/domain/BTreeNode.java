package domain;

public class BTreeNode {

    public BTreeNode left;
    public BTreeNode right;
    public Object data;
    public String path;

    public BTreeNode(Object data) {
        this.left = null;
        this.right = null;
        this.data = data;
    }
    public BTreeNode(Object data, String path){
        this.data=data;
        this.path=path;
        this.left=this.right=null;

    }
}
