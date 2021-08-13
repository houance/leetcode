import java.util.HashSet;
import java.util.Set;

public class NodeListUtils {

    public static void main(String[] args) {

    }


    /**
     * 快慢指针找链表环的入口
     * @param head 链表的头部
     * @return 链表中环的入口
     * 思路 : 快指针前进两格, 慢指针前进一格, 相遇证明有环, 此时将满指针指向链表头部, 两个指针同速前进
     * 再次相遇就是链表环的入口
     * 具体看公式推导
     */
    public static Node findCycleEntry(Node head){

        // 快慢指针初始化
        Node slow = head;
        Node fast = head;

        while (fast!=null){

            // 快慢指针差速前进
            fast = fast.next.next;
            slow = slow.next;

            // 相遇之后慢指针指向头部, 两个指针同速前进......
            if (fast == slow){
                slow = head;
                while (slow!=null){
                    fast = fast.next;
                    slow = slow.next;
                    if (slow == fast){
                        return slow;
                    }
                }
            }

        }
        return null;

    }



    /**
     * 找到链表交汇的地方, 注意交汇的地方是指 指针相同, 而不是 值相同
     * @param headA 链表 A 的头部
     * @param headB 链表 B 的头部
     * @return 返回交汇的结点
     * 思路 : 计算两个链表的长度, 将两个链表对齐后开始比较
     */
    public static Node findIntersectionNode(Node headA, Node headB){

        // 分别计算两个链表的长度
        int headALength = 0;
        int headBLength = 0;
        Node pointer = headA;
        while (pointer!=null){
            headALength++;
            pointer = pointer.next;
        }
        pointer = headB;
        while (pointer!=null){
            headBLength++;
            pointer = pointer.next;
        }

        // 计算链表需要对齐的长度
        int flag = -1;
        int diff;
        if (headALength>headBLength){
            flag = 1;
            diff = headALength - headBLength;
        }
        else {
            diff = headBLength - headALength;
        }

        // 链表对齐
        while (diff>0){
            if (flag>0){
                headA = headA.next;
            }
            else headB = headB.next;
            diff--;
        }

        // 从对齐的地方开始比较
        while (headA!=null){
            if (headA == headB){
                return headA;
            }
            headA = headA.next;
            headB = headB.next;
        }

        return null;
    }


    /**
     * 快慢指针删除倒数第 n 个结点
     * @param head 链表头
     * @param n 倒数第 n 个结点
     * 快指针和慢指针保持 n + 1 格距离, 当快指针走到尾部时, 慢指针就相当于指向倒数第 n + 1 个结点
     */
    public static void deleteLastNthNodeDoublePointer(Node head, int n){

        // 虚拟头结点, 当要删除的结点是链表头部时, 方便操作
        Node dummyHead = new Node();
        dummyHead.next = head;

        // 快慢指针初始化
        Node fast = dummyHead;
        Node slow = dummyHead;

        // 快指针前进 n 步
        while (n>0){
            fast = fast.next;
            n--;
        }

        // fast 再前进一步, 现在快慢指针就相差 n + 1 步了
        fast = fast.next;

        // 这里的判断条件为 fast!=null, 即快指针走到尾部结点后, 还会再走一步才停止, 即快指针总共走了 链表长度 + 2 的距离
        // 引入 dummyHead 的长度就抵消了
        while (fast!=null){
            slow = slow.next;
            fast = fast.next;
        }

        // 执行删除任务
        slow.next = slow.next.next;

    }



    // 因为递归到尾部的时候, 不会执行 ++lastNthStep, 所以需要初始化为 1
    public static int lastNthStep = 1;
    public static Node target;

    /**
     * 递归找到要删除结点的前一个结点
     * @param current 链表的头结点
     * @param n 倒数第 n 个
     * lastNthStep 开始 “归” 的时候记录走过的步数
     * target 倒数第 n 个结点的前一个结点， 如果 target 为 null， 说明要删除的结点是链表头部
     */
    public static void findLastNthNode(Node current, int n){

        // 遍历到链表尾部, 则退出
        if (current.next==null){
           return;
        }

        // 进入递归
        findLastNthNode(current.next, n);

        // 步数递增
        ++lastNthStep;

        // 因为要返回第 n 个结点的前一个结点, 所以设置为 n + 1
        if (lastNthStep == (n+1))
            target = current;
    }


