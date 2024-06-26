package it.uniroma3.siw.siwbook.controller;

import it.uniroma3.siw.siwbook.controller.validator.AuthorValidator;
import it.uniroma3.siw.siwbook.model.Author;
import it.uniroma3.siw.siwbook.model.Book;
import it.uniroma3.siw.siwbook.model.Image;
import it.uniroma3.siw.siwbook.model.Review;
import it.uniroma3.siw.siwbook.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import it.uniroma3.siw.siwbook.service.AuthorService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;


@Controller
public class AuthorController {

    @Autowired
    private AuthorValidator authorValidator;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookService bookService;

    @GetMapping("/authors")
    public String showAuthors (Model model) {
        model.addAttribute("authors", this.authorService.findAll());
        return "authors.html";
    }

    @GetMapping("/author/{id}")
    public String getBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("autore", this.authorService.findById(id));
        model.addAttribute("libri", this.authorService.findById(id).getBooks());
        return "author.html";
    }

    @GetMapping("/Admin/addAuthor/{id}")
    public String chooseAuthorForBook (@PathVariable("id") Long id, Model model) {
        model.addAttribute("libro", this.bookService.findById(id));
        model.addAttribute("autori", this.authorService.findAll());
        return "/Admin/addAuthor.html";
    }


    @GetMapping("/Admin/addAuthor/{id}/{idAuthor}")
    public String addAuthorToBook (@PathVariable("id") Long id, @PathVariable("idAuthor") Long idAuthor, Model model) {
        Book book = this.bookService.findById(id);
        Author author = this.authorService.findById(idAuthor);

        this.authorService.addAuthortoBook(author, book);

        return "redirect:/book/" + id;
    }

    @GetMapping("/Admin/newAuthor")
    public String newAuthor(Model model) {
        model.addAttribute("author", new Author());
        return "/Admin/formNewAuthor.html";
    }


    @PostMapping("/Admin/formNewAuthor")
    public String newBook(@RequestParam("files") MultipartFile[] files, @Valid @ModelAttribute("author") Author author, BindingResult bindingResult, Model model) {


        if(files==null) {
            bindingResult.reject("image.null");
        }

        author.setImages(new ArrayList<>());

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
                author.getImages().add(immagine);
            } catch (IOException ex) {
                bindingResult.reject("image.readError");
            }
        }

        this.authorValidator.validate(author, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/Admin/formNewAuthor.html";
        }

        this.authorService.save(author);
        return "redirect:/author/" + author.getId();
    }

}
