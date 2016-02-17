package ui;

import multiformat.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorResultView extends JPanel implements ActionListener {

    private JTextField mCalcInput;

    public CalculatorResultView() {
        this.setLayout(new BorderLayout());
        mCalcInput = new JTextField("");
        this.add(mCalcInput, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Calculator calc = (Calculator) e.getSource();
        mCalcInput.setText(calc.getInputText());
    }

}
