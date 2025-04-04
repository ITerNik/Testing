package second;

import config.TimingRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.ifmo.second.FibonacciHeap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(TimingRules.class)
public class Tests {

    private FibonacciHeap<Integer> heap;

    @BeforeEach
    public void setUp() {
        heap = new FibonacciHeap<>();
    }

    @Test
    @DisplayName("Вставка и извлечение минимального элемента")
    public void testInsertAndExtractMin() {
        heap.insert(10, 10);
        heap.insert(20, 20);
        heap.insert(5, 5);

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertNull(heap.extractMin());
    }

    @Test
    @DisplayName("Извлечение минимального элемента из пустой кучи")
    public void testEmptyHeapExtractMin() {
        assertNull(heap.extractMin());
    }

    @Test
    @DisplayName("Вставка минимального элемента")
    public void testInsertMin() {
        heap.insert(10, 10);
        heap.insert(20, 20);
        heap.insert(5, 5);

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
    }

    @Test
    @DisplayName("Вставка элементов с одинаковым ключом")
    public void testInsertWithSameKey() {
        FibonacciHeap<String> heap = new FibonacciHeap<>();

        heap.insert("A", 5);
        heap.insert("B", 5);
        heap.insert("C", 5);

        assertEquals("A", heap.extractMin());
        assertEquals("B", heap.extractMin());
        assertEquals("C", heap.extractMin());
    }

    @Test
    @DisplayName("Извлечение минимального элемента с дочерними узлами")
    public void testExtractMinWithChildNodes() {
        heap.insert(10, 10);
        heap.insert(20, 20);
        heap.insert(5, 5);
        heap.insert(30, 30);

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertEquals(30, heap.extractMin());
    }

    @Test
    @DisplayName("Извлечение минимального элемента из кучи с несколькими узлами и детьми")
    public void testExtractMinFromHeapWithMultipleNodesAndChildren() {
        heap.insert(3, 3);
        heap.insert(1, 1);
        heap.insert(5, 5);
        heap.insert(7, 7);
        heap.insert(2, 2);

        assertEquals(1, heap.extractMin());
        assertEquals(2, heap.extractMin());
        assertEquals(3, heap.extractMin());
        assertEquals(5, heap.extractMin());
        assertEquals(7, heap.extractMin());
    }
}
