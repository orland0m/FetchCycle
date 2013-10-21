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
public class Running implements State {

    private List<PCB> queue = new LinkedList<PCB>();
    private final LogContainer cnt = new LogContainer();
    private Ready listo;
    private Blocked bloqueado;
    private Finished terminado;
    private final int turno = 20;

    public void setEstadoListo(Ready listo) {
        this.listo = listo;
    }

    public void setEstadoBloqueado(Blocked listo) {
        this.bloqueado = listo;
    }

    public void setEstadoTerminado(Finished listo) {
        this.terminado = listo;
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
        return "Ejecutandose";
    }

    @Override
    public void transition(State state, PCB process) {
        assert (queue.isEmpty()); // Should always be empty
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
        if (queue.remove(process)) {
            process.setState(null);
            appendToLog("[" + process + "] elminado de [" + this + "]");
        } else {
            appendToLog("[" + process + "] No existe en [" + this + "]");
        }
    }

    @Override
    public void tick() {
        PCB process;
        assert (listo != null);
        assert (bloqueado != null);
        assert (terminado != null);
        if (!getQueue().isEmpty()) {
            process = getQueue().get(0);
            if (!process.isBlocking() && !process.isError()) {
                process.execute();
                if (process.isTurnOver()) {
                    listo.transition(this, process);
                } else if (process.isDone()) {
                    terminado.transition(this, process);
                } else {
                    return;
                }
            }
        }
        while (true) {
            if (listo.getQueue().isEmpty() && getQueue().isEmpty()) {
                return; // Nothing to do
            }
            if (getQueue().isEmpty()) {
                transition(listo, listo.getQueue().get(0));
            }
            assert (!getQueue().isEmpty());
            process = getQueue().get(0);
            if (process.isError()) {
                terminado.transition(this, process);
                continue;
            }
            if (process.wasBlocked()) {
                break;
            }
            process.tryToBlock();
            if (process.wasBlocked()) {
                bloqueado.transition(this, process);
                continue;
            }
            break;
        }
        process.setTurnCount(turno);
        process.execute();
        if (process.isDone()) {
            terminado.transition(this, process);
        }
    }

    @Override
    public void reset() {
        queue = new LinkedList<PCB>();
    }
}
