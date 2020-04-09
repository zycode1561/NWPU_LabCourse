package Experiment2;


import java.util.Random;
import java.util.Vector;

public class BB4TSP_Test {
    public static void main(String[] args) {
        BB4TSP bb4TSP = new BB4TSP();
        Back4TSP back4TSP = new Back4TSP();
        Random random =new Random();

        /**
         * 测试分支界限法
         * n = 5
         */
        long startTime=System.currentTimeMillis();
        int [][] a1=new int [6][];
        for(int i=0;i<6;i++) {
            a1[i]=new int[6];
            for(int j=0;j<6;j++) {
                if(i==0||j==0) {
                    a1[i][j]=-1;
                }else if(i==j){
                    a1[i][j]=-1;
                }else {
                    a1[i][j]=Integer.valueOf(random.nextInt(10));
                }
            }
        }
        bb4TSP.bb4TSP(a1,5);
        System.out.println("分支界限法（5城）最短回路长为："+bb4TSP.getMinCost());

        /**
         * 测试分支界限法
         * n = 10
         */
        int [][] b1=new int [11][];
        for(int i=0;i<11;i++) {
            b1[i]=new int[11];
            for(int j=0;j<11;j++) {
                if(i==0||j==0) {
                    b1[i][j]=-1;
                }else if(i==j){
                    b1[i][j]=-1;
                }else {
                    b1[i][j]=Integer.valueOf(random.nextInt(10));
                }
            }
        }
        bb4TSP.bb4TSP(b1,10);
        System.out.println("分支界限法（10城）最短回路长为："+bb4TSP.getMinCost());
        long endTime=System.currentTimeMillis();
        System.out.println("程序运行时间："+(endTime-startTime)+"ms");
        
        
        /**
         * 测试回溯法
         * 5城
         */
        startTime=System.currentTimeMillis();
        int [][] a2=new int [6][];
        for(int i=0;i<6;i++) {
            a2[i]=new int[6];
            for(int j=0;j<6;j++) {
                if(i==0||j==0) {
                    a2[i][j]=-1;
                }else if(i==j){
                    a2[i][j]=-1;
                }else {
                    a2[i][j]=Integer.valueOf(random.nextInt(10));
                }
            }
        }
        back4TSP.backtrack4TSP(a2,5);
        System.out.println("回溯法（5城）最短回路长为："+back4TSP.bestc);
        
        
        /**
         * 测试回溯法
         * 10城
         */
        int [][] b2=new int [11][];
        for(int i=0;i<11;i++) {
            b2[i]=new int[11];
            for(int j=0;j<11;j++) {
                if(i==0||j==0) {
                    b2[i][j]=-1;
                }else if(i==j){
                    b2[i][j]=-1;
                }else {
                    b2[i][j]=Integer.valueOf(random.nextInt(10));
                }
            }
        }
        back4TSP.backtrack4TSP(b2,5);
        System.out.println("回溯法（10城）最短回路长为："+back4TSP.bestc);
        
        endTime=System.currentTimeMillis();
        System.out.println("程序运行时间："+(endTime-startTime)+"ms");
    }
}
