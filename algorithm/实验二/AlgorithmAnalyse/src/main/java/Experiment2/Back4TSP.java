package Experiment2;

public class Back4TSP {

	int NoEdge = -1;
	int bigInt = Integer.MAX_VALUE;
	int[][] a; // 邻接矩阵，存储任意两个城市间的代价
	int cc = 0; // 存储当前代价
	int bestc = bigInt;// 存储当前最小代价
	int[] x; // 存储最优解
	int[] bestx;// 存储当前最小代价对应的路线
	int n = 0; // 城市数量
	
	private void backtrack(int i) {//iΪ��ʼ���
		if (i > n) {
			//TODO
	             bestc = cc; bestx = x;
		} else {
			//TODO
			for(int j = i;j <= n; j++) {
			if(check(i,j)) {
				swap(x[i],x[j]);
				if(i < n && cc + a[x[i-1]][x[i]] < bestc) {
	                  cc = cc + a[x[i-1]][x[i]]; //加入城市x[t]后更新cc
	             	backtrack(i + 1);
			        cc = cc - a[x[i-1]][x[i]]; 
	             	} 
	             if(i == n  &&  cc + a[x[i-1]][x[i]]  + a[x[n]][x[1]] < bestc) {
	                  cc = cc + a[x[i-1]][x[i]]  + a[x[n]][x[1]];
	             	backtrack(i + 1);
	            	cc = cc -  a[x[i-1]][x[i]]  - a[x[n]][x[1]];
		    }
	             swap(x[i], x[j]); //恢复现场

			}
		  }
		}

	}
	
	private void swap(int i, int j) {
		int temp = x[i];
		x[i] = x[j];
		x[j] = temp;
	}
	
	public boolean check(int i,int j) {
		//TODO
		  if(i < 2) return true;
	      if(j < n && a[x[i-1]][x[j]] != NoEdge) return true;
	      if(j == n && (a[x[i-1]][x[j]] != NoEdge) && a[x[j]][x[1]] != NoEdge ) return true; 
	      return false;
	}
	
	public void backtrack4TSP(int[][] b, int num) {
		n = num;
		x = new int[n + 1];
		for (int i = 0; i <= n; i++)
			x[i] = i;
		bestx = new int[n + 1];
		a = b;
		backtrack(2);
	}

}
