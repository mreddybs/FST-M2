package activities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class Activity1 {
    static ArrayList<String> list;

    @BeforeEach
    public void startUp(){
        // Initialize a new ArrayList
        list = new ArrayList<String>();

// Add values to the list
        list.add("alpha"); // at index 0
        list.add("beta"); // at index 1
    }

    @Test
    public void insertTest(){
        assertEquals(2, list.size(), "correct size");

        list.add("gamma");

        assertEquals(3, list.size(), "correct new size");

        assertEquals("alpha", list.get(0), "Element is correct");
        assertEquals("beta", list.get(1), "Element is correct");
        assertEquals("gamma", list.get(2), "Element is correct");
    }

    @Test
    public void replaceTest(){
        assertEquals(2, list.size(), "correct size");

        list.add("delta");

        assertEquals(3, list.size(), "correct new size");

        list.set(1, "lambda");


        assertEquals("alpha", list.get(0), "Element is correct");
        assertEquals("lambda", list.get(1), "Element is correct");
        assertEquals("delta", list.get(2), "Element is correct");

    }

}