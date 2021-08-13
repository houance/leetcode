import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class HashTableUtils {


    public static void main(String[] args) {

        int[] numsA = {1,2};
        int[] numsB = {-2,-1};
        int[] numsC = {-1,2};
        int[] numsD = {0,2};

        System.out.println(fourSumCount(numsA, numsB, numsC, numsD));

    }



    /**
     * Map求两数之和加强版
     * @param numsA 数组a
     * @param numsB 数组b
     * @param numsC 数组c
     * @param numsD 数组d
     * @return 四元组的数量
     * 题目 : 给定四个包含整数的数组列表 A , B , C , D ,
     * 计算有多少个元组 (i, j, k, l) ，使得 A[i] + B[j] + C[k] + D[l] = 0
     * 思路 : 因为只需要求出个数, 而不需要四元组具体的值, 所以可以参考两数之和的方法
     * 将四个数组分为两组, 用两个 Map 分别储存一组, key为两个数组全排列的和, value为和的次数
     * 然后遍历一个 Map, 用 -key 对另外一个 Map 求交集( contains方法 ), 如果存在交集,
     * 则四元组个数为 value 相乘
     */
    public static AtomicInteger fourSumCount(int[] numsA, int[] numsB, int[] numsC, int[] numsD){

        HashMap<Integer, Integer> abHashMap = new HashMap<>();
        HashMap<Integer, Integer> cdHashMap = new HashMap<>();
        AtomicInteger result = new AtomicInteger();

        // 求两组数组全排列的和
        for (int i = 0; i < numsA.length; i++) {
            for (int i1 = 0; i1 < numsB.length; i1++) {
                abHashMap.put(numsA[i]+numsB[i1], abHashMap.getOrDefault(i + i1, 0)+1);
            }
        }
        for (int i = 0; i < numsC.length; i++) {
            for (int i1 = 0; i1 < numsD.length; i1++) {
                cdHashMap.put(numsC[i]+numsD[i1], cdHashMap.getOrDefault(i + i1, 0)+1);
            }
        }

        // 遍历一个数组, 用 -key 求交集
        abHashMap.forEach((k, v)->{
            if (cdHashMap.containsKey(-k)){
                result.addAndGet(v * cdHashMap.get(-k));
            }
        });

        return result;

    }



    /**
     * 两数之和 : 利用 差数 和 Map 求交集
     * @param nums 数组
     * @param target 目标值
     * @return 两个数字在数组中的下标
     * 思路 : 如果数组是有序的, 那么也可以使用 左右指针,
     * 如果数组是无序的, 那么可以使用 Map 集合, key为数值大小, value为数值下标
     * 使用 target - nums[i] 去求交集
     */
    public static int[] twoSum(int[] nums, int target){

        HashMap<Integer, Integer> num = new HashMap<>();
        int[] result = new int[2];

        for (int i = 0; i < nums.length; i++) {
            num.put(nums[i], i);
            int temp = target - nums[i];
            if (num.containsKey(temp)){

                if (num.get(temp)==i) continue;
                result[0] = i;
                result[1] = num.get(temp);
                return result;
            }
        }

        return null;

    }


    /**
     * 哈希表求交集
     * @param n 整型
     * @return 如果 n 为快乐数, 则返回 true, 否则返回 false
     * 快乐数 : 对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，重复这个过程
     * 如果最终该数变成 1, 则该数为 "快乐数"; 否则这个过程会 "无限循环"
     * 思路 : 无限循环 = 平方和会重复出现, 即判断平方和是否重复出现即可
     */
    public static boolean isHappy(int n) {
        Set<Integer> record = new HashSet<>();
        while (n != 1 && !record.contains(n)) {
            record.add(n);
            n = getNextNumber(n);
        }
        return n == 1;
    }


    /**
     * 输入一个整数, 返回它每个位置上的数字的平方和
     * @param n 整数
     * @return 平方和
     */
    private static int getNextNumber(int n) {
        int res = 0;

        // %10运算 表示 "取" 个位上的数字
        // /10运算 表示 "去除" 个位上的数字
        while (n > 0) {
            int temp = n % 10;
            res += temp * temp;
            n = n / 10;
        }
        return res;
    }
}
