package cz.jngrss.shipmonkdemo.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

class SortedLinkedListTest {

    @Test
    void shouldAddIntegersAndSortTheList() {
        List<Integer> integersAsc = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            integersAsc.add(i);
        }

        List<Integer> shuffledInput = new ArrayList<>(integersAsc);
        Collections.shuffle(shuffledInput);

        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList<>();
        shuffledInput.forEach(sortedLinkedList::add);
        final Node<?> headNode = sortedLinkedList.sortList();

        assertThat(sortedLinkedList.getSize()).isEqualTo(10);
        assertThat(getSortedLinkedListIntegerValues(headNode)).isEqualTo(integersAsc);
    }

    @Test
    void nullNodeListSortReturnsCorrectNode() {
        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList<>();
        final Node<?> headNode = sortedLinkedList.sortList();

        assertNotNull(sortedLinkedList);
        assertNull(headNode);
        assertThat(sortedLinkedList.getSize()).isZero();
    }

    @Test
    void shouldAddValueAndSortIntegerList() {
        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList<>(20);
        final Node<?> headNode = sortedLinkedList.addAndSort(10);

        assertThat(sortedLinkedList.getSize()).isEqualTo(2);
        assertThat(getSortedLinkedListIntegerValues(headNode)).containsExactly(10, 20);
    }

    @Test
    void shouldAddValueAndSortStringList() {
        SortedLinkedList<String> sortedLinkedList = new SortedLinkedList<>("init value");
        final Node<?> headNode = sortedLinkedList.addAndSort("a-value");

        assertThat(sortedLinkedList.getSize()).isEqualTo(2);
        assertThat(getSortedLinkedListStringValues(headNode)).containsExactly("a-value", "init value");
    }

    @Test
    void singleNodeListSortReturnsCorrectNode() {
        List<Integer> integersAsc = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            integersAsc.add(i);
        }

        List<Integer> shuffledInput = new ArrayList<>(integersAsc);
        Collections.shuffle(shuffledInput);

        SortedLinkedList<Integer> sortedLinkedList = new SortedLinkedList<>();
        shuffledInput.forEach(sortedLinkedList::add);
        final Node<?> headNode = sortedLinkedList.sortList();

        assertThat(sortedLinkedList.getSize()).isEqualTo(1);
        assertThat(getSortedLinkedListIntegerValues(headNode)).isEqualTo(integersAsc);
    }

    @Test
    void shouldAddStringsAndSortTheList() {
        int lowerLimit = 48; // '0'
        int upperLimit = 122; // 'z'
        int stringLength = 10;
        Random random = new Random();

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add(
                    random.ints(lowerLimit, upperLimit + 1)
                            .limit(stringLength)
                            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                            .toString()
            );
        }

        List<String> expectedResult = new ArrayList<>(strings);
        Collections.sort(expectedResult);

        SortedLinkedList<String> sortedLinkedList = new SortedLinkedList<>();
        strings.forEach(sortedLinkedList::add);
        final Node<?> headNode = sortedLinkedList.sortList();

        assertThat(sortedLinkedList.getSize()).isEqualTo(10);
        assertThat(getSortedLinkedListStringValues(headNode)).isEqualTo(expectedResult);
    }

    @Test
    void testGetHeadAndHeadValue() {
        assertNull(new SortedLinkedList<Integer>().getHead());
        assertNotNull(new SortedLinkedList<Integer>(1).getHead());
        assertEquals(1, new SortedLinkedList<>(1).getHead().getValue());
        assertNull(new SortedLinkedList<>().getHead());
        assertNotNull(new SortedLinkedList<>("head").getHead());
        assertEquals("head", new SortedLinkedList<>("head").getHead().getValue());
    }

    private List<Integer> getSortedLinkedListIntegerValues(Node<?> node) {
        List<Integer> integers = new ArrayList<>();
        while (node != null) {
            integers.add((Integer) node.getValue());
            node = node.getNext();
        }
        return integers;
    }

    private List<String> getSortedLinkedListStringValues(Node<?> node) {
        List<String> strings = new ArrayList<>();
        while (node != null) {
            strings.add((String) node.getValue());
            node = node.getNext();
        }
        return strings;
    }

}
