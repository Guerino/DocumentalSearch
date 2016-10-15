package view.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class TextCrawler
 {
 //Used to make sure correct number of arguments is being passed
 private static int numCommandArgu = 4;
 //Error messages
 private static String message="Error – Arguments Needed. Format:\n fileName.txt Max-Number-Of-Occurrances Word Case-Sensitivity-Value";
private static String message1="Error – Case Sensitivity Can Only Be 1 or 0";
 private static String message2="Error – Enter a Word";
private static String message3="Error – Number of Occurrances can only\n be in the range of 1-50";
 private static String message4="Error – Please Enter a valid file to target\nFormat is: – fileName.txt";
private static String message5="Error – The File to Target Doesn’t exist in the current directory";

//Final output messages
 private static String message6="";
private static String message7=" occurrances were found in the file: ";

//public static void main(String [] args)throws IOException
// {
// String filePattern="[a-zA-Z]+.txt"; //Pattern to make sure incoming file format is correct
// String sensitPattern="1|0";//pattern to make sure case sensitivity is correct
// String wordPattern ="[a-zA-Z]+";//Pattern to make sure word is a proper word
//
//int aSize; //To hold size of the ArrayList
// int caseS;//To hold value for case sensitivity
//
//if(args.length == numCommandArgu)
// {
// String fileToTarget = args[0];
// int numOccur = Integer.parseInt(args[1]);
// String wordToSearch = args[2];
// String caseSen = args[3];
// int totalOccur;
//
//if(fileToTarget.matches(filePattern))
// if(numOccur ==1)
// if(wordToSearch.matches(wordPattern))
// if(caseSen.matches(sensitPattern))
// {
// caseS = Integer.parseInt(caseSen);
//
//message6 = "Word Requested: \u201C" + wordToSearch + "\u201D \nOccurrances Requested:" + numOccur + "\n";
//
////An arrayList to hold the references
// ArrayList listRef = new ArrayList();
//
//File firstFile = new File(fileToTarget);
//
//if(firstFile.exists())
// {
// //adding the first reference – the file to target first
// listRef.add(new FileToSearch(fileToTarget));
//
//aSize = listRef.size();
//
//for(int y=0;y<aSize;y++)
// {
// searchForRef(listRef,listRef.get(y).getFileName());
// //if a new reference is added to the list it will cause the loop to run again
// aSize = listRef.size();
// }
//
//totalOccur = searchForWord(listRef,numOccur,caseS,wordToSearch);
//
////getting the appropriate information depending on whether requested amount of occurrances was filled
// if(totalOccur == numOccur)
// {
// message6 +="Occurrances found: " + totalOccur + "\nFiles Searched:\n";
//
//for(int r=0;r<listRef.size();r++)
// {
// if(listRef.get(r).getBeenSearched())
// message6 += listRef.get(r).getOccurrances() + message7 + listRef.get(r).getFileName() + "\n";
// }
// }
//else
// {
// message6 += "Only " + totalOccur + " occurrances were found after searching the following files\n";
//
//for(int w=0;w<listRef.size();w++)
// {
// if(listRef.get(w).getBeenSearched())
// message6 += listRef.get(w).getOccurrances() + message7 + listRef.get(w).getFileName() + "\n";
// }
//
//}
//
//JOptionPane.showMessageDialog(null,message6);
// }
// else
// JOptionPane.showMessageDialog(null,message5);
// }
// else
// JOptionPane.showMessageDialog(null,message1);
// else
// JOptionPane.showMessageDialog(null,message2);
// else
// JOptionPane.showMessageDialog(null,message3);
// else
// JOptionPane.showMessageDialog(null,message4);
// }
// else
// JOptionPane.showMessageDialog(null,message);
//
//}
// //A method to find all possible references. Starting search with the targeted file
// public static void searchForRef(ArrayList listOfRef, String fileName)throws IOException
// {
// int i;
// int j;
// int counter=0;//Used to see if file already appears in ArrayList
// String refPattern = "[\\W]{1}[a-zA-Z]+.txt[\\W]{1}";//Pattern to check for reference
// String [] sentence;//Array to hold a sentence
// String temp="";//temporary location to hold reference name while stripping out brackets from reference
// String lineFromFile;
//
//boolean fileAdded=false;
//
//FileReader aReader = new FileReader(fileName);
// BufferedReader buffR = new BufferedReader(aReader);
//
//while((lineFromFile=buffR.readLine()) != null)
// {
// sentence = lineFromFile.split(" ");
//
////For loop to check each word in sentence to see if it matches reference pattern
// for(j=0;j<sentence.length;j++)
// {
// if(sentence[j].matches(refPattern))
// {
//
////for loop to remove parenthesis – saves whats between characters at position 0 and sentence[j].length() -1
// for(int k=1;k<sentence[j].length()-1;k++)
// temp += sentence[j].substring(k,k+1);
// }
//
//}
//
//if(!temp.equals(""))
// {
//File refFromFile = new File(temp);
//
////to check if reference is a file that exists in current directory
// if(refFromFile.exists())
// {
// //loop to check if reference exists in ArrayList already
// for(int k =0;k<listOfRef.size();k++)
// {
//
//if(temp.equals(listOfRef.get(k).getFileName()))
// counter++;
//
//}
//
//if(counter == 0)
// {
// listOfRef.add(new FileToSearch(temp));
// fileAdded = true;
// }
// counter=0;
// }
// }
//
//temp="";
// }
//
//}
// //Method to search for the number of occurrances of a specific word in a file
// public static int searchForWord(ArrayList listOfRef,int maxOccur,int caseS,String word)throws IOException
// {
// String characterPattern ="[a-zA-z|'|-]";//This will be used when removing unwanted punctuation
// String lineFromFile,temp="";
//String [] sentence;
// int currentOccur=0,perFileOccur=0;
// int i;
//
//for(i=0;i< listOfRef.size();i++){
//    FileReader aReader = new FileReader(listOfRef.get(i).getFileName());
// BufferedReader aBuffR = new BufferedReader(aReader);
//
//while((lineFromFile=aBuffR.readLine()) != null && currentOccur < maxOccur)
// {
// sentence = lineFromFile.split(" ");
//
//for(int k=0;k<sentence.length;k++)
// {
// for(int j=0;j<sentence[k].length();j++)
// {
// //stripping words of unnecessary punctuation
// if(sentence[k].substring(j,j+1).matches(characterPattern))
// temp += sentence[k].substring(j,j+1);
// }
// sentence[k] = temp;
// temp="";
//
//}
//
//for(int f=0;f<sentence.length;f++)
// {
// if(caseS == 1)
// {
// if(sentence[f].equalsIgnoreCase(word) && currentOccur) {
// currentOccur++;
// perFileOccur++;
// }
// }
// else
// {
// if(sentence[f].equals(word)&& currentOccur) {
// currentOccur++;
// perFileOccur++;
// }
// }
// }
// }
// //setting the number of occurrances per file and that the file has been searched
// listOfRef.get(i).setOccurrances(perFileOccur);
// listOfRef.get(i).setBeenSearched(true);
// perFileOccur=0;
// }
//
//return currentOccur;
// }
 }

