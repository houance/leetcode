public class DynamicProgramming {

    public static void main(String[] args) {

        int n = 10;

        System.out.println(integerBreak(n));


    }


    /**
     * 题目 :
     * @param nums 钱
     * @return 总额
     * dp 数组 : dp[i] 表示偷窃第 i 家, 得到的最大金额
     * 递推公式 : 对于一家, 只有偷和不偷两个选择
     *           偷 : dp[i] = dp[i-2] + nums[i]
     *            不偷 : dp[i] = dp[i-1]
     *             显然, dp[i] = max(dp[i - 1], dp[i - 2] + nums[i])
     * 初始化 : dp[0] = nums[0]
     *         dp[1] = max(dp[0], nums[1])
     */
    public static int rob(int[] nums){

        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(dp[0], nums[1]);

        // 递推公式
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
        }

        return dp[nums.length - 1];
    }



    /**
     * 题目 :
     * @param n 整数
     * @return 最大乘积
     * dp 数组 : dp[i] 表示将 i 拆分后最大的乘积, 不管拆分的数量
     * 初始化 : 显然, 1和 0 是不可拆分的, 所以从 dp[2] 开始拆分, 令 dp[2] = 1
     * 递推公式 : 对于数字来说, 有两种选择拆分
     *           一: 将 i 拆分为 j 和 i-j, 并不再拆分. 显然此时 dp[i] = j*(i-j)
     *            二: 将 i 拆分为 j 和 i-j, 并继续拆分 i-j. 显然此时 dp[i] = j*dp[i-j]
     *             显然, 当 j 固定的时候, dp[i] = max(j*(i-j), j*dp[i-j])
     *              j 的取值为 1~n-1, 所以需要遍历全部 j 来确定 dp[i]
     *               最终, dp[i] = max(j*(i-j), j*dp[i-j]){ 0<j<n-1 }
     */
    public static int integerBreak(int n){

        // dp[0] 是不用的
        int[] dp = new int[n+1];

        // 初始化
        dp[2] = 1;


        // 递推公式
        for (int i = 3; i < n+1; i++) {
            for (int j = 1; j < i - 1; j++) {
                dp[i] = Math.max(dp[i], Math.max(j*(i-j), j*dp[i-j]));
            }
        }

        return dp[n];
    }



    /**
     * 题目 ： 一个机器人位于一个 m x n 网格的左上角，请问机器人从左上角到右下角，
     * 总共有多少条不同的路径可以到达？机器人每次只能往右或者往下走一步
     * @param map 地图
     * @return 路径数量
     * dp 数组 : dp[i][j] = 机器人到达 i,j 的位置有多少种路径
     * 递推公式 ： 显然, 推导 dp[i][j] 只有两个方向.
     *            即 dp[i][j] = dp[i][j-1] + dp[i-1][j]
     * 初始化 ：dp[0][j] = 1, dp[i][0] = 1, 因为机器人只能向下或向右走
     * 遍历顺序 ：从左到右一层一层遍历
     */
    public static int differentPaths(int[][] map){

        int weight = map[0].length;
        int height = map.length;

        // 初始化
        for (int i = 0; i < weight; i++) {
            map[0][i] = 1;
        }

        // 初始化
        for (int i = 0; i < height; i++) {
            map[i][0] = 1;
        }

        // 递推公式
        for (int i = 1; i < height; i++) {
            for (int j = 1; j < weight; j++) {
                map[i][j] = map[i-1][j] + map[i][j-1];
            }
        }

        return map[height-1][weight-1];
    }
}