    /**
     * 两两交换结点-代码控制
     * @param head 链表的头结点
     * @return 两两交换结点之后链表的头结点
     * 变量 : current 指向当前结点(注意是虚拟头结点), next1 为第二个结点, next2 为第三个结点, next3 为第四个结点
     * 步骤 : 第三个结点指向第二个, 第二个指向第四个, 当前指向第三个,
     * 然后移动 current 指针指向 "第二个结点"(注意此时链表已经发生改变, current 指针的位置其实是在 "第三个结点")
     */
    public static Node changeNodeInStep2(Node head){


        // 新建两个虚拟头结点 - 哨兵技巧
        Node virtualHead = new Node();
        Node current = new Node();
        current.next = head;
        virtualHead.next = current;

        // 第一个判断是结点数量为偶数的情况
        // 第二个判断是结点数量为奇数的情况
        while (current.next!=null && current.next.next!=null){


            Node next3 = current.next.next.next;
            Node next2 = current.next.next;
            Node next1 = current.next;

            current.next.next.next = current.next;
            current.next.next = next3;
            current.next = next2;
            current = next1;

        }
        return virtualHead.next.next;

    }


    /**
     * 双指针反转链表
     * @param current 链表的头结点, 运用哨兵技巧即当前结点
     * @return 反转后的链表的头结点
     * 指针 : prev 指针指向当前结点的 前一个结点, current 指针指向当前结点, next 指针指向当前结点的下一个结点
     * 步骤 : current 结点指向 prev 结点, 然后 prev 指针指向 current 结点, 最后 current 指向 next 结点
     */
    public static Node reverseNodeList(Node current){

        // 虚拟表头，是一个值为 null 的结点
        // 即使用哨兵技巧, 不需要为 头结点 额外写处理代码
//        Node prev = new Node();
        Node prev = null;

        // 注意跳出条件为 当前结点不为空
        // 因为我们需要遍历到链表的最后一个结点
        while (current!=null){

            // 缓存当前结点的下一个结点
            Node next = current.next;

            // 修改当前结点指向, 指向它的前一个结点
            // 对于头结点来说, 就是指向空结点
            current.next = prev;

            // prev 指针指向当前结点
            prev = current;

            // 当前结点指针指向下一个结点
            current = next;
        }

        return prev;
    }


    public static Node reverseNodeListRecursion(Node current){

        if (current.next==null){
            return current;
        }

        Node last = reverseNodeListRecursion(current.next);

        current.next.next = current;
        current.next = null;
        return last;
    }



    public static Node reverseNodeListRecursionNtoM(Node current, int n, int m){

        if (n==1){
            return reverseNodeListRecursionFirstN(current, m);
        }


        current.next = reverseNodeListRecursionNtoM(current.next, n-1, m-1);

        return current;
    }


    public static Node reverseNodeListRecursionFirstN(Node current, int n){

        if (n==1){
            return current;
        }

        Node last = reverseNodeListRecursionFirstN(current.next, n - 1);

        current.next.next = current;
        current.next = null;
        return last;


    }


    public static Node generateNodeList(int num){

        Node head = new Node(0);
        Node headsHead = new Node(head, -1);

        for (int i = 1; i < num+1; i++) {
            Node next = new Node(i);
            head.next = next;
            head = next;
        }

        return headsHead.next.next;
    }


    public static void printlnNodeList(Node head){

        while (head!=null){
            System.out.println(head.value);
            head = head.next;
        }

    }



    private static class Node{

        public Node next;
        public int value;

        public Node(Node next, int value) {
            this.next = next;
            this.value = value;
        }

        public Node(int value) {
            this.value = value;
        }

        public Node(){}

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    '}';
        }
    }
}


