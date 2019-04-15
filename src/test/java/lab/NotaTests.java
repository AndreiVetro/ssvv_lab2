package lab;

import lab.src.domain.Nota;
import lab.src.domain.Student;
import lab.src.domain.Tema;
import lab.src.repository.NotaXMLRepo;
import lab.src.repository.StudentXMLRepo;
import lab.src.repository.TemaXMLRepo;
import lab.src.service.Service;
import lab.src.validation.NotaValidator;
import lab.src.validation.StudentValidator;
import lab.src.validation.TemaValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class NotaTests
{
    private Tema tema;
    private Student student;
    private Nota nota;
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
        filenameTema = "src/test/fisiere_test/Teme.xml";
        filenameNota = "src/test/fisiere_test/Note.xml";
        tema = new Tema("5", "sdi", 1, 7);
        student = new Student("vaie2245", "Vetro Andrei", 936, "vaie2245@scs.ubbcluj.ro");
        nota = new Nota("5", "vaie2245", "5", 10, LocalDate.of(2018, 10, 2));
        studentXMLRepository = new StudentXMLRepo(filenameStudent);
        temaXMLRepository = new TemaXMLRepo(filenameTema);
        notaXMLRepository = new NotaXMLRepo(filenameNota);
        temaValidator = new TemaValidator();
        studentValidator = new StudentValidator();
        notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void addNotaExpectDoubleTest()
    {
        service.addStudent(student);
        service.addTema(tema);
        assert service.addNota(nota, "feedback") == 10;
    }


    @After
    public void teardown()
    {
        try (BufferedWriter bufferedWriterNota = new BufferedWriter(new FileWriter(filenameNota)))
        {
            String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>";

            bufferedWriterNota.write(xmlString);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
