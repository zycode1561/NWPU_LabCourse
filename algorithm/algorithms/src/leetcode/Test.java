package leetcode;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
//        Map<Integer, Integer> map = new HashMap<>();
//        map.put(1, 5);
//        map.put(2, 4);
//        map.put(3, 2);
//        for (Integer key : map.keySet()) {
//            System.out.println("key:" + key);
//            System.out.println("value:" + map.get(key));
//        }
        int[] a ={1,2,3,4,5,6};
        int[] b = Arrays.copyOfRange(a,0,3);
        for(int i = 0;i < b.length;i++){
            System.out.print(b[i]+",");
        }
    }
}
