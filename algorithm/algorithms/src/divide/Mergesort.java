package divide;

public class Mergesort {
    private void memeryArray(int[] a, int first, int mid, int last,int[] temp)
    {
        int i = first,j = mid + 1;
        int m = mid,n = last;
        int k = 0;
        while(i <= m && j <= n)
        {
            if(a[i] < a[j])
                temp[k++] = a[i++];
            else
                temp[k++] = a[j++];
        }
        while(i <= m)
            temp[k++] = a[i++];
        while(j <= n)
            temp[k++] = a[j++];
        for(i = 0; i < k; i++)
            a[first+i] = temp[i];
    }
    public void mergesort(int[] a, int first, int last, int[] temp)
    {
        if(first < last)
        {
            int mid = (first+last)/2;
            mergesort(a,first,mid,temp);
            mergesort(a,mid+1,last,temp);
            memeryArray(a,first,mid,last,temp);
        }
    }
}
