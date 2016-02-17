package ui;

import multiformat.*;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

/**
 * CommandController class which contains the buttons that control the Calculator
 *
 * @version 0.0.1
 * @author Iris Meijer
 * @author Roelof Kallenkoot
 */
public class CommandController extends JPanel implements ActionListener {

    private Calculator mCalc;

    private JButton mHexBase;
    private JButton mDecimalBase;
    private JButton mOctalBase;
    private JButton mBinaryBase;

    private JButton mMultiplyButton;
    private JButton mDivisionButton;
    private JButton mSubtractButton;
    private JButton mAddButton;
    private JButton mEqualsButton;

    private JButton mClearButton;
    private JButton mAddOperand;


    private JPanel mDigitButtons;

    public CommandController(Calculator mCalc) {
        this.setLayout(new BorderLayout(1,1));
        this.mCalc = mCalc;

        mDigitButtons = new JPanel();
        this.add(mDigitButtons, BorderLayout.WEST);

        setupBaseButtons();
        setupDigitButtons();
        setupInterface();
    }

    private void setupInterface(){
        JPanel actionButtons = new JPanel();
        actionButtons.setLayout(new GridLayout(0,2));

        mAddOperand = new JButton("Add OP");
        mAddOperand.addActionListener(this);
        actionButtons.add(mAddOperand);

        mClearButton = new JButton("C");
        mClearButton.addActionListener(this);
        actionButtons.add(mClearButton);

        mDivisionButton = new JButton("÷");
        mDivisionButton.addActionListener(this);
        actionButtons.add(mDivisionButton);

        mSubtractButton = new JButton("-");
        mSubtractButton.addActionListener(this);
        actionButtons.add(mSubtractButton);

        mMultiplyButton = new JButton("X");
        mMultiplyButton.addActionListener(this);
        actionButtons.add(mMultiplyButton);

        mAddButton = new JButton("+");
        mAddButton.addActionListener(this);
        actionButtons.add(mAddButton);

        mEqualsButton = new JButton("=");
        mEqualsButton.addActionListener(this);
        actionButtons.add(mEqualsButton);

        this.add(actionButtons, BorderLayout.EAST);
    }

    private void setupBaseButtons(){
        JPanel baseButtons = new JPanel();


        mHexBase = new JButton("HEX");
        mHexBase.addActionListener(e -> {
            mCalc.setBase(new HexBase());
            baseSwitched();
        });
        baseButtons.add(mHexBase);

        mDecimalBase = new JButton("DEC");
        mDecimalBase.addActionListener(e -> {
            mCalc.setBase(new DecimalBase());
            baseSwitched();
        });
        baseButtons.add(mDecimalBase);

        mOctalBase = new JButton("OCT");
        mOctalBase.addActionListener(e -> {
            mCalc.setBase(new OctalBase());
            baseSwitched();
        });
        baseButtons.add(mOctalBase);

        mBinaryBase = new JButton("BIN");
        mBinaryBase.addActionListener(e -> {
            mCalc.setBase(new BinaryBase());
            baseSwitched();
        });
        baseButtons.add(mBinaryBase);

        this.add(baseButtons, BorderLayout.NORTH);
    }

    private void baseSwitched(){
        setupDigitButtons();
        setInput(mCalc.secondOperand());
    }

    private void setupDigitButtons(){
        mDigitButtons.removeAll();

        String digits[] = mCalc.getBase().getDigits().split("");

        mDigitButtons.setLayout(new GridLayout(0, 3));

        for(String digit : digits){
            final JButton btn = new JButton(digit);
            mDigitButtons.add(btn);
            btn.addActionListener(e -> mCalc.appendInput(digit));
        }

        JButton commaButton = new JButton(".");
        commaButton.addActionListener(e -> mCalc.appendInput("."));
        mDigitButtons.add(commaButton);

        mDigitButtons.revalidate();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if(src == mMultiplyButton){
            mCalc.multiply();
        } else if (src == mAddOperand) {
            try {
                mCalc.addOperand();
            } catch (FormatException | NumberBaseException e1) {
                JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        } else if (src == mDivisionButton){
            mCalc.divide();
        } else if (src == mSubtractButton){
            mCalc.subtract();
        } else if (src == mAddButton){
            mCalc.add();
        } else if (src == mEqualsButton){
            // Currently no implementation
            setInput(mCalc.secondOperand());
        } else if (src == mClearButton){
            // Called twice because of two operands
            mCalc.delete();
            mCalc.delete();
        }
    }

    public void setInput(String input){
        mCalc.setInputText(input);
    }

    public void appendInput(String input) {
        mCalc.appendInput(input);
    }
}
