import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.Enumeration;

public class Main {


    public static void main(String[] args) {
        Opdracht1();
//        Opdracht2();
    }

    /**
     * Opdracht 2
     */
    private static void Opdracht2(){

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultMutableTreeNode person = new DefaultMutableTreeNode("person");
        DefaultMutableTreeNode employee = new DefaultMutableTreeNode("employee");
        DefaultMutableTreeNode sales_rep = new DefaultMutableTreeNode("sales_rep");
        DefaultMutableTreeNode engineer = new DefaultMutableTreeNode("engineer");

        DefaultMutableTreeNode customer = new DefaultMutableTreeNode("customer");
        DefaultMutableTreeNode us_customer = new DefaultMutableTreeNode("us_customer");
        DefaultMutableTreeNode non_us_customer = new DefaultMutableTreeNode("non_us_customer");
        DefaultMutableTreeNode local_customer = new DefaultMutableTreeNode("local_customer");
        DefaultMutableTreeNode regional_customer = new DefaultMutableTreeNode("regional_customer");

        employee.add(sales_rep);
        employee.add(engineer);
        person.add(employee);

        us_customer.add(local_customer);
        us_customer.add(regional_customer);
        customer.add(us_customer);
        customer.add(non_us_customer);
        person.add(customer);

        JTree tree = new JTree(person);

        JScrollPane scrollPane = new JScrollPane(tree);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(250, 300);
        frame.setVisible(true);

        System.out.println("## BreadthFirst");
        Enumeration treeEnumeration = person.breadthFirstEnumeration();
        while(treeEnumeration.hasMoreElements()){
            System.out.println(treeEnumeration.nextElement());
        }

        System.out.println("");
        System.out.println("## PostOrder");
        Enumeration postOrder = person.postorderEnumeration();
        while(postOrder.hasMoreElements()){
            System.out.println(postOrder.nextElement());
        }

        System.out.println("");
        System.out.println("## PreOrder");
        Enumeration preOrder = person.preorderEnumeration();
        while(preOrder.hasMoreElements()){
            System.out.println(preOrder.nextElement());
        }

    }

    /**
     * Opdracht 1
     */
    private static void Opdracht1(){
        Integer ints[] = {50, 5,15,4,6,14,16};

        Integer ints1[] = {50,4,5,6,14,15,16};

        BinaryTree<Integer> tree = new BinaryTree<>(ints);

        BinaryTree<Integer> tree1 = new BinaryTree<>(ints1);

        System.out.println("## Inorder");
        tree.inorder();

        System.out.println();
        System.out.println("## Preorder");
        tree.preorder();

        System.out.println();
        System.out.println("## Postorder");
        System.out.println("Tree");
        tree.postorderRec(tree.root);
    }

}
