import java.util.Iterator;

public interface Tree<E> extends Comparable<E>{

    boolean search(E e);
    boolean insert(E e);
    boolean delete(E e);

    void inorder();
    void postorder();
    void preorder();

    int getSize();
    boolean isEmpty();

    Iterator<E> iterator();

}
