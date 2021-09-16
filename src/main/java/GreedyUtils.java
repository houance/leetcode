import java.util.*;

public class GreedyUtils {

    public static void main(String[] args) {

        int i = 120;

        System.out.println(monotoneIncreasingDigits(i));
    }


    /**
     * 贪心
     * 详细见笔记
     */
    private int count = 0;
    public int minCameraCover(TreeUtils.TreeNode root) {
        if (travel(root) == 0) count++;
        return count;
    }

    private int travel(TreeUtils.TreeNode root) {
        if (root == null) return 2;

        int left = travel(root.left);
        int right = travel(root.right);

        // 但凡有一个子节点没有覆盖, 则说明本节点需要安装摄像头
        // 所以状态置 1, 同时摄像头数量加 1
        if (left == 0 || right == 0){
            count++;
            return 1;
        }

        // 但凡有一个子节点安装了摄像头, 说明本节点覆盖了
        // 所以状态置 2
        if (left == 1 || right == 1) {
            return 2;
        }

        // 如果两个子节点都覆盖了, 说明本节点肯定没有覆盖, 且摄像头应该设在父节点
        // 所以状态置 0
        if (left == 2 && right == 2){
            return 0;
        }

        // 永远不会走到的地方
        return -1;
    }


    /**
     * 贪心
     * @param number 数字
     * @return 在所有小于等于 number 的数字中最大的一个数字, 并且每一位上的数字是单调递增(从左到右)
     * 例子 : number = 332, return 299
     * 思路 : 从后往前遍历, 如果 number[i-1] > number[i], 说明不满足递增规律, 那么 number[i-1]--, 同时更新最后 i 的位置
     *        再从 i 的位置出发从前往后遍历, 帮 number[i] 都置 9
     */
    public static int monotoneIncreasingDigits(int number){

        // 建议不要使用 toCharArray 方法, 因为再转 Integer 比较麻烦
        String[] numberInArray = String.valueOf(number).split("");

        // 记录最后 i 的位置
        int startChange = numberInArray.length;

        // 从后往前遍历
        for (int i = numberInArray.length-1; i > 0; i--) {

            // 贪心
            if (Integer.parseInt(numberInArray[i]) < Integer.parseInt(numberInArray[i-1])){
                numberInArray[i-1] = String.valueOf(Integer.parseInt(numberInArray[i - 1]) - 1);
                startChange = i;
            }
        }

        // 置 9
        for (int i = startChange; i < numberInArray.length; i++) {
            numberInArray[i] = "9";
        }

        // join 方法方便把 String 数组转为一个字符串
        return Integer.parseInt(String.join("", numberInArray));
    }


    /**
     * 贪心
     * @param S 字符串
     * @return 每一段的长度
     * 题目 : 给定一个子符串, 将字符串尽可能地分为多个段(连续的), 使得原字符串中的每一种字符, 只会出现在一段中
     * 例子 : aabbccdd -> [aa], [bb], [cc], [dd]. 而不是 [aabb], [ccdd], 因为要尽可能地多分
     * 思路 : 每一种字符只会出现在一个分段内 = 分段内的每一种字符 *不会出现在其他分段中*
     *        则 最佳分段方式 = 一个分段的右边界, 就等于分段内全部种类的字符中, 在字符串中 *最后出现的位置是最远的*
     *
     * 步骤 : 遍历一遍字符串, 记录每一种字符在字符串中 *出现的最远位置*
     *        再遍历一遍字符串, 如果 当前位置 = 当前记录到的字符中出现的 *最远位置*,
     *         则分段
     *
     */
    public static List<Integer> partitionLabels(String S){

        // 记录最远位置和结果
        HashMap<Character, Integer> lastIndexMap = new HashMap<>();
        ArrayList<Integer> result = new ArrayList<>();

        char[] chars = S.toCharArray();

        // 记录每一种字符出现的最远位置
        for (int i = 0; i < chars.length; i++) {
            lastIndexMap.put(chars[i], i);
        }

        // 哨兵机制
        int maxIndex = Integer.MIN_VALUE;
        int lastMaxIndex = -1;

        for (int i = 0; i < chars.length; i++) {

            // 获得当前字符在字符串中的最远位置
            Integer currentCharMaxIndex = lastIndexMap.get(chars[i]);


            if (currentCharMaxIndex>=maxIndex) {
                if (currentCharMaxIndex == i){
                    result.add(i-lastMaxIndex);
                    lastMaxIndex = i;
                    maxIndex = Integer.MIN_VALUE;
                }
                else maxIndex = currentCharMaxIndex;
            }
        }
        return result;
    }



