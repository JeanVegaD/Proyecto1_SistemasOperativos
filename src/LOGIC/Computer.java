/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LOGIC;

import java.io.File;
import org.json.simple.JSONObject;
import java.util.ArrayList;
import LOGIC.Process;
import java.awt.Color;
import java.util.Random;


/**
 *
 * @author Jean
 */
public class Computer {
    
    /*Files*/
    private ArrayList<File> loadFiles = new ArrayList<>();
    
    /*System*/
    private JSONObject timeAndMemorySizeConfig = new JSONObject();
    private int executionTime = 0;
    private String output = "";
    ArrayList<Color> colorList = new ArrayList<Color>();
    
    /*Process*/
    private ArrayList<Process> processList = new ArrayList<Process>();
    private int processId = 0;
    
    /*Memory*/
    private int lastIdProcessLoad = 0;
    private Memory mainMemory = new Memory(128);
    private Memory hardDisk = new Memory(512);
    
    /*Cores*/
    private Core core1 = new Core();
    private Core core2 = new Core();
    
    
    
    
    public Computer(){
        setMemoryConfig();      //Se establece la memoria principal
        //System.out.println(timeAndMemorySizeConfig.get("STORE"));
    }

    /* Establece los arvhicos que se cargan en la aplicacion */
    public void addFile(File p_file){
        loadFiles.add(p_file);        
    }
    
    public int getSizeOfLoadFiles (){
        return loadFiles.size();
    }
    
    /* Establece la configuracion de los tiempos de ejecucion y la memoria */
    public void setMemoryConfig(){        
        timeAndMemorySizeConfig.put("LOAD", 2);
        timeAndMemorySizeConfig.put("STORE", 2);
        timeAndMemorySizeConfig.put("MOV", 1);
        timeAndMemorySizeConfig.put("ADD", 3);
        timeAndMemorySizeConfig.put("SUB", 3);
        timeAndMemorySizeConfig.put("INC", 1);
        timeAndMemorySizeConfig.put("DEC", 1);
        timeAndMemorySizeConfig.put("DEC", 2);
        timeAndMemorySizeConfig.put("CMP", 2);
        timeAndMemorySizeConfig.put("JE", 2);
        timeAndMemorySizeConfig.put("JNE", 2);
        timeAndMemorySizeConfig.put("PUSH", 1);
        timeAndMemorySizeConfig.put("POP", 1);
        timeAndMemorySizeConfig.put("PARAM", 3);        
    }
    
    /*incremente el tiempo de ejecuccion del la computadora*/
    public void increaseTime(){
        executionTime++;
    }
    
    /*Obtiene el tiempo de ejeuccion de la computadora*/
    public int getExecutionTime(){
        return executionTime;
    }
    
    /*Envia mensajes al output*/
    public void sendMessagetoOutput(String message){
        output+= "> " + message + "\n";
    }
    
    //Obtiene la informacion del output
    public String getOutput(){
        return output;
    }
    
    public Memory getMainMemory(){
        return this.mainMemory;
    }
    
    public Memory getHardDiskMemory(){
        return this.hardDisk;
    }
    
    // carga los al planificador de processos
    public void loadProcessfromFile(){
        for (int i = 0; i < loadFiles.size(); i++) {
            Process tempProcess = new Process(processId,"new",loadFiles.get(i).getName(),loadFiles.get(i).getPath(),getNewColor());
            processId++;
            processList.add(tempProcess);
        }
        loadNewProcessToMemory();
    }
    
    public void loadNewProcessToMemory(){
        if(lastIdProcessLoad<processList.size() && hardDisk.getInstrucctionsByProcess().size()==0){
            while(mainMemory.canInsert(processList.get(lastIdProcessLoad))){
                mainMemory.insertInstructions(processList.get(lastIdProcessLoad));
                processList.get(lastIdProcessLoad).setEstado("prepared");

                lastIdProcessLoad++;
                if(lastIdProcessLoad>=processList.size()){
                    break;
                }
            }
            
        }
        
        if(lastIdProcessLoad<processList.size()){
            
            while(hardDisk.canInsert(processList.get(lastIdProcessLoad))){
                hardDisk.insertInstructions(processList.get(lastIdProcessLoad));
                processList.get(lastIdProcessLoad).setEstado("new");

                lastIdProcessLoad++;
                if(lastIdProcessLoad>=processList.size()){
                    break;
                }
            }
         }
        
        addProcesstoQueue();
    }
     
