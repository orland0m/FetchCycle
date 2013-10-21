/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orland0m.scheduler;

import com.orland0m.job.PCB;
import java.util.Comparator;

/**
 *
 * @author orlando
 */
public class SJF implements Comparator<PCB> {

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
        if (o1.getSize() > o2.getSize()) {
            return 1;
        }
        if (o1.getSize() < o2.getSize()) {
            return -1;
        }
        return 0;
    }
    
    @Override
    public String toString(){
        return "SJF";
    }
}
