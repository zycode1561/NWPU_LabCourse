package branch;

public class TestBB4TSP {

    public static void main(String[] args) {
        BB4TSP bb4TSP = new BB4TSP();
        int[][] b = { { -1, -1, -1, -1, -1 }, { -1, -1, 9, 19, 13 }, { -1, 21, -1, -1, 14 }, { -1, 1, 40, -1, 17 },
                { -1, 41, 80, 10, -1 } };
        int[][] b1 = { { -1, -1, -1, -1, -1,-1 }, {-1, -1, 3, 1, 5, 8 }, {-1, 3, -1, 6, 7, 9 }, { -1,1, 6, -1, 4, 2 },
                {-1, 5, 7, 4, -1, 3 }, {-1, 8, 9, 2, 3, -1} };
        int n = 5;
        bb4TSP.bb4TSP(b, 4);
//        System.out.println(b.length);
        System.out.println(bb4TSP.getMinCost()+"  34");
    }
}
