package utils;

import hibernate.entity.Course;
import hibernate.entity.Student;
import hibernate.entity.StudentProfile;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();
        Course enterprise = saveCourse (session, "Java Enterprise");
        Course core = saveCourse (session, "Java Core");
        Course python = saveCourse (session, "Python");

        StudentProfile profile1 =saveProfile(session, 6.7);
        StudentProfile profile2 =saveProfile(session, 7.7);
        StudentProfile profile3 =saveProfile(session, 5.7);
        StudentProfile profile4 =saveProfile(session, 6.7);
        StudentProfile profile5 =saveProfile(session, 6.7);
        StudentProfile profile6 =saveProfile(session, 6.7);


        Student firstStudent = saveStudent(session,
                "Jack", "+375-44-123-45-67", "jack@mail.com",
                enterprise, profile1);
        Student secondtStudent = saveStudent(session,
                "Bill", "+375-29-123-45-67", "bill@mail.com",
                enterprise, profile2);
        Student thirdtStudent = saveStudent(session,
                "Danny", "+375-33-123-45-67", "danny@mail.com",
                enterprise, profile3);
        Student fourthStudent = saveStudent(session,
                "Charly", "+375-44-765-43-21", "charly@mail.com",
                core, profile4);
        Student fifthStudent = saveStudent(session,
                "Kate", "+375-29-765-43-21", "kate@mail.com",
                core, profile5);
        Student sixthStudent = saveStudent(session,
                "Vic", "+375-33-765-43-21", "vic@mail.com",
                python, profile6);


    }

    private static StudentProfile saveProfile(Session session, double mark) {
        StudentProfile studentProfile = StudentProfile.builder()
                .mark(mark)
                .build();
        session.save(studentProfile);
        return studentProfile;
    }

    public Course saveCourse (Session session, String name){
        Course course = Course.builder()
                .name(name)
                .build();
        session.save(course);
        return course;
    }

    public Student saveStudent(Session session,
                               String name,
                               String phone,
                               String email,
                               Course course,
                               StudentProfile studentProfile){
        Student student = Student.builder()
                .name(name)
                .phone(phone)
                .email(email)
                .course(course)
                .studentProfile(studentProfile)
                .build();
        studentProfile.setStudent(student);
        session.save(studentProfile);
        session.save(student);
        return student;
    }
}
