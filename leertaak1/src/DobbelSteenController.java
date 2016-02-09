import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DobbelsteenController extends JPanel implements ActionListener {

    private DobbelsteenModel dob;
    private JButton hoger;
    private JButton lager;
    private JButton gooi;


    public DobbelsteenController(DobbelsteenModel dob) {
        hoger = new JButton("Hoger");
        lager = new JButton("Lager");
        gooi = new JButton("gooi");

        this.dob = dob;
        this.add(hoger);
        hoger.addActionListener(this);

        this.add(lager);
        lager.addActionListener(this);

        this.add(gooi);
        gooi.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hoger) {
            dob.verhoog();
        }
        if (e.getSource() == lager) {
            dob.verlaag();
        }
        if (e.getSource() == gooi) {
            dob.gooi();
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }

}
