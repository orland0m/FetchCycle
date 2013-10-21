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
