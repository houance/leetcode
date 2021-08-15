import java.util.*;

public class BackTrackUtils {

    public static void main(String[] args) {

        HashMap<Character, String> phoneNumberMap = new HashMap<>();
        phoneNumberMap.put('2', "abc");
        phoneNumberMap.put('3', "def");
        phoneNumberMap.put('4', "ghi");
        phoneNumberMap.put('5', "jkl");
        phoneNumberMap.put('6', "mno");
        phoneNumberMap.put('7', "pqrs");
        phoneNumberMap.put('8', "tuv");
        phoneNumberMap.put('9', "wxyz");

        ArrayList<String> result = new ArrayList<>();
        phoneNumberCombine("2345", 0, phoneNumberMap, new StringBuilder(), result);
        System.out.println(result);

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
