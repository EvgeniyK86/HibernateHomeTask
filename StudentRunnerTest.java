package hibernate;


import hibernate.entity.Course;
import hibernate.entity.Student;
import hibernate.entity.StudentProfile;
import lombok.Cleanup;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.Test;

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

        course.addStudent(student);

        session.save(course);

        session.getTransaction().commit();
    }


    @Test
    public void oneToMany() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var company = session.get(Course.class, 2l);
        System.out.println(company.getStudents());

        session.getTransaction().commit();

    }

    @Test
    public void deleteCourse() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var course = session.get(Course.class, 4L);
        session.delete(course);

        session.getTransaction().commit();
    }
}
