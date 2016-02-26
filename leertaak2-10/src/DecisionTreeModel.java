import classifier.DecisionTree;
import classifier.Feature;
import classifier.FeatureType;
import classifier.Item;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class DecisionTreeModel {

    private List<ActionListener> actionListenerArrayList;

    private static final String TRAINING_SET = "TrainingSet.txt";
    private static final String OPTIONS = "OptiesText.txt";

    private Map<Item, String> trainingSet = new HashMap<>();
    private Map<String, FeatureType> features;
    private List<String> featureKeys;
    private DecisionTree tree;

    private int featureCount;

    public DecisionTreeModel(){
        actionListenerArrayList = new ArrayList<>();
        featureCount = 0;
        featureKeys = new ArrayList<>();
        features = new HashMap<>();
        buildFeaturesMap();
        buildTree();
        System.out.println(tree.toString());
    }

    private void buildFeaturesMap(){
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            Scanner scan = new Scanner(new File(classLoader.getResource(OPTIONS).getFile()));
            FeatureType yn = new FeatureType("yn", new String[]{"0","1"});
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                featureKeys.add(line);
                features.put(line, yn);
            }
        } catch(FileNotFoundException ex){
            ex.printStackTrace();
        }
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }

    public void buildTree(){
        ClassLoader classLoader = getClass().getClassLoader();
        int itemCount = 0;
        try {
            Reader in = new FileReader(classLoader.getResource(TRAINING_SET).getFile());
            CSVFormat format = CSVFormat.newFormat(';');
            Iterable<CSVRecord> records = format.parse(in);
            for(CSVRecord record: records){
                // Skip de eerste twee records
                if(record.getRecordNumber() == 1){
                    featureCount = Integer.parseInt(record.get(1));
                    continue;
                }
                if(record.getRecordNumber() == 2){
                    itemCount = Integer.parseInt(record.get(1));
                    continue;
                }

                Feature featureArray[] = new Feature[featureCount];
                for (int i = 0; i < featureCount; i++) {
                    Feature feature = new Feature(featureKeys.get(i), record.get(i+1), features.get(featureKeys.get(i)));
                    featureArray[i] = feature;
                }
                String itemName = record.get(0);
                String category = record.get(featureCount+1);
                trainingSet.put(new Item(itemName, featureArray), category);
            }
            tree = new DecisionTree(trainingSet, features);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAssignedCategory(Item item){
        return tree.assignCategory(item);
    }

    public void testItem(){
        Feature testFeatureArray[] = new Feature[]{
                new Feature("1", "1", features.get("1")),
                new Feature("2", "1", features.get("2")),
                new Feature("3", "0", features.get("3")),
                new Feature("4", "0", features.get("4")),
                new Feature("5", "0", features.get("5")),
                new Feature("6", "0", features.get("6")),
                new Feature("7", "1", features.get("7")),
                new Feature("8", "1", features.get("8"))
        };
        Item testItem = new Item("testItem", testFeatureArray);
        System.out.println(tree.assignCategory(testItem));
    }

    public List<String> getFeatureKeys() {
        return featureKeys;
    }

    public Map<String, FeatureType> getFeatures() {
        return features;
    }

    public FeatureType getFeatureType(String key){
        return features.get(key);
    }

    private void processEvent(ActionEvent actionEvent) {
        for (ActionListener al : actionListenerArrayList) {
            al.actionPerformed(actionEvent);
        }
    }

    public void addActionListener(ActionListener actionListener) {
        actionListenerArrayList.add(actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        if (actionListenerArrayList.contains(actionListener)) {
            actionListenerArrayList.remove(actionListener);
        }
    }
}
