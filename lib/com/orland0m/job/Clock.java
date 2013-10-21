/* 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. 
 * Any copyright is dedicated to the Public Domain.
 * http://creativecommons.org/publicdomain/zero/1.0/ 
 * Author: Miramontes, Orlando O.
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
