package branch;


        import java.util.Arrays;
        import java.util.Collections;
        import java.util.LinkedList;
        import java.util.Vector;


public class BB4TSP {

    int NoEdge = -1; //��ʾû�б�
    private int minCost = Integer.MAX_VALUE; //��ǰ��С����
    public int getMinCost() {
        return minCost;
    }

    public void setMinCost(int minCost) {
        this.minCost = minCost;
    }

    private LinkedList<HeapNode> heap = new LinkedList<HeapNode>();//创建一个指针链表
    private Vector<Integer> bestH = new Vector<Integer>();


    @SuppressWarnings("rawtypes")
    public static class HeapNode implements Comparable{
        Vector<Integer> liveNode;//存储活节点
        int lcost; //子树费用的下界
        int level;//0-level的城市是已经排好的
        //���췽��
        public HeapNode(Vector<Integer> node,int lb, int lev){
            liveNode = node;
            lcost = lb;
            level = lev;
        }

        @Override
        public int compareTo(Object x) {//��������, ÿһ��pollFirst
            int xu=((HeapNode)x).lcost;
            if(lcost<xu) return -1;
            if(lcost==xu) return 0;
            return 1;
        }
        public boolean equals(Object x){
            return lcost==((HeapNode)x).lcost;
        }

    }

    /**
     * ���㲿�ֽ���½�.
     *
     * @param liveNode
     * 		              ���е���й
     *
     * @param
     * 			   ��ǰȷ���ĳ��еĸ���.
     * @param cMatrix
     *            �ڽӾ��󣬵�0�У�0�в���
     *
     * @exception IllegalArgumentException
     */
    private int computeLB(Vector<Integer> liveNode, int level, int[][] cMatrix)
    {
        //TODO
        int lb = 0; //初始化下界为0
        int n = cMatrix.length; //数组的长度为n，即城市个数
        int[][] sortArr = new int[n][n]; //创建一个排序数组
        int minback = Integer.MAX_VALUE;
        for(int i = 0;i<n;i++){ //遍历数组，排序
            for(int j=0;j<n;j++){
                sortArr[i][j]=cMatrix[i][j];
            }
            Arrays.sort(sortArr[i],0,n);
        }
        for(int i = 0; i < level - 1;i++){
            lb = lb +cMatrix[liveNode.get(i)][liveNode.get(i+1)];
        }
//        System.out.println(lb);
        for(int i = level;i < n - 1;i++) {
            for (int j = 1; j < n - 1; j++) {
                if (sortArr[liveNode.get(i - 1)][j]>0){
                    lb = lb+sortArr[liveNode.get(i - 1)][j];
                    break;
                }
            }
            if(cMatrix[liveNode.get(i)][1]<minback) minback = cMatrix[liveNode.get(i)][1];
        }
//        System.out.println(lb);
        if(minback == Integer.MAX_VALUE) return lb+100;
        else{
            return lb+minback;
        }
    }

    private int dfsUb(int[][] cMatrix, int root, int level, int cost, int[] booleans){ //贪心算法求出近似解作为上界ub
        if(level == cMatrix.length-1) return cost+cMatrix[root][1];
        int min = Integer.MAX_VALUE;
        int p=1;
        booleans[root] = 1;
        for(int i=0;i<cMatrix.length;i++){
            if(cMatrix[root][i]!=NoEdge && min > cMatrix[root][i] && booleans[i]==0){
                min = cMatrix[root][i];
                p = i;
            }
        }
        return dfsUb(cMatrix,p,level+1,cost+min,booleans);
    }

    /**
     *
     * @param cMatrix
     * @param n
     * @return
     */
    public int bb4TSP(int[][] cMatrix, int n)
    {
        //构造初始节点
        Vector<Integer> liveNode = new Vector<Integer>() ;//城市排列
        for(int i = 1; i<=n; i++) liveNode.add(i);
//        liveNode.add(1);
//        liveNode.add(2);
//        liveNode.add(4);
//        liveNode.add(3);
        int level = 1;//0-level的城市是已经排好的
        int lcost = computeLB(liveNode, level, cMatrix) ; //代价的下界
//        System.out.println("lcost"+lcost);
        int[] booleans = new int[cMatrix.length];//贪心算法中存储被选过的节点
        int ub = dfsUb(cMatrix,1,level,0,booleans);
        HeapNode enode=new HeapNode(liveNode,lcost,level);
        //搜索排列空间树
        while(enode!=null&&enode.level < n )
        {
            //TODO
            //参考优先队列，不停扩展节点,选取下一个节点
            if(enode.level == n - 1){
                //当前扩展结点是叶节点的父节点
                //再加两条边构成回路
                //所构成回路是否优于当前最优解
                if(cMatrix[enode.liveNode.get(n-2)][enode.liveNode.get(n-1)]!=NoEdge&&
                        cMatrix[enode.liveNode.get(n-1)][enode.liveNode.get(0)]!=NoEdge){
                    Vector<Integer> liveNode1 = new Vector<Integer>();
                    for(int a = 0;a < n;a++){
                        liveNode1.add(enode.liveNode.get(a));
                    }
                    int lb = computeLB(liveNode1,enode.level+1,cMatrix);
                    int lev = enode.level+1;
                    int cost = 0;
                    bestH = enode.liveNode;
                    for(int i = 0;i < n -1;i++){
                        cost = cost + cMatrix[bestH.get(i)][bestH.get(i+1)];
                    }
                    cost = cost + cMatrix[bestH.get(n-1)][bestH.get(0)];
                    if(cost<minCost) minCost = cost;
                    HeapNode node = new HeapNode(liveNode1,lb,lev);
                    heap.add(node);
                    Collections.sort(heap);
                }
            }else{
                for(int i = enode.level ; i < n ; i++){
                    if(cMatrix[enode.liveNode.get(enode.level - 1)][enode.liveNode.get(i)]!=NoEdge){
                        Vector<Integer> liveNode1 = new Vector<Integer>();
                        for(int a = 0;a < n;a++){
                            liveNode1.add(enode.liveNode.get(a));
                        }
                        int middle = liveNode1.get(enode.level);
                        liveNode1.set(enode.level,liveNode1.get(i));
                        liveNode1.set(i , middle);
                        int lb = computeLB(liveNode1,enode.level+1,cMatrix);
                        int lev = enode.level+1;
                        if(lb <= ub){ //子树可能含有最优解，加入最小堆
                            HeapNode node = new HeapNode(liveNode1,lb,lev);
                            heap.add(node);
                            Collections.sort(heap);
                        }
                    }
                }
            }
            enode=heap.poll();

        }
        System.out.println();
        return minCost;
    }


}
