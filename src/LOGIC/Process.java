/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LOGIC;

import java.awt.Color;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * @author Jean
 */
public class Process {
    private int ID;
    private String name = "";
    private String estado = "new";
    private String PC = "0";
    private String AC = "0";;
    private String AX = "0";
    private String BX = "0";
    private String CX = "0";
    private String DX = "0";
    private ArrayList<String> pila =  new ArrayList<String>();
    private int CPU = -1;
    private String initTime = "0";
    private String transcuredTime  = "0";
    // ArrayList<String> files;
    private ArrayList<String> instructions = new  ArrayList<String>();
    private int sizeOfInstructions =0;
    private Color rowColor;
    
    public Process (int p_id,String p_Estado,String p_Name,String p_path,Color p_color){
        this.ID = p_id;
        this.estado = p_Estado;
        this.name = p_Name;
        readFile(Paths.get(p_path));
        this.rowColor = p_color;
    }
    
    //crea un array con las intrucciones obtenidas en del archivo
    public void readFile(Path filePath ){
        try {
             instructions= (ArrayList<String>) Files.readAllLines(filePath, StandardCharsets.UTF_8);//lectura del archivo
             sizeOfInstructions = instructions.size();
        } catch (Exception ex) {
            instructions = null;
        }
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public int getSizeOfInstructions() {
        return sizeOfInstructions;
    }

    public void setSizeOfInstructions(int sizeOfInstructions) {
        this.sizeOfInstructions = sizeOfInstructions;
    }
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPC() {
        return PC;
    }

    public void setPC(String PC) {
        this.PC = PC;
    }

    public String getAC() {
        return AC;
    }

    public void setAC(String AC) {
        this.AC = AC;
    }

    public String getAX() {
        return AX;
    }

    public void setAX(String AX) {
        this.AX = AX;
    }

    public String getBX() {
        return BX;
    }

    public void setBX(String BX) {
        this.BX = BX;
    }

    public String getCX() {
        return CX;
    }

    public void setCX(String CX) {
        this.CX = CX;
    }

    public String getDX() {
        return DX;
    }

    public void setDX(String DX) {
        this.DX = DX;
    }

    public ArrayList<String> getPila() {
        return pila;
    }

    public void setPila(ArrayList<String> pila) {
        this.pila = pila;
    }

    public int getCPU() {
        return CPU;
    }

    public void setCPU(int CPU) {
        this.CPU = CPU;
    }

    public String getInitTime() {
        return initTime;
    }

    public void setInitTime(String initTime) {
        this.initTime = initTime;
    }

    public String getTranscuredTime() {
        return transcuredTime;
    }

    public void setTranscuredTime(String transcuredTime) {
        this.transcuredTime = transcuredTime;
    }
    
    public Color getColor() {
       return this.rowColor;
    }


}
