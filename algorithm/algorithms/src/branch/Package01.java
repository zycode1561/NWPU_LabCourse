package branch;

import java.util.*;

public class Package01 {

    private int maxValue = Integer.MIN_VALUE;
    private static   LinkedList<Node> heap = new LinkedList<Node>();//创建一个指针链表

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public static class Node implements Comparable{
        double ub; //价值上界
        int lb; //价值下界
        int level; //所在层数
        Node parent; //父节点
        int cValue; //当前价值
        int restPWeight; //剩余背包容量
        public Node(double ub,int lb,int level,Node node,int cValue,int restPWeight){
            this.ub = ub;
            this.lb = lb;
            this.level = level;
            this.parent = node;
            this.cValue = cValue;
            this.restPWeight = restPWeight;
        }

        @Override
        public int compareTo(Object o) {
            double compareUb = ((Node)o).ub;
            if(ub < compareUb) return 1;
            if(ub == compareUb) return 0;
            return -1;
        }
        public boolean equals(Object x){
            return ub==((Node)x).ub;
        }
    }


        /**
     * 用贪心法算出最优解的下界lb
     * @param weight 对应物品重量
     * @param value 对应物品的价值
     * @param pWeight 背包重量
     * @return 返回求出的下界
     */
    private static int computeLb(int[] weight, int[] value, int pWeight){
        int lb = 0;
        Map<Integer,Integer> omap = new HashMap<>();
        Map<Integer,Integer> map = new TreeMap<>(new Comparator<Integer>() {
            public int compare(Integer obj1, Integer obj2) {
                // 降序排序
                return obj2.compareTo(obj1);
            }
        });//按照key对map进行排序
        for(int i = 1;i <= weight.length;i++){
            omap.put((value[i-1]/weight[i-1]),i);
        }
        map.putAll(omap);
        //遍历map，得到排好序的物品编号顺序
        for(Map.Entry<Integer,Integer> entry: map.entrySet()){
            int number = entry.getValue();
            //判断物品是否能被装下
            if(weight[number - 1] > pWeight) continue;
            pWeight = pWeight - weight[number - 1];
            lb = lb + value[number - 1];
        }
        return lb;
    }

    /**
     * 用贪心法算出最优解的上界ub
     * @param weight 对应物品重量
     * @param value 对应物品的价值
     * @param pWeight 背包重量
     * @return 返回求出的上界
     */
    private static double computeUb(int[] weight,int[] value,int pWeight){
        double ub = 0;
        int cpweight = pWeight; //当前背包容量
        Map<Integer,Integer> omap = new HashMap<>();
        Map<Integer,Integer> map = new TreeMap<>(new Comparator<Integer>() {
            public int compare(Integer obj1, Integer obj2) {
                // 降序排序
                return obj2.compareTo(obj1);
            }
        });//按照key对map进行排序
        for(int i = 1;i <= weight.length;i++){
            omap.put((value[i-1]/weight[i-1]),i);
        }
        map.putAll(omap);
//        System.out.println(map);
        //遍历map，得到排好序的物品编号顺序
        for(Map.Entry<Integer,Integer> entry: map.entrySet()){
            int number = entry.getValue();
            if(weight[number - 1] > pWeight)continue;
            if(weight[number - 1] > cpweight){
                ub = ub + cpweight * entry.getKey();
                return ub;
            }else {
                cpweight = cpweight - weight[number - 1];
                ub = ub + value[number - 1];
            }
        }
        return ub;
    }

    public static void main(String[] args) {
        int[] weight = {4,7,5,3,2,1}; //物品重量
        int[] value = {40,42,25,12,6,2}; //物品价值
        int maxLevel = weight.length;
        int pWeight = 10; //背包重量
        int[] result = new int[maxLevel]; //用来存储结果
        int maxValue = 0; //存储方案最大价值
        int lb = computeLb(weight,value,pWeight);//计算出下界
        double ub = computeUb(weight,value,pWeight);//计算出上界
        System.out.println(lb+" "+ub);
        int level = 1;
        Node node = new Node(ub,lb,level,null,0,pWeight);
        //搜索子集空间树
        while (node!=null&&node.level<=maxLevel){
          //参考优先队列，不停的扩展结点，选取下一个结点
                //选择装入
            if(node.restPWeight-weight[node.level - 1] >= 0) {
                int[] cweight = Arrays.copyOfRange(weight, node.level, maxLevel);//去掉已经判断过的物品，下面同理
                int[] cvalue = Arrays.copyOfRange(value, node.level, maxLevel);
                double nodeub = node.cValue + value[node.level - 1] + computeUb(cweight, cvalue, node.restPWeight - weight[node.level - 1]); //算出当前上界
                int nodelb = lb; //当前下界
                int nodevalue = value[node.level - 1];//当前判断是否装入物品的价值
                if (nodeub >= lb) {
                    //创建节点
                    Node childNode = new Node(nodeub, nodelb, node.level + 1, node, node.cValue + nodevalue, node.restPWeight - weight[node.level - 1]);
                    heap.add(childNode);
                    int cValue = node.cValue+value[node.level-1];
                    if(cValue > maxValue) maxValue = cValue;
                    result[node.level - 1] = 1;
                    Collections.sort(heap);
                }
            }
                //选择不装入
                int[] cweight1 = Arrays.copyOfRange(weight,node.level,maxLevel);//去掉已经判断过的物品，下面同理
                int[] cvalue1 = Arrays.copyOfRange(value,node.level,maxLevel);
                double nodeub1 = node.cValue+computeUb(cweight1,cvalue1,node.restPWeight); //算出当前上界
                int nodelb1 = lb; //当前下界
                if(nodeub1>=lb){
                    //创建节点
                    Node childNode1 = new Node(nodeub1,nodelb1,node.level+1,node,node.cValue,node.restPWeight);
                    heap.add(childNode1);
                    int cValue = node.cValue;
                    if(cValue > maxValue) maxValue = cValue;
                    Collections.sort(heap);
                }
            node = heap.poll();
        }
        System.out.println(maxValue);
        for(int i = 0;i<result.length;i++){
            System.out.print(result[i]+" ");
        }
    }
}
