package com.fsd.spring.helper;

import com.fsd.spring.model.Book;
import com.fsd.spring.model.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class SubjectHelper {
	
	static Comparator<Subject> byTitle = Comparator.comparing(Subject::getSubtitle);

    @Autowired
    private FileReadWriteHelper fileReadWriteHelper;

    @Autowired
    private Book book;

    @Autowired
    private Subject subject;

    public int deleteSubject(String searchSubTitle) throws IOException, ClassNotFoundException {
        List<Subject> subjectList = fileReadWriteHelper.readSubjectsFromFile();
        if (subjectList.isEmpty()) {
            System.out.println("There are no subjects in the system");
            return 0;
        }
        ListIterator<Subject> listIterator = subjectList.listIterator();
        int count=0;
        while (listIterator.hasNext()){
            Subject subject = listIterator.next();
            if(subject.getSubtitle().contains(searchSubTitle)) {
                listIterator.remove();
                count++;
            }
        }
        boolean status = fileReadWriteHelper.writeToSubjectFile(subjectList);
        System.out.println("Number of records deleted : "+count);
        return count;
    }

    public List<Book> searchBySubject(String subTitle) throws IOException, ClassNotFoundException {
        List<Subject> subjectList = fileReadWriteHelper.readSubjectsFromFile();
        List<Book> bookDetailsList  = new ArrayList<Book>();
        if (subjectList.isEmpty()) {
            System.out.println("There are no subjects in the system");
            return bookDetailsList;
        }
         for (Subject subject:subjectList){
            if(subTitle!=null
                    && subject.getSubtitle()!=null
                    && subject.getSubtitle().toLowerCase().contains(subTitle.toLowerCase())) {
                for (Book book : subject.getReferences()) {
                    bookDetailsList.add(book);
                }
            }
        }
        if(bookDetailsList.isEmpty()){
            System.out.println("no books found for your search : "+subTitle);
        }else{
            System.out.println("Matching Books :\n"+bookDetailsList);
        }
        return bookDetailsList;
    }
}
