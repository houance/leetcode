import java.util.HashMap;

public class StringUtil {

    public static void main(String[] args) {


        String a = "asdfghjkl";
        String target = "al";

        System.out.println(slightWindow(a, target));


    }


    /**
     * 双指针判断回文字符串
     * @param string 字符串
     * @return 是否为回文字符串
     */
    public static boolean palindrome(String string){
        int left = 0;
        int right = string.length()-1;
        while (left<right){
            if (string.charAt(left)!=string.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }




    /**
     * 滑动窗口解决子串问题(不保证顺序)
     * @param a 字符串
     * @param target 目标串
     * @return 字符串中 "最短的" 并且可以覆盖目标串的子串
     * 思路 :
     */
    public static String slightWindow(String a, String target){


        // count 的 key 为目标串中字符, value 存放窗口中属于目标串的字符的数量
        HashMap<Character, Integer> count = new HashMap<>();

        // targetMap 的 key 为目标串中的字符, value 为目标串中的字符对应的数量
        HashMap<Character, Integer> targetMap = new HashMap<>();


        // 填充两个 Map
        for (char c : target.toCharArray()) {
            count.put(c, 0);
            targetMap.put(c, targetMap.getOrDefault(c, 0)+1);
        }


        // length 初始化为最大值, 即使用哨兵机制, 方便后续判断
        int left, right, length, start, valid;
        left = right = valid = start = 0;
        length = Integer.MAX_VALUE;

        // 开始处理
        while (right<a.length()){

            // right 先自增再缩小窗口, 保证 length > 0
            char charAtRight = a.charAt(right);
            right++;

            // 当 count 里面的 value 与 targetMap 里面的 value 相等,
            // 则说明当前窗口里已经覆盖了 "目标串中一个字符"
            // 所以 valid 自增
            // 此处说明 count.value 可能大于 targetMap.value
            if (count.containsKey(charAtRight)){
                count.put(charAtRight, count.get(charAtRight)+1);
                if (count.get(charAtRight).equals(targetMap.get(charAtRight))){
                    valid++;
                }
            }

            // valid 与 count/targetMap 的大小相等时,
            // 说明当前窗口里已经覆盖了 "整个目标串"
            // 则需要缩小窗口
            while (valid==count.size()){

                char charAtLeft = a.charAt(left);

                // 如果缩小窗口时包含目标串中的字符
                // 则 count.value 自减
                // 当 count.value 小于 targetMap.value 时, 说明窗口 "已经不覆盖" 目标串了
                // 则需要停止缩小窗口, 转而扩大窗口, 并且记录此时窗口的大小和起始位置
                if (count.containsKey(charAtLeft)){
                    count.put(charAtLeft, count.get(charAtLeft)-1);

                    if (count.get(charAtLeft)<targetMap.get(charAtLeft)){
                        valid--;

                        if (right - left < length){
                            length = right - left;
                            start = left;
                        }
                    }
                }
                left++;

            }

        }

        return a.substring(start, start+length);
    }



}
