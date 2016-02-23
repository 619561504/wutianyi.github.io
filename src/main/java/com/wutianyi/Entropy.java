/**
 * 2015年6月11日hanjiewu
 */
package com.wutianyi;

/**
 * 信息熵计算
 * 越小，说明纯度越高
 *
 * @author hanjiewu
 *         <p/>
 *         监督离散化（极大化区间纯度）
 *         寻找合适点，将值划分为两个区间，使得区间的熵最小
 *         取大值的熵区间，重复上述过程，直到区间为n
 */
public class Entropy {

    public static double entropy(String str) {

        double h = 0;
        int sum = 0;
        int[] letter = new int[26];

        str = str.toUpperCase();

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                letter[c - 'A']++;
                ++sum;
            }
        }

        for (int i = 0; i < 26; i++) {
            double p = 1.0 * letter[i] / sum;
            if (p > 0) {
                h += -(p * Math.log(p) / Math.log(2));// H = -∑Pi*log2(Pi)
            }

        }

        return h;
    }

    public static void main(String[] args) {
        System.out.println(entropy("aaaa"));
    }
}
