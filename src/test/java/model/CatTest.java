package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CatTest {
    private Cat c;

    @BeforeEach
    void setup() {
        c = new Cat("Larry", Sex.MALE, null);
    }

    @Test
    void testGetName() {
        assertEquals("Larry", c.getName());
    }

    @Test
    void testSetName() {
        c.setName("Wumbo");
        assertEquals("Wumbo", c.getName());
    }

}
