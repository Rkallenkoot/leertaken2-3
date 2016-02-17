package ui;

import multiformat.Calculator;
import multiformat.DecimalBase;
import multiformat.FixedPointFormat;

import javax.swing.*;
import java.awt.*;

/**
 * CalculatorController
 *
 * @version 0.0.1
 * @author Roelof Kallenkoot
 * @author Iris Meijer
 */
public class CalculatorController extends JFrame {


    public CalculatorController() {
        // add Contents
        Calculator calc = new Calculator();
        calc.setBase(new DecimalBase());
        CommandController commandController = new CommandController(calc);
        CalculatorResultView resultView = new CalculatorResultView();


        calc.addActionListener(resultView);

        this.setLayout(new BorderLayout());
        this.add(resultView, BorderLayout.NORTH);
        this.add(commandController, BorderLayout.CENTER);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        new CalculatorController();
    }
}
