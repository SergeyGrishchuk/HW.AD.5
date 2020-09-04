package HomeWork5;

import java.util.Arrays;

public class HomeWork {
    static final int SIZE = 10000000;
    static final int H = SIZE/2;


    public static void main(String[] args) {
        float[] arr = new float[SIZE];
        fillingArray(arr);
        theFirstMethod(arr);

        fillingArray(arr);
       theSecondMethod(arr);
    }

    public static void fillingArray(float[] arr){
        Arrays.fill(arr, 1);
    }

    public static void theFirstMethod(float[] arr) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
           arr[i] = (float)(arr[i]*Math.sin(0.2f + i/5)*Math.cos(0.2f + i/5)*Math.cos(0.4f + i/2));
        }
        long end = System.currentTimeMillis();
        System.out.println("theFirstMethod performed for " + (end - start) + " ms");
    }

    public static void theSecondMethod(float[] arr){
        long start = System.currentTimeMillis();
        float[] arr1 = new float[H];
        float[] arr2 = new float[H];

        System.arraycopy(arr, 0, arr1, 0, H);
        System.arraycopy(arr, H, arr2, 0, H);

        Thread threadOne = new Thread(() -> {
            for (int i = 0; i < arr1.length; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });

        Thread threadTwo = new Thread(() -> {
            for (int i = 0; i < arr2.length; i++) {
                arr[i] = (float)(arr[i]*Math.sin(0.2f + (i+H)/5)*Math.cos(0.2f + (i + H)/5)*Math.cos(0.4f + (i + H)/2));
            }
        });

        threadOne.start();
        threadTwo.start();

        try{
            threadOne.join();
            threadTwo.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arr1, 0, arr, 0, H);
        System.arraycopy(arr2, 0, arr, H, H);

        long end = System.currentTimeMillis();
        System.out.println("theSecondMethod performed for " + (end - start) + " ms");
    }
}


