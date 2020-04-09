package branch;


import java.util.Collections;
import java.util.LinkedList;
import java.util.Vector;

import static java.lang.Integer.max;

/**
 * Created by zhangyun on 2019/12/17
 * 最大团问题
 */
public class MaxClique {

    private LinkedList<Node> heap = new LinkedList<Node>();//创建一个指针链表

    /**
     * 树节点的结构体
     */
    public  class Node implements Comparable{
        double cliqueSize; //价值上界
        int level; //所在层数
        Vector<Integer> selectedNode = new Vector<>(); //存储已选节点
        public Node(double cliqueSize,int level,Vector<Integer> nodes){
            this.selectedNode.addAll(nodes);
            this.cliqueSize = cliqueSize;
            this.level = level;
        }

        /**
         * 重写比较器，ub大的先出栈
         */
        @Override
        public int compareTo(Object o) {
            double compareUb = ((Node)o).cliqueSize;
            if(cliqueSize< compareUb) return 1;
            if(cliqueSize == compareUb) return 0;
            return -1;
        }
        public boolean equals(Object x){
            return cliqueSize==((Node)x).cliqueSize;
        }
    }

    /**
     *
     * @param nodeIndex 每个节点的与其他节点是否有边，有边为1，无边为0,根节点全部初始化为0
     * @param n 节点总数
     * @return
     */
    public int maxClique(int[][] nodeIndex,int n){
        int bestNum = 0; //最优解
        int cliqueSize = 0; //cliqueSize = cliqueSize + n - level + 1顶点数上界upperSize的值
        int level = 0; //层级从0开始，此时团内无节点
        int upperSize = cliqueSize + n - level; // 上界
        Vector<Integer> selectedNode = new Vector<>();
//        int[] nodes = new int[n + 1]; //存储顶点
//        //写入顶点{0,1,2,3,...,n},0表示根节点，实际不存在,为了使节点与level对应
//        for(int i = 0;i <= n;i++){
//            nodes[i] = i ;
//        }
        Node node = new Node(cliqueSize,level,selectedNode); //初始化根节点，下标为0
        while(node.level < n - 1){
            if(node.level + 1 == n - 1) return node.selectedNode.size();
            //扩展左子树
            if(ifEdge(nodeIndex,node.level + 1,node.selectedNode)){
                Vector<Integer> childSelectedNode = new Vector<>();
                childSelectedNode.addAll(node.selectedNode);
                childSelectedNode.add(level + 1);
                int upper = node.selectedNode.size() + n - node.level; //计算上界
                Node leftChild = new Node(upper,node.level + 1,childSelectedNode);
                heap.add(leftChild);
                Collections.sort(heap);
            }
            //扩展右子树,因为不加节点，不用判断是否右边，但是要比较上界和最优解的大小
            //如果当前上界大于最优解的话，说明可能有最优解在子树中，加入队列
            if(node.selectedNode.size() + n - node.level >= bestNum){
                Vector<Integer> childSelectedNode = new Vector<>();
                childSelectedNode.addAll(node.selectedNode);
                int upper = node.selectedNode.size() + n - node.level - 1; //计算上界
                Node rightChild = new Node(upper,node.level + 1, childSelectedNode);
                heap.add(rightChild);
                Collections.sort(heap);
            }
            node = heap.poll();
            bestNum = max(bestNum,node.selectedNode.size()); // 加入节点后更新最优解
        }
        return 0;
    }

    /**
     * 判断要加入节点和其他节点是否右边
     * @param nodeIndex 每个节点的与其他节点是否有边，有边为1，无边为0，根节点全部初始化为0
     * @param level 要加入节点的层级
     * @param selectedNode 已加入节点的层级
     * @return
     */
    public boolean ifEdge(int[][] nodeIndex,int level,Vector<Integer> selectedNode){
        for(int i = 0;i < selectedNode.size();i++){
            if(nodeIndex[selectedNode.get(i)][level] == 0)
                return false;
        }
        return true;
    }
}
