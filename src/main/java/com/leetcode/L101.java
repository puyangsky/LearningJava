package com.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by puyangsky on 2017/1/4.
 */
public class L101 {
    public boolean isSymmetric(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        if (root == null) return true;
        queue.offer(root);
        while (queue.peek() != null) {
            TreeNode node = queue.poll();
            System.out.println(node.val);
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return false;
    }
}
