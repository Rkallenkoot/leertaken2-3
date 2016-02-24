
public class Main {

    public static void main(String[] args) {

        DecisionTreeModel builder = new DecisionTreeModel();

        ClassifierView view = new ClassifierView(builder);

        builder.addActionListener(view);
    }

}
