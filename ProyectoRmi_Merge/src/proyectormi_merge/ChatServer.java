/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi_merge;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ChatServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ChatImplementation chat = new ChatImplementation();
            Naming.rebind("rmi://localhost:1099/ChatService", chat);
            System.out.println("Server is ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
