public class BinaryTree<E extends Comparable<E>> extends AbstractTree<E>{


    protected TreeNode<E> root;
    protected int size = 0;

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
        return 0;
    }

    @Override
    public int compareTo(E o) {
        return 0;
    }

    @Override
    public void inorder(){
        inorder(root);
    }

    public void inorder(TreeNode<E> root) {
        if(root == null) return;
        inorder(root.left);
        System.out.println(root.element + " ");
        inorder(root.right);
    }

    public void clear(){
        root = null;
        size = 0;
    }
}
