package backTracking;

public class NQueens {
    private int[] x;//当前解
    private int N;//皇后个数
    private int sum = 0;//当前已找到的可行方案数
    private int totalNQueens(int n) {
        N = n;
        x = new int[N+1];
        backTrace(1);
        return sum;
    }
    /**
     * col行这个点，x[col]列这个点，与已经存在的几个皇后，是否符合要求，放到这个位置上，
     * @param col
     * @return
     */
    private boolean place(int col){
        for(int i = 1; i < col; i++){
            if(Math.abs(col - i)==Math.abs(x[col]-x[i])||x[col]==x[i]){//判断列是否相同，行的差的绝对值和列的差的绝对值是否相同（对角线）
                return false;
            }
        }
        return true;
    }
    private void backTrace(int t) {
        if(t>N){
            sum++;
        }else {
            //第t行，遍历所有的节点
            for(int j = 1; j <= N; j++) {
                x[t] = j ;
                //如果第j个节点可以放下皇后
                if(place(t)){
                    //接着放下一个
                    backTrace(t+1);
                }
            }
        }

    }
    public static void main(String[] args) {
        NQueens n = new NQueens();
        System.out.println(n.totalNQueens(5));
    }
}
