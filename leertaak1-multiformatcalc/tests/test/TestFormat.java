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
package test;

import static org.junit.Assert.*;
import org.junit.Test;

import multiformat.*;

public class TestFormat {

    @Test
    public void testFormatBase(){
    	Calculator calc = new Calculator();
    	
    	try {
			calc.addOperand("0.75");

            calc.setBase(new HexBase());
			calc.setFormat(new FloatingPointFormat());
			assertEquals("C.0*10^-1.0",calc.secondOperand());
			calc.setBase(new BinaryBase());
			assertEquals("1.1*10^-1.0",calc.secondOperand());
			calc.setBase(new DecimalBase());
			assertEquals("7.5*10^-1.0",calc.secondOperand());

			calc.setFormat(new RationalFormat());
			assertEquals("3.0/4.0",calc.secondOperand());
			calc.setBase(new BinaryBase());
			assertEquals("11.0/100.0",calc.secondOperand());
			calc.setBase(new HexBase());
			assertEquals("3.0/4.0",calc.secondOperand());
		} catch (FormatException e) {
			fail("Unexpected exception");
		}
    	
    }

    @Test
    public void testBases() {
        try {
            Calculator calc = new Calculator();
            calc.addOperand("8");
            assertEquals("8.0",calc.secondOperand());
            calc.setBase(new BinaryBase());
            assertEquals("1000.0",calc.secondOperand());
            calc.setBase(new HexBase());
            assertEquals("8.0",calc.secondOperand());

            calc.setBase(new OctalBase());
            assertEquals("10.0", calc.secondOperand());
        } catch (FormatException e) {
            fail("Unexpected exception");
        }

    }
}
