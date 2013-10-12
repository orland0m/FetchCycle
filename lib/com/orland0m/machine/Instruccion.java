/* 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Any copyright is dedicated to the Public Domain.
 * http://creativecommons.org/publicdomain/zero/1.0/ 
 * Author: Miramontes, Orlando O.
 */

package com.orland0m.machine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;


public abstract class Instruccion{
	public abstract int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws Exception;
	
	public static Instruccion parse(Registros reg){
		switch(reg.instruccion()){
		case 0:
			return new Cero();
		case 1:
			return new Uno();
		case 2:
			return new Dos();
		case 3:
			return new Tres();
		case 4:
			return new Cuatro();
		case 5:
			return new Cinco();
		case 6:
			return new Seis();
		case 7:
			return new Siete();
		case 8:
			return new Ocho();
		case 9:
			return new Nueve();
		}
		return null;
	}
	
	public static Memoria buscaDireccion(Set<Memoria> mem, int dir) throws DatoInvalido {
		for (Iterator<Memoria> it = mem.iterator(); it.hasNext(); ) {
	        Memoria fila = it.next();
	        Memoria tmp = new Memoria();
	        tmp.setDireccion(dir);
	        if (fila.equals(tmp))
	            return fila;
	    }
		return null;
	}
}

class Cero extends Instruccion{

	@Override
	public int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws DatoInvalido, DireccionInvalida {
		Registros r = reg.peek();
		int salto = r.memdir();
		Memoria fila = Instruccion.buscaDireccion(mem, salto);
		if(fila != null){
			return fila.getDireccion();
		}
		throw new DireccionInvalida("0 - Recibi direccion inexistente: " + salto);
	}
	
	@Override
	public String toString(){
		return "0";
	}
}

class Uno extends Instruccion{

	@Override
	public int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws DatoInvalido, DireccionInvalida {
		Registros r = reg.peek();
		int memDir = r.memdir();
		Memoria fila = Instruccion.buscaDireccion(mem, memDir);
		if(fila != null){
			r.setAC(fila.getContenido());
			return r.getPC() + 1;
		}
		throw new DireccionInvalida("1 - Recibi direccion inexistente: " + memDir);
	}
	
	@Override
	public String toString(){
		return "1";
	}
}

class Dos extends Instruccion{

	@Override
	public int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws DireccionInvalida, DatoInvalido {
		Registros r = reg.peek();
		int memDir = r.memdir();
		Memoria fila = Instruccion.buscaDireccion(mem, memDir);
		if(fila != null){
			fila.setContenido(r.getAC());
			return r.getPC() + 1;
		}
		throw new DireccionInvalida("2 - Recibi direccion inexistente: " + memDir);
	}
	
	@Override
	public String toString(){
		return "2";
	}
}

class Tres extends Instruccion{

	@Override
	public int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws DatoInvalido, DireccionInvalida {
		Registros r = reg.peek();
		int memDir = r.memdir();
		Memoria fila = Instruccion.buscaDireccion(mem, memDir);
		if(fila != null){
			r.setAC(fila.getContenido()+r.getAC());
			return r.getPC() + 1;
		}
		throw new DireccionInvalida("3 - Recibi direccion inexistente: " + memDir);
	}
	
	@Override
	public String toString(){
		return "3";
	}
}

class Cuatro extends Instruccion{

	@Override
	public int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws DatoInvalido, DireccionInvalida {
		Registros r = reg.peek();
		int memDir = r.memdir();
		Memoria fila = Instruccion.buscaDireccion(mem, memDir);
		if(fila != null){
			r.setAC(r.getAC() - fila.getContenido());
			return r.getPC() + 1;
		}
		throw new DireccionInvalida("4 - Recibi direccion inexistente: " + memDir);
	}
	
	@Override
	public String toString(){
		return "4";
	}
}

class Cinco extends Instruccion{

	@Override
	public int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws DatoInvalido, DireccionInvalida {
		Registros r = reg.peek();
		int memDir = r.memdir();
		Memoria fila = Instruccion.buscaDireccion(mem, memDir);
		if(fila != null){
			r.setAC(r.getAC() * fila.getContenido());
			return r.getPC() + 1;
		}
		throw new DireccionInvalida("5 - Recibi direccion inexistente: " + memDir);
	}
	
	@Override
	public String toString(){
		return "5";
	}
}

class Seis extends Instruccion{

	@Override
	public int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws DatoInvalido, DireccionInvalida {
		Registros r = reg.peek();
		int memDir = r.memdir();
		Memoria fila = Instruccion.buscaDireccion(mem, memDir);
		if(fila != null){
			if(fila.getContenido()==0){
				throw new DatoInvalido("6.- Division entre cero");
			}
			r.setAC(r.getAC() / fila.getContenido());
			return r.getPC() + 1;
		}
		throw new DireccionInvalida("6 - Recibi direccion inexistente: " + memDir);
	}
	
	@Override
	public String toString(){
		return "6";
	}
}

class Siete extends Instruccion{

	@Override
	public int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws DatoInvalido, ArchivoInvalido {
		Registros r = reg.peek();
		int archivo = leerDato();
		r.setAC(r.getAC() + archivo);
		return r.getPC() + 1;
	}
	
	private int leerDato() throws ArchivoInvalido, DatoInvalido {
		File file = new File("archivo.txt");
		Scanner scanner;
		try{
			scanner = new Scanner(file);
		}catch(FileNotFoundException e){
			throw new ArchivoInvalido("7 - archivo.txt no existe");
		}
		if (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			scanner.close();
			try{
				return Integer.parseInt(line);
			}catch(NumberFormatException e){
				throw new DatoInvalido("7 - archivo.txt debe contener un numero valido");
			}
		}
		scanner.close();
        throw new ArchivoInvalido("7 - Archivo vacio");
	}
	
	@Override
	public String toString(){
		return "7";
	}
}

class Ocho extends Instruccion{

	@Override
	public int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws ArchivoInvalido {
		PrintWriter writer;
		Registros r = reg.peek();
		try {
			writer = new PrintWriter("archivo.txt");
		} catch (FileNotFoundException e) {
			throw new ArchivoInvalido("8 - No se puede escribir a archivo.txt");
		}
		writer.println("" + r.getAC());
		writer.close();
		return r.getPC() + 1;
	}
	
	@Override
	public String toString(){
		return "8";
	}
}

class Nueve extends Instruccion{

	@Override
	public int ejecutar(Set<Memoria> mem, Stack<Registros> reg) throws ArchivoInvalido {
		PrintWriter writer;
		Registros r = reg.peek();
		try {
			writer = new PrintWriter("secundaria.txt");
		} catch (FileNotFoundException e) {
			throw new ArchivoInvalido("9 - No se puede escribir a secundaria.txt");
		}
		for (Iterator<Memoria> it = mem.iterator(); it.hasNext(); ) {
	        Memoria fila = it.next();
	        writer.println(fila);
	    }
		writer.close();
		return r.getPC()+1;
	}
	
	@Override
	public String toString(){
		return "9";
	}
}