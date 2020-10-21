/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Color;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import LOGIC.Computer;
import LOGIC.Memory;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Jean
 */
public class Home extends javax.swing.JFrame {
    
    /*Modelos de tablas*/
    DefaultTableModel modelFiles; 
    DefaultTableModel modelProcess; 
    DefaultTableModel modelMainMemory; 
    DefaultTableModel modelHardMemory; 
    DefaultTableModel modelQueue;
    
    
    /***/
    Computer compu =  new Computer();

   
    public Home() {
        try{
 
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      }
      catch (Exception e)
       {
        e.printStackTrace();
       }
        initComponents();
        
        /*
        MatteBorder( top, lef, bottom, right)
        */
        pnl_files.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(238,238,238)));
        pnl_process.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1,  new Color(238,238,238)));
        pnl_elements.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0,  new Color(238,238,238)));
        pnl_nucleo1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1,  new Color(238,238,238)));
        pnl_nucleo2.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1,  new Color(238,238,238)));
        pnl_mainmemory.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 0,  new Color(238,238,238)));
        pnl_harddisk.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0,  new Color(238,238,238)));
        pnl_execution.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1,  new Color(238,238,238)));
        pnl_elements1.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1,  new Color(238,238,238)));
        
        setModels();
    }
    

    public void setModels(){
        modelFiles = (DefaultTableModel) tbl_files.getModel();
        modelProcess = (DefaultTableModel) tbl_process.getModel();
        modelMainMemory =  (DefaultTableModel) tbl_mainMemory.getModel();
        modelHardMemory =  (DefaultTableModel) tbl_hardMemory.getModel();
        modelQueue = (DefaultTableModel) tbl_queue.getModel();
    }
    
    
    /*Actaulzia los elementos visuales del programa con respecto a lo que exist
    en los datos almacenados*/
    public void updateVisualElements(){
        txt_executionTime.setText(String.valueOf(compu.getExecutionTime()));
        txt_output.setText(compu.getOutput());
        updateProcessTable();
        
        updateMainMemoryTable();
        updateHardMemoryTable();
        updateQueueTable();
        updateCore1Table();
        updateCore2Table();
    }
    
    public void updateProcessTable(){
        clearTable(modelProcess);
        ArrayList<LOGIC.Process> processList = compu.getProcessList();

        for (int i=0; i<processList.size();i++){
            LOGIC.Process temp = processList.get(i);
            Object[] objectData = new Object[] { 
                String.valueOf(temp.getID()),
                temp.getName(),
                temp.getEstado(),
                temp.getAC(),
                temp.getAX(),
                temp.getBX(),
                temp.getCX(),
                temp.getDX()
                        };
            modelProcess.addRow(objectData);
            
        }
        tbl_process.setModel(modelProcess);
     
    }
    
    public void updateMainMemoryTable(){
        clearTable(modelMainMemory);
        Memory tempMemory = compu.getMainMemory();
        ArrayList<LOGIC.Process> processInMemory = tempMemory.getInstrucctionsByProcess();
        
        for (int i=0; i<processInMemory.size();i++){
            
            LOGIC.Process temp = processInMemory.get(i);
            ArrayList<String> instrucString = temp.getInstructions();
            
            for (int j=0; j<instrucString.size();j++){
                Object[] objectData = new Object[] { 
                    String.valueOf(temp.getID()),
                    instrucString.get(j),
                };
                modelMainMemory.addRow(objectData);
                
            } 
        }
        tbl_mainMemory.setModel(modelMainMemory);
        
        int porcentMemory = tempMemory.getMemoryUsed();
        txt_mainMemoryPorcent.setText(String.valueOf(porcentMemory) + "%");
        if(porcentMemory<70){
            txt_mainMemoryPorcent.setForeground(Color.GREEN);
        }else{
            txt_mainMemoryPorcent.setForeground(Color.RED);
        }
        
    }
    
    public void updateHardMemoryTable(){
        clearTable(modelHardMemory);
        Memory tempMemory = compu.getHardDiskMemory();
         ArrayList<LOGIC.Process> processInMemory = tempMemory.getInstrucctionsByProcess();
        
        for (int i=0; i<processInMemory.size();i++){
            
            LOGIC.Process temp = processInMemory.get(i);
            ArrayList<String> instrucString = temp.getInstructions();
            
            for (int j=0; j<instrucString.size();j++){
                Object[] objectData = new Object[] { 
                    String.valueOf(temp.getID()),
                    instrucString.get(j),
                };
                modelHardMemory.addRow(objectData);
                
            } 
        }
        tbl_hardMemory.setModel(modelHardMemory);
        
        int porcentMemory = tempMemory.getMemoryUsed();
        txt_hardiskMemory.setText(String.valueOf(porcentMemory) + "%");
        if(porcentMemory<70){
            txt_hardiskMemory.setForeground(Color.GREEN);
        }else{
            txt_hardiskMemory.setForeground(Color.RED);
        }
    }
    
    public void updateQueueTable(){
        clearTable(modelQueue);
        Memory tempMemory = compu.getMainMemory();
        ArrayList<LOGIC.Process> processInMemory = tempMemory.getInstrucctionsByProcess();
        
        for (int i=0; i<processInMemory.size();i++){
            
            LOGIC.Process temp = processInMemory.get(i);
            ArrayList<String> instrucString = temp.getInstructions();
            
            for (int j=0; j<instrucString.size();j++){
                Object[] objectData = new Object[] { 
                    String.valueOf(temp.getID()),
                    temp.getCPU(),
                    instrucString.get(j),
                };
                modelQueue.addRow(objectData);
                
            } 
        }
        tbl_queue.setModel(modelQueue);
    }
    
    public void updateCore1Table(){
        if(compu.getCore1().getCurrentProcess()!= null){
            C1_instruction.setText(compu.getCore1().getCurrentInstruction());
            C1_porcessID.setText(String.valueOf(compu.getCore1().getCurrentProcess().getID()));
            C1_rt.setText(String.valueOf(compu.getCore1().getRemainingInsTime()));
        }
        else{
            C1_instruction.setText("");
            C1_porcessID.setText("");
            C1_rt.setText("");
        }
    }
    
    public void updateCore2Table(){
        if(compu.getCore2().getCurrentProcess()!= null){
            C2_instruction.setText(compu.getCore2().getCurrentInstruction());
            C2_processID.setText(String.valueOf(compu.getCore2().getCurrentProcess().getID()));
            C2_rt.setText(String.valueOf(compu.getCore2().getRemainingInsTime()));
        }else{
            C2_instruction.setText("");
            C2_processID.setText("");
            C2_rt.setText("");
        }
        
    }
    
    
    
    
    /*
    public void setColorTable(JTable tabla, int p_row, Color p_color){
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(p_row == row){
                    c.setBackground(p_color);
                }else{
                    c.setBackground(this.getBackground());
                }
                
                return c;
            }
        });
    }*/
    
     private void clearTable(DefaultTableModel p_model){
        if(p_model.getRowCount()>0){
            int countrows=p_model.getRowCount();
            for (int i = countrows; i> 0;i--){
                p_model.removeRow(i-1);
            }
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl_files = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btn_loadFiles = new javax.swing.JButton();
        btn_startExecution = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_files = new javax.swing.JTable();
        pnl_elements = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txt_output = new javax.swing.JTextPane();
        txt_input = new javax.swing.JTextField();
        pnl_process = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_process = new javax.swing.JTable();
        pnl_nucleo1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbl_mainMemory = new javax.swing.JTable();
        txt_mainMemoryPorcent = new javax.swing.JLabel();
        pnl_harddisk = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        C2_processID = new javax.swing.JLabel();
        C2_instruction = new javax.swing.JLabel();
        C2_rt = new javax.swing.JLabel();
        pnl_nucleo2 = new javax.swing.JPanel();
        lbl_hardisk = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_hardMemory = new javax.swing.JTable();
        txt_hardiskMemory = new javax.swing.JLabel();
        pnl_execution = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btn_next = new javax.swing.JButton();
        txt_executionTime = new javax.swing.JLabel();
        pnl_mainmemory = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        C1_porcessID = new javax.swing.JLabel();
        C1_instruction = new javax.swing.JLabel();
        C1_rt = new javax.swing.JLabel();
        pnl_elements1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbl_queue = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1200, 700));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnl_files.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(69, 90, 100));
        jLabel1.setText("Files");

        btn_loadFiles.setBackground(new java.awt.Color(69, 90, 100));
        btn_loadFiles.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btn_loadFiles.setForeground(new java.awt.Color(255, 255, 255));
        btn_loadFiles.setText("Load files");
        btn_loadFiles.setBorder(null);
        btn_loadFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loadFilesActionPerformed(evt);
            }
        });

        btn_startExecution.setBackground(new java.awt.Color(69, 90, 100));
        btn_startExecution.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btn_startExecution.setForeground(new java.awt.Color(255, 255, 255));
        btn_startExecution.setText("Start");
        btn_startExecution.setBorder(null);
        btn_startExecution.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_startExecutionActionPerformed(evt);
            }
        });

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tbl_files.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "File"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_files.setGridColor(java.awt.Color.lightGray);
        tbl_files.setSelectionBackground(java.awt.Color.lightGray);
        jScrollPane1.setViewportView(tbl_files);

        javax.swing.GroupLayout pnl_filesLayout = new javax.swing.GroupLayout(pnl_files);
        pnl_files.setLayout(pnl_filesLayout);
        pnl_filesLayout.setHorizontalGroup(
            pnl_filesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_filesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_filesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(btn_startExecution, javax.swing.GroupLayout.DEFAULT_SIZE, 170, Short.MAX_VALUE)
                    .addGroup(pnl_filesLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(btn_loadFiles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_filesLayout.setVerticalGroup(
            pnl_filesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_filesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_loadFiles, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_startExecution, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        getContentPane().add(pnl_files, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 350));

        pnl_elements.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(69, 90, 100));
        jLabel3.setText("Output");

        jScrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        txt_output.setEditable(false);
        txt_output.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(238, 238, 238)));
        txt_output.setFont(new java.awt.Font("Consolas", 0, 14)); // NOI18N
        jScrollPane3.setViewportView(txt_output);

        txt_input.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        txt_input.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_inputActionPerformed(evt);
            }
        });
        txt_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_inputKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout pnl_elementsLayout = new javax.swing.GroupLayout(pnl_elements);
        pnl_elements.setLayout(pnl_elementsLayout);
        pnl_elementsLayout.setHorizontalGroup(
            pnl_elementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_elementsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_elementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(pnl_elementsLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 207, Short.MAX_VALUE))
                    .addComponent(txt_input))
                .addContainerGap())
        );
        pnl_elementsLayout.setVerticalGroup(
            pnl_elementsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_elementsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_input, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(pnl_elements, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 0, 310, 350));

        pnl_process.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(69, 90, 100));
        jLabel2.setText("Process");

        jScrollPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tbl_process.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Process ID", "Process name", "State", "AC", "AX", "BX", "CX", "DX"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_process.setGridColor(new java.awt.Color(238, 238, 238));
        jScrollPane2.setViewportView(tbl_process);

        javax.swing.GroupLayout pnl_processLayout = new javax.swing.GroupLayout(pnl_process);
        pnl_process.setLayout(pnl_processLayout);
        pnl_processLayout.setHorizontalGroup(
            pnl_processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_processLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addGroup(pnl_processLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnl_processLayout.setVerticalGroup(
            pnl_processLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_processLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        getContentPane().add(pnl_process, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 700, 350));

        pnl_nucleo1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(69, 90, 100));
        jLabel6.setText("Main Memory");

        jScrollPane6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tbl_mainMemory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Process ID", "Instruction"
            }
        ));
        tbl_mainMemory.setGridColor(new java.awt.Color(238, 238, 238));
        jScrollPane6.setViewportView(tbl_mainMemory);

        txt_mainMemoryPorcent.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        txt_mainMemoryPorcent.setForeground(new java.awt.Color(69, 90, 100));
        txt_mainMemoryPorcent.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txt_mainMemoryPorcent.setText("0%");

        javax.swing.GroupLayout pnl_nucleo1Layout = new javax.swing.GroupLayout(pnl_nucleo1);
        pnl_nucleo1.setLayout(pnl_nucleo1Layout);
        pnl_nucleo1Layout.setHorizontalGroup(
            pnl_nucleo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_nucleo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_nucleo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_nucleo1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                        .addComponent(txt_mainMemoryPorcent))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_nucleo1Layout.setVerticalGroup(
            pnl_nucleo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_nucleo1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_nucleo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_mainMemoryPorcent))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(pnl_nucleo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 350, 350, 350));

        pnl_harddisk.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(69, 90, 100));
        jLabel5.setText("Core 2");

        jLabel15.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(69, 90, 100));
        jLabel15.setText("Remaining time:");

        jLabel16.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(69, 90, 100));
        jLabel16.setText("Process ID:");

        jLabel17.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(69, 90, 100));
        jLabel17.setText("Instruction:");

        C2_processID.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        C2_processID.setForeground(new java.awt.Color(69, 90, 100));

        C2_instruction.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        C2_instruction.setForeground(new java.awt.Color(69, 90, 100));

        C2_rt.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        C2_rt.setForeground(new java.awt.Color(69, 90, 100));

        javax.swing.GroupLayout pnl_harddiskLayout = new javax.swing.GroupLayout(pnl_harddisk);
        pnl_harddisk.setLayout(pnl_harddiskLayout);
        pnl_harddiskLayout.setHorizontalGroup(
            pnl_harddiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_harddiskLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_harddiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_harddiskLayout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(C2_instruction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_harddiskLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(C2_processID, javax.swing.GroupLayout.DEFAULT_SIZE, 195, Short.MAX_VALUE))
                    .addGroup(pnl_harddiskLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnl_harddiskLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(C2_rt, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_harddiskLayout.setVerticalGroup(
            pnl_harddiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_harddiskLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(pnl_harddiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(C2_processID, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(pnl_harddiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(C2_instruction)
                    .addComponent(jLabel17))
                .addGap(18, 18, 18)
                .addGroup(pnl_harddiskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(C2_rt))
                .addGap(33, 33, 33))
        );

        getContentPane().add(pnl_harddisk, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 520, 310, 180));

        pnl_nucleo2.setBackground(new java.awt.Color(255, 255, 255));

        lbl_hardisk.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        lbl_hardisk.setForeground(new java.awt.Color(69, 90, 100));
        lbl_hardisk.setText("Hard disk");

        jScrollPane7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tbl_hardMemory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proess ID", "Instruction"
            }
        ));
        tbl_hardMemory.setGridColor(new java.awt.Color(238, 238, 238));
        jScrollPane7.setViewportView(tbl_hardMemory);

        txt_hardiskMemory.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        txt_hardiskMemory.setForeground(new java.awt.Color(69, 90, 100));
        txt_hardiskMemory.setText("0%");

        javax.swing.GroupLayout pnl_nucleo2Layout = new javax.swing.GroupLayout(pnl_nucleo2);
        pnl_nucleo2.setLayout(pnl_nucleo2Layout);
        pnl_nucleo2Layout.setHorizontalGroup(
            pnl_nucleo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_nucleo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_nucleo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_nucleo2Layout.createSequentialGroup()
                        .addComponent(lbl_hardisk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 187, Short.MAX_VALUE)
                        .addComponent(txt_hardiskMemory))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_nucleo2Layout.setVerticalGroup(
            pnl_nucleo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_nucleo2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_nucleo2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_hardisk)
                    .addComponent(txt_hardiskMemory))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(pnl_nucleo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 350, 350, -1));

        pnl_execution.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(69, 90, 100));
        jLabel4.setText("Execution time");

        btn_next.setBackground(new java.awt.Color(69, 90, 100));
        btn_next.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        btn_next.setForeground(new java.awt.Color(255, 255, 255));
        btn_next.setText("Next instruction");
        btn_next.setBorder(null);
        btn_next.setEnabled(false);
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });

        txt_executionTime.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 36)); // NOI18N
        txt_executionTime.setForeground(new java.awt.Color(69, 90, 100));
        txt_executionTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_executionTime.setText("0");

        javax.swing.GroupLayout pnl_executionLayout = new javax.swing.GroupLayout(pnl_execution);
        pnl_execution.setLayout(pnl_executionLayout);
        pnl_executionLayout.setHorizontalGroup(
            pnl_executionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_executionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_executionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_executionLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 1, Short.MAX_VALUE))
                    .addComponent(btn_next, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(pnl_executionLayout.createSequentialGroup()
                .addComponent(txt_executionTime, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnl_executionLayout.setVerticalGroup(
            pnl_executionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_executionLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_executionTime, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_next, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        getContentPane().add(pnl_execution, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 190, 350));

        pnl_mainmemory.setBackground(new java.awt.Color(255, 255, 255));

        jLabel11.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(69, 90, 100));
        jLabel11.setText("Core1");

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(69, 90, 100));
        jLabel7.setText("Remaining time:");

        jLabel9.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(69, 90, 100));
        jLabel9.setText("Process ID:");

        jLabel10.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(69, 90, 100));
        jLabel10.setText("Instruction:");

        C1_porcessID.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        C1_porcessID.setForeground(new java.awt.Color(69, 90, 100));

        C1_instruction.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        C1_instruction.setForeground(new java.awt.Color(69, 90, 100));

        C1_rt.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        C1_rt.setForeground(new java.awt.Color(69, 90, 100));

        javax.swing.GroupLayout pnl_mainmemoryLayout = new javax.swing.GroupLayout(pnl_mainmemory);
        pnl_mainmemory.setLayout(pnl_mainmemoryLayout);
        pnl_mainmemoryLayout.setHorizontalGroup(
            pnl_mainmemoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_mainmemoryLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_mainmemoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_mainmemoryLayout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(C1_porcessID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnl_mainmemoryLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(C1_instruction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel11)
                    .addGroup(pnl_mainmemoryLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(C1_rt, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnl_mainmemoryLayout.setVerticalGroup(
            pnl_mainmemoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_mainmemoryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnl_mainmemoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(C1_porcessID))
                .addGap(18, 18, 18)
                .addGroup(pnl_mainmemoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(C1_instruction))
                .addGap(18, 18, 18)
                .addGroup(pnl_mainmemoryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(C1_rt))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        getContentPane().add(pnl_mainmemory, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 350, 310, 175));

        pnl_elements1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(69, 90, 100));
        jLabel8.setText("Queue");

        jScrollPane8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tbl_queue.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Proess ID", "Core", "Instruction"
            }
        ));
        tbl_queue.setGridColor(new java.awt.Color(238, 238, 238));
        jScrollPane8.setViewportView(tbl_queue);

        javax.swing.GroupLayout pnl_elements1Layout = new javax.swing.GroupLayout(pnl_elements1);
        pnl_elements1.setLayout(pnl_elements1Layout);
        pnl_elements1Layout.setHorizontalGroup(
            pnl_elements1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_elements1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnl_elements1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnl_elements1Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(0, 207, Short.MAX_VALUE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnl_elements1Layout.setVerticalGroup(
            pnl_elements1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnl_elements1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(pnl_elements1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 0, -1, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        if(!compu.finishProgram()){
            compu.nextInstruction();
            updateVisualElements();
        }
        else{
            btn_next.setEnabled(false);
        }
        
        
    }//GEN-LAST:event_btn_nextActionPerformed

    private void btn_loadFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loadFilesActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setMultiSelectionEnabled(true);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "ASM FILES", "asm", "asm");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            
            File[] files = chooser.getSelectedFiles();
            
            for (int i=0; i<files.length;i++){
                File temp = files[i];
                compu.addFile(temp);
                modelFiles.addRow(new Object[]{temp.getName()});
            }
            
            tbl_files.setModel(modelFiles);
  
        }
        
    }//GEN-LAST:event_btn_loadFilesActionPerformed

    private void txt_inputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_inputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_inputActionPerformed

    private void btn_startExecutionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_startExecutionActionPerformed
        if(compu.getSizeOfLoadFiles()>0){
            compu.loadProcessfromFile();     
            compu.sendMessagetoOutput("files uploaded successfully");
            compu.sendMessagetoOutput("execution started");
            btn_startExecution.setEnabled(false);
            btn_loadFiles.setEnabled(false);
            btn_next.setEnabled(true);
        }else{
            compu.sendMessagetoOutput("please load some files first");
        }
        
        updateVisualElements();
        
        
    }//GEN-LAST:event_btn_startExecutionActionPerformed

    private void txt_inputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_inputKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER) {
          compu.keyboardEnter(txt_input.getText());
          txt_input.setText("");
        }
        else if(evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
          txt_input.setEditable(true);
        }else{
            String value = txt_input.getText();
            int l = value.length();
            if (evt.getKeyChar() >= '0' && evt.getKeyChar() <= '9') {
               txt_input.setEditable(true);
            } else {
               txt_input.setEditable(false);
            }
        }
    }//GEN-LAST:event_txt_inputKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel C1_instruction;
    private javax.swing.JLabel C1_porcessID;
    private javax.swing.JLabel C1_rt;
    private javax.swing.JLabel C2_instruction;
    private javax.swing.JLabel C2_processID;
    private javax.swing.JLabel C2_rt;
    private javax.swing.JButton btn_loadFiles;
    private javax.swing.JButton btn_next;
    private javax.swing.JButton btn_startExecution;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lbl_hardisk;
    private javax.swing.JPanel pnl_elements;
    private javax.swing.JPanel pnl_elements1;
    private javax.swing.JPanel pnl_execution;
    private javax.swing.JPanel pnl_files;
    private javax.swing.JPanel pnl_harddisk;
    private javax.swing.JPanel pnl_mainmemory;
    private javax.swing.JPanel pnl_nucleo1;
    private javax.swing.JPanel pnl_nucleo2;
    private javax.swing.JPanel pnl_process;
    private javax.swing.JTable tbl_files;
    private javax.swing.JTable tbl_hardMemory;
    private javax.swing.JTable tbl_mainMemory;
    private javax.swing.JTable tbl_process;
    private javax.swing.JTable tbl_queue;
    private javax.swing.JLabel txt_executionTime;
    private javax.swing.JLabel txt_hardiskMemory;
    private javax.swing.JTextField txt_input;
    private javax.swing.JLabel txt_mainMemoryPorcent;
    private javax.swing.JTextPane txt_output;
    // End of variables declaration//GEN-END:variables
}