    /**
     * 贪心
     * @param interval 区间数组
     * @return 需要删除的数组
     * 题目 : 给定一个区间的集合，找到需要移除区间的最小数量，使剩余区间互不重叠
     *        区间 [1,2] 和 [2,3] 的边界相互“接触”, 但没有相互重叠
     * 思路 : 按照 *右边界大小从小到大排序* ,
     *        贪心 : 遍历排序后的数组, 当出现重叠的时候, 保留 *右边界最小* 的区域,
     *         因为右边界越小, 留给下一个区域的空间就越大, 就越少可能出现重叠
     *
     */
    public static int eraseOverlapIntervals(int[][] interval){

        if (interval.length<2) return 0;

        // 根据右边界排序
        Arrays.sort(interval, Comparator.comparingInt(o -> o[1]));


        // 记录没有重叠的区域数量
        int noOverLapIntervals = 0;
        // 哨兵机制
        int rightEdge = Integer.MIN_VALUE;


        for (int[] ints : interval) {

            // 当上一个区域的右边界 小于等于 当前区域的左边界
            // 说明没有重叠
            if (rightEdge <= ints[0]) {
                noOverLapIntervals++;
                rightEdge = ints[1];
            }
        }
        return interval.length - noOverLapIntervals;
    }



    /**
     * 贪心
     * @param people 等待排队的数组, 即乱序的
     * @return 排好队的数组
     * 题目 : 每个 people[i] = [hi, ki] 表示第 i 个人的身高为 hi ，前面正好 有 ki 个身高大于或等于 hi 的人
     *         请对 people 数组重新排序, 保证 ki 满足要求
     *          题目保证一定可以实现排队
     * 思路 : 两个维度, 需要先确定一个维度
     *        所有首先按照身高对数组进行排序 , 身高相同, 则 ki 小的在前面,
     *        然后按照 ki 插入新的数组, 即 people[i] 在新数组的位置为 i ,
     *         *如果该位置已经有元素, 则应该插入在该元素的前面*
     *
     * 解释 : 当按照身高排好序后, people[i] 前面一定有 >=ki 个人的身高大于等于 hi, 因为题目保证一定可以重排
     *        所以在新数组中可以直接插入到 ki 的位置.
     *         已有元素的时候, 一定要插入到该元素前面,
     *          因为如果插入到该元素后面, 就会有 >=ki 个人的身高大于等于 hi
     */
    public static int[][] reconstructQueue(int[][] people){

        // 根据身高重排
        Arrays.sort(people, (o1, o2)->{
            if (o1[0]==o2[0]) return o1[1] - o2[1];
            return o2[0] - o1[0];
        });

        // list 的 add 方法, 当该位置已经有元素的时候
        // 会插入到该元素的前面
        // 使用 linkedList 显然可以减少数据搬运
        LinkedList<int[]> queue = new LinkedList<>();

        // 根据 ki 插入新数组
        for (int[] person : people) {
            queue.add(person[1], person);
        }

        return queue.toArray(new int[people.length][]);
    }



