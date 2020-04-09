package divide;

public class Integerdivision {
    public int equationCount(int n,int m){
        if(n == 1||m == 1)
            return 1;
        else if(n < m)
            return equationCount(n,n);
        else if(n == m)
            return 1+equationCount(n,n-1);
        else
            return equationCount(n,m-1)+equationCount(n-m,m);
    }
}
