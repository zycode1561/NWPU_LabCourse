package divide;

public class FullPermutation {


    public  void Perm(char[] perm ,int  k ,int m){
        int i;
        if(k == m)
        {
            for(i = 0;i < m; i++ ) System.out.print(perm[i]+" ");
            System.out.println("\n");
            return;
        }

        for(i = k; i<m;i++)
        {
            Swap(perm,k,i);
            Perm(perm, k+1, m);
            Swap(perm,i, k);
        }
    }
    public void Swap(char[]perm,int a ,int b)
    {
        char n = perm[a];
        perm[a] = perm[b];
        perm[b] = n;
    }
}
