package com.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by puyangsky on 2017/1/4.
 */
public class L257 {
    List<String> ans = new ArrayList<String>();

    public List<String> binaryTreePaths(TreeNode root) {
        if(root != null)
            path(root, String.valueOf(root.val));

        return ans;
    }

    public void path(TreeNode node, String p) {
        if (node.left == null && node.right == null) {
            ans.add(p);
        }
        if(node.left != null)
            path(node.left, p + "->" + node.left.val);
        if(node.right != null)
            path(node.right, p+"->" + node.right.val);
    }
}
