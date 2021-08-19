import java.util.*;

public class BackTrackUtils {

    public static void main(String[] args) {

        String s = "1111";
        ArrayList<String> result = new ArrayList<>();
        restoreIpAddress(s, 0, 0, result);
        System.out.println(result);

    }


    /**
     * 回溯算法模拟 字符串 分割为 ip 地址的过程
     * @param ip 源字符串
     * @param startIndex 分割加点开始位置
     * @param pointNum 点的数量
     * @param result 存储结果
     * 题目 : 给定一个只包含数字的字符串，返回所有可能的 IP 地址格式
     * 思路 : 类似字符串分割的题目, 增加一个 pointNum 记录加点数, 达到三, 则剩下的字符串为 ip 地址的最后一段
     * 算法行为如下, 首先选取 0~1,1~2,2~3, 3~end, 每次选取都需要检验是否为合法的 ip 地址,
     *              然后回溯选取 0~1, 1~2, 2~4, 4~end , 以此类推
     */
    public static void restoreIpAddress(String ip, int startIndex, int pointNum,
                                        ArrayList<String> result){

        // 当加点数等于 3 , 则直接检验剩下的字符串是否为合法 ip 地址,
        // 是则加入结果, 否则返回
        if (pointNum==3){
            String substring = ip.substring(startIndex);
            if (isValid(substring)){
                result.add(ip);
            }
            return;
        }

        // 外层 for 循环, 与分割字符串类似
        for (int i = startIndex; i < ip.length(); i++) {

            // 选取一段字符串为 ip 地址中的一段
            String substring = ip.substring(startIndex, i + 1);

            // 判断合法性, 合法才进入循环, 否则直接退出, 即终止回溯
            if (isValid(substring)){

                // 添加点, 过程是截取 0~i 的字符串拼接点
                ip = ip.substring(0, i+1) + "." + ip.substring(i+1);
                pointNum++;

                // 因为添加了句点, 所以传入下一栈帧的 startIndex 是 i + 2
                restoreIpAddress(ip, i + 2, pointNum, result);
                pointNum--;

                // 去掉句点, 原理是每一次添加句点都是在 i + 1 的地方添加, 所以去掉 i + 1 的地方的句点就行
                ip = ip.substring(0, i + 1) + ip.substring(i + 2);
            }
            else break;
        }

    }


    private static boolean isValid(String string){
        if (string.length()>3||string.length()==0) return false;
        if (string.charAt(0)=='0') return false;
        if (Integer.parseInt(string)>255) return false;
        for (char c : string.toCharArray()) {
            if (c>'9'||c<'0') return false;
        }
        return true;
    }






    /**
     * 回溯算法切割回文字符串, 即回溯算法解决模拟切割字符串
     * @param source 源字符串
     * @param startIndex 切割的起始位置
     * @param resultTmp 存储路径
     * @param result 存储结果
     * 题目 : 给定一个字符串 s，将 s 分割成一些子串，使每个子串都是回文串
     * 思路 : 模拟切割是最困难的一步.
     *        这里使用 startIndex 记录每次切割的起始位置, 动态创建的 for 循环的 i + 1 作为切割的终止位置
     *         先切割为 0~1, 1~2, 2~3, 3~4, 4~5
     *          记录之后回溯, 再切割为 0~1, 1~2, 2~3, 3~5
     *           记录之后再回溯, 再切割为 0~1, 1~2, 2~5, 以此类推
     *            最后为 0~length
     */
    public static void palindromePartitioning(String source,int startIndex,
                                                      LinkedList<String> resultTmp,
                                                      ArrayList<LinkedList<String>> result){

        // 切割位置大于 源字符串 长度, 说明已经切割完成
        // 即找到了一组可行的方案
        if (startIndex>=source.length()){
            result.add(new LinkedList<>(resultTmp));
            return;
        }

        for (int i = startIndex; i < source.length(); i++) {

            // 切割字符串
            String substring = source.substring(startIndex, i+1);

            // 判断切割的字符串是否为回文串, 是则记录
            // 否则应该 continue, 而不是进入递归
            // 因为要求全部子串都是回文串
            if (StringUtil.palindrome(substring)){
                resultTmp.add(substring);
            }
            else continue;

            // 递归继续切割字符串
            palindromePartitioning(source, i+1, resultTmp, result);
            resultTmp.removeLast();
        }
    }





