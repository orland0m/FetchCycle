/* 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Any copyright is dedicated to the Public Domain.
 * http://creativecommons.org/publicdomain/zero/1.0/ 
 * Author: Miramontes, Orlando O.
 */
package com.orland0m.job;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author orlando
 */
public class New implements State {

    private List<PCB> queue = new LinkedList<PCB>();
    private final LogContainer cnt = new LogContainer();
    private Ready listo;

    public void setEstadoListo(Ready listo){
        this.listo = listo;
    }
    
    @Override
    public String getLog() {
        String tmp;
        synchronized (cnt) {
            tmp = cnt.log;
        }
        return tmp;
    }

    @Override
    public void appendToLog(Object obj) {
        synchronized (cnt) {
            cnt.log += "[t="+State.reloj.getTime()+"] "+obj + "\n";
        }
    }

    @Override
    public String toString() {
        return "Nuevo";
    }

    @Override
    public void transition(State state, PCB process) {
        assert (state == null); // No transitions from other states
        if (queue.isEmpty()) {
            appendToLog("====================");
        }
        appendToLog("Nuevo proceso: " + process);
        queue.add(process);
        appendToLog("Agregado a la lista");
        process.appendToLog(process.getId() + " Creado. Agregado a lista de Nuevos");
    }

    @Override
    public List<PCB> getQueue() {
        return queue;
    }

    @Override
    public void removeFromQueue(PCB process) {
        if (queue.remove(process)) {
            process.setState(null);
            appendToLog(process + " elminado de la lista de Nuevos");
        } else {
            appendToLog(process + " No existe en la lista de nuevos");
        }
    }

    @Override
    public void tick() {
        // TODO: Add timed transitions
        assert (listo != null);
        while (!getQueue().isEmpty()) {
            appendToLog("[" + getQueue().get(0) + "] Movido a [" + listo + "]");
            listo.transition(this, getQueue().get(0));
        }
    }

    @Override
    public void reset() {
        queue = new LinkedList<PCB>();
    }
}
