package Experiment3;

import org.junit.Assert;
import org.junit.Test;

public class GATest {
	int popNum = 10;
	int length = 5;
	int codeNum = 5;
	int[] codes = {1, 2, 3, 4, 5};
	int[] codeCount = {1, 1, 1, 1, 1};
	int[][] a = {
			{100, 3, 1, 5, 8},
			{3, 100, 6, 7, 9},
			{1, 6, 100, 4, 2},
			{5, 7, 4, 100, 3},
			{8, 9, 2, 3, 100}
	};
	int[][] iniPop = new int[popNum][length];
	GAOperations gaOperations = new GAOperations();
	
	@Test
	public void TestRandomInitialiation()
	{		
		gaOperations.RandomInitialiation(popNum, length, codes, codeNum, codeCount, iniPop,a);
		int i, j;
		int[] nJs = new int[codeNum];//ͳ��ÿ���������������
		for(i = 0; i < popNum; i++)
		{
			for(j = 0; j < codeNum; j++)
			{
				nJs[j] = 0;
			}
			for(j = 0; j < length; j++) //ͳ��ÿ��code������
			{
				int pos = GAOperations.getCodePos(iniPop[i][j], codeNum, codes);//��ȡcode ��codes�е�λ��
				nJs[pos]++;
			}
			for(j = 0; j < codeNum; j++)
			{
				Assert.assertEquals(nJs[j], codeCount[j]);
			}
		}
	}
	
	@Test
	public void TestComputeFitness()
	{
		int[] pop = {1, 3, 5, 4, 2};
		double fit = GAOperations.computeFitness(pop, length, a);
		Assert.assertTrue(Math.abs(fit-1/16.0) < 0.0001);
	}
	
	@Test
	public void TestRoundBet()
	{
		gaOperations.RandomInitialiation(popNum, length, codes, codeNum, codeCount, iniPop,a);
		int i, j;
		int[] nJs = new int[codeNum];//ͳ��ÿ���������������
		double[] fitness = new double[popNum];
		for(i = 0; i < popNum; i++)
		{
			fitness[i] = GAOperations.computeFitness(iniPop[i], length, a);
		}
		GAOperations.roundBet(popNum, length, iniPop, fitness);
		for(i = 0; i < popNum; i++)
		{
			for(j = 0; j < codeNum; j++)
			{
				nJs[j] = 0;
			}
			for(j = 0; j < length; j++) //ͳ��ÿ��code������
			{
				int pos = GAOperations.getCodePos(iniPop[i][j], codeNum, codes);//��ȡcode ��codes�е�λ��
				nJs[pos]++;
			}
			for(j = 0; j < codeNum; j++)
			{
				Assert.assertEquals(nJs[j], codeCount[j]);
			}
		}
	}

	@Test
	public void TestDisturbance()
	{
		gaOperations.RandomInitialiation(popNum, length, codes, codeNum, codeCount, iniPop,a);
		int i, j;
		int[] nJs = new int[codeNum];//ͳ��ÿ���������������
		GAOperations.Disturbance(iniPop, popNum, length, 5);
		for(i = 0; i < popNum; i++)
		{
			for(j = 0; j < codeNum; j++)
			{
				nJs[j] = 0;
			}
			for(j = 0; j < length; j++) //ͳ��ÿ��code������
			{
				int pos = GAOperations.getCodePos(iniPop[i][j], codeNum, codes);//��ȡcode ��codes�е�λ��
				nJs[pos]++;
			}
			for(j = 0; j < codeNum; j++)
			{
				Assert.assertEquals(nJs[j], codeCount[j]);
			}
		}
	}
}