    // obtiene la lista de los procesos
    public ArrayList<Process> getProcessList(){
        return processList;
    }
    
    public void addProcesstoQueue(){
        ArrayList<LOGIC.Process> processInMainMemory = mainMemory.getInstrucctionsByProcess();
         for (int i = 0; i < processInMainMemory.size(); i++) {
            if(processInMainMemory.get(i).getCPU()<=0){
                Random rand = new Random();
                int selectedCore = rand.nextInt( 2 );
                processInMainMemory.get(i).setCPU(selectedCore+1);
                
                if(selectedCore == 0){
                    if(core1.getCurrentProcess() == null){
                        core1.setCurrentProcess(processInMainMemory.get(i));

                    }
                }else{
                    if(core2.getCurrentProcess() == null){
                        core2.setCurrentProcess(processInMainMemory.get(i));
                    }
                }
                
            }
        }
    }
    
    
    public Color getNewColor(){
        Random rand = new Random();
        float r;
        float g;
        float b;
        Color newColor;
        boolean similarFound = false;
        
        do{
            r = (float) (rand.nextFloat() / 2f + 0.5);
            g = (float) (rand.nextFloat() / 2f + 0.5);
            b = (float) (rand.nextFloat() / 2f + 0.5);
            newColor = new Color(r, g, b, 1);
            
            for(Color color : colorList){
                
                if(color.getRGB()== newColor.getRGB()){
                    similarFound = true;
                    break;
                }
            }
        }while(similarFound);
        
        return newColor;  
    }
    
    public Core getCore1(){
        return core1;
    }
    
    public Core getCore2(){
        return core2;
    }
    
    public void removeProcessFromMainMemory(int core){
        ArrayList<LOGIC.Process> processInMainMemory = mainMemory.getInstrucctionsByProcess();
        for (int i = 0; i < processInMainMemory.size(); i++) {
            if(processInMainMemory.get(i).getCPU()==core){
                mainMemory.removeInstructions(processInMainMemory.get(i).getSizeOfInstructions());
                processInMainMemory.remove(i);
                break;
            }
        }
    }
    
    public void bringProccesFromHarkDisk(){
        ArrayList<LOGIC.Process> processInHardMemory = hardDisk.getInstrucctionsByProcess();
        if(processInHardMemory.size()>0){
             LOGIC.Process temp = processInHardMemory.get(0);
             if(mainMemory.canInsert(temp)){
                mainMemory.insertInstructions(temp);
                hardDisk.removeInstructions(processInHardMemory.get(0).getSizeOfInstructions());
                processInHardMemory.remove(0);
                addProcesstoQueue();
                loadNewProcessToMemory();
             }
             
        }
    }
    
    public void setNewProcessToCore(int core){
        ArrayList<LOGIC.Process> processInMainMemory = mainMemory.getInstrucctionsByProcess();
        for (int i = 0; i < processInMainMemory.size(); i++) {
            if(processInMainMemory.get(i).getCPU()==core){
                if(core==1){
                    core1.setCurrentProcess(processInMainMemory.get(i));
                    break;
                }else{
                    core2.setCurrentProcess(processInMainMemory.get(i));
                    break;
                }
            }
        }
    }
    
    public void nextInstruction(){
        core1.increasetime();
        if(core1.getCurrentProcess()==null){
            removeProcessFromMainMemory(1);
            setNewProcessToCore(1);
            bringProccesFromHarkDisk();
        }
        core2.increasetime();
        if(core2.getCurrentProcess()==null){
            removeProcessFromMainMemory(2);
            setNewProcessToCore(2);
            bringProccesFromHarkDisk();
        }
       
        increaseTime();
        
    }
    
    
    
}
