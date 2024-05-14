package com.example.seprojback;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomClassNameConflictExample {
    public static class String{ }
    @Test
    public void exec() {
        String s = "This is a test";
        System.out.println(s);
    }
}
