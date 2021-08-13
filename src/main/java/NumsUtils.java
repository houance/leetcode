import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NumsUtils {

    public static void main(String[] args) {

        int[] nums = {2,7,11,15};
        int target = 26;

    }


    /**
     * 左右指针计算两数之和, 返回的是坐标
     * @param nums 数组,必须是排好序的
     * @param target 目标值
     * @return 两数的坐标
     * 思路 : 左右指针指向数组两端, 根据 sum 与 target 的大小关系, 移动左或右指针
     */
    public static int[] twoSum(int[] nums, int target){

        int left = 0;
        int right = nums.length-1;
        int[] result = new int[2];

        while (left<right){

            int sum = nums[left] + nums[right];

            if (sum==target){
                result[0] = left;
                result[1] = right;
                return result;
            }
            else if (sum>target){
                right--;
            }
            else {
                left++;
            }


        }

        return null;
    }


    /**
     * 左右指针算法
     * @param nums 升序数组
     * @return 平方后排好序的数组
     */
    public static int[] sortedSquares(int[] nums) {
        // 左右指针初始化
        int left, right;
        left = 0;
        right = nums.length-1;

        // 新的数组初始化
        // newIndex 用来控制在新的数组写入的位置
        int[] newNums = new int[nums.length];
        int newIndex = nums.length-1;

        // 这里的思路是
        // 升序数组中, 可能存在正数和负数, 那么负数平方后有可能会大于正数
        // 那么这就说明, 数组平方后的最大值只可能存在 最左或者最右两个端点
        // 所以使用左右指针, 指向数组的两端,
        // 并且根据两者指向的元素平方后的大小比较, 移动两个指针中的一个
        // 这里要注意的 edge case 是,
        // 当两个指针相邻时, 比较完之后还要把剩下的那个元素放入新数组
        // 所以 while 的终止条件是 小于等于, 即使用了 哨兵技巧
        while (left<=right){

            double leftSquareNum = Math.pow(nums[left], 2);
            double rightSquareNum = Math.pow(nums[right], 2);

            if (leftSquareNum>rightSquareNum){
                newNums[newIndex] = (int) leftSquareNum;
                left++;
            }

            else{
                newNums[newIndex] = (int) rightSquareNum;
                right--;
            }
            newIndex--;

        }

        return newNums;


    }


    /**
     * 滑动窗口算法
     * @param nums 给定数组
     * @param val 数组中 某一连续的子数组的和要大于等于 val, 求该子数组的最小长度
     * @return 返回符合条件的子数组的长度
     * 具体思路参考字符串中的滑动窗口
     */
    public static int slightWindow(int[] nums, int val){

        int sum, left, right, length;
        sum = left = right =  0;
        length = Integer.MAX_VALUE;

        while (right<nums.length){

            sum += nums[right];
            right++;


            while (sum>=val){

                int leftVal = nums[left];
                sum -= leftVal;

                if (right-left<length){

                    length = right - left;
                }
                left++;

            }



        }

        return (length==Integer.MAX_VALUE)?0:length;
    }




    /**
     * 二分查找算法
     * @param nums 升序数组
     * @param target 目标值
     * @param bounderType 边界类型， 大于零表示左边界， 等于零表示没有边界， 小于零表示左边界
     * @return 返回目标值在数组中的序号
     *
     */

    public static int divSearch(int[] nums, int target, int bounderType){

        int left=0, right = nums.length-1;
        int targetPosition=-1;

        while (left<=right){

            int midPosition = (left + right)/2;
            int mid = nums[midPosition];

            if (target < mid)
                right = midPosition-1;

            else if (target==mid){
                if (bounderType==0){
                    return midPosition;
                }
                else if (bounderType>0){
                    left = midPosition + 1;
                    targetPosition = midPosition;
                }
                else {
                    right = midPosition - 1;
                    targetPosition = midPosition;
                }


            }

            else if (target>mid)
                left = midPosition+1;

        }

        return targetPosition;

    }


    /**
     * 滑动窗口算法
     * @param source 源字符串
     * @param target 目标串
     * @return 在源字符串中, 找到能 `无序地包含整个目标串` 的子串
     * 例如 : 源字符串 = "asdfghjkl", 目标串 = "jhf", 返回 = "fhj"
     */
    public static String slightWindow(String source, String target) {

        // 因为没有要求是有序覆盖, 所以使用字典来存储
        HashMap<Character, Integer> charsCount = new HashMap<>();
        HashMap<Character, Integer> charsNeed = new HashMap<>();

        // 填充两个字典
        // charsNeed 是用于比对, 所以初始化后不再更改
        // charsCount 是用于计算是否覆盖目标串, 在遍历过程中不断变化
        for (char c : target.toCharArray()){
            charsNeed.put(c, charsNeed.getOrDefault(c, 0)+1);
            charsCount.put(c, 0);
        }

        // 初始化指针
        int left = 0, right = 0;

        // valid 用于记录覆盖的情况
        // 即 valid == target.length() , 标识窗口内包含目标串
        int valid = 0;

        // 记录最小覆盖子串的起始索引及长度
        // length 初始化为
        int start = 0, length = Integer.MAX_VALUE;


        while (right < source.length()) {
            // charAtRight 是将移入窗口的字符
            char charAtRight = source.charAt(right);
            // 右移窗口
            right++;
            // 进行窗口内数据的一系列更新
            if (charsNeed.containsKey(charAtRight)) {

                charsCount.put(charAtRight, charsCount.get(charAtRight)+1);

                if (charsCount.get(charAtRight)<=charsNeed.get(charAtRight))
                    valid++;
            }

            // 判断左侧窗口是否要收缩
            while (valid == target.length()) {

                // 在这里更新最小覆盖子串
                if (right - left < length) {
                    start = left;
                    length = right - left;
                }

                // charAtLeft 是将移出窗口的字符
                char charAtLeft = source.charAt(left);

                // 进行窗口内数据的一系列更新
                if (charsCount.containsKey(charAtLeft)) {

                    if (charsCount.get(charAtLeft).equals(charsNeed.get(charAtLeft)))
                        valid--;
                    charsCount.put(charAtLeft, charsCount.get(charAtLeft)-1);

                }
                left++;
            }
        }

        // 返回最小覆盖子串
        return length == Integer.MAX_VALUE ?
                null : source.substring(start, start+length);
    }


    /**
     * 快慢指针算法
     * @param nums 数组
     * @param val 要删除的元素
     * @return 删除给定元素后数组的大小
     * 例子 : 数组 = {1,2,3,3,4} , 要删除的元素 = 3, 删除给定元素后的数组 = {1,2,4,3,3}, 数组大小 = 3
     */
    public static int deleteValInNums(int[] nums, int val){

        // 一开始快慢指针同速前进
        int fastPointer = 0;
        int slowPointer = 0;

        for (; fastPointer < nums.length; fastPointer++) {

            // 如果快指针指向的元素 '不等于' 给定值, 则将快指针指向的元素 '覆盖' 慢指针指向的元素, 同时两个指针前进一步
            // 这里的思路就是, 当快指针指向的元素与目标值相同时, 慢指针不动, 快指针继续前进, 此时不做覆盖动作, 相当于只是标记元素
            // 当快指针指向的元素与目标值不同时, 表示此时需要执行覆盖动作, 所以将快指针指向元素覆盖慢指针指向元素
            if (nums[fastPointer]!=val){

                nums[slowPointer] = nums[fastPointer];
                slowPointer++;

            }
        }
        return slowPointer;


    }


    /**
     * 螺旋矩阵, 即顺时针填充数组
     * @param n 数组大小, 规定是 nxn 形状
     * @return 返回填充之后的数组
     */
    public static int[][] matrix(int n){
        int[][] res = new int[n][n];

        // 循环次数
        // 如果 n 为偶数, 则在下面的 while 循环中填充完成
        // 如果 n 是奇数, 则需要在 while 下面的判断中, 将数组 '中间' 的位置,
        // 也就是下面计算的 mid 位置填充好
        int loop = n / 2;

        // 计算中间位置
        int mid = n / 2;

        // 定义第一次 while 循环起始位置
        // 即从 左上角开始
        int startX = 0;
        int startY = 0;

        // 定义已经填充的层数
        // 因为思路采取的是左闭右开的填充方式
        // 即每一层 不填充最后一位
        // 所以 filledLayersNum 设为 1
        int filledLayersNum = 1;

        // 定义填充数字
        int count = 1;


        // 思路是, 每一次 while 循环, 包含四个 for 循环
        // 每个 for 循环打印一层(左闭右开)
        // 即一次 while 循环打印一圈
        while (loop>0){

            // 注意, 二维数组的[i][j]
            // 第一个表示纵坐标, 第二个表示横坐标
            int i = startY;
            int j = startX;

            // 左到右打印
            for (;  j<n - filledLayersNum ; j++) {
                res[i][j] = count;
                count++;
            }

            // 上到下打印
            for (; i<n-filledLayersNum; i++){
                res[i][j] = count;
                count++;
            }

            // 右到左打印
            for (; j>filledLayersNum-1;j--){
                res[i][j] = count;
                count++;
            }

            // 下到上打印
            for (;i>filledLayersNum-1;i--){
                res[i][j] = count;
                count++;
            }


            // startX, startY 往右下方前进一格
            startX++;
            startY++;

            // filledLayersNum++, loop--,  表示已经打印了一层
            filledLayersNum++;
            loop--;

        }

        // 如果 n 为奇数, 则需要填充 数组中间的位置
        if (n%2==1){
            res[mid][mid] = count;
        }

    return res;
    }

}
