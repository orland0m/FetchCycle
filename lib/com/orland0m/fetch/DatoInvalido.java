/* 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Any copyright is dedicated to the Public Domain.
 * http://creativecommons.org/publicdomain/zero/1.0/ 
 * Author: Miramontes, Orlando O.
 */
 
package com.orland0m.fetch;

public class DatoInvalido extends Exception{
	private static final long serialVersionUID = 1L;
	public DatoInvalido(String s){
		super(s);
	}
}