package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Stack;

public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        Stack<Character> stack = new Stack<>();
        LinkedList<Character> queue = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9')) {
                stack.push(Character.toLowerCase(c));
                queue.add(Character.toLowerCase(c));
            }
        }
        while (!stack.empty()) {
            if (stack.pop() != queue.pop()) {
                return false;
            }
        }
        return true;
    }
}
