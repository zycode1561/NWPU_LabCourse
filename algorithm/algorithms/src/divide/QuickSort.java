package divide;

public class QuickSort {
    public void quickSort(int[] arr,int low,int high)//arr表示传入的数组，low，high分别表示要排序的起始位置和终止位置(注意下标从0开始)
    {
        int mid;
        if(low<high){
            mid = partition(arr,low,high); //基准位置
            quickSort(arr,low,mid-1); //对左子序列进行快排
            quickSort(arr,mid+1,high); //对右子序列进行快排
        }
    }
    /**
     * 划分序列，找到基准元素的位置
     */
    private int partition(int[] arr,int low,int high)
    {
        int i = low;
        int j = high;
        int temp = arr[low];
        while(i < j)
        {
            while(i < j && arr[j] > temp) //从右往左扫描，找到比基准元素小的值为止
                j--;

            if(i < j)
                arr[i++] = arr[j]; //交换二者的值 并i++ 向右移动一位

            while(i < j && arr[i] <= temp) //从左往右扫描，找到比基准元素大的值为止
                i++;

            if(i < j)
                arr[j--] = arr[i]; //交换二者的值，并j--
        }
        arr[i] = temp;  //填充基准元素
        return i;       //返回基准元素所在位置
    }
}
