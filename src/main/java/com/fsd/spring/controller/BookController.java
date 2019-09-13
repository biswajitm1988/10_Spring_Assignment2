package com.fsd.spring.controller;

import com.fsd.spring.helper.BookHelper;
import com.fsd.spring.helper.SubjectHelper;
import com.fsd.spring.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookHelper bookHelper;

    @Autowired
    private SubjectHelper subjectHelper;

    @GetMapping("/home")
    public ModelAndView index() throws IOException {
        System.out.println("Book Controller index");
        bookHelper.createFilesIfNotExists();
        return new ModelAndView("home");
    }

    @GetMapping("/searchWithSubTitle")
    @ResponseBody
    public String searchWithSubTitle(@RequestParam(name = "searchSubTitle") String subTitle) throws IOException, ClassNotFoundException {
        System.out.println("Book Controller searchWithSubTitle >> "+subTitle);
        List<Book> bookList = subjectHelper.searchBySubject(subTitle);
        return bookHelper.transformToHtml(bookList);
    }

    @GetMapping("/searchWithBookTitle")
    @ResponseBody
    public String searchWithBookTitle(@RequestParam(name = "searchBookTitle") String bookTitle) throws IOException, ClassNotFoundException {
        System.out.println("Book Controller searchWithBookTitle >> "+bookTitle);
        List<Book> bookList = bookHelper.searchByBook(bookTitle);
        return bookHelper.transformToHtml(bookList);
    }

    @PostMapping("/deleteWithSubjectTitle")
    @ResponseBody
    public String deleteWithSubjectTitle(@RequestParam(name = "deleteSubTitle") String deleteSubTitle) throws IOException, ClassNotFoundException {
        System.out.println("Book Controller deleteWithSubjectTitle >> "+deleteSubTitle);
        int count = subjectHelper.deleteSubject(deleteSubTitle);
        return String.valueOf(count).concat(" records deleted");
    }

    @PostMapping("/deleteWithBookTitle")
    @ResponseBody
    public String deleteWithBookTitle(@RequestParam(name = "deleteBookTitle") String deleteBookTitle) throws IOException, ClassNotFoundException {
        System.out.println("Book Controller deleteWithBookTitle >> "+deleteBookTitle);
        int count = bookHelper.deleteBook(deleteBookTitle);
        return String.valueOf(count).concat(" records deleted");
    }

    @PostMapping("/addBook")
    @ResponseBody
    public String addBook(@ModelAttribute Book book) throws IOException, ClassNotFoundException {
        System.out.println("Book Controller deleteWithBookTitle >> "+book);
        long bookId = bookHelper.addBook(book);
        return "Number ".concat(String.valueOf(bookId)).concat(" book added");
    }
}
