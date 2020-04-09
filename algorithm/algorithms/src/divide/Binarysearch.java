package divide;

public class Binarysearch {
    public int search(int a[], int x, int low,int high)//x表示要查找的元素
    {
        if(low>high)
            return -1;
        int middle = (low+high)/2;
        if(x == a[middle])
            return middle;
        else if(x>a[middle])
            return search(a, x, middle+1, high);
        else
            return search(a,x,low,middle-1);
    }
}
