package Experiment3;

import java.util.Random;

public class GAOperations {
	/**
	 * 随机产生初始解，思路：先产生，后修复（也可以边产生边修复，如产生的位置的代码计数过多，则重新随机产生）.
	 * 
	 * @param popNum    种群大小
	 * @param length    每一个个体长度.
	 * @param iniPop    产生的初始种群.
	 * @param codes     编码序列.
	 * @param codeNum   编码的数量.
	 * @param codeCount 每一个编码的计数.
	 */
	public void RandomInitialiation(int popNum, int length, int[] codes, int codeNum, int[] codeCount, int[][] iniPop,
			int[][] a) {

		Random random = new Random();

		// 初始化种群
		for (int k = 0; k < popNum; k++) {
			iniPop[k][0] = random.nextInt(length) + 1;
			for (int i = 1, j = 0; i < length;) {
				iniPop[k][i] = random.nextInt(length) + 1;
				for (j = 0; j < i; j++) { // 初始化不得有相同数字
					if (iniPop[k][i] == iniPop[k][j])
						break;
				}
				if (i == j)
					i++;
			}
		}

		// 计算个体的适应度并进化
		double[] fitness = new double[popNum];
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < popNum; i++) {
				fitness[i] = computeFitness(iniPop[i], length, a);
			}
			roundBet(popNum, length, iniPop, fitness);
			Disturbance(iniPop, popNum, length, 5);
		}
	}

	/**
	 * 
	 * @param pop    个体
	 * @param length 个体长度.
	 * @param a      邻接矩阵
	 */
	public static double computeFitness(int[] pop, int length, int[][] a) {
		double fit = 0;
		if (length <= 1) // 至少有两个城市
			return 0;
		else {
			for (int i = 0; i < length; i++) {
				if (i < length - 1)
					fit += a[pop[i] - 1][pop[i + 1] - 1];
				else {
					fit += a[pop[i] - 1][0]; // 回到起点
				}
			}
		}
		return 1 / fit;
	}

	/**
	 * 
	 * @param popNum  个体 个数
	 * @param length  个体长度.
	 * @param iniPop1 种群
	 * @param fitness 每一个个体的适应度
	 */
	public static void roundBet(int popNum, int length, int[][] iniPop1, double[] fitness) {
		Random random = new Random();
		double totalfit = 0;
		int[][] iniPop2 = new int[popNum][length];

		// 计算总适应度
		for (int j = 0; j < fitness.length; j++) {
			totalfit += fitness[j];
		}
		int count = 0; // 计数器
		int i = 0;

		// 轮盘赌挑选个体
		for (count = 0; count < popNum;) {
			double child = random.nextDouble();
			for (i = 0; i < popNum; i++) {
				if (child <= fitness[count] / totalfit)
					break;
			}
			if (i != 10) { // 避免出现不存在的大概率
				for (int j = 0; j < length; j++) {
					iniPop2[count][j] = iniPop1[i][j];
				}
				count++;
			}
		}

		for (i = 0; i < popNum; i++) {
			for (int j = 0; j < length; j++) {
				iniPop1[i][j] = iniPop2[i][j];
			}
		}
	}

	/**
	 * 
	 * @param iniPop 种群
	 * @param popNum 个体 个数
	 * @param length 个体长度.
	 * @param disPos 随机交换的位置数
	 */
	public static void Disturbance(int[][] iniPop, int popNum, int length, int disPos) {
		Random random = new Random();
		int ran1, ran2, temp;

		// 注:子节点自身交换
		for (int i = 0; i < disPos; i++) {
			ran1 = random.nextInt(length);
			ran2 = random.nextInt(length);
			while (ran1 == ran2) {
				ran2 = random.nextInt(length);
			}
			for (int j = 0; j < length; j++) {
				temp = iniPop[j][ran1];
				iniPop[j][ran1] = iniPop[j][ran2];
				iniPop[j][ran2] = temp;
			}
		}
	}

	/**
	 * 获取code在codes中的位置
	 * 
	 * @param code    编码
	 * @param codeNum 总编码数
	 * @param codes   编码矩阵.
	 */
	public static int getCodePos(int code, int codeNum, int[] codes) {
		int pos = 0;
		for (; pos < codeNum; pos++) {
			if (code == codes[pos]) {
				return pos;
			}
		}
		return -1;
	}
}
