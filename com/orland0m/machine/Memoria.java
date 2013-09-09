/* 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Any copyright is dedicated to the Public Domain.
 * http://creativecommons.org/publicdomain/zero/1.0/ 
 * Author: Miramontes, Orlando O.
 */
 
package com.orland0m.machine;

public class Memoria{
	private int direccion, contenido;
	public boolean esInstruccion = false;
	public int getDireccion() {
		return direccion;
	}
	public void setDireccion(int direccion) throws DatoInvalido {
		if(direccion<0 || direccion>999){
			throw new DatoInvalido("0 < direccion < 1,000");
		}
		this.direccion = direccion;
	}

	public int getContenido() {
		return contenido;
	}

	public void setContenido(int contenido) throws DatoInvalido {
		if(contenido < 0 || contenido >9999){
			throw new DatoInvalido("0 < contenido < 10,000");
		}
		this.contenido = contenido;
	}
	
	@Override
	public String toString(){
		return String.format("%03d", getDireccion()) + " " + String.format("%04d", getContenido());
	}
	
	@Override
	public boolean equals(Object o){
		if(o instanceof Memoria){
			Memoria tmp = (Memoria)o;
			return tmp.getDireccion() == this.getDireccion();
		}
		return false;
	}
	
	@Override
	public int hashCode(){
		return getDireccion();
	}
}