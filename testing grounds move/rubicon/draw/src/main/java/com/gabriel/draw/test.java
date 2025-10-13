package com.gabriel.draw;

public class test {
    public static void main(String[] args) {
        // Use the class name directly to access the resource
        java.net.URL url = test.class.getResource("/com/anastacio/draw/view/undo.png");
        if (url == null) {
            System.out.println("Icon not found");
        } else {
            System.out.println("Icon found at: " + url);
        }
    }
}
