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
public class Finished implements State {
    List<PCB> queue = new LinkedList<PCB>();
    private final LogContainer cnt = new LogContainer();
    
    @Override
    public String getLog() {
        String tmp;
        synchronized(cnt){
            tmp = cnt.log;
        }
        return tmp;
    }

    @Override
    public void appendToLog(Object obj) {
        synchronized(cnt){
            cnt.log += "[t="+State.reloj.getTime()+"] "+obj + "\n";
        }
    }
    
    @Override
    public String toString(){
        return "Terminado";
    }

    @Override
    public void transition(State state, PCB process) {
        appendToLog("[" + process + "] Transicion proveniente del estado [" + state + "]");
        state.appendToLog("[" + process + "] Movido a [" + this + "]");
        state.removeFromQueue(process);
        process.setState(this);
        queue.add(process);
    }

    @Override
    public List<PCB> getQueue() {
        return queue;
    }

    @Override
    public void removeFromQueue(PCB process) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void tick() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reset() {
        queue = new LinkedList<PCB>();
    }
    
}
