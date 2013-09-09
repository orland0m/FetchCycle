/* 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Any copyright is dedicated to the Public Domain.
 * http://creativecommons.org/publicdomain/zero/1.0/ 
 * Author: Miramontes, Orlando O.
 */
package com.orland0m.machine;

import java.util.LinkedHashSet;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


public class Maquina {
	public Stack<Registros> reg;
	private LinkedHashSet<Memoria> mem;
	private int primera;
	private boolean once = true;
	
	public Maquina(){
		reg = new Stack<Registros>();
		reg.push(new Registros());
		mem = new  LinkedHashSet<Memoria>();
	}
	
	public void cargar(String dir, String cont) throws DatoInvalido, DireccionInvalida{
		try{
			int idir = Integer.parseInt(dir);
			int icont = Integer.parseInt(cont);
			Memoria tmem = new Memoria();
			if(once){
				primera = idir;
				once = false;
			}
			tmem.setDireccion(idir);
			tmem.setContenido(icont);
			if(!mem.add(tmem)){
				throw new DireccionInvalida("Direccion repetida");
			}
		}catch(NumberFormatException e){
			throw new DatoInvalido("Direccion/Contenido debe ser un numero valido");
		}
	}
	
	public void fetch(int actual) throws Exception{
		Registros rtmp;
		Instruccion inst;
		int siguiente;
	    Memoria fila = Instruccion.buscaDireccion(mem, actual);
	    if(fila==null) return;
	    rtmp = new Registros();
	    rtmp.setPC(fila.getDireccion());
	    rtmp.setIR(fila.getContenido());
	    rtmp.setAC(reg.peek().getAC());
	    reg.push(rtmp);
	    inst = Instruccion.parse(reg.peek());
	    if(inst==null){
	    	throw new Exception("Instruccion no reconocida");
	    }
	    siguiente = inst.ejecutar(mem, reg);
		System.out.println(reg.peek());
	    fetch(siguiente);
	}
	
	public void leer(){
		Scanner entrada = new Scanner(System.in);
		String linea = null;
		String[] datos;
		int control = 0;
		ciclo:
		while(entrada.hasNextLine()){
			linea = entrada.nextLine();
			try{
				switch(control){
					case 0:
						datos = linea.split("\\s+");
						if(datos.length==2){
							cargar(datos[0], datos[1]);
						}else{
							control = 1;
						}
						break;
					case 1:
						datos = linea.split("\\s+");
						if(datos.length==2){
							cargar(datos[0], datos[1]);
						}else{
							System.out.println("Captura finalizada");
							break ciclo;
						}
						break;
				}
			}catch(Exception e){
				System.err.println("Exception: "+e.getMessage());
				System.err.println("Linea ignorada");
			}
		}
		entrada.close();
	}
	
	public static void main(String... args){
		Maquina m = new Maquina();
		try{
			m.leer();
			m.fetch(m.primera);
		}catch(Exception e){
			System.out.println("Exception: "+e.getMessage());
		}
	}
	
}
