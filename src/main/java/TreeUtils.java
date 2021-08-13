import java.util.ArrayList;

public class TreeUtils {

    public static void main(String[] args) {
        TreeNode root = generateBinaryTreeDepth3();

        printlnTree(root, "middle");

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


    /**
     * 二叉树的遍历
     * @param current 当前结点
     * @param manner 遍历的方式
     * 使用递归进行二叉树的遍历, 代码的顺序和遍历的顺序一致
     */
    public static void printlnTree(TreeNode current, String manner){

        // 中序和后序遍历都是到树的最底层, 才开始处理

        if (current==null) return;

        // 中 --> 左 --> 右
        if (manner.equals("front")){
            System.out.println(current); //中
            printlnTree(current.left, manner); //左
            printlnTree(current.right, manner); // 右
        }

        // 左 --> 中 --> 右
        if (manner.equals("middle")){
            printlnTree(current.left, manner); // 左
            System.out.println(current); // 中
            printlnTree(current.right, manner); // 右
        }

        // 左 --> 右 --> 中
        if (manner.equals("back")){
            printlnTree(current.left, manner); //左
            printlnTree(current.right, manner); //右
            System.out.println(current); //中
        }

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
