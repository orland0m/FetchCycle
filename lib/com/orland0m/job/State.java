/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orland0m.job;

import com.orland0m.scheduler.Log;
import java.util.List;

/**
 *
 * @author orlando
 */
public interface State extends Log {
    public static Clock reloj =  new Clock();
    public void transition(State state, PCB process);
    public List<PCB> getQueue();
    public void removeFromQueue(PCB process);
    public void tick();
    public void reset();
}
