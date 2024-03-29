import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// NOTA BENE: Deze code is niet thread-safe omdat jullie dat in de 1e week nog niet kennen.
// Zie paragraaf 30.2 voor de thread-safe implementatie.
public class DobbelsteenModel {

    private int waarde;
    private List<ActionListener> actionListenerArrayList;

    public DobbelsteenModel() {
        waarde = (int) (Math.random() * 6 + 1);
        actionListenerArrayList = new ArrayList<>();
    }

    public DobbelsteenModel(int waarde) {
        this.waarde = waarde;
        actionListenerArrayList = new ArrayList<>();
    }

    public void verhoog() {
        waarde++;
        if (waarde > 6) {
            waarde = 1;
        }
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }

    public void verlaag() {
        waarde--;
        if (waarde < 1) {
            waarde = 6;
        }

        // Merk op dat we de 3e String-parameter van de constructor van de ActionEvent niet invullen.
        // In dit geval zou je die kunnen gebruiken om de nieuwe dobbelsteenwaarde mee te geven
        // aan de ActionListener. Dan hoeft de ActionListener niet met e.getSource() weer naar
        // het model toe te gaan.
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }

    public void gooi() {
        waarde = (int) (Math.random() * 6 + 1);
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }


    public int getWaarde() {
        return waarde;
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
