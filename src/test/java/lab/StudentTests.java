package lab;

import lab.src.domain.Student;
import lab.src.repository.NotaXMLRepo;
import lab.src.repository.StudentXMLRepo;
import lab.src.repository.TemaXMLRepo;
import lab.src.service.Service;
import lab.src.validation.NotaValidator;
import lab.src.validation.StudentValidator;
import lab.src.validation.TemaValidator;
import lab.src.validation.ValidationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class StudentTests
{
    private Student student;
    private Service service;
    private StudentXMLRepo studentXMLRepository;
    private TemaXMLRepo temaXMLRepository;
    private NotaXMLRepo notaXMLRepository;
    private StudentValidator studentValidator;
    private TemaValidator temaValidator;
    private NotaValidator notaValidator;
    private String filenameStudent;
    private String filenameTema;
    private String filenameNota;

    @Before
    public void setup()
    {
        filenameStudent = "src/test/fisiere_test/Studenti.xml";
        filenameTema = "fisiere/Teme.xml";
        filenameNota = "fisiere/Note.xml";
        student = new Student("id1", "First Student", 1, "student@gmail.com");
        studentXMLRepository = new StudentXMLRepo(filenameStudent);
        temaXMLRepository = new TemaXMLRepo(filenameTema);
        notaXMLRepository = new NotaXMLRepo(filenameNota);
        temaValidator = new TemaValidator();
        studentValidator = new StudentValidator();
        notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }


    @Test
    public void addStudentTestExpectNull()
    {
        Student result = service.addStudent(student);
        assert result == null;
    }

    @Test
    public void addStudentTestExpectStudent()
    {
        service.addStudent(student);
        Student result = service.addStudent(student);
        assert result.equals(student);
    }

    @Test(expected = ValidationException.class)
    public void addStudentTestIdEmpty()
    {
        student.setID("");
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addStudentTestIdNull()
    {
        student.setID(null);
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addStudentTestNumeEmpty()
    {
        student.setNume("");
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addStudentTestNumeNull()
    {
        student.setNume(null);
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addStudentTestEmailEmpty()
    {
        student.setEmail("");
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addStudentTestEmailNull()
    {
        student.setEmail(null);
        service.addStudent(student);
    }

    @Test(expected = ValidationException.class)
    public void addStudentTestGrupaLessThan0()
    {
        student.setGrupa(-1);
        service.addStudent(student);
    }

    @Test
    public void addStudentTestGrupa0()
    {
        student.setGrupa(0);
        Student result = service.addStudent(student);
        assert result == null;
    }

    @After
    public void teardown()
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filenameStudent)))
        {
            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
