package ca.jrvs.practice.codingChallenge;

import java.util.ArrayList;

public class LinkedListCycle {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    public boolean hasCycle(ListNode head) {
        ArrayList<ListNode> listNodes = new ArrayList<>();
        if (head == null) {
            return false;
        }
        listNodes.add(head);
        head = head.next;
        while (head != null) {
            if (listNodes.contains(head)) {
                return true;
            } else {
                listNodes.add(head);
                head = head.next;
            }
        }
        return false;
    }
}
