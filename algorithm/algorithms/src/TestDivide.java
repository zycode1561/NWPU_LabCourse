import divide.*;

/**
 * 用于测试各种算法例题的具体实现
 */
public class TestDivide {

    public static void main(String[] args) {
        //TestQuickSort();
        //TestBinarysearch();
        //TestIntegerdivision();
        //TestFullPermutation();
        //TestMergesort();

    }

    public static void TestQuickSort(){
        QuickSort quickSort = new QuickSort();
        int[] a = {4,7,2,9,17,23,8,1,3};
        quickSort.quickSort(a,0,a.length-1);
        for(int i = 0;i < a.length; i++){
            System.out.print(a[i]+" ");
        }
    }

    public static void TestBinarysearch(){
        Binarysearch binarysearch = new Binarysearch();
        int[] a ={1,2,3,12,14,16,45,67,89};
       int result = binarysearch.search(a,16,0,a.length-1);
       System.out.println(result);
    }

    public static void  TestIntegerdivision(){
        Integerdivision integerdivision = new Integerdivision();
        System.out.println(integerdivision.equationCount(4,4));
    }

    public static void TestFullPermutation(){
        FullPermutation fullPermutation = new FullPermutation();
        char[] a = {'a','b','c','d'};
        fullPermutation.Perm(a,0,4);
    }

    public static void TestMergesort()
    {
        Mergesort mergesort = new Mergesort();
        int[] a = {4,3,5,6,2,7,1,80,48,34,78};
        int[] temp = new int[100];
        mergesort.mergesort(a,0,a.length-1,temp);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i]+",");
    }
}
