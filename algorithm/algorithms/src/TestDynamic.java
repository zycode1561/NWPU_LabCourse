import dynamic_program.MatrixChainMultiplication;
import dynamic_program.PackageProblem;

import java.sql.SQLOutput;

public class TestDynamic {

    public static void main(String[] args) {
            TestPackage();
           // TestMatrixChain();
    }

    public static void TestMatrixChain()
    {
        int[] arr = {30,35,15,5,10,20,25};
        int N = arr.length;
        int[][] m =new int[N][N];
        int[][] s =new int[N][N];
        MatrixChainMultiplication mc = new MatrixChainMultiplication();
        mc.matrixChainMultiplication(arr, m , s ,N);
        mc.printAnswer(s,1,N-1);
    }
    public static void TestPackage()
    {
        int[] weight = {0,3,4,6,8,10};
        int[] value = {0,3,4,5,6,1}; //第一个元素为0为了让位置和物品编号对应
        int n = weight.length;
        int pw = 23;
        int[][] flag = new int[n][pw+1];
        int[] item = new int[n];
        int s=0;
        PackageProblem pc = new PackageProblem();
//        pc.findMax01(weight,value,flag,pw,item); //测试01背包问题
        pc.findMaxFull(weight,value,flag,pw,item); //测试完全背包问题
        System.out.println(flag[n-1][pw]);
        System.out.print("被装入背包的物品编号为（括号内表示放入物品的数目）：");
        while (s < n)
        {
            if(item[s]!=0) System.out.print(s+1+"("+item[s]+")");
            s++;
        }
        System.out.print("\n");
        for(int i = 0; i <weight.length;i++){
            for(int j =0;j <= pw;j++)
            {
                System.out.print(flag[i][j]+" ");
            }
            System.out.print("\n");
        }
    }
}
