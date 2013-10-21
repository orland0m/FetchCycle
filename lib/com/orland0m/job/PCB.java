/* 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Any copyright is dedicated to the Public Domain.
 * http://creativecommons.org/publicdomain/zero/1.0/ 
 * Author: Miramontes, Orlando O.
 */
package com.orland0m.job;

import com.orland0m.scheduler.Log;
import java.util.Objects;

/**
 *
 * @author orlando
 */
public final class PCB implements Log {

    private String id = "";
    private int size = 0;
    private int remainingInstructions = 0;
    private int priority = 0;
    private int errorInstruction = 0;
    private int blockingInstruction = 0;
    private int arrivalTime = 0;
    private int PC = 0;
    private State state = null;
    private String log = "";
    private boolean blocked = false;
    private int turnCount = 0;
    private int blockTime = 0;

    public PCB(String id) {
        setId(id);
    }

    public void execute() {
        PC++;
        remainingInstructions--;
        turnCount--;
    }
    
    public int getInstruccionTurno(){
        return turnCount;
    }

    public boolean isDone() {
        return PC >= (size - 1);
    }

    public void setBlockTime(int blockTime) {
        this.blockTime = blockTime;
    }

    public void blockTick() {
        blockTime--;
    }

    public boolean isBlockOver() {
        return blockTime <= 0;
    }

    public boolean isTurnOver() {
        return turnCount <= 0;
    }

    public void setTurnCount(int turnCount) {
        if (turnCount < 0) {
            appendToLog("Instrucciones de turno negativas, ignoradas");
            return;
        }
        this.turnCount = turnCount;
        appendToLog("Instrucciones de turno asignadas: " + turnCount);
    }

    public boolean wasBlocked() {
        return blocked;
    }

    public void tryToBlock() {
        if (isBlocking()) {
            blocked = true;
            appendToLog("Proceso bloqueado");
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        appendToLog("Estado cambiado a " + state);
    }

    public int getPC() {
        return PC;
    }

    public void setPC(int PC) {
        if (PC < 0) {
            appendToLog("Contador de programa negativo, ignorado");
            return;
        }
        this.PC = PC;
        appendToLog("Contador de programa cambiado a " + PC);
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int time) {
        if (time < 0) {
            appendToLog("Tiempo de llegada negativo, ignorado");
            return;
        }
        this.arrivalTime = time;
        appendToLog("Tiempo de llegada cambiado a " + time);
    }

    public boolean isError() {
        int error = getErrorInstruction();
        return error > 0 ? remainingInstructions == error : false;
    }

    public boolean isBlocking() {
        int block = getBlockingInstruction();
        return block > 0 ? remainingInstructions == block : false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            appendToLog("ID nulo, ignorado");
            return;
        }
        this.id = id;
        appendToLog("ID cambiado a " + id);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        if (size < 0) {
            appendToLog("Tamanio negativo, ignorado");
            return;
        }
        this.size = this.remainingInstructions = size;
        appendToLog("Tamanio cambiado a " + size);
    }

    public int getRemainingInstructions() {
        return remainingInstructions;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        if (priority < 0) {
            appendToLog("Prioridad negativa, ignorada");
            return;
        }
        this.priority = priority;
        appendToLog("Prioridad cambiada a " + priority);
    }

    public int getErrorInstruction() {
        return errorInstruction;
    }

    public void setErrorInstruction(int errorInstruction) {
        if (errorInstruction < 0) {
            appendToLog("Instruccion de error negativa, ignorada");
            return;
        }
        this.errorInstruction = errorInstruction;
        appendToLog("Instruccion de error cambiada a " + errorInstruction);
    }

    public int getBlockingInstruction() {
        return blockingInstruction;
    }

    public void setBlockingInstruction(int blockingInstruction) {
        if (blockingInstruction < 0) {
            appendToLog("Instruccion de bloqueo negativa, ignorada");
            return;
        }
        this.blockingInstruction = blockingInstruction;
        appendToLog("Instruccion de bloqueo cambiada a " + blockingInstruction);
    }

    @Override
    public String getLog() {
        return log;
    }

    @Override
    public void appendToLog(Object obj) {
        log += obj + "\n";
    }

    @Override
    public String toString() {
        return getId()+", PC="+getPC()+", INSF="+getRemainingInstructions();
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        PCB tmp;
        if (obj instanceof PCB) {
            tmp = (PCB) obj;
            return tmp.getId().equals(getId());
        }
        return false;
    }
}
