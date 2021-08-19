import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

class Solution {

    public static void main(String[] args) {

        TreeUtils.TreeNode treeNode = TreeUtils.generateBinaryTreeDepth3();

        TreeUtils.TreeNode treeNode1 = new TreeUtils.TreeNode();

        pathSum(treeNode, 1);
    }


    public static List<List<Integer>> pathSum(TreeUtils.TreeNode root, int target) {

        List<List<Integer>> result = new ArrayList<>();

        backtracking(root, target, 0, new LinkedList<>(), result);

        boolean contains = result.contains(new ArrayList<>());

        System.out.println(result);

        return result;
    }


    public static void backtracking(TreeUtils.TreeNode current, int target, int sum,
                                    LinkedList<Integer> resultTmp, List<List<Integer>> result){

        if (sum>target) return;
        if (sum==target&&current.left==null&&current.right==null){
            result.add(new LinkedList<>(resultTmp));
            return;
        }

        if (current==null) return;
        sum+=current.value;
        resultTmp.add(current.value);
        backtracking(current.left, target, sum, resultTmp, result);
        backtracking(current.right, target, sum, resultTmp, result);
        resultTmp.removeLast();

    }
}