import java.util.*;

public class StackQueueUtils {


    public static void main(String[] args) {
        int[] nums = {0,1,0,1,0,1,0,1,0,1,0};
        int k = 4;
        System.out.println(maxSlightWindow(nums, k));
    }


    /**
     * 单调递减的队列
     * @param nums 数组
     * @param k 窗口大小, 移动的步长为 1
     * @return 每次移动, 窗口中的最大值
     * 题目 : 有一个窗口, 大小为 k, 步长为 1, 在数组中往尾部移动,
     * 你需要返回每次移动, 窗口中最大的元素
     * 思路 : 将窗口中的元素添加到 "单调递减队列中", 队列会自动找到最大的元素,
     * 窗口移动的时候, 判断移出窗口的元素是否在队列的头部,
     * 是则将元素从队列中移出, 否则不执行任何操作
     */
    public static List<Integer> maxSlightWindow(int[] nums, int k){

        // 储存结果
        ArrayList<Integer> integers = new ArrayList<>();
        DescQueue descQueue = new DescQueue();

        // 使用一个全局的索引维护窗口
        // i 在窗口初始化后, 永远指向窗口的尾部
        int i = 0;

        // 窗口初始化
        // 即进行第一次移动
        for (; i < k; i++) {
            descQueue.add(nums[i]);
        }

        // 保存结果
        integers.add(descQueue.peekMax());

        // 开始移动窗口
        for (; i<nums.length ; i++) {

            // 判断移出窗口的元素是否等于队列的头部元素
            // 是则从队列中移出元素
            if (nums[i-k]==descQueue.peekMax()){
                descQueue.removeFirst();
            }

            // 将加入窗口的元素放入队列
            // 队列会自动找出窗口中的最大值, 即队列头部的元素
            descQueue.add(nums[i]);

            // 保存结果
            integers.add(descQueue.peekMax());
        }

        return integers;
    }

    /**
     * 单调递减的队列, 队列中的元素 "永远" 按照递减的顺序排好序
     * 要维护这个队列, 重点在于 add(添加元素到队列尾部) 的方法
     * 当使用 add 方法时, 需要判断此时队列尾部的元素 "是否大于当前入队的元素"
     * 不是的话则将队列尾部的元素移出队列
     * 重复执行上述行为, 直到满足规则
     */
    static class DescQueue {

        private Deque<Integer> deque = new ArrayDeque<>();

        void add(int value){

            while (deque.peekLast()!=null && deque.peekLast()<value){
                deque.removeLast();
            }
            deque.addLast(value);
        }

        void removeFirst(){
            deque.removeFirst();
        }

        int peekMax(){
            return deque.peekFirst();
        }

    }
}
