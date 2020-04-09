package dynamic_program;

public class MatrixChainMultiplication {
    public void matrixChainMultiplication(int[] arr,int[][] m,int[][] s,int length)
    {
        int n = length-1;
        int l,i,j,k,q=0;
        //m[i][i]只有一个矩阵，所以相乘次数都是0
        for(i=1;i<=n;i++)
        {
            m[i][i]=0;
        }
        //l表示矩阵链的长度
        //l=2时，计算m[i,i+1].i=1,2,...,n-1(长度l=2的链的最小代价)
        for(l=2;l<=n;l++)
        {
            for(i=1;i<=n-l+1;i++)
            {
                j = i+l-1;//i为起始位置，j为长度为l的链的末位
                m[i][j] = 0x7fffffff;
                //k从i到j-1，以k为位置划分
                for(k=i;k<=j-1;k++)
                {
                    q=m[i][k]+m[k+1][j]+arr[i-1]*arr[k]*arr[j];
                    if(q<m[i][j])
                    {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
        System.out.println(m[1][length-1]);
    }
    public void printAnswer(int[][] s,int i,int j)
    {
        if(i==j)
        {
            System.out.print("A"+i);
        }
        else
        {
            System.out.print("(");
            printAnswer(s,i,s[i][j]);
            printAnswer(s,s[i][j]+1,j);
            System.out.print(")");
        }
    }
}