    /**
     * 贪心
     * @param ratings 评分数组
     * @return 最少的需要分发的糖果数
     * 题目 : 每个孩子都要一个评分(ratings[i]), 保证评分更高的孩子必须比他两侧的孩子获得更多的糖果, 每个孩子至少有一颗糖果
     * 例子 : ratings = {1,2,87,87,87,2,1}, 分发的糖果数 = {1,2,3,1,3,2,1}
     * 思路 : 全局最优 -> 相邻孩子中, 评分高的孩子获得的糖果最多
     *       局部最优1 -> 评分比左侧的孩子高, 则糖果更多
     *        局部最优2 -> 评分比右侧的孩子高, 则糖果更多
     *         即先从左到右扫一遍数组, 按局部最优1 分发糖果
     *          然后从右到左扫一遍数组, 此时注意, 如果当前分发的糖果数已经满足全局最优, 则不增加糖果, 否则应该增加糖果
     */
    public static int candy(int[] ratings){

        int[] candy = new int[ratings.length];

        // 局部最优1
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i]>ratings[i-1]) candy[i] = candy[i-1]+1;
        }

        // 局部最优2
        for (int i = ratings.length-2; i>=0; i--) {
            if (ratings[i]>ratings[i+1]){

                // 如果已经满足全局最优, 则不应该再分发糖果
                candy[i] = Math.max(candy[i], candy[i+1]+1);
            }

        }

        // + ratings.length, 保证每个孩子至少有一颗糖果
        return Arrays.stream(candy).sum()+ratings.length;

    }



    /**
     * 贪心
     * @param gas 获得的油量数组
     * @param cost 消耗的油量数组
     * @return 起点
     * 题目 : 在 *环路上* 有多个加油站(gas[]), 每到达一个加油站获得对应油量, 前往下一个加油站需要消耗对应油量(cost[]),
     *        问是否存在一个起点, 可以在环路上走一圈
     *         且如果题目有解, 一定是唯一解
     *
     * 思路 : 可以走一圈 = 总油量 >= 总消耗, 其中 油量 - 消耗 = 剩余油量
     *        转换为贪心的算法, 就是对于可行的起点来说
     *         剩余油量 *一定是大于等于零*, 并且从这个地点往后累加剩余油量, 一定是大于等于零的, 否则应该从下一个地点出发重新累加油量
     */
    public static int canCompleteCircuit(int[] gas, int[] cost){

        int sum = 0;
        boolean easyCase = true;

        // 遍历一遍数组, 如果总剩余油量小于零, 就无解
        // 如果剩余油量都是大于零, 那么起点就是第一个地点
        for (int i = 0; i < gas.length; i++) {

            int rest = gas[i] - cost[i];
            sum += gas[i] - cost[i];
            if (rest<0) easyCase=false;
        }

        if (sum<0) return -1;
        if (easyCase) return 0;

        int sumRest=0;
        int index=-1;

        // 再遍历一遍数组, 从剩余油量大于等于零的地方出发
        // 累加剩余油量
        for (int i = 0; i < gas.length; i++) {
            int rest = gas[i] - cost[i];
            sumRest+=rest;

            if (rest<0&&sumRest<0) {
                sumRest=0;
                index=-1;
            }
            else{

                // 如果起点没有被初始化, 才记录起点
                if (index==-1) index=i;
            }
        }

        return index;
    }



    /**
     * 贪心
     * @param nums 数组
     * @param k 可以取反 k 次
     * @return 最大数组和
     * 题目 : 给定一个数组 和 k, 可以对数组内的任意元素取反 k 次, 要求得到最大的数组和
     * 思路 : 对数组按照 *绝对值* 大小排序, 然后遍历
     *         如果元素是正数, 那么明显不需要取反, 只需要对数组最后一个元素进行 k 次取反
     *          如果元素是负数, 则需要取反, 直到消耗完 K 次,
     *           如果全部负数都取反后, 取数组最后的一个元素, 即 *最小的一个元素* 取反, 直到消耗完 K
     */
    public static int kTimeNumsSum(int[] nums, int k){

        // 按绝对值排序
        int[] sortedNums = Arrays.stream(nums)
                .boxed()
                .sorted((o1, o2) -> Math.abs(o2) - Math.abs(o1))
                .mapToInt(Integer::intValue)
                .toArray();

        int sum=0;

        for (int i = 0; i < nums.length; i++) {
            if (k==0) return sum;

            // 取反
            if (sortedNums[i]<0){
                k--;
                sortedNums[i] = Math.abs(sortedNums[i]);
            }

            sum+=sortedNums[i];
        }

        // 如果剩余的 K 可以被 2 整除, 则说明对最大数组和没有影响
        if (k%2==0) return sum;
        // 否则就等于 取反一次
        else return sum - (sortedNums[nums.length - 1] * 2);

    }



    /**
     *
     * @param steps 跳跃步数数组
     * @return 跳跃次数
     * 题目 : 与 *跳跃游戏1* 类似, 只是保证肯定可以到达终点, 求最少的跳跃次数
     * 思路 : 还是理解为覆盖范围, 因为题目要求最少的跳跃次数, 所以每次需要在覆盖范围里面,
     *        选出一个 *最佳位置*, 跳到该位置可以获得最大的覆盖范围.
     */
    public static int canJump2(int[] steps){

        // 计算步数
        int count=0;

        for (int i = 0; i < steps.length;) {

            count++;

            int cover = steps[i];

            // 判断覆盖范围是否包含终点
            if (i+cover>=steps.length-1) break;

            // 跳到覆盖范围里面的 *最佳位置*
            i = findMax(steps, i+1, i+1+cover);

        }

        return count;

    }


    /**
     * 贪心
     * @param nums 跳跃步数数组
     * @return 是否可以到达终点
     * 题目 : 给定一个非负整数数组, 你最初位于数组的第一个位置. 数组中的每个元素代表你在该位置可以跳跃的最大长度。
     *        判断你是否能够到达最后一个位置.
     * 思路 : 把 当前位置 + 最大跳跃长度 理解为 *覆盖范围*, 问题就变为 *覆盖范围* 是否包含终点
     * 步骤 : 只能在覆盖范围里面移动, 每移动一格更新覆盖范围, 同时判断覆盖范围是否大于等于数组长度
     */
    public static boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }

        // 初始化覆盖范围
        // nums[0] = 0 + nums[0]
        int cover = nums[0];

        // 在循环中更新覆盖范围 = 在移动中更新覆盖范围
        for (int i = 0; i <= cover; i++) {

            // cover 等于上一格的覆盖范围, i + nums[i] 等于当前格的覆盖范围
            // 两者取最大值就等于新的覆盖范围
            cover = Math.max(cover, i + nums[i]);
            if (cover >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }



    /**
     * 贪心
     * @param prices 股票数组
     * @return 最大利润
     * 题目 : 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。进行多次买卖, 达到最大利润.
     *        注意, 在购买股票前必须出售当前持有的股票
     * 思路 : 通常的思路是, 找到股票最低价的一天买入, 假设为 prices[1],
     *        然后在股票最高价的一天卖出, 假设为 prices[4].
     *         总利润为 prices[4] - prices[1].
     *          但是, 这个利润的公式可以分解为
     *           (prices[4] - prices[3]) + (prices[3] - prices[2]) + (prices[2] - prices[1])
     *            可见, 最大利润就等于剔除掉上式中 *差小于等于零* 的项, 由此可以使用贪心算法
     *             即每天都进行一次买入, 卖出(卖掉上一天持有的股票), 差价小于等于零则忽略
     */
    public static int buyStock2(int[] prices){

        int sum=0;
        int buyStock = prices[0];

        for (int i = 1; i < prices.length; i++) {

            int price = prices[i] - buyStock;
            if (price>0) sum+=price;

            buyStock = prices[i];
        }
        return sum;

    }


    /**
     * 贪心
     * @param nums 数组
     * @return 最大连续子数组和
     * 题目 : 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     * 思路 : 局部最优解 = 当连续和加入当前元素后小于零, 则应该从下一个元素重新计算连续和
     */
    public static int maxSubArray(int[] nums){


        int result, sum;

        // 哨兵机制
        result = Integer.MIN_VALUE;


        sum = 0;

        if (nums.length == 1){
            return nums[0];
        }

        for (int num : nums) {

            // 计算连续和
            sum += num;

            // 使用 Max 函数, 是当数组中全是负数的时候, 仍然可以找到最大的负数
            result = Math.max(sum, result);

            // 从下一个元素重新开始计算 = 连续和置零
            if (sum<=0) sum = 0;
        }


        return result;
    }



    /**
     * 贪心
     * @param nums 数组
     * @return 最长的摆动序列的长度
     * 摆动序列 : [1,7,4,9,2,5] 是一个摆动序列，因为差值 (6,-3,5,-7,3) 是正负交替出现的, 一个元素的数组也是摆动序列
     * 题目 : 给定一个数组, 通过删除(或不删除元素), 计算最长摆动序列的长度
     * 算法 : 对于最长摆动序列来说, 它的每一个元素都是波峰或者波谷, 即形如 \/\/\/\/.
     *        所以要删除不在波峰波谷的元素, 即要删除单调坡上除了两端的所有元素.
     *         但是, 因为题目只要求返回长度, 而不是具体的摆动序列,
     *          所以我们可以使用贪心的算法, 即 *只有差正负交替出现, 才记录长度*
     *           这样子就不是删除单调坡上的元素,
     *            而是忽略单调坡上第三个元素开始到倒数第二个元素(左闭右闭)间的所有元素
     */
    public static int wiggleMaxLength(int[] nums) {

        // 当前差值
        int diff = 0;

        // 上一个差值
        // 初始化为零是利用了哨兵机制,
        // 考虑了第一二个元素相同的情况
        int lastDiff = 0;

        // 一个元素的数组也是摆动序列
        int count = 1;

        for (int i = 1; i < nums.length; i++) {
            //得到当前差值
            diff = nums[i] - nums[i - 1];

            // 如果当前差值和上一个差值为一正一负
            // 则记录长度并更新差值
            if ((diff > 0 && lastDiff <= 0) || (diff < 0 && lastDiff >= 0)) {
                count++;
                lastDiff = diff;
            }
        }
        return count;
    }


    public static int findMax(int[] steps, int startIndex, int endIndex){


        int max = Integer.MIN_VALUE;
        int index = 0;

        for (int i = startIndex; i < endIndex; i++) {
            if (steps[i]+i>=max){
                max = steps[i]+i;
                index = i;
            }
        }

        return index;

    }

}
