/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.test;

/**
 *
 * @author
 * Guerino
 */
public class QuickSort {
  
     public static int SIZE = 5646548;// 2300 milliseconds, 1120 millise
     
     public static void main(String args[]){
        int[] a = new int[SIZE];
        for(int i=0;i<SIZE;i++){
            a[i] = (int)(Math.random()*SIZE);
        }
        
        QuickSort mNew = new QuickSort();
        long start = System.currentTimeMillis();
        mNew.sort(a);
        long end = System.currentTimeMillis();
        System.out.println("Time taken to sort a million elements : "+(end-start)+" milliseconds");
     }

     public void print(int[] inputData) {
        for(int i:inputData){
         System.out.print(i+" ");
        }
        System.out.println();
     }

     public int[] sort(int[] input) {
        quickSort(input, 0, input.length-1);
        return input;
     }
     
     private void quickSort(int arr[], int left, int right) {
        int index = partition(arr, left, right);
        if (left < index - 1)
         quickSort(arr, left, index - 1);
        if (index < right)
         quickSort(arr, index, right);
     }

     private int partition(int arr[], int left, int right) {
        int i = left, j = right;
        int tmp;
        int pivot = arr[(left + right) / 2];
        while (i <= j) {
            while (arr[i] < pivot) i++;
            while (arr[j] > pivot) j--;
            if (i <= j) {
             tmp = arr[i];
             arr[i] = arr[j];
             arr[j] = tmp;
             i++;
             j--;
            }
        }
        return i;
     }
}
//Time taken to sort a million elements : 187 milliseconds

