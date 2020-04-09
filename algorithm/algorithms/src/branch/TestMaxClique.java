package branch;

public class TestMaxClique {
    public static void main(String[] args) {
        int[][] nodes = {{0,0,0,0,0,0},
                         {0,0,1,0,1,1},
                         {0,1,0,1,0,1},
                         {0,0,1,0,0,1},
                         {0,1,0,0,0,1},
                         {0,1,1,1,1,0}};
        int n = nodes.length;
        MaxClique maxClique = new MaxClique();
        int num = maxClique.maxClique(nodes,n);
        System.out.println("最大团的顶点数为：" + num);
    }
}
