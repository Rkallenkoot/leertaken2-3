import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TekstView extends JPanel implements ActionListener {
    private JTextField steenRoodVeld = new JTextField();

    private DobbelsteenModel d;
    private int keer;

    public TekstView() {
        this.setLayout(new FlowLayout());
        this.add(steenRoodVeld);
    }

    public void actionPerformed(ActionEvent e) {
        d = (DobbelsteenModel) e.getSource();
        keer++;
        steenRoodVeld.setText("Waarde : " + d.getWaarde() + "\n Keer " + keer);
    }

    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }
}
