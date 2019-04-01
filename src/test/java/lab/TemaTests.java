package lab;

import lab.src.domain.Tema;
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

public class TemaTests
{
    private Tema tema;
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
        tema = new Tema("5", "sdi", 5, 5);
        studentXMLRepository = new StudentXMLRepo(filenameStudent);
        temaXMLRepository = new TemaXMLRepo(filenameTema);
        notaXMLRepository = new NotaXMLRepo(filenameNota);
        temaValidator = new TemaValidator();
        studentValidator = new StudentValidator();
        notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void addTemaTestExpectNull()
    {
        assert service.addTema(tema) == null;
    }

    @Test(expected = ValidationException.class)
    public void addTemaTest_IdNull()
    {
        tema.setID(null);
        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void addTemaTest_IdEmpty()
    {
        tema.setID("");
        service.addTema(tema);
    }

    @Test(expected = ValidationException.class)
    public void addTemaTest_descriereEmpty()
    {
        tema.setDescriere("");
        service.addTema(tema);
    }


    @Test(expected = ValidationException.class)
    public void addTemaTest_deadlineLess1()
    {
        tema.setDeadline(0);
        service.addTema(tema);
    }


    @Test(expected = ValidationException.class)
    public void addTemaTest_primireLess1()
    {
        tema.setPrimire(0);
        service.addTema(tema);
    }


    @After
    public void teardown()
    {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filenameTema)))
        {
            bufferedWriter.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><inbox></inbox>");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
