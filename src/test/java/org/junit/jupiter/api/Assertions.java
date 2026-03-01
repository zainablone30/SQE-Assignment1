package org.junit.jupiter.api;

public class Assertions {
    public static void assertTrue(boolean condition) {
        if (!condition) {
            throw new AssertionError("Assertion failed: expected true");
        }
    }

    public static void assertFalse(boolean condition) {
        if (condition) {
            throw new AssertionError("Assertion failed: expected false");
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        if ((expected == null && actual != null) ||
            (expected != null && !expected.equals(actual))) {
            throw new AssertionError("Assertion failed: expected <" + expected + "> but was <" + actual + ">");
        }
    }

    public static void assertEquals(double expected, double actual, double delta) {
        if (Math.abs(expected - actual) > delta) {
            throw new AssertionError("Assertion failed: expected <" + expected + "> but was <" + actual + ">");
        }
    }

    public static void assertNull(Object object) {
        if (object != null) {
            throw new AssertionError("Assertion failed: expected null but was <" + object + ">");
        }
    }

    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new AssertionError("Assertion failed: expected not null");
        }
    }
}
