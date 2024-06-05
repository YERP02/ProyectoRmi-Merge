/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi_merge;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote {
    void sendSubArray(int[] subArray) throws RemoteException;
    int[] receiveSortedSubArray() throws RemoteException;
    void sortSubArray() throws RemoteException;
}
