package com.fsd.spring.helper;

import com.fsd.spring.model.Book;
import com.fsd.spring.model.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileReadWriteHelper {

    @Value("file:${user.home}/10_Spring_Assignment1_book.txt")
    private Resource bookFile;

    @Value("file:${user.home}/10_Spring_Assignment1_subject.txt")
    private Resource subjectFile;

    public boolean writeToBookFile(Object obj) throws IOException {
        return writeToFile(bookFile.getFile(), obj);
    }

    public boolean writeToSubjectFile(Object obj) throws IOException {
        return writeToFile(subjectFile.getFile(), obj);
    }

    private boolean writeToFile(File file, Object obj) {
        try {
            Files.write(file.toPath(), objectTobyteStream(obj));
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

    public byte[] objectTobyteStream(Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] objectInBytes = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            objectInBytes = bos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return objectInBytes;
    }

    public List<Book> readBooksFromFile() throws IOException, ClassNotFoundException {
        List<Book> objectList = new ArrayList<Book>();
        File file = bookFile.getFile();
        if (file.exists()) {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            objectList = (ArrayList) readByteStream(fileContent);
        }
        return objectList;
    }

    public List<Subject> readSubjectsFromFile() throws IOException, ClassNotFoundException {
        File file = subjectFile.getFile();
        List<Subject> objectList = new ArrayList<Subject>();
        if (file.exists()) {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            objectList = (ArrayList) readByteStream(fileContent);
        }
        return objectList;
    }

    public Object readByteStream(byte[] fileContent) throws IOException, ClassNotFoundException {
        ArrayList<Object> objFromFile = null;
        ByteArrayInputStream bis = new ByteArrayInputStream(fileContent);
        while(bis.available()>0) {
            ObjectInputStream ois = new ObjectInputStream(bis);
            try {
                objFromFile = (ArrayList<Object>) ois.readObject();
            } finally {
                try {
                    if (ois != null) {
                        ois.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return objFromFile;
    }
}
