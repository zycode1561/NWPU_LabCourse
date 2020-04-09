package leetcode;

import java.util.HashMap;
import java.util.Map;

public class LengthOfLongestSubstring {
    private static int lengthOfLongestSubstring(String s){
        int n = s.length();
        int ans = 0;//用来存储最长子串的长度
        Map<Character,Integer> map = new HashMap<>();
        for(int i = 0,j = 0;j < n;j++){
            if(map.containsKey(s.charAt(j))){//判断当前位置j对应的字符与前面的是否有重复
                //防止i发生左移。
                // 原因：因为如果不去两个中的最大值，而是一直取 s.charAt(j) 的话 ，
                // 可能会出现 某一字符的相同字符在很靠前的位置，这样获取的i 就会比当前 的i 要小，
                // 出现了i 左移的情况 例如 abcdefcma 到第二个c的时候 i变成了 3，但到第二个 a的时候如果取第一个a的位置，
                // 而不是a和当前i的最大值， i就变成了1，左侧索引左移
                i = Math.max(map.get(s.charAt(j)),i);
            }
            ans = Math.max(ans,j-i+1);
            map.put(s.charAt(j),j+1);
        }
        return ans;
    }

    public static void main(String[] args) {
        int num;
        num = lengthOfLongestSubstring("abcdefcma");
        System.out.println(num);
    }
}
