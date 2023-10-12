package byog.lab5;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestHexWorld {
    @Test
    public void testRowWidth() {
        assertEquals(3, HexWorld.rowWidth(0, 3));
        assertEquals(5, HexWorld.rowWidth(1, 3));
        assertEquals(7, HexWorld.rowWidth(3, 3));
        assertEquals(3, HexWorld.rowWidth(5, 3));
        assertEquals(11, HexWorld.rowWidth(6, 5));

    }

    @Test
    public void testRowOffset() {
        assertEquals(0, HexWorld.rowOffset(0, 3));
        assertEquals(1, HexWorld.rowOffset(1, 3));
        assertEquals(2, HexWorld.rowOffset(2, 3));
        assertEquals(2, HexWorld.rowOffset(3, 3));
        assertEquals(1, HexWorld.rowOffset(4, 3));
        assertEquals(0, HexWorld.rowOffset(5, 3));

        assertEquals(0, HexWorld.rowOffset(0, 4));
        assertEquals(1, HexWorld.rowOffset(1, 4));
        assertEquals(2, HexWorld.rowOffset(2, 4));
        assertEquals(3, HexWorld.rowOffset(3, 4));
        assertEquals(3, HexWorld.rowOffset(4, 4));
        assertEquals(2, HexWorld.rowOffset(5, 4));
        assertEquals(1, HexWorld.rowOffset(6, 4));
        assertEquals(0, HexWorld.rowOffset(7, 4));
    }
}
