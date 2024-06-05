/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi_merge;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.util.Arrays;
import java.util.Random;

public class ChatClient extends JFrame {
    String num;
    int num2[];
    int TamContra; // tamaño de la contraseña

    ChatInterface chat;
    ChatInterface chat1;
    ChatInterface chat2;

    public ChatClient() {
        try {
            chat = (ChatInterface) Naming.lookup("rmi://localhost:1099/ChatService");
            chat1 = (ChatInterface) Naming.lookup("rmi://otherhost1:1099/ChatService"); // Replace with actual IP
            chat2 = (ChatInterface) Naming.lookup("rmi://otherhost2:1099/ChatService"); // Replace with actual IP
        } catch (Exception e) {
            e.printStackTrace();
        }
        initUI();
    }

    private void initUI() {
        setTitle("Practica");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel label1 = new JLabel("Tamaño de la contraseña:");
        label1.setBounds(100, 20, 200, 20);

        JLabel label5 = new JLabel("Arreglo Desacomodado:");
        label5.setBounds(100, 120, 200, 20);

        JLabel label2 = new JLabel("Arreglo Organizado:");
        label2.setBounds(100, 330, 200, 20);

        JLabel label3 = new JLabel("Tiempo:");
        label3.setBounds(100, 560, 200, 20);

        JTextArea ContraTam = new JTextArea("");
        ContraTam.setBounds(300, 20, 200, 20);

        JTextArea arregloDes = new JTextArea("");
        arregloDes.setEditable(false);
        arregloDes.setLineWrap(true);
        arregloDes.setWrapStyleWord(true);
        JScrollPane scrollPane1 = new JScrollPane(arregloDes);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane1.setBounds(100, 140, 800, 160);
        add(scrollPane1);

        JTextArea arregloAcom = new JTextArea("");
        arregloAcom.setEditable(false);
        arregloAcom.setLineWrap(true);
        arregloAcom.setWrapStyleWord(true);
        JScrollPane scrollPane12 = new JScrollPane(arregloAcom);
        scrollPane12.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane12.setBounds(100, 350, 800, 160);
        add(scrollPane12);

        JTextArea tiempoA = new JTextArea("");
        tiempoA.setEditable(false);
        tiempoA.setBounds(100, 580, 200, 100);

        JTextArea tiempoB = new JTextArea("");
        tiempoB.setEditable(false);
        tiempoB.setBounds(400, 580, 200, 100);

        JTextArea tiempoC = new JTextArea("");
        tiempoC.setEditable(false);
        tiempoC.setBounds(700, 580, 200, 100);

        JButton borrar = new JButton("Borrar");
        borrar.setBounds(400, 850, 200, 30);
        borrar.addActionListener(e -> {
            arregloAcom.setText("");
            num2 = null;
            ContraTam.setText("");
            tiempoA.setText("");
            tiempoB.setText("");
            tiempoC.setText("");
            arregloDes.setText("");
        });

        JButton secuencial = new JButton("Secuancial");
        secuencial.setBounds(100, 750, 200, 30);
        secuencial.addActionListener(e -> {
            try {
                int base = num2.length / 3;
                int remainder = num2.length % 3;

                int[] subArray1 = Arrays.copyOfRange(num2, 0, base + (remainder > 0 ? 1 : 0));
                int[] subArray2 = Arrays.copyOfRange(num2, base + (remainder > 0 ? 1 : 0), 2 * base + (remainder > 1 ? 1 : 0));
                int[] subArray3 = Arrays.copyOfRange(num2, 2 * base + (remainder > 1 ? 1 : 0), num2.length);

                chat.sendSubArray(subArray1);
                chat1.sendSubArray(subArray2);
                chat2.sendSubArray(subArray3);

                long startTime = System.currentTimeMillis();
                chat.sortSubArray();
                chat1.sortSubArray();
                chat2.sortSubArray();
                long endTime = System.currentTimeMillis();

                int[] sortedSubArray1 = chat.receiveSortedSubArray();
                int[] sortedSubArray2 = chat1.receiveSortedSubArray();
                int[] sortedSubArray3 = chat2.receiveSortedSubArray();

                int[] sortedArray = mergeSortedArrays(mergeSortedArrays(sortedSubArray1, sortedSubArray2), sortedSubArray3);
                arregloAcom.setText(Arrays.toString(sortedArray));
                tiempoA.setText((endTime - startTime) + " milisegundos");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        JButton Agregar = new JButton("Agregar");
        Agregar.setBounds(550, 50, 200, 20);
        Agregar.addActionListener(e -> {
            String contra = ContraTam.getText();
            int conTam = Integer.parseInt(contra);
            String arreglo3 = "";

            if (num2 == null && TamContra == 0) {
                num2 = new int[conTam];
                Random random = new Random();
                for (int i = 0; i < num2.length; i++) {
                    num2[i] = random.nextInt(1000);
                    arreglo3 += "," + num2[i];
                }
                arregloDes.setText(arreglo3);
            }
        });

        getContentPane().add(ContraTam);
        getContentPane().add(Agregar);
        getContentPane().add(label5);
        getContentPane().add(scrollPane1);
        getContentPane().add(scrollPane12);
        getContentPane().add(label1);
        getContentPane().add(label2);
        getContentPane().add(label3);
        getContentPane().add(borrar);
        getContentPane().add(tiempoA);
        getContentPane().add(tiempoB);
        getContentPane().add(tiempoC);
        getContentPane().add(secuencial);
        getContentPane().setLayout(null);
    }

    private int[] mergeSortedArrays(int[] arr1, int[] arr2) {
        int[] merged = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] <= arr2[j]) {
                merged[k++] = arr1[i++];
            } else {
                merged[k++] = arr2[j++];
            }
        }
        while (i < arr1.length) {
            merged[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            merged[k++] = arr2[j++];
        }
        return merged;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChatClient practica = new ChatClient();
            practica.setVisible(true);
        });
    }
}