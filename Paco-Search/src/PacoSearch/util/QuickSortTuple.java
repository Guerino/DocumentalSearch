package PacoSearch.util;

/**
 *
 * @author
 * Guerino
 */
public class QuickSortTuple {
    
    public Tuple[] sort(Tuple[] input) {
        quickSort(input, 0, input.length-1);
        return input;
     }
     
    private void quickSort(Tuple arr[], int left, int right) {
        int index = partition(arr, left, right);
        if (left < index - 1)
         quickSort(arr, left, index - 1);
        if (index < right)
         quickSort(arr, index, right);
    }

    private int partition(Tuple arr[], int left, int right) {
        int i = left, j = right;
        Tuple tmp;
        Tuple pivot = arr[(left + right) / 2];
        while (i <= j) {
//            System.out.println(arr[i].getWord());
            
// if(una_palabra.compareTo(otra_palabra) < 0)
// System.out.println("una_palabra es lexicograficamente menor a otra_palabra");
            while(arr[i].getWord().compareTo(pivot.getWord()) < 0) i++;
            while(arr[j].getWord().compareTo(pivot.getWord()) > 0) j--;
//            while (arr[i] < pivot) i++;
//            while (arr[j] > pivot) j--;
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
