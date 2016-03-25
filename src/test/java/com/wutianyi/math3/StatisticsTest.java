package com.wutianyi.math3;

import com.google.common.base.Joiner;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.stat.Frequency;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.ranking.NaNStrategy;
import org.apache.commons.math3.stat.ranking.NaturalRanking;
import org.apache.commons.math3.stat.ranking.TiesStrategy;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.junit.Test;

/**
 * Created by hanjiewu on 2016/2/1.
 * <p/>
 * 极差：即最大值－最小值（也就是极差）来评价一组数据的离散度
 * 离均差平方和：数据与均值之差（我们叫它离均差）加起来就能反映出一个准确的离散程度
 * 方差：离均差的平方和求平均值
 * 标准差：由于方差是数据的平方，与检测值本身相差太大，人们难以直观的衡量，所以常用方差开根号换算回来这就是我们要说的标准差。
 * 标准误：表示的是抽样的误差。因为从一个总体中可以抽取出无数多种样本，每一个样本的数据都是对总体的数据的估计。
 */
public class StatisticsTest {
	@Test
	public void statisticsTest() {
		DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();

		int[] inputArray = new int[]{11, 13, 14, 5, 7, 9, 9, 10};
		for (int input : inputArray) {
			descriptiveStatistics.addValue(input);
		}
		System.out.printf("mean: %f\nstd: %f\nvariance: %f\nmedian: %f\n", descriptiveStatistics.getMean(),
				descriptiveStatistics.getStandardDeviation(), descriptiveStatistics.getPopulationVariance(),
				descriptiveStatistics.getPercentile(50));
		System.out.println(ArrayUtils.toString(descriptiveStatistics.getSortedValues()));
		System.out.println(descriptiveStatistics.getN());
	}

	@Test
	public void frequencyTest() {
		Frequency f = new Frequency();
		f.addValue(1);
		f.addValue(new Integer(1));
		f.addValue(new Long(1));
		f.addValue(2);
		f.addValue(new Integer(-1));

		System.out.println(f.getCount(1));
		System.out.println(f.getCumPct(0));
		System.out.println(f.getPct(new Integer(1)));
		System.out.println(f.getCumPct(-2));
		System.out.println(f.getCumPct(10));
	}

	@Test
	public void simpleRegressionTest() {
		SimpleRegression regression = new SimpleRegression();
		regression.addData(1d, 2d);
		regression.addData(3d, 3d);
		regression.addData(3d, 3d);
		System.out.println(regression.getIntercept());
		System.out.println(regression.getSlope());
		System.out.println(regression.getSlopeStdErr());
		System.out.println(regression.predict(1.5d));
	}

	@Test
	public void rankTransformations() {
		NaturalRanking ranking = new NaturalRanking(NaNStrategy.MINIMAL, TiesStrategy.SEQUENTIAL);
		double[] data = {20, 17, 30, 42.3, 17, 50, Double.NaN, Double.NEGATIVE_INFINITY, 17};
		double[] ranks = ranking.rank(data);
		for (double rank : ranks) {
			System.out.println(rank);
		}
	}

	/**
	 * 协方差，用于衡量两个变量的总体误差，表示两个变量总体误差的期望 大于0表示正相关，小于0表示负相关，0表示不相关
	 * <p/>
	 * 方差则是表示一个变量的误差
	 */
	@Test
	public void covarianceAndCorrelation() {
		Covariance covariance = new Covariance();
		double dd = covariance.covariance(new double[]{1, 2, 3}, new double[]{1, 2, 3});
		System.out.println(dd);
	}

	@Test
	public void statUtilsTest() {

	}
}
