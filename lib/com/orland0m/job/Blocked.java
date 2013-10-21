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
public class Blocked implements State {

    List<PCB> queue = new LinkedList<PCB>();
    private final LogContainer cnt = new LogContainer();
    private final int blockTime = 10;
    private Ready listo;
    
    public void setListo(Ready listo){
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
        return "Bloqueado";
    }

    @Override
    public void transition(State state, PCB process) {
        appendToLog("[" + process + "] Transicion proveniente del estado [" + state + "]");
        state.appendToLog("[" + process + "] Movido a [" + this + "]");
        state.removeFromQueue(process);
        process.setState(this);
        process.setBlockTime(blockTime);
        queue.add(process);
    }

    @Override
    public List<PCB> getQueue() {
        return queue;
    }

    @Override
    public void removeFromQueue(PCB process) {
        if (queue.remove(process)) {
            process.setState(null);
            appendToLog("[" + process + "] elminado de [" + this + "]");
        } else {
            appendToLog("[" + process + "] No existe en [" + this + "]");
        }
    }

    @Override
    public void tick() {
        PCB tmp;
        assert (listo != null);
        for (PCB process : getQueue()) {
            process.blockTick();
        }
        for (int i = 0; i < getQueue().size(); i++) {
            tmp = getQueue().get(i);
            if (tmp.isBlockOver()) {
                listo.transition(this, tmp);
                i = 0;
            }
        }
    }

    @Override
    public void reset() {
        queue = new LinkedList<PCB>();
    }
}
