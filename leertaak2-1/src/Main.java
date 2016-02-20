
public class Main {


    public static void main(String[] args) {

        Integer ints[] = {50, 5,15,4,6,14,16};
        Integer ints1[] = {50,4,5,6,14,15,16};

        BinaryTree<Integer> tree = new BinaryTree<>(ints);
        BinaryTree<Integer> tree1 = new BinaryTree<>(ints1);

//        System.out.println("## Inorder");
//        tree.inorder();
//        tree1.inorder();
//
//        System.out.println();
//        System.out.println("## Preorder");
//        tree.preorder();
//        tree1.preorder();

        System.out.println();
        System.out.println("## Postorder");
        System.out.println("Tree");
        tree.postorderRec(tree.root);
        System.out.println("Tree 1");
        tree1.postorderRec(tree.root);
    }

}
