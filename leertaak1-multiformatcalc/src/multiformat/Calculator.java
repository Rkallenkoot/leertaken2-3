/*
 * (C) Copyright 2005 Davide Brugali, Marco Torchiano
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */
package multiformat;

import java.awt.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The multiformat calculator
 *
 * @version 0.0.2
 * @author Iris Meijer
 * @author Roelof Kallenkoot
 */
public class Calculator {

    public static final String ADD = "ADD";
    public static final String MINUS = "MIN";
    public static final String MULTIPLY = "MUL";
    public static final String DIVISION = "DIV";

    public static final String ADD_OPERAND = "ADD_OPERAND";
    public static final String DELETE = "DELETE";


    private String inputText;

    private Rational operand_0 = new Rational();
    private Rational operand_1 = new Rational();

    // The current format of the calculator
    private Format format = new FixedPointFormat();
    // The current numberbase of the calculator
    private Base base = new DecimalBase();
    private ArrayList<ActionListener> actionListenerArrayList;


    public Calculator() {
        inputText = "";
        actionListenerArrayList = new ArrayList<>();
    }

    /**
     * Adds the current inputText as an Operand
     *
     * @throws FormatException     Incorrect Format
     * @throws NumberBaseException Incorrect digit for Base
     */
    public void addOperand() throws FormatException, NumberBaseException {
        String operand = this.inputText;
        operand_1 = operand_0;
        operand_0 = format.parse(operand, base);
        setInputText("");
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ADD_OPERAND));
    }

    public void addOperand(String newOperand) throws FormatException, NumberBaseException {
        this.validateOperand(newOperand);
        operand_1 = operand_0;
        operand_0 = format.parse(newOperand, base);
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ADD_OPERAND));
    }

    private void validateOperand(String operand) throws NumberBaseException {
        String digits[] = this.base.getDigits().split("");
        if(operand.length() == 1){
            if(!Arrays.asList(digits).contains(operand)){
                throw new NumberBaseException(operand + " is not a correct operand");
            }
        } else {
            for (String digit : operand.split("")){
                // Puur voor fixed point
                if(!digit.equals(".")){
                    if(!Arrays.asList(digits).contains(digit)){
                        throw new NumberBaseException(digit + " is not a correct digit in Operand: " + operand);
                    }
                }
            }
        }
    }

    public void add(){
        operand_0 = operand_1.plus(operand_0);
        operand_1 = new Rational();
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ADD));
    }
    public void subtract() {
        operand_0 = operand_1.minus(operand_0);
        operand_1 = new Rational();
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, MINUS));
    }
    public void multiply() {
        operand_0 = operand_1.mul(operand_0);
        operand_1 = new Rational();
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, MULTIPLY));
    }
    public void divide() {
        try {
            operand_0 = operand_1.div(operand_0);
        } catch(IllegalArgumentException ex){
            System.out.println(ex.getMessage());
        } finally {
            operand_1 = new Rational();
            processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, DIVISION));
        }
    }
    public void delete() {
        operand_0 = operand_1;
        operand_1 = new Rational();
        setInputText("");
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, DELETE));
    }

    public String firstOperand(){
        return format.toString(operand_1,base);
    }
    public String secondOperand(){
        return format.toString(operand_0,base);
    }

    public void setBase(Base newBase){
        base = newBase;
    }

    public Base getBase(){
        return base;
    }

    public void setFormat(Format newFormat){
        format = newFormat;
    }

    public Format getFormat(){
        return format;
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

    public String getInputText() {
        return inputText;
    }

    public void appendInput(String inputText){
        this.inputText = this.inputText + inputText;
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
        processEvent(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
    }
}