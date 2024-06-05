/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectormi_merge;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ChatImplementation extends UnicastRemoteObject implements ChatInterface {
    private int[] subArray;

    protected ChatImplementation() throws RemoteException {
        super();
    }

    @Override
    public void sendSubArray(int[] subArray) throws RemoteException {
        this.subArray = subArray;
    }

    @Override
    public int[] receiveSortedSubArray() throws RemoteException {
        return subArray;
    }

    @Override
    public void sortSubArray() throws RemoteException {
        if (subArray != null) {
            Arrays.sort(subArray);
        }
    }
}