    /**
     * 组合总和的去重， 去重包括 ： 1.数字在一个组合内只能使用一次， 2.组合是无序的（隐性去重）
     * @param nums 数组集合
     * @param target 目标和
     * @param sum “路径” 的和
     * @param startIndex 动态创建 for 循环的起始位置
     * @param isUsed 标志位
     * @param resultTmp 存储路径
     * @param result 存储结果
     * 题目 ： 给定一个数组和一个目标数，找出数组中所有和为目标数的组合
     * 思路 ： 对于 1. 的解决方法， 每次动态创建 for 循环， 只从当前位置的下一个位置开始，
     *        对于 2. 的解决方法， 参考 n 数之和中的解决方法， 假设 a+b+c = target， 那么a的值确定， b+c的值也唯一确定了
     *          但是在 回溯 算法中, 因为选择 a 的时候, 是不会把整个集合扫一遍的, 所以需要使用一个 布尔数组, 确定前一个元素是否使用过
     *          维护一个 isUsed 布尔数组， 每次选择 a 的时候， 判断是否和前一个一样， 并且前一个的 isUsed 为 false，
     *          如果两个条件为真， 说明此时选择的值为 a， 否则为 b，c
     *          注意, 数组需要排好序
     */
    public static void test(int[] nums, int target, int sum, int startIndex, boolean[] isUsed,
                            LinkedList<Integer> resultTmp, ArrayList<LinkedList<Integer>> result){

        if (sum==target){
            result.add(new LinkedList<>(resultTmp));
            return;
        }
        else if (sum>target) return;

        for (int i = startIndex; i < nums.length; i++) {
            if (i>0&&nums[i]==nums[i-1]&&!isUsed[i-1]) continue;

            sum+=nums[i];
            resultTmp.add(nums[i]);
            isUsed[i] = true;
            test(nums, target, sum, i+1, isUsed, resultTmp, result);
            sum-=nums[i];
            resultTmp.removeLast();
            isUsed[i] = false;
        }
    }



    /**
     * 多个集合内求 全组合/全排列
     * @param number 电话号码字符串
     * @param startIndex 从 number 中挑选字符的位置
     * @param phoneNumberMap 电话本的映射关系 --> 即两个集合的映射关系
     * @param temp 存储路径
     * @param result 存储结果
     * 题目 : 电话本
     */
    public static void phoneNumberCombine(String number, int startIndex, Map<Character, String> phoneNumberMap,
                                          StringBuilder temp, ArrayList<String> result){

        // 路径等于 number 的长度
        // 即从每个数字映射的字母串中都挑选了一个字母
        if (temp.length()==number.length()){
            result.add(temp.toString());
            return;
        }

        // 因为有两个集合, 并且这两个集合存在映射关系
        // 所以需要两层循环
        for (int i = startIndex; i < number.length(); i++) {

            // 第一层循环从一个集合里面挑选
            char c = number.charAt(i);

            // 第二层循环根据映射关系从另外一个集合里面挑选
            for (char c1 : phoneNumberMap.get(c).toCharArray()) {
                temp.append(c1);

                // 因为是 全组合, 所以 startIndex = i + 1
                phoneNumberCombine(number,i+1, phoneNumberMap, temp, result);
                temp.deleteCharAt(temp.length()-1);
            }
        }
    }



    /**
     * 回溯算法解决 全组合/全排列 问题.
     * 组合: 无序集合,即 [1,2,3] 和 [2,3,1] 视为一个集合; 排列 : 有序集合, 即 [1,2,3] 和 [2,3,1] 是两个集合
     * @param startIndex 每次循环的起始位置
     * @param nums 数组
     * @param k 集合的大小
     * @param resultTmp 存储 "路径"
     * @param resultList 存储结果
     * 题目 : 在集合中寻找所有 "大小为 k 的 组合/排列" 的子集
     */
    public static void combinationOrArrangement(int startIndex, int[] nums, int k,
                                                LinkedList<Integer> resultTmp, ArrayList<List<Integer>> resultList,
                                                String manner){

        // 子集的大小满足条件, 则退出
        if (resultTmp.size()==k){
            resultList.add(new LinkedList<>(resultTmp));
            return;
        }


        for (int i = startIndex; i < nums.length; i++) {

            // 全组合意味着每次递归创建的 for 循环,
            // 应该从上一个栈帧的位置 "往前" 搜索, 所以传入的 startIndex = i + 1
            if (manner.equals("combination")){
                resultTmp.add(nums[i]);
                combinationOrArrangement(i+1, nums, k, resultTmp, resultList, manner);
                resultTmp.removeLast();
            }

            // 全排列意味着每次递归创建的 for 循环,
            // 应该 "从头开始搜索", 所以传入的 startIndex = 0
            // 并且需要配合去重
            else if (manner.equals("arrangement")){
                if (!resultTmp.contains(nums[i])){
                    resultTmp.add(nums[i]);
                    combinationOrArrangement(0, nums, k, resultTmp, resultList, manner);
                    resultTmp.removeLast();
                }
            }
        }

    }

}
