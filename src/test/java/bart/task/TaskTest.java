package bart.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TaskTest {

    private Task task;

    @BeforeEach
    void setUp() {
        task = new Task("Test Task");
    }

    /**
     * Tests the markAsDone method.
     * Ensures that the task is marked as done.
     */
    @Test
    void testMarkAsDone() {
        // Act
        task.markAsDone(true);

        // Assert
        assertTrue(task.isDone(), "Task should be marked as done");
    }

    /**
     * Tests the markAsDone method for marking a task as undone.
     * Ensures that the task is marked as undone.
     */
    @Test
    void testMarkAsUndone() {
        // Arrange
        task.markAsDone(true); // First mark the task as done

        // Act
        task.markAsDone(false);

        // Assert
        assertFalse(task.isDone(), "Task should be marked as undone");
    }

    /**
     * Tests the addTag method.
     * Ensures that a tag is added correctly.
     */
    @Test
    void testAddTag() {
        // Act
        task.addTag("#urgent");

        // Assert
        Set<String> tags = task.getTags();
        assertEquals(1, tags.size(), "There should be 1 tag");
        assertTrue(tags.contains("#urgent"), "Task should contain the added tag");
    }

    /**
     * Tests the removeTag method.
     * Ensures that a tag is removed correctly.
     */
    @Test
    void testRemoveTag() {
        // Arrange
        task.addTag("#urgent");

        // Act
        task.removeTag("#urgent");

        // Assert
        Set<String> tags = task.getTags();
        assertEquals(0, tags.size(), "There should be no tags");
        assertFalse(tags.contains("#urgent"), "Task should not contain the removed tag");
    }

    /**
     * Tests the getTagsToString method.
     * Ensures that the string representation of tags is correct.
     */
    @Test
    void testGetTagsToString() {
        // Arrange
        task.addTag("#urgent");
        task.addTag("#homework");

        // Act
        String tagsString = task.getTagsToString();

        // Assert
        assertEquals(" #urgent #homework", tagsString, "The tags string representation should match");
    }

    /**
     * Tests the getTagsToFileFormat method.
     * Ensures that the file format of tags is correct.
     */
    @Test
    void testGetTagsToFileFormat() {
        // Arrange
        task.addTag("#urgent");

        // Act
        String tagsFileFormat = task.getTagsToFileFormat();

        // Assert
        assertEquals(" | #urgent", tagsFileFormat, "The tags file format should match");
    }

    /**
     * Tests the equality method.
     * Ensures that tasks with the same description are considered equal.
     */
    @Test
    void testEquality() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 1");

        // Act & Assert
        assertTrue(task1.equals(task2), "Tasks with the same description should be equal");
    }

    /**
     * Tests the equality method when tasks have different descriptions.
     * Ensures that tasks with different descriptions are not considered equal.
     */
    @Test
    void testEqualityDifferentDescription() {
        Task task1 = new Task("Task 1");
        Task task2 = new Task("Task 2");

        // Act & Assert
        assertFalse(task1.equals(task2), "Tasks with different descriptions should not be equal");
    }
}
