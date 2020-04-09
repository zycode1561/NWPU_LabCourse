package dynamic_program;

public class PackageProblem {
    public void findMax01(int[] weight,int[] value, int[][] flag,int pw,int[] item)
    {
        //weight:物品的体积或重量;  value:物品的价值;  flag:动态规划表;   pw:背包大总重量或体积;  item:最优解情况
        for(int i = 1;i <= weight.length-1;i++){
            for(int j = 1;j <= pw; j++){
                if(j < weight[i]) //判断当前物品是否能被装下
                    flag[i][j] = flag[i-1][j];
                else
                    flag[i][j] = max(flag[i-1][j],flag[i-1][j-weight[i]]+value[i]); //两种情况，装或者不装，取最大值
            }
        }
        findWhat(weight.length-1,pw,weight,value,flag,item); //找出被装的物品编号，并存储在item数组中
    }
    public void findWhat(int i, int j,int[] weight,int[] value,int[][] flag,int[] item) {				//最优解情况
        if (i > 0) { //判断是否在表中
            if (flag[i][j] == flag[i - 1][j]) { //判断当前物品是否被装（动态规划表中是否和i的上一位相同）
                item[i] = 0;
                findWhat(i - 1, j,weight,value,flag,item); //去到上一行重新判断
            }
            else if (j - weight[i] >= 0 && flag[i][j] == flag[i - 1][j - weight[i]] + value[i]) { //判断当前物品是否被装
                item[i] = 1; //物品被装，相应数组位置值变为1
                findWhat(i - 1, j - weight[i],weight,value,flag,item); //返回上一行重新判断，知道i=0结束
            }
        }
    }

    public void findMaxFull(int[] weight,int[] value, int[][] flag,int pw,int[] item)
    {
        //weight:物品的体积或重量;  value:物品的价值;  flag:动态规划表;   pw:背包大总重量或体积;  item:最优解情况
        for(int i = 1; i <= pw; i++)
        {
            flag[1][i] = (i < weight[1])?0:((i/weight[1])*value[1]);//与01背包不同
        }
        for(int i = 2;i <= weight.length-1;i++){
            for(int j = 1;j <= pw; j++){
                if(j < weight[i])
                    flag[i][j] = flag[i-1][j];
                else
                    flag[i][j] = max(flag[i-1][j],flag[i][j-weight[i]]+value[i]); //与01背包不同，并没有去到上一行，因为可以重复装
            }
        }
        findWhatFull(weight.length-1,pw,weight,value,flag,item);
    }

    public void findWhatFull(int i, int j,int[] weight,int[] value,int[][] flag,int[] item) {				//最优解情况
        if (i > 0) {
            if (flag[i][j] == flag[i - 1][j]) {
                //此处不能给item[i]赋值0，不然会覆盖。
                findWhatFull(i - 1, j,weight,value,flag,item);
            }
            else if (j - weight[i] >= 0 && flag[i][j] == flag[i][j - weight[i]] + value[i]) {
                item[i]++; //计算当前被装物品被装的数目
                findWhatFull(i, j - weight[i],weight,value,flag,item);//在当前行继续查询
            }
        }
    }

    private int max(int a,int b)
    {
        if(a>=b) return a;
        else return b;
    }
}
