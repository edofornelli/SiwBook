package it.uniroma3.siw.siwbook.controller;

import it.uniroma3.siw.siwbook.model.Book;
import it.uniroma3.siw.siwbook.model.Image;

import it.uniroma3.siw.siwbook.service.BookService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;


@Controller
public class BookController {

    @Autowired
    private BookService bookService;



    @GetMapping("/book/{id}")
    public String getBook(@PathVariable("id") Long id, Model model) {
        Book libro = this.bookService.findById(id);

        model.addAttribute("book", this.bookService.findById(id));
        model.addAttribute("ingredients", this.bookService.findById(id).getImages());
        model.addAttribute("recensioni", this.bookService.findById(id).getReviews());
        model.addAttribute("titoloLibro", libro.getTitle());
        return "book.html";
    }

    @GetMapping("/books")
    public String showBooks (Model model) {
        model.addAttribute("books", this.bookService.findAll());
        return "books.html";
    }





    @GetMapping("/Admin/newBook")
    public String formNewLibro (Model model) {
        model.addAttribute("book", new Book());
        return "/Admin/formNewBook.html";
    }

    @PostMapping("/Admin/formNewBook")
    public String newBook(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute("book") Book book, BindingResult bindingResult, Model model) {

        // this.bookValidator.validate(book, bindingResult);

        if(files==null) {
            bindingResult.reject("image.null");
        }

        book.setImages(new ArrayList<>());

        for (MultipartFile file : files) {
            try {
                Image immagine = new Image();
                immagine.setFilename(file.getOriginalFilename());
                immagine.setImageData(file.getBytes());
                String format = immagine.getFormat();
                if (!(format.equals("jpeg") || format.equals("png") || format.equals("jpg"))) {
                    bindingResult.reject("image.formatNotSupported");
                    continue;
                }
                book.getImages().add(immagine);
            } catch (IOException ex) {
                bindingResult.reject("image.readError");
            }
        }

        this.bookService.save(book);
        return "redirect:/book/" + book.getId();
    }


}


