public class DynamicProgramming {

    public static void main(String[] args) {

        int[][] map = new int[3][7];

        System.out.println(differentPaths(map));


    }


    /**
     * 题目 ： 一个机器人位于一个 m x n 网格的左上角，请问机器人从左上角到右下角，
     * 总共有多少条不同的路径可以到达？机器人每次只能往右或者往下走一步
     * @param map 地图
     * @return 路径数量
     * 递推公式 ： 机器人到达某一格的路径数 = 机器人到达该格子的上方和左方
     * 初始化 ：
     * 遍历顺序 ：
     */
    public static int differentPaths(int[][] map){

        int weight = map[0].length;
        int height = map.length;

        for (int i = 0; i < weight; i++) {
            map[0][i] = 1;
        }
        for (int i = 0; i < height; i++) {
            map[i][0] = 1;
        }

        for (int i = 1; i < height; i++) {
            for (int j = 1; j < weight; j++) {
                map[i][j] = map[i-1][j] + map[i][j-1];
            }
        }

        return map[height-1][weight-1];

    }

}
