package search;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * ╔══════════════════════════════════════════════════════════╗
 * ║          MC/DC TEST CASES — Order Class                 ║
 * ╠══════════════════════════════════════════════════════════╣
 * ║ Method 1: isActive()                                    ║
 * ║   Decision: PENDING || PROCESSING || SHIPPED            ║
 * ║                                                         ║
 * ║ Method 2: qualifiesForDiscount()                        ║
 * ║   Decision: C1 AND C2 AND C3                            ║
 * ║   C1 = amount > 100                                     ║
 * ║   C2 = isActive()                                       ║
 * ║   C3 = status != SHIPPED                                ║
 * ║                                                         ║
 * ║ Method 3: isHighPriority()                              ║
 * ║   Decision: (C1 OR C2) AND C3                           ║
 * ║   C1 = totalAmount > 500                                ║
 * ║   C2 = customerName starts with "VIP"                   ║
 * ║   C3 = status == PENDING                                ║
 * ╚══════════════════════════════════════════════════════════╝
 */
@DisplayName("Order – MC/DC Test Suite")
class OrderTest {

    // ─────────────────────────────────────────────────────────
    // 1. isActive()
    //
    // Truth Table (OR logic):
    // TC | PENDING | PROCESSING | SHIPPED | Result
    //  1 |    T    |     F      |    F    |   T    ← C1 independently true
    //  2 |    F    |     T      |    F    |   T    ← C2 independently true
    //  3 |    F    |     F      |    T    |   T    ← C3 independently true
    //  4 |    F    |     F      |    F    |   F    ← all false baseline
    // ─────────────────────────────────────────────────────────

    @Test @DisplayName("isActive TC1 – PENDING → true")
    void isActive_TC1() { assertTrue(new Order(1,"A",100,"PENDING").isActive()); }

    @Test @DisplayName("isActive TC2 – PROCESSING → true")
    void isActive_TC2() { assertTrue(new Order(2,"B",100,"PROCESSING").isActive()); }

    @Test @DisplayName("isActive TC3 – SHIPPED → true")
    void isActive_TC3() { assertTrue(new Order(3,"C",100,"SHIPPED").isActive()); }

    @Test @DisplayName("isActive TC4 – DELIVERED → false (all conditions false)")
    void isActive_TC4() { assertFalse(new Order(4,"D",100,"DELIVERED").isActive()); }

    @Test @DisplayName("isActive – CANCELLED → false")
    void isActive_Cancelled() { assertFalse(new Order(5,"E",100,"CANCELLED").isActive()); }

    @Test @DisplayName("isActive – null status → false")
    void isActive_Null() { assertFalse(new Order(6,"F",100,null).isActive()); }

    // ─────────────────────────────────────────────────────────
    // 2. qualifiesForDiscount()
    //
    // Truth Table (AND logic):
    // TC | C1:amt>100 | C2:isActive | C3:notShipped | Result
    //  1 |     T      |      T      |       T       |   T   ← baseline
    //  2 |     F      |      T      |       T       |   F   ← C1 changes result
    //  3 |     T      |      F      |       T       |   F   ← C2 changes result
    //  4 |     T      |      T      |       F       |   F   ← C3 changes result
    // ─────────────────────────────────────────────────────────

    @Test @DisplayName("qualifiesForDiscount TC1 – all true → true")
    void discount_TC1() { assertTrue(new Order(1,"A",200,"PROCESSING").qualifiesForDiscount()); }

    @Test @DisplayName("qualifiesForDiscount TC2 – C1 false (amount=50) → false")
    void discount_TC2() { assertFalse(new Order(2,"B",50,"PROCESSING").qualifiesForDiscount()); }

    @Test @DisplayName("qualifiesForDiscount TC3 – C2 false (DELIVERED) → false")
    void discount_TC3() { assertFalse(new Order(3,"C",200,"DELIVERED").qualifiesForDiscount()); }

    @Test @DisplayName("qualifiesForDiscount TC4 – C3 false (SHIPPED) → false")
    void discount_TC4() { assertFalse(new Order(4,"D",700,"SHIPPED").qualifiesForDiscount()); }

    @Test @DisplayName("qualifiesForDiscount – PENDING, high amount → true")
    void discount_Pending() { assertTrue(new Order(5,"E",150,"PENDING").qualifiesForDiscount()); }

    // ─────────────────────────────────────────────────────────
    // 3. isHighPriority()
    //
    // Truth Table: (C1 OR C2) AND C3
    // TC | C1:highVal | C2:vip | C3:pending | Result
    //  1 |     T      |   F    |     T      |   T   ← C1 changes result (pair TC3)
    //  2 |     F      |   T    |     T      |   T   ← C2 changes result (pair TC3)
    //  3 |     F      |   F    |     T      |   F   ← both false baseline
    //  4 |     T      |   T    |     F      |   F   ← C3 changes result (pair TC1)
    //  5 |     T      |   F    |     F      |   F   ← boundary
    //  6 |     F      |   T    |     F      |   F   ← boundary
    // ─────────────────────────────────────────────────────────

    @Test @DisplayName("isHighPriority TC1 – highValue=T, vip=F, pending=T → true")
    void priority_TC1() { assertTrue(new Order(1,"Alice",600,"PENDING").isHighPriority()); }

    @Test @DisplayName("isHighPriority TC2 – highValue=F, vip=T, pending=T → true")
    void priority_TC2() { assertTrue(new Order(2,"VIP-Bob",30,"PENDING").isHighPriority()); }

    @Test @DisplayName("isHighPriority TC3 – highValue=F, vip=F, pending=T → false")
    void priority_TC3() { assertFalse(new Order(3,"Carol",50,"PENDING").isHighPriority()); }

    @Test @DisplayName("isHighPriority TC4 – highValue=T, vip=T, pending=F → false")
    void priority_TC4() { assertFalse(new Order(4,"VIP-Dave",600,"PROCESSING").isHighPriority()); }

    @Test @DisplayName("isHighPriority TC5 – highValue=T, pending=F → false")
    void priority_TC5() { assertFalse(new Order(5,"Eve",600,"SHIPPED").isHighPriority()); }

    @Test @DisplayName("isHighPriority TC6 – vip=T, pending=F → false")
    void priority_TC6() { assertFalse(new Order(6,"VIP-Frank",30,"SHIPPED").isHighPriority()); }

    // ─────────────────────────────────────────────────────────
    // Boundary & Edge Cases
    // ─────────────────────────────────────────────────────────

    @Test @DisplayName("Boundary – exactly 500 is NOT high value")
    void boundary_500() { assertFalse(new Order(7,"Greg",500.0,"PENDING").isHighPriority()); }

    @Test @DisplayName("Boundary – 500.01 IS high value")
    void boundary_500_01() { assertTrue(new Order(8,"Heidi",500.01,"PENDING").isHighPriority()); }

    @Test @DisplayName("Setters update fields correctly")
    void setters() {
        Order o = new Order(9,"Iris",100,"PENDING");
        o.setStatus("DELIVERED");
        o.setTotalAmount(999.0);
        assertEquals("DELIVERED", o.getStatus());
        assertEquals(999.0, o.getTotalAmount(), 0.001);
    }

    @Test @DisplayName("compareTo – orders sort by ID")
    void compareTo() {
        Order a = new Order(1,"A",100,"PENDING");
        Order b = new Order(5,"B",100,"PENDING");
        assertTrue(a.compareTo(b) < 0);
        assertTrue(b.compareTo(a) > 0);
        assertEquals(0, a.compareTo(new Order(1,"X",50,"SHIPPED")));
    }
}