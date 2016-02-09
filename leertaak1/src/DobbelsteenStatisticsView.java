import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DobbelsteenStatisticsView extends JPanel implements ActionListener {


    private JLabel throwsCountLabel;
    private JLabel oneThrowsCountLabel;
    private JLabel twoThrowsCountLabel;
    private JLabel threeThrowsCountLabel;
    private JLabel fourThrowsCountLabel;
    private JLabel fiveThrowsCountLabel;
    private JLabel sixThrowsCountLabel;

    private int oneThrowsCount;
    private int twoThrowsCount;
    private int threeThrowsCount;
    private int fourThrowsCount;
    private int fiveThrowsCount;
    private int sixThrowsCount;

    public DobbelsteenStatisticsView() {
        initializeProperties();
        setupInterface();
    }

    private void initializeProperties() {
        oneThrowsCount = 0;
        twoThrowsCount = 0;
        threeThrowsCount = 0;
        fourThrowsCount = 0;
        fiveThrowsCount = 0;
        sixThrowsCount = 0;
    }

    private void setupInterface() {
        // Gridlayout with 7 rows and 1 column
        setLayout(new GridLayout(7, 1));

        throwsCountLabel = new JLabel(String.format("Totaal: %d worpen", getThrowsCount()));
        oneThrowsCountLabel = new JLabel("1: 0 keer");
        twoThrowsCountLabel = new JLabel("2: 0 keer");
        threeThrowsCountLabel = new JLabel("3: 0 keer");
        fourThrowsCountLabel = new JLabel("4: 0 keer");
        fiveThrowsCountLabel = new JLabel("5: 0 keer");
        sixThrowsCountLabel = new JLabel("6: 0 keer");

        add(throwsCountLabel);
        add(oneThrowsCountLabel);
        add(twoThrowsCountLabel);
        add(threeThrowsCountLabel);
        add(fourThrowsCountLabel);
        add(fiveThrowsCountLabel);
        add(sixThrowsCountLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            DobbelsteenModel dob = (DobbelsteenModel) e.getSource();

            switch (dob.getWaarde()) {
                case 6:
                    sixThrowsCount++;
                    sixThrowsCountLabel.setText(String.format("6: %d keer", sixThrowsCount));
                    break;
                case 5:
                    fiveThrowsCount++;
                    fiveThrowsCountLabel.setText(String.format("5: %d keer", fiveThrowsCount));
                    break;
                case 4:
                    fourThrowsCount++;
                    fourThrowsCountLabel.setText(String.format("4: %d keer", fourThrowsCount));
                    break;
                case 3:
                    threeThrowsCount++;
                    threeThrowsCountLabel.setText(String.format("3: %d keer", threeThrowsCount));
                    break;
                case 2:
                    twoThrowsCount++;
                    twoThrowsCountLabel.setText(String.format("2: %d keer", twoThrowsCount));
                    break;
                case 1:
                    oneThrowsCount++;
                    oneThrowsCountLabel.setText(String.format("1: %d keer", oneThrowsCount));
                    break;
            }
            throwsCountLabel.setText(String.format("Totaal: %d worpen keer", getThrowsCount()));

        } catch (ClassCastException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getThrowsCount() {
        return oneThrowsCount + twoThrowsCount +
                threeThrowsCount + fourThrowsCount +
                fiveThrowsCount + sixThrowsCount;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(150, 100);
    }
}
