package ui;

import multiformat.Calculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorStatsView extends JPanel implements ActionListener {


    private int addCount;
    private int minusCount;
    private int addOperandCount;
    private int deleteCount;
    private int multiplyCount;
    private int divisionCount;

    private JLabel addLabel;
    private JLabel minusLabel;
    private JLabel addOperandLabel;
    private JLabel deleteLabel;
    private JLabel multiplyLabel;
    private JLabel divisionLabel;

    public CalculatorStatsView() {
        setLayout(new GridLayout(3,2));

        addCount = 0;
        minusCount = 0;
        addOperandCount = 0;
        deleteCount = 0;
        multiplyCount = 0;
        divisionCount = 0;

        addLabel = new JLabel(Calculator.ADD);
        minusLabel = new JLabel(Calculator.MINUS);
        addOperandLabel = new JLabel(Calculator.ADD_OPERAND);
        deleteLabel = new JLabel(Calculator.DELETE);
        multiplyLabel = new JLabel(Calculator.MULTIPLY);
        divisionLabel = new JLabel(Calculator.DIVISION);
        add(addLabel);
        add(minusLabel);
        add(addOperandLabel);
        add(deleteLabel);
        add(multiplyLabel);
        add(divisionLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if(action == null){
            return;
        }
        switch(action){
            case Calculator.ADD:
                addCount++;
                addLabel.setText(Calculator.ADD + ": " + addCount);
                break;
            case Calculator.MINUS:
                minusCount++;
                minusLabel.setText(Calculator.MINUS + ": " +minusCount);
                break;
            case Calculator.ADD_OPERAND:
                addOperandCount++;
                addOperandLabel.setText(Calculator.ADD_OPERAND + ": " + addOperandCount);
                break;
            case Calculator.DELETE:
                deleteCount++;
                deleteLabel.setText(Calculator.DELETE + ": " + deleteCount/2);
                break;
            case Calculator.MULTIPLY:
                multiplyCount++;
                multiplyLabel.setText(Calculator.MULTIPLY + ": " + multiplyCount);
                break;
            case Calculator.DIVISION:
                divisionCount++;
                divisionLabel.setText(Calculator.DIVISION + ": " + divisionCount);
                break;
        }
    }
}
