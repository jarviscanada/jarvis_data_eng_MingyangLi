package ca.jrvs.practice.codingChallenge;

public class RotateString {
    public boolean rotateString(String s, String goal) {
        for (int i = 0; i < s.length(); i++) {
            if (goal.equals(s.substring(i,s.length()) + s.substring(0,i))) {
                return true;
            }
        }
        return false;
    }
}
