package ca.jrvs.practice.codingChallenge;

public class RemoveNthNodeFromEndOfList {
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode backPointer = head;
        ListNode frontPointer = head;
        int i = 1;

        while (backPointer.next != null) {
            backPointer = backPointer.next;
            if (i > n) {
                frontPointer= frontPointer.next;
            }
            i++;
        }
        if (i < n) {
            return null;
        }
        if (i == n) {
            return head.next;
        }
        frontPointer.next = frontPointer.next.next;
        return head;
    }
}
