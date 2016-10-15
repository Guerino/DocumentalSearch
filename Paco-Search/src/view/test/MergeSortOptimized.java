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
public class MergeSortOptimized{

    public static int SIZE = 1000000;

    public static void main(String args[]){
        int[] a = new int[SIZE];
        for(int i=0;i<SIZE;i++){
        a[i] = (int)(Math.random()*SIZE);
    }
    long start = System.currentTimeMillis();
    MergeSortOptimized mNew = new MergeSortOptimized();
    mNew.sort(a);
    long end = System.currentTimeMillis();
    System.out.println("Time taken to sort a million elements : "+(end-start)+" milliseconds");
    }

    public int[] sort(int[] input){
    int[] temp = new int[input.length];
    mergeSort(input, temp, 0, input.length-1);
    return input;
    }

    public void mergeSort(int[] fromArray, int[] toArray, int left, int right){
    if(left<right){
    int center = (left+right)/2;
    mergeSort(fromArray, toArray, left, center);
    mergeSort(fromArray, toArray, center+1, right);
    merge(fromArray, toArray, left, center+1, right);
    }
    }

    public void merge(int[] fromArray, int[] toArray, int leftPos, int rightPos, int rightEnd){
    int leftEnd = rightPos - 1;
    int tempPos = leftPos;

    int numElements = rightEnd - leftPos + 1;

    while(leftPos<=leftEnd && rightPos<=rightEnd){
    if(fromArray[leftPos]<fromArray[rightPos]){
    toArray[tempPos++] = fromArray[leftPos++];
    }else{
    toArray[tempPos++] = fromArray[rightPos++];
    }
    }

    while(leftPos<=leftEnd){
    toArray[tempPos++] = fromArray[leftPos++];
    }
    while(rightPos<=rightEnd){
    toArray[tempPos++] = fromArray[rightPos++];
    }

    for(int i=0;i<numElements;i++, rightEnd--){
    fromArray[rightEnd] = toArray[rightEnd];
    }

    }

}
//Time taken to sort a million elements : 280 milliseconds

