package ca.jrvs.practice.codingChallenge;

import java.util.LinkedList;

public class ImplementStackUsingQueues {
    class MyStack {
        private LinkedList<Integer> q1 = new LinkedList<>();
        private LinkedList<Integer> q2 = new LinkedList<>();
        private int top;

        public MyStack() {}

        public void push(int x) {
            top = x;
            if (q2.isEmpty()) {
                q2.add(x);
            } else {
                q1.add(q2.remove());
                q2.add(x);
            }

        }

        public int pop() {
            if (!q2.isEmpty()) {
                return q2.remove();
            } else {
                if(q1.size() == 1) {
                    return q1.remove();
                }
                while (q1.size() > 2) {
                    q2.add(q1.remove());
                }
                top = q1.remove();
                int result = q1.remove();
                q1 = q2;
                q2 = new LinkedList<>();
                q2.add(top);
                return result;
            }
        }

        public int top() {
            top = pop();
            push(top);
            return top;
        }

        public boolean empty() {
            return q1.isEmpty() && q2.isEmpty();
        }
    }

}
