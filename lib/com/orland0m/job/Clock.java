/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orland0m.job;

/**
 *
 * @author orlando
 */
public class Clock {
    private int time = 0;
    public int getTime(){
        return time;
    }
    public void tick(){
        time++;
    }
    public void reset(){
        time = 0;
    }
}
