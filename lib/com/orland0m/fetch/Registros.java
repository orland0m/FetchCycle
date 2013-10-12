/* 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Any copyright is dedicated to the Public Domain.
 * http://creativecommons.org/publicdomain/zero/1.0/ 
 * Author: Miramontes, Orlando O.
 */

package com.orland0m.fetch;

public class Registros{
	private int PC, IR, AC;
	
	public int getPC() {
		return PC;
	}
	
	public void setPC(int pC) throws DatoInvalido {
		if(pC<0 || pC>999){
			throw new DatoInvalido("0 < IR < 1,000");
		}
		PC = pC;
	}
	
	public int getIR() {
		return IR;
	}
	
	public void setIR(int iR) throws DatoInvalido {
		if(iR < 0 || iR>9999){
			throw new DatoInvalido("0 < IR < 10,000");
		}
		IR = iR;
	}
	
	public int instruccion(){
		return Integer.parseInt(String.format("%04d", IR).substring(0, 1));
	}
	
	public int memdir(){
		return Integer.parseInt(String.format("%04d", IR).substring(1, 4));
	}
	
	public int getAC() {
		return AC;
	}
	
	public void setAC(int aC) throws DatoInvalido {
		if(aC < 0 || aC>9999){
			throw new DatoInvalido("0 < AC < 10,000");
		}
		AC = aC;
	}
	
	@Override
	public String toString(){
		return String.format("%03d", getPC()) + " " + String.format("%04d", getIR()) + " " + String.format("%04d", getAC());
	}
}