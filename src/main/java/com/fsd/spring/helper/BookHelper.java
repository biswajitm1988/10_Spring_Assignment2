package com.fsd.spring.helper;

import com.fsd.spring.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class BookHelper {

    @Autowired
    private FileReadWriteHelper fileReadWriteHelper;

    @Autowired
    private Book book;

    public List<Book> searchByBook(String bookTitle) throws IOException, ClassNotFoundException {
        List<Book> bookList = fileReadWriteHelper.readBooksFromFile();
        List<Book> bookDetailsList = new ArrayList<Book>();
        if (bookList.isEmpty()) {
            System.out.println("There are no books in the system");
            return bookList;
        }

        for (Book book : bookList) {
            if (bookTitle != null && book.getTitle() != null
                    && book.getTitle().toLowerCase().contains(bookTitle.toLowerCase())) {
                bookDetailsList.add(book);
            }
        }
        if (bookDetailsList.isEmpty()) {
            System.out.println("no books found for your search : " + bookTitle);
        } else {
            System.out.println("Matching Books :\n" + bookDetailsList);
        }
        return bookDetailsList;
    }

    public int deleteBook(String bookTitle) throws IOException, ClassNotFoundException {
        List<Book> bookList = fileReadWriteHelper.readBooksFromFile();
        if (bookList.isEmpty()) {
            System.out.println("There are no books in the system");
            return 0;
        }
        int count = 0;
        ListIterator<Book> listIterator = bookList.listIterator();
        while (listIterator.hasNext()) {
            Book book = listIterator.next();
            if (book.getTitle().contains(bookTitle)) {
                listIterator.remove();
                count++;
            }
        }
        boolean status = fileReadWriteHelper.writeToBookFile(bookList);
        System.out.println("Number of records deleted : " + count);

        return count;
    }

    public Long addBook(Book book) throws IOException, ClassNotFoundException {
        List<Book> bookList = fileReadWriteHelper.readBooksFromFile();
        Long bookId = null;
        if (bookList != null && !bookList.isEmpty()) {
            Collections.sort(bookList);
            bookId = bookList.get(bookList.size() - 1).getBookId() + 1;
        } else {
            bookList = new ArrayList<Book>();
            bookId = 1l;
        }
        book.setBookId(bookId);
        bookList.add(book);
        boolean status = fileReadWriteHelper.writeToBookFile(bookList);
        System.out.println("\nBook Added " + status + " Id=" + book.getBookId());
        return book.getBookId();
    }

    public void createFilesIfNotExists() throws IOException {
        File bookFile = new File(System.getProperty("user.home"), "10_Spring_Assignment1_book.txt");
        bookFile.createNewFile();
        File subjectFile = new File(System.getProperty("user.home"), "10_Spring_Assignment1_subject.txt");
        subjectFile.createNewFile();
        System.out.println("File Locations \n" + subjectFile.getAbsolutePath() + "\n" + bookFile.getAbsolutePath());
    }

    public String transformToHtml(List<Book> bookList) {
        if(CollectionUtils.isEmpty(bookList)){
            return "No Book Found for the Search Key";
        }
        StringBuilder buf = new StringBuilder();
        buf.append("<html>" +
                "<body>" +
                "<table>" +
                "<tr>" +
                "<th>Book ID</th>" +
                "<th>Book Title</th>" +
                "<th>Price</th>" +
                "<th>Volume</th>" +
                "<th>Published Date</th>" +
                "</tr>");
        bookList.forEach(book ->{
            buf.append("<tr><td>")
                    .append(book.getBookId())
                    .append("</td><td>")
                    .append(book.getTitle())
                    .append("</td><td>")
                    .append(book.getPrice())
                    .append("</td><td>")
                    .append(book.getVolume())
                    .append("</td><td>")
                    .append(book.getPublishDate())
                    .append("</td></tr>");
        });
        buf.append("</table>" +
                "</body>" +
                "</html>");
        return buf.toString();
    }
}
