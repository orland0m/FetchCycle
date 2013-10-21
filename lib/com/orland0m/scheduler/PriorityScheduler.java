/* 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Any copyright is dedicated to the Public Domain.
 * http://creativecommons.org/publicdomain/zero/1.0/ 
 * Author: Miramontes, Orlando O.
 */
package com.orland0m.scheduler;

import com.orland0m.job.PCB;
import java.util.Comparator;

/**
 *
 * @author orlando
 */
public class PriorityScheduler implements Comparator<PCB> {

    @Override
    public int compare(PCB o1, PCB o2) {
        if (o1 == null && o2 == null) {
            return 0;
        }
        if (o2 == null) {
            return 1;
        }
        if (o1 == null) {
            return -1;
        }
        if (o1.getPriority() > o2.getPriority()) {
            return 1;
        }
        if (o1.getPriority() < o2.getPriority()) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public String toString(){
        return "Prioridades";
    }
}
