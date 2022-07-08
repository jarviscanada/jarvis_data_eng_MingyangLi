package ca.jrvs.practice.codingChallenge;

import java.util.Stack;

public class ValidParentheses {
    public boolean isValid(String s) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.add(1);
            } else if (s.charAt(i) == ')') {
                if (stack.empty() || stack.pop() != 1) {
                    return false;
                }
            } else if (s.charAt(i) == '[') {
                stack.add(2);
            } else if (s.charAt(i) == ']') {
                if (stack.empty() || stack.pop() != 2) {
                    return false;
                }
            } else if (s.charAt(i) == '{') {
                stack.add(3);
            } else if (s.charAt(i) == '}') {
                if (stack.empty() || stack.pop() != 3) {
                    return false;
                }
            }

        }
        return stack.empty();
    }
}
