/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.test;

import java.util.Arrays;

/**
 *
 * @author
 * Guerino
 */
public class MergeSort{

public static int SIZE = 300000;

    public static void main(String args[]){
        int[] a = new int[SIZE];
        for(int i=0;i<SIZE;i++){
        a[i] = (int)(Math.random()*SIZE);
        }
        long start = System.currentTimeMillis();
        
        MergeSort mSort = new MergeSort();
        mSort.sort(a);
        
        long end = System.currentTimeMillis();
        System.out.println("Time taken to sort a 100,000 elements : "+(end-start)+" milliseconds");
    }

    public int[] sort(int[] input) {
        if(input.length<=1){
        return input;
        }
        int[] left, right, result;

        int mid = input.length/2;

        left = Arrays.copyOfRange(input, 0, mid);
        right = Arrays.copyOfRange(input,mid,input.length);

        left = sort(left);
        right = sort(right);

        result = merge(left,right);

        return result;
    }

    public int[] merge(int[] left, int[] right){
        int[] result = new int[left.length+right.length];
        int indexOfResult = 0;
        while(left.length>0 && right.length>0){
        if(left[0]<right[0]){
        result[indexOfResult++] = left[0];
        left = Arrays.copyOfRange(left, 1, left.length);
        }else{
        result[indexOfResult++] = right[0];
        right = Arrays.copyOfRange(right, 1, right.length);
        }
        }
        if(left.length>0){
        for(int i=0;i<left.length;i++){
        result[indexOfResult++] = left[i];
        }
        }else{
        for(int i=0;i<right.length;i++){
        result[indexOfResult++] = right[i];
        }
        }
        return result;
    }
}
//Time taken to sort a 100,000 elements : 13401 milliseconds

