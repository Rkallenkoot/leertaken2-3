import classifier.DecisionTree;
import classifier.Feature;
import classifier.FeatureType;
import classifier.Item;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class ClassifierView extends JFrame implements ActionListener, ItemListener {

    private DecisionTreeModel tree;
    private ArrayList<JCheckBox> checkBoxArrayList;
    private List<String> features;

    public ClassifierView(DecisionTreeModel tree) {
        checkBoxArrayList = new ArrayList<>();
        this.tree = tree;
        this.setTitle("Classifier");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,700);
        this.setVisible(true);
        setLayout(new BorderLayout());
        this.setupLabelPanel();
        this.setupButton();
    }

    private void setupLabelPanel(){
        JPanel labelPanel = new JPanel();
        features = tree.getFeatureKeys();
        labelPanel.setLayout(new GridLayout(features.size()+1, 2));
        JLabel featureLabel = new JLabel("Feature");
        JLabel checkboxLabel = new JLabel("Heeft uw auto dit?");

        labelPanel.add(featureLabel);
        labelPanel.add(checkboxLabel);

        for (String feature : features) {
            JLabel label = new JLabel(feature);
            JCheckBox checkbox = new JCheckBox("", false);

            checkBoxArrayList.add(checkbox);

            labelPanel.add(label);
            labelPanel.add(checkbox);
        }
        this.add(labelPanel, BorderLayout.CENTER);
        invalidate();
    }

    private void setupButton(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));
        JButton checkCategoryButton = new JButton("Check category");
        checkCategoryButton.addActionListener(e -> {
            Feature itemFeatures[] = new Feature[features.size()];
            for (int i = 0; i < checkBoxArrayList.size(); i++) {
                FeatureType type = tree.getFeatureType(features.get(i));
                String selected = checkBoxArrayList.get(i).isSelected() ? "1" : "0";
                itemFeatures[i] = new Feature(features.get(i), selected, type);
            }
            Item item = new Item("testItem", itemFeatures);
            String category = tree.getAssignedCategory(item);
            JOptionPane.showMessageDialog(this, category);
        });
        panel.add(checkCategoryButton);

        this.add(panel, BorderLayout.SOUTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

    }
}
