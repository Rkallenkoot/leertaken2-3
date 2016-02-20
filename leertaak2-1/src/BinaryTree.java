import java.util.Stack;

public class BinaryTree<E extends Comparable<E>> extends AbstractTree<E>{

    protected TreeNode<E> root;
    protected int size = 0;

    public BinaryTree(TreeNode<E> root){
        this.root = root;
    }

    public BinaryTree(E[] objects) {
        for (E object : objects) {
            insert(object);
        }
    }

    @Override
    public boolean search(E e) {
        return false;
    }

    @Override
    public boolean insert(E e) {
        if(root == null){
            root = createNewNode(e);
        }
        else {
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while(current != null){
                if(e.compareTo(current.element) < 0){
                    parent = current;
                    current = current.left;
                }
                else if (e.compareTo(current.element) > 0){
                    parent = current;
                    current = current.right;
                }
                else {
                    return false;
                }
            }
            if(e.compareTo(parent.element) < 0){
                parent.left = createNewNode(e);
            }
            else if(e.compareTo(parent.element) > 0){
                parent.right = createNewNode(e);
            }
        }
        size++;
        return true;
    }

    private TreeNode<E> createNewNode(E e) {
        return new TreeNode<>(e);
    }

    @Override
    public boolean delete(E e) {
        return false;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public int compareTo(E o) {
        return o.compareTo(root.element);
    }

    @Override
    public void inorder(){
        Stack<TreeNode<E>> stack = new Stack<>();
        TreeNode<E> current = root;
        while(!stack.isEmpty() || current != null){
            if(current != null){
                stack.push(current);
                current = current.left;
            } else {
                current = stack.pop();
                System.out.println(current.element);
                current = current.right;
            }
        }
    }

    @Override
    public void preorder(){
        Stack<TreeNode<E>> stack = new Stack<>();
        TreeNode<E> current = root;
        while(!stack.isEmpty() || current != null){
            if(current != null){
                stack.push(current);
                System.out.println(current.element);
                current = current.left;
            } else {
                current = stack.pop();
                current = current.right;
            }
        }
    }

    public void postorderRec(TreeNode<E> node){
        if(node == null) return;
        postorderRec(node.left);
        postorderRec(node.right);
        System.out.println(node.element);
    }

    public void postorder(){
        Stack<TreeNode<E>> stack = new Stack<>();
        TreeNode<E> current = root;
        while(!stack.isEmpty() || current != null){

        }
    }

    public void inorderRec(TreeNode<E> root) {
        if(root == null) return;
        inorderRec(root.left);
        System.out.println(root.element + " ");
        inorderRec(root.right);
    }

    public void clear(){
        root = null;
        size = 0;
    }
}
