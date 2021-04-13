package test;

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import enrolmentManager.EnrolmentManager;
import object.StudentEnrolment;

public class Tester {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private static EnrolmentManager obj;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        System.out.println("Before class");
    }

    @Before
    public void setUpBeforeMethod() throws Exception {
        System.out.println("Before each method");
        obj = EnrolmentManager.getTestInstance();
        System.setOut(new PrintStream(outContent));

    }

    @AfterClass
    public static void setUpAfterClass() throws Exception {
        System.out.println("After class");
    }

    @After
    public void setUpAfterMethod() throws Exception {
        System.out.println("After each method");
        System.setOut(originalOut);
    }

    @Test
    public void testAddEnrolment() {
        StudentEnrolment enrolment = new StudentEnrolment("Nguyen Van A", "Programming 1", "2021A");
        assertTrue(obj.add(enrolment));
        assertFalse(obj.add(enrolment));
    }

    @Test
    public void testUpdateEnrolmentSuccess() {
        StudentEnrolment enrolment = new StudentEnrolment("Nguyen Van A", "Programming 1", "2021A");
        obj.add(enrolment);

        obj.update(1, "Nguyen Van A", "UCD", "2021A");

        assertTrue(outContent.toString().trim().equals(""));
    }

    @Test
    public void testUpdateEnrolmentExist() {
        StudentEnrolment enrolment1 = new StudentEnrolment("Nguyen Van A", "Programming 1", "2021A");
        StudentEnrolment enrolment2 = new StudentEnrolment("Nguyen Van A", "Further Programming", "2021A");
        StudentEnrolment enrolment3 = new StudentEnrolment("Nguyen Van A", "Web Programming", "2021A");
        obj.add(enrolment1);
        obj.add(enrolment2);
        obj.add(enrolment3);

        obj.update(1, "Nguyen Van A", "Programming 1", "2021A");

        assertTrue(outContent.toString().trim().equals("Course already exist in the list."));
    }

    @Test
    public void testUpdateEnrolmentNotExist() {
        StudentEnrolment enrolment1 = new StudentEnrolment("Nguyen Van A", "Programming 1", "2021A");
        StudentEnrolment enrolment2 = new StudentEnrolment("Nguyen Van A", "Further Programming", "2021A");
        StudentEnrolment enrolment3 = new StudentEnrolment("Nguyen Van A", "Web Programming", "2021A");
        obj.add(enrolment1);
        obj.add(enrolment2);
        obj.add(enrolment3);

        obj.update(2, "Nguyen Van A", "UCD", "2021A");

        assertTrue(outContent.toString().trim().equals("Course does not exist in the list."));
    }

    @Test
    public void testDeleteEnrolment() {
        StudentEnrolment enrolment1 = new StudentEnrolment("Nguyen Van A", "Programming 1", "2021A");
        StudentEnrolment enrolment2 = new StudentEnrolment("Nguyen Van A", "Further Programming", "2021A");
        StudentEnrolment enrolment3 = new StudentEnrolment("Nguyen Van A", "Web Programming", "2021A");
        obj.add(enrolment1);
        obj.add(enrolment2);
        obj.add(enrolment3);
        obj.delete(1);

        EnrolmentManager obj2 = EnrolmentManager.getTestInstance();
        obj2.add(enrolment1);
        obj2.add(enrolment3);

        assertTrue(obj.getAll().equals(obj2.getAll()));
    }

    @Test
    public void testGetAll() {
        StudentEnrolment enrolment1 = new StudentEnrolment("Nguyen Van A", "Programming 1", "2021A");
        StudentEnrolment enrolment2 = new StudentEnrolment("Nguyen Van A", "Further Programming", "2021A");
        StudentEnrolment enrolment3 = new StudentEnrolment("Nguyen Van A", "Web Programming", "2021A");
        obj.add(enrolment1);
        obj.add(enrolment2);
        obj.add(enrolment3);

        EnrolmentManager obj2 = EnrolmentManager.getTestInstance();
        obj2.add(enrolment1);
        obj2.add(enrolment2);
        obj2.add(enrolment3);

        assertTrue(obj.getAll().equals(obj2.getAll()));
    }
}