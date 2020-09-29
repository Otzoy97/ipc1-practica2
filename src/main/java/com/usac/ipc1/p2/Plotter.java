/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.usac.ipc1.p2;

/**
 *
 * @author erick
 */
public class Plotter {  
    
    private static int arr[] = new int[10];
    
    public static void main(String args[]){
        arr[0] = 1;
        arr[1] = 8;
        arr[2] = 4;
        arr[3] = 3;
        arr[4] = 7;
        arr[5] = 10;
        arr[6] = 9;
        arr[7] = 50;
        arr[8] = 2;
        arr[9] = 0;
        
        shell();
        //quick(0, 9);
        
        for(int v : arr){
            System.out.println(v);
        }
    }
    
    public static void shell(){
        var i = 1;
        while (i < arr.length/3){
            i = i * 3 + 1;
        }
        while (i > 0){
            for (int o = i; o < arr.length;o++){
                var tempVal = arr[o];
                var auxI = o;
                while(auxI > i - 1 &&
                        arr[auxI - i] >= tempVal){
                    arr[auxI] = arr[auxI - i];
                    auxI -= i;
                }
                arr[auxI] = tempVal;
            }
            i = (i - 1)/3;
        }
    }
    
    public static void quick(int min, int max){
        if(min < max){
            var p = partition(min, max);
            quick(min, p);
            quick(p+1, max);
        }
    }
    
    public static int partition(int min, int max){
        var pv = arr[(max+min)/2];
        var i = min - 1;
        var j = max + 1;
        while(true){
            do{
                i++;
            }while(arr[i] > pv);
            do{
                j--;
            }while(arr[j] < pv);
            if (i >= j){
                return j;
            }
            var temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
}
