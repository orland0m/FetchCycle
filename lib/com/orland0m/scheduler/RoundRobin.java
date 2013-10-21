/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.orland0m.scheduler;

import com.orland0m.job.PCB;
import com.orland0m.job.State;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author orlando
 */
public class RoundRobin implements Runnable {

    public JTextArea logArea;
    public JComboBox seleccion;
    public State[] estados;
    public final StopContainer stopLock = new StopContainer();
    public JTextField idActual;
    public JTextField instruccionActual;
    public JTextField instruccionesTurno;
    public JTextField instruccionesRestantes;

    private void updateFields() {
        State run = estados[2];
        PCB proceso;
        if(run.getQueue().isEmpty()){
            idActual.setText("");
            instruccionActual.setText("");
            instruccionesTurno.setText("");
            instruccionesRestantes.setText("");
        }else{
            proceso = run.getQueue().get(0);
            idActual.setText(proceso.getId());
            instruccionActual.setText(proceso.getPC()+"");
            instruccionesTurno.setText(proceso.getInstruccionTurno()+"");
            instruccionesRestantes.setText(proceso.getRemainingInstructions()+"");
        }
    }

    public class StopContainer {
        public boolean stop;
    }

    @Override
    public void run() {
        State.reloj.reset();
        while (!done()) {
            tick();
            updateFields();
            updateLog();
            pause();
        }
    }
    
    private void tick(){
        State.reloj.tick();
        estados[0].tick();
        estados[2].tick();
        estados[3].tick();
    }
    
    public void setSelectionComboBox(JComboBox selection){
        this.seleccion = selection;
    }

    private boolean done() {
        boolean tmp;
        synchronized (stopLock) {
            tmp = stopLock.stop;
        }
        if(tmp){
            informAll();
            return true;
        }
        return false; // Infinite
    }

    private void updateLog() {
        logArea.setText(estados[seleccion.getSelectedIndex()].getLog());
    }

    private void pause() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
        }
    }

    private void informAll() {
        for (State e : estados) {
            e.appendToLog("========== RR Interrupted ==========");
        }
    }
}
