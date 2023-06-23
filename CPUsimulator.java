

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class CPUsimulator implements ActionListener {
    //CPUsimulator
    ArrayList<String> instructionMemory = new ArrayList<>();
    ArrayList<String> MAR = new ArrayList<>();
    ArrayList<String> MDR = new ArrayList<>();
    Integer PC = 0;
    ArrayList<String> IR = new ArrayList<>();

    JFrame frame;

    JTextArea readText;
    JTextArea translateText;
    JTextArea finalresultText;
    int num1;
    int num2;
    int H,S,V,N,Z,C;
    int R0,R1,R2,R3,
            R4,R5,R6,R7,
            R8,R9,R10,R11,
            R12,R13,R14,R15;
    JButton readButton, translateButton, finalresultButton;


    CPUsimulator() {
        //整个页面
        frame = new JFrame("CPU模拟");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(820, 630);
        frame.setLayout(null);
        Container contentPane = frame.getContentPane();
        //读取文本区域
        readText = new JTextArea();
        readText.setBackground(Color.LIGHT_GRAY); // 设置背景颜色
        readText.setBounds(0, 0, 400, 280);
        readText.setEditable(false);
        readText.setLineWrap(true);
        readText.setText("读取结果");
        //编译文本区域
        translateText = new JTextArea();
        translateText.setBackground(Color.LIGHT_GRAY); // 设置背景颜色
        translateText.setBounds(0, 281, 400, 280);
        translateText.setEditable(false);
        translateText.setLineWrap(true);
        translateText.setText("编译结果");
        //最终结果区域
        finalresultText = new JTextArea(34, 33);
        JPanel jPanel = new JPanel();
        JScrollPane scrollPane = new JScrollPane();
        finalresultText.setBackground(Color.LIGHT_GRAY); // 设置背景颜色
        jPanel.setBounds(400, 0, 400, 560);
        scrollPane.getViewport().add(finalresultText);
        scrollPane.setBounds(0,0,400,560);
        jPanel.add(scrollPane);
        finalresultText.setText("最终结果");
        //按钮设置
        readButton = new JButton("读取");
//        readButton.setFont(new Font("Arial", Font.BOLD, 5));
        readButton.setBounds(40, 560, 60, 30);
        readButton.addActionListener(this);

        translateButton = new JButton("编译");
//        translateButton.setFont(new Font("Arial", Font.BOLD, 18));
        translateButton.setBounds(110, 560, 60, 30);
        translateButton.addActionListener(this);

        finalresultButton = new JButton("生成结果文档");
//        finalresultButton.setFont(new Font("Arial", Font.BOLD, 18));
        finalresultButton.setBounds(180, 560, 140, 30);
        finalresultButton.addActionListener(this);
        //将内容加入页面
        contentPane.add(readText);
        contentPane.add(translateText);

        contentPane.add(jPanel);
        contentPane.add(readButton);
        contentPane.add(translateButton);
        contentPane.add(finalresultButton);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new CPUsimulator();
    }

    // 当用户点击按钮时调用该方法
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == readButton) {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
            int result = fileChooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(selectedFile));
                    String line;
                    readText.setText("");
                    while ((line = reader.readLine()) != null) {
                        readText.append(line + "\n");
                    }
                    reader.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else if (e.getSource() == translateButton) {
            String str01 = readText.getText();
            String[] arrs = str01.split("\\n");
            for (String arr : arrs) {
                instructionMemory.add(arr);
            }
            translateText.setText("");
            for (String str : instructionMemory) {
                String[] arr = str.split("[,\\s]+");
                switch (arr[0]) {
                    case "Add":
                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        num2 = Integer.parseInt(arr[2].replace("R", ""));

                        translateText.append(String.format("%4s", Integer.toHexString(PC++)).toUpperCase().replace(' ', '0')+": "+"0000 1100 " + String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')
                                + " " + String.format("%4s", Integer.toBinaryString(num2)).replace(' ', '0') + "\n");
                        break;
                    case "Sub":
                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        num2 = Integer.parseInt(arr[2].replace("R", ""));

                        translateText.append(String.format("%4s", Integer.toHexString(PC++)).toUpperCase().replace(' ', '0')+": "+"0000 1000 " + String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')
                                + " " + String.format("%4s", Integer.toBinaryString(num2)).replace(' ', '0') + "\n");
                        break;
                    case "Mul":
                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        num2 = Integer.parseInt(arr[2].replace("R", ""));

                        translateText.append(String.format("%4s", Integer.toHexString(PC++)).toUpperCase().replace(' ', '0')+": "+"1001 1100 " + String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')
                                + " " + String.format("%4s", Integer.toBinaryString(num2)).replace(' ', '0') + "\n");
                        break;
                    case "Mov":
                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        num2 = Integer.parseInt(arr[2].replace("R", ""));
                        translateText.append(String.format("%4s", Integer.toHexString(PC++)).toUpperCase().replace(' ', '0')+": "+"0010 1100 " + String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')
                                + " " + String.format("%4s", Integer.toBinaryString(num2)).replace(' ', '0') + "\n");
                        break;
                    case "RJMP":
                        num1 = Integer.parseInt(arr[1]);
                        translateText.append(String.format("%4s", Integer.toHexString(PC++)).toUpperCase().replace(' ', '0')+": "+"1100 " + String.format("%12s", Integer.toBinaryString(num1)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")
                                + "\n");
                        break;
                    case "BRMI":
                        num1 = Integer.parseInt(arr[1]);
                        translateText.append(String.format("%4s", Integer.toHexString(PC++)).toUpperCase().replace(' ', '0')+": "+"1111 0001 " + String.format("%8s", Integer.toBinaryString(num1)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")
                                + "\n");
                        break;
                    case "Ldi":
                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        String te = arr[2].substring(0,1);
                        String bin = Integer.toBinaryString(Integer.parseInt(te, 16));
                        translateText.append(String.format("%4s", Integer.toHexString(PC++)).toUpperCase().replace(' ', '0')+": "+"1110 "+bin+" "+
                                String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')+" "+ bin+"\n");
                        break;
                    case "Ld":
//                        1001 0000 dddd 1100
                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        translateText.append(String.format("%4s", Integer.toHexString(PC++)).toUpperCase().replace(' ', '0')+": "+"1001 0000 "+
                                String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')+" 1100\n");
                        break;
                    case "St":
//                        1001 0000 dddd 1100
                        num2 = Integer.parseInt(arr[2].replace("R", ""));
                        translateText.append(String.format("%4s", Integer.toHexString(PC++)).toUpperCase().replace(' ', '0')+": "+"1001 0010 "+
                                String.format("%4s", Integer.toBinaryString(num2)).replace(' ', '0')+" 1100\n");
                        break;
                    case "Nop":
                        translateText.append(String.format("%4s", Integer.toHexString(PC++)).toUpperCase().replace(' ', '0')+": "+"0000 0000 0000 0000\n");
                        break;
                }
            }
            PC=0;
        } else if (e.getSource() == finalresultButton) {
            String temp;
            finalresultText.setText("");
            for (String str : instructionMemory) {
                String[] arr = str.split("[,\\s]+");
//                System.out.println(arr[0]+""+arr[1]);
                switch (arr[0]) {
                    case "Add":
                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        num2 = Integer.parseInt(arr[2].replace("R", ""));
                        finalresultText.append("执行："+ arr[0] + " " + arr[1]+","+arr[2]+"\n");
                        finalresultText.append("当前PC寄存器: " + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("进行取指令\n");
                        temp  = "0000 1100 " + String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')
                                + " " + String.format("%4s", Integer.toBinaryString(num2)).replace(' ', '0') + "\n";
                        finalresultText.append("\t指令存储器地址总线："+String.format("%16s", Integer.toBinaryString(PC++)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("\t指令存储器数据总线："+temp);
                        finalresultText.append("\t当前指令寄存器："+temp);
                        finalresultText.append("取指令完成，进行译码\n");
                        finalresultText.append(arr[0]+"\n");
                        finalresultText.append("译码结果：加法指令\n");
                        finalresultText.append("机器码："+temp);
                        if((num2+num1)==0){
                            Z = 1;
                        }
                        finalresultText.append("目的操作数存储器地址总线："+String.format("%16s", Integer.toBinaryString(num1)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("源操作数存储器地址总线："+String.format("%16s", Integer.toBinaryString(num2)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("此时PC寄存器：" + String.format("%04X", PC)+"\n");
                        finalresultText.append("H:"+ H +"      S:"+S+"      V:"+V+"      N:"+N+"     Z:"+Z+"      C:"+C+"\n");
                        finalresultText.append("R0:"+ R0 +"      R1:"+R1+"      R2:"+R2+"      R3:"+R3+"\n");
                        finalresultText.append("R4:"+ R4 +"      R5:"+R5+"      R6:"+R6+"      R7:"+R7+"\n");
                        finalresultText.append("R8:"+ R8 +"      R9:"+R9+"      R10:"+R10+"      R11:"+R11+"\n");
                        finalresultText.append("R12:"+ R12 +"      R13:"+R13+"      R14:"+R14+"      R15:"+R15+"\n");
                        finalresultText.append("--------------------------------------------\n");
                        break;
                    case "Sub":
                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        num2 = Integer.parseInt(arr[2].replace("R", ""));
                        finalresultText.append("执行："+ "Sub" + " " + arr[1]+","+arr[2]+"\n");
                        finalresultText.append("当前PC寄存器: " + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("进行取指令\n");
                        temp  = "0000 1100 " + String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')
                                + " " + String.format("%4s", Integer.toBinaryString(num2)).replace(' ', '0') + "\n";
                        finalresultText.append("\t指令存储器地址总线："+String.format("%16s", Integer.toBinaryString(PC++)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("\t指令存储器数据总线："+temp);
                        finalresultText.append("\t当前指令寄存器："+temp);
                        finalresultText. append("取指令完成，进行译码\n");
                        finalresultText.append(arr[0]+"\n");
                        finalresultText.append("译码结果：减法指令\n");
                        finalresultText.append("机器码："+temp);
                        if((num2-num1)==0){
                            Z = 1;
                        }
                        finalresultText.append("目的操作数存储器地址总线："+String.format("%16s", Integer.toBinaryString(num1)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("源操作数存储器地址总线："+String.format("%16s", Integer.toBinaryString(num2)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("此时PC寄存器：" + String.format("%04X", PC)+"\n");
                        finalresultText.append("H:"+ H +"      S:"+S+"      V:"+V+"      N:"+N+"     Z:"+Z+"      C:"+C+"\n");
                        finalresultText.append("R0:"+ R0 +"      R1:"+R1+"      R2:"+R2+"      R3:"+R3+"\n");
                        finalresultText.append("R4:"+ R4 +"      R5:"+R5+"      R6:"+R6+"      R7:"+R7+"\n");
                        finalresultText.append("R8:"+ R8 +"      R9:"+R9+"      R10:"+R10+"      R11:"+R11+"\n");
                        finalresultText.append("R12:"+ R12 +"      R13:"+R13+"      R14:"+R14+"      R15:"+R15+"\n");
                        finalresultText.append("--------------------------------------------\n");
                        break;
                    case "Mul":
                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        num2 = Integer.parseInt(arr[2].replace("R", ""));
                        finalresultText.append("执行："+ "Mul" + " " + arr[1]+","+arr[2]+"\n");
                        finalresultText.append("当前PC寄存器: " + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("进行取指令\n");
                        temp  = "0000 1100 " + String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')
                                + " " + String.format("%4s", Integer.toBinaryString(num2)).replace(' ', '0') + "\n";
                        finalresultText.append("\t指令存储器地址总线："+String.format("%16s", Integer.toBinaryString(PC++)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("\t指令存储器数据总线："+temp);
                        finalresultText.append("\t当前指令寄存器："+temp);
                        finalresultText.append("取指令完成，进行译码\n");
                        finalresultText.append(arr[0]+"\n");
                        finalresultText.append("译码结果：无符号乘法指令\n");
                        finalresultText.append("机器码："+temp);
//                        目的操作数存储器地址总线：0000 0000 0000 0011
//                        源操作数存储器地址总线：0000 0000 0000 1001
//                        源操作数存储器数据总线：0000 0000
//                        此时PC寄存器：0002H
                        if((num2*num1)==0){
                            Z = 1;
                        }
                        finalresultText.append("目的操作数存储器地址总线："+String.format("%16s", Integer.toBinaryString(num1)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("源操作数存储器地址总线："+String.format("%16s", Integer.toBinaryString(num2)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("此时PC寄存器：" + String.format("%04X", PC)+"\n");
                        finalresultText.append("H:"+ H +"      S:"+S+"      V:"+V+"      N:"+N+"     Z:"+Z+"      C:"+C+"\n");
                        finalresultText.append("R0:"+ R0 +"      R1:"+R1+"      R2:"+R2+"      R3:"+R3+"\n");
                        finalresultText.append("R4:"+ R4 +"      R5:"+R5+"      R6:"+R6+"      R7:"+R7+"\n");
                        finalresultText.append("R8:"+ R8 +"      R9:"+R9+"      R10:"+R10+"      R11:"+R11+"\n");
                        finalresultText.append("R12:"+ R12 +"      R13:"+R13+"      R14:"+R14+"      R15:"+R15+"\n");
                        finalresultText.append("--------------------------------------------\n");
                        break;
                    case "Mov":
                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        num2 = Integer.parseInt(arr[2].replace("R", ""));
                        finalresultText.append("执行："+ "Mov" + " " + arr[1]+","+arr[2]+"\n");
                        finalresultText.append("当前PC寄存器: " + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("进行取指令\n");
                        temp  = "0000 1100 " + String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')
                                + " " + String.format("%4s", Integer.toBinaryString(num2)).replace(' ', '0') + "\n";
                        finalresultText.append("\t指令存储器地址总线："+String.format("%16s", Integer.toBinaryString(PC++)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("\t指令存储器数据总线："+temp);
                        finalresultText.append("\t当前指令寄存器："+temp);
                        finalresultText.append("取指令完成，进行译码\n");
                        finalresultText.append(arr[0]+"\n");
                        finalresultText.append("译码结果：数据传送指令\n");
                        finalresultText.append("机器码："+temp);
                        finalresultText.append("目的操作数存储器地址总线："+String.format("%16s", Integer.toBinaryString(num1)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("源操作数存储器地址总线："+String.format("%16s", Integer.toBinaryString(num2)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("此时PC寄存器：" + String.format("%04X", PC)+"\n");
                        finalresultText.append("H:"+ H +"      S:"+S+"      V:"+V+"      N:"+N+"     Z:"+Z+"      C:"+C+"\n");
                        finalresultText.append("R0:"+ R0 +"      R1:"+R1+"      R2:"+R2+"      R3:"+R3+"\n");
                        finalresultText.append("R4:"+ R4 +"      R5:"+R5+"      R6:"+R6+"      R7:"+R7+"\n");
                        finalresultText.append("R8:"+ R8 +"      R9:"+R9+"      R10:"+R10+"      R11:"+R11+"\n");
                        finalresultText.append("R12:"+ R12 +"      R13:"+R13+"      R14:"+R14+"      R15:"+R15+"\n");
                        finalresultText.append("--------------------------------------------\n");
                        break;
                    case "RJMP":
                        num1 = Integer.parseInt(arr[1]);
                        finalresultText.append("执行："+ "RJMP " + arr[1] +"\n");
                        finalresultText.append("当前PC寄存器: " + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("进行取指令\n");
                        temp  = "1100 " + String.format("%12s", Integer.toBinaryString(num1)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")
                                + "\n";
                        finalresultText.append("\t指令存储器地址总线："+String.format("%16s", Integer.toBinaryString(PC++)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("\t指令存储器数据总线："+temp);
                        finalresultText.append("\t当前指令寄存器："+temp);
                        finalresultText.append("取指令完成，进行译码\n");
                        finalresultText.append(arr[0]+"\n");
                        finalresultText.append("译码结果：无条件相对跳转指令\n");
                        finalresultText.append("机器码："+temp);
                        finalresultText.append("此时PC寄存器：" + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("H:"+ H +"      S:"+S+"      V:"+V+"      N:"+N+"     Z:"+Z+"      C:"+C+"\n");
                        finalresultText.append("R0:"+ R0 +"      R1:"+R1+"      R2:"+R2+"      R3:"+R3+"\n");
                        finalresultText.append("R4:"+ R4 +"      R5:"+R5+"      R6:"+R6+"      R7:"+R7+"\n");
                        finalresultText.append("R8:"+ R8 +"      R9:"+R9+"      R10:"+R10+"      R11:"+R11+"\n");
                        finalresultText.append("R12:"+ R12 +"      R13:"+R13+"      R14:"+R14+"      R15:"+R15+"\n");
                        finalresultText.append("--------------------------------------------\n");
                        break;
                    case "BRMI":
//
                        finalresultText.append("执行："+ "BRMI " + arr[1] +"\n");
                        finalresultText.append("当前PC寄存器: " + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("进行取指令\n");
                        temp  = "1111 0001 " + String.format("%8s", Integer.toBinaryString(num1)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")
                                + "\n";
                        finalresultText.append("\t指令存储器地址总线："+String.format("%16s", Integer.toBinaryString(PC++)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("\t指令存储器数据总线："+temp);
                        finalresultText.append("\t当前指令寄存器："+temp);
                        finalresultText.append("取指令完成，进行译码\n");
                        finalresultText.append(arr[0]+"\n");
                        finalresultText.append("译码结果：有条件相对跳转指令\n");
                        finalresultText.append("机器码："+temp);
                        finalresultText.append("此时PC寄存器：" + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("H:"+ H +"      S:"+S+"      V:"+V+"      N:"+N+"     Z:"+Z+"      C:"+C+"\n");
                        finalresultText.append("R0:"+ R0 +"      R1:"+R1+"      R2:"+R2+"      R3:"+R3+"\n");
                        finalresultText.append("R4:"+ R4 +"      R5:"+R5+"      R6:"+R6+"      R7:"+R7+"\n");
                        finalresultText.append("R8:"+ R8 +"      R9:"+R9+"      R10:"+R10+"      R11:"+R11+"\n");
                        finalresultText.append("R12:"+ R12 +"      R13:"+R13+"      R14:"+R14+"      R15:"+R15+"\n");
                        finalresultText.append("--------------------------------------------\n");
                        break;
                    case "Ldi":

                        num1 = Integer.parseInt(arr[1].replace("R", ""));
                        String te = arr[2].substring(0,1);
                        String bin = Integer.toBinaryString(Integer.parseInt(te, 16));

                        finalresultText.append("执行："+ "Ldi" + " " + arr[1]+","+arr[2]+"\n");
                        finalresultText.append("当前PC寄存器: " + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("进行取指令\n");
                        temp  = "1110 "+bin+" "+
                                String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')+" "+ bin+"\n";
                        finalresultText.append("\t指令存储器地址总线："+String.format("%16s", Integer.toBinaryString(PC++)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("\t指令存储器数据总线："+temp);
                        finalresultText.append("\t当前指令寄存器："+temp);
                        finalresultText.append("取指令完成，进行译码\n");
                        finalresultText.append(arr[0]+"\n");
                        finalresultText.append("译码结果：载入立即数指令\n");
                        finalresultText.append("机器码："+temp);
                        finalresultText.append("此时PC寄存器：" + String.format("%04X", PC)+"\n");
                        finalresultText.append("H:"+ H +"      S:"+S+"      V:"+V+"      N:"+N+"     Z:"+Z+"      C:"+C+"\n");
                        finalresultText.append("R0:"+ R0 +"      R1:"+R1+"      R2:"+R2+"      R3:"+R3+"\n");
                        finalresultText.append("R4:"+ R4 +"      R5:"+R5+"      R6:"+R6+"      R7:"+R7+"\n");
                        finalresultText.append("R8:"+ R8 +"      R9:"+R9+"      R10:"+R10+"      R11:"+R11+"\n");
                        finalresultText.append("R12:"+ R12 +"      R13:"+R13+"      R14:"+R14+"      R15:"+R15+"\n");
                        finalresultText.append("--------------------------------------------\n");
                        break;
                    case "Ld":

                        num1 = Integer.parseInt(arr[1].replace("R", ""));

                        finalresultText.append("执行："+ "Ld" + " " + arr[1]+","+arr[2]+"\n");
                        finalresultText.append("当前PC寄存器: " + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("进行取指令\n");
                        temp  = "1001 0000 "+
                                String.format("%4s", Integer.toBinaryString(num1)).replace(' ', '0')+" 1100\n";
                        finalresultText.append("\t指令存储器地址总线："+String.format("%16s", Integer.toBinaryString(PC++)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("\t指令存储器数据总线："+temp);
                        finalresultText.append("\t当前指令寄存器："+temp);
                        finalresultText.append("取指令完成，进行译码\n");
                        finalresultText.append(arr[0]+"\n");
                        finalresultText.append("译码结果：装载指令\n");
                        finalresultText.append("机器码："+temp);
                        finalresultText.append("此时PC寄存器：" + String.format("%04X", PC)+"\n");
                        finalresultText.append("H:"+ H +"      S:"+S+"      V:"+V+"      N:"+N+"     Z:"+Z+"      C:"+C+"\n");
                        finalresultText.append("R0:"+ R0 +"      R1:"+R1+"      R2:"+R2+"      R3:"+R3+"\n");
                        finalresultText.append("R4:"+ R4 +"      R5:"+R5+"      R6:"+R6+"      R7:"+R7+"\n");
                        finalresultText.append("R8:"+ R8 +"      R9:"+R9+"      R10:"+R10+"      R11:"+R11+"\n");
                        finalresultText.append("R12:"+ R12 +"      R13:"+R13+"      R14:"+R14+"      R15:"+R15+"\n");
                        finalresultText.append("--------------------------------------------\n");
                        break;
                    case "St":

                        num2 = Integer.parseInt(arr[2].replace("R", ""));

                        finalresultText.append("执行："+ "St" + " " + arr[1]+","+arr[2]+"\n");
                        finalresultText.append("当前PC寄存器: " + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("进行取指令\n");
                        temp  = "1001 0010 "+
                                String.format("%4s", Integer.toBinaryString(num2)).replace(' ', '0')+" 1100\n";
                        finalresultText.append("\t指令存储器地址总线："+String.format("%16s", Integer.toBinaryString(PC++)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("\t指令存储器数据总线："+temp);
                        finalresultText.append("\t当前指令寄存器："+temp);
                        finalresultText.append("取指令完成，进行译码\n");
                        finalresultText.append(arr[0]+"\n");
                        finalresultText.append("译码结果：存储指令\n");
                        finalresultText.append("机器码："+temp);
                        finalresultText.append("此时PC寄存器：" + String.format("%04X", PC)+"\n");
                        finalresultText.append("H:"+ H +"      S:"+S+"      V:"+V+"      N:"+N+"     Z:"+Z+"      C:"+C+"\n");
                        finalresultText.append("R0:"+ R0 +"      R1:"+R1+"      R2:"+R2+"      R3:"+R3+"\n");
                        finalresultText.append("R4:"+ R4 +"      R5:"+R5+"      R6:"+R6+"      R7:"+R7+"\n");
                        finalresultText.append("R8:"+ R8 +"      R9:"+R9+"      R10:"+R10+"      R11:"+R11+"\n");
                        finalresultText.append("R12:"+ R12 +"      R13:"+R13+"      R14:"+R14+"      R15:"+R15+"\n");
                        finalresultText.append("--------------------------------------------\n");
                        break;
                    case "Nop":

                        finalresultText.append("执行："+ "Nop\n");
                        finalresultText.append("当前PC寄存器: " + String.format("%4s", Integer.toHexString(PC)).toUpperCase().replace(' ', '0')+
                                "H\n");
                        finalresultText.append("进行取指令\n");
                        temp  = "0000 0000 0000 0000\n";
                        finalresultText.append("\t指令存储器地址总线："+String.format("%16s", Integer.toBinaryString(PC++)).replace(' ', '0').replaceAll("(.{4})(?!$)", "$1 ")+"\n");
                        finalresultText.append("\t指令存储器数据总线："+temp);
                        finalresultText.append("\t当前指令寄存器："+temp);
                        finalresultText.append("取指令完成，进行译码\n");
                        finalresultText.append(arr[0]+"\n");
                        finalresultText.append("译码结果：空操作指令\n");
                        finalresultText.append("机器码："+temp);
                        finalresultText.append("此时PC寄存器：" + String.format("%04X", PC)+"\n");
                        finalresultText.append("H:"+ H +"      S:"+S+"      V:"+V+"      N:"+N+"     Z:"+Z+"      C:"+C+"\n");
                        finalresultText.append("R0:"+ R0 +"      R1:"+R1+"      R2:"+R2+"      R3:"+R3+"\n");
                        finalresultText.append("R4:"+ R4 +"      R5:"+R5+"      R6:"+R6+"      R7:"+R7+"\n");
                        finalresultText.append("R8:"+ R8 +"      R9:"+R9+"      R10:"+R10+"      R11:"+R11+"\n");
                        finalresultText.append("R12:"+ R12 +"      R13:"+R13+"      R14:"+R14+"      R15:"+R15+"\n");
                        finalresultText.append("--------------------------------------------\n");
                        break;
                }

            }
            BufferedWriter writer = null;
            try {
                writer = new BufferedWriter(new FileWriter("file.txt"));
                writer.write(translateText.getText());
                writer.write(finalresultText.getText()); // 将文本区域的内容写入到文件中
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (writer != null)
                        writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
