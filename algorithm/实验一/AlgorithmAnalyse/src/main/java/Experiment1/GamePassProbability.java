package Experiment1;

import java.math.BigDecimal;

public class GamePassProbability {
	
	public double calculatePassProbability(int[] p, int num)
    {
        int l = num + 1;
        double[] dp = new double[l];
        double[][] flag = new double[l][l];
        double pass = 0.0d;
        int low;

        for(int i = 0;i < num;i++) {
            dp[i] = (double) p[i]/100;
        }
        flag[0][0] = 1;  //当有零场比赛需要胜利零场时晋升概率为1
        for(int j = 1;j < l; j++){
            flag[0][j] = 0;  //初始化动态规划数组第一行，当有0场比赛时，需要获胜j场概率都是0
        }
        for(int n = 1; n < l; n++){
            flag[n][0] = flag[n-1][0]*(1-dp[n-1]); // 递归关系 i = 0,因为数组下标从0开始，所以dp[0]对应P1
            for(int i = 1; i < l; i++){
                flag[n][i] = flag[n-1][i-1]*dp[n-1]+flag[n-1][i]*(1-dp[n-1]); // 递归关系 i > 0
            }
        }
        int rmd = (num*7)%10;
        if(rmd == 0){
            low = (num*7)/10;
        }
        else low = (num*7)/10 + 1;
        for(int i = low;i < l ; i++){
            pass+=flag[l-1][i];
        }
        BigDecimal b = new BigDecimal(pass);
        double f1 = b.setScale(5, BigDecimal.ROUND_HALF_UP).doubleValue();
        return f1;
    }

}
