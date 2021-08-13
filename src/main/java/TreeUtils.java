import java.util.ArrayDeque;
import java.util.ArrayList;

public class TreeUtils {

    public static void main(String[] args) {
        TreeNode root = generateBinaryTreeDepth3();
         /*
                    0
                1       2
              3   4   5   6
         */

        printlnTreeIteration(root, "back");

    }


    /**
     * 迭代的方式遍历二叉树
     * @param root 二叉树根结点
     * @param manner 遍历的方式
     * 迭代的方式遍历， 就是使用 栈 和 指针 模拟递归时候进入栈帧， 保存局部变量， 退出栈帧的行为
     */
    public static void printlnTreeIteration(TreeNode root, String manner){


        // 结果集, 方便输出
        ArrayList<Integer> result = new ArrayList<>();

        // 栈, 用于模拟栈帧
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode pre = new TreeNode();

        switch (manner) {
            // 前序遍历 : 中 --> 左 --> 右
            case "front":
                while (root != null) {


                    while (root != null) {
                        stack.push(root);
                        result.add(root.value);
                        root = root.left;
                    }
                    while (root == null && !stack.isEmpty()) {
                        root = stack.pop().right;
                    }
                }
                System.out.println(result);
                break;
            case "middle":
                while (root != null || !stack.isEmpty()) {
                    while (root != null) {
                        stack.push(root);
                        root = root.left;
                    }
                    root = stack.pop();
                    result.add(root.value);
                    root = root.right;
                }
                System.out.println(result);
                break;
            case "back":
                while (root != null || !stack.isEmpty()) {
                    while (root != null) {
                        stack.push(root);
                        root = root.left;
                    }
                    root = stack.peek();
                    assert root != null;
                    if (root.right == null || root.right == pre) {
                        pre = root;
                        result.add(stack.pop().value);
                        root = null;
                    } else {
                        root = root.right;
                    }
                }
                System.out.println(result);
                break;
        }

    }



    /**
     * 二叉树的遍历
     * @param current 当前结点
     * @param manner 遍历的方式
     * 使用递归进行二叉树的遍历, 代码的顺序和遍历的顺序一致
     */
    public static void printlnTreeRecursion(TreeNode current, String manner){

        // 中序和后序遍历都是到树的最底层, 才开始处理

        if (current==null) return;

        // 中 --> 左 --> 右
        if (manner.equals("front")){
            System.out.println(current); //中
            printlnTreeRecursion(current.left, manner); //左
            printlnTreeRecursion(current.right, manner); // 右
        }

        // 左 --> 中 --> 右
        if (manner.equals("middle")){
            printlnTreeRecursion(current.left, manner); // 左
            System.out.println(current); // 中
            printlnTreeRecursion(current.right, manner); // 右
        }

        // 左 --> 右 --> 中
        if (manner.equals("back")){
            printlnTreeRecursion(current.left, manner); //左
            printlnTreeRecursion(current.right, manner); //右
            System.out.println(current); //中
        }

    }


    public static TreeNode generateBinaryTreeDepth3(){

        /*
                    0
                1       2
              3   4   5   6
         */


        ArrayList<TreeNode> treeNodes = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            treeNodes.add(new TreeNode(i));
        }

        treeNodes.get(0).left = treeNodes.get(1);
        treeNodes.get(0).right = treeNodes.get(2);
        treeNodes.get(1).left = treeNodes.get(3);
        treeNodes.get(1).right = treeNodes.get(4);
        treeNodes.get(2).left = treeNodes.get(5);
        treeNodes.get(2).right = treeNodes.get(6);

        return treeNodes.get(0);
    }


    static class TreeNode{

        private int value;
        private TreeNode left;
        private TreeNode right;


        public TreeNode() {
        }

        public TreeNode(int value) {
            this.value = value;
        }

        public TreeNode(int value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "value=" + value +
                    '}';
        }
    }
}
