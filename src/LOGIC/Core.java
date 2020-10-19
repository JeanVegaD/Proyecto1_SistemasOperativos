/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LOGIC;

import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public class Core {
    
    private LOGIC.Process currentProcess = null;
    private int remainingInsTime = 0;
    private String currentInstruction = "";
    private int countInstruction = 0;
    private ArrayList<String> instructions = new ArrayList<>();
    
    public Core(){
        
    }

    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(Process p_currentProcess) {
        this.currentProcess = p_currentProcess;
        this.instructions = this.currentProcess.getInstructions();
        this.countInstruction = 0;
        this.currentInstruction = this.instructions.get(countInstruction);
        this.remainingInsTime = 100; //calcular el tiempo de ejecucion de la instruccion
        this.currentProcess.setEstado("execution");
        
    }

    public int getRemainingInsTime() {
        return remainingInsTime;
    }

    public void setRemainingInsTime(int remainingInsTime) {
        this.remainingInsTime = remainingInsTime;
    }

    public String getCurrentInstruction() {
        return currentInstruction;
    }

    public void setCurrentInstruction(String currentInstruction) {
        this.currentInstruction = currentInstruction;
    }
    
    public void increasetime(){
        if(currentProcess!=null){
            if(countInstruction<instructions.size()-1){
                this.countInstruction++;
                this.currentInstruction = this.instructions.get(countInstruction);
                this.remainingInsTime--;    
            }
            else{
                this.currentProcess.setEstado("finalized");
                this.currentProcess = null;

            }
        }
    }
    
    
    
    
    
}
