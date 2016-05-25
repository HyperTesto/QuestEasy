package me.hypertesto.questeasy;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import me.hypertesto.questeasy.voice.Recognition;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void permaneza_recognition_isCorrect() throws Exception {
        Recognition r = new Recognition();
        List<String> testList = new ArrayList<>();
        testList.add("asdas permanenza 10 giorni");
		testList.add("asdfg 12 giorni di permanenza    ");
		testList.add("garbage");
        assertEquals("Should match first case", 10, r.parsePermanenza(testList.get(0)));
		assertEquals("Should match second case", 12, r.parsePermanenza(testList.get(1)));
		assertEquals("Shoud return -1", -1, r.parsePermanenza(testList.get(2)));
    }
}