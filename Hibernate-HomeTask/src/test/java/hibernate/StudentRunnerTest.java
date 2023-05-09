package hibernate;


import hibernate.entity.*;
import lombok.Cleanup;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;
import utils.TestDataImporter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class StudentRunnerTest {

    @Test
    public void addStudentToNewCourse() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var course = Course.builder().
                name("Java Enterprise").build();
        var studentProfile = StudentProfile.builder()
                .mark(7.8)
                .build();
        var student = Student.builder()
                .name("Евгений")
                .phone("1323")
                .email("qwe@mail.com")
                .build();
        studentProfile.setStudent(student);

        course.addStudent(student);

        session.save(course);

        session.getTransaction().commit();
    }


    @Test
    public void findAllJavaStudent() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        TestDataImporter.importData(sessionFactory);
        Long i = 1l;
        while (true) {
            var course = session.get(Course.class, i);
            if (course.getName().equals("Java Enterprise")) {
                List<Student> students = course.getStudents();
                for (Student student : students) {
                    System.out.println(student);
                }
                break;
            } else i++;
        }

        session.getTransaction().commit();

    }

    @Test
    public void deleteBadStudents() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();
        TestDataImporter.importData(sessionFactory);
        Long i = 1l;
        List<Student> badStudents = new ArrayList<>();
        while (true) {
            var course = session.get(Course.class, i);
            if (course.getName().equals("Java Enterprise")) {
                List<Student> students = course.getStudents();
                for (Student student : students) {
                    double mark = student.getStudentProfile().getMark();
                    if (mark < 6) {
                        System.out.println("This student - is a bad student: " + student.getName());
                        badStudents.add(student);
                    }
                }
                break;
            } else i++;
        }
        for (Student student:badStudents) {
            System.out.println(student);
            session.delete(student);
        }
        session.getTransaction().commit();
    }

    @Test
    public void deleteJavaCourse() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        TestDataImporter.importData(sessionFactory);
        Long i = 1l;
        while (true) {
            var course = session.get(Course.class, i);
            if (course.getName().equals("Java Enterprise")) {
                break;
            } else i++;
        }
        var course = session.get(Course.class, i);
        session.delete(course);

        session.getTransaction().commit();
    }

    @Test
    public void addStudentToJavaCourse() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        TestDataImporter.importData(sessionFactory);
        Long i = 1l;
        while (true) {
            var course = session.get(Course.class, i);
            if (course.getName().equals("Java Enterprise")) {
                break;
            } else i++;
        }
        var course = session.get(Course.class, i);
        var studentProfile = StudentProfile.builder()
                .mark(7.8)
                .build();
        var student = Student.builder()
                .name("Евгений")
                .phone("1323")
                .email("qwe@mail.com")
                .build();
        studentProfile.setStudent(student);

        course.addStudent(student);

        session.save(course);

        session.getTransaction().commit();
    }

    @Test
    public void addTrainer() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        TestDataImporter.importData(sessionFactory);
        var trainer = Trainer.builder()
                .name("Java Guru")
                .phone("+375-44-111-11-11")
                .email("allaboutjava@mail.com")
                .build();
        session.save(trainer);
        var course = session.get(Course.class, 1l);
        var course2 = session.get(Course.class, 2l);

        var info = Info.builder().build();
        info.setTrainer(trainer);
        info.setCourse(course);

        var info1 = Info.builder().build();

        info1.setTrainer(trainer);
        info1.setCourse(course2);

        session.save(info);
        session.save(info1);
        session.getTransaction().commit();
    }

    @Test
    public void updateCourse() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        StudentRunnerTest test = new StudentRunnerTest();
        test.addTrainer();
        var course = session.get(Course.class, 3l);
        var info = session.get(Info.class, 2l);
        info.setCourse(course);
        session.saveOrUpdate(info);
        session.getTransaction().commit();
    }

    @Test
    public void deleteTrainersCourse() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        StudentRunnerTest test = new StudentRunnerTest();
        test.addTrainer();
        var info = session.get(Info.class, 2l);
        session.delete(info);
        session.getTransaction().commit();

    }


}


