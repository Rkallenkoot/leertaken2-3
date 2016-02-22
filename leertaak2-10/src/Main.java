import classifier.DecisionTree;
import classifier.Feature;
import classifier.FeatureType;
import classifier.Item;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        Map<Item, String> trainingSet = new HashMap<>();
        Map<String, FeatureType> features = new HashMap<>();

        String csvFile = "/Users/roelof/IdeaProjects/Leertaken2-3/TrainingSet.txt";
        int itemCount = 0;
        int featureCount = 0;
        try {
            Reader in = new FileReader(csvFile);
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

                // features ophalen
                if(record.getRecordNumber() == 3){
                    for (int i = 1; i <= featureCount; i++) {
                        features.put(String.valueOf(i), new FeatureType(String.valueOf(i), new String[]{"0", "1"}));
                    }
                }
                System.out.println("## " + record.get(0));
                Feature featureArray[] = new Feature[featureCount];
                for (int i = 1; i <= featureCount; i++) {
                    Feature feature = new Feature(String.valueOf(i), record.get(i), features.get(String.valueOf(i)));
                    featureArray[i-1] = feature;
                }
                String itemName = record.get(0);
                String category = record.get(featureCount+1);
                trainingSet.put(new Item(itemName, featureArray), category);
                System.out.println("# Category: " + category);
            }
            System.out.println("## DONE");
            DecisionTree tree = new DecisionTree(trainingSet, features);

            System.out.println(tree.toString());
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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
