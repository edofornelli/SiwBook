package it.uniroma3.siw.siwbook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank
    String title;

    @ManyToOne(fetch = FetchType.EAGER)
    public Author author;

    @NotNull
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date releaseDate;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    @OneToMany
    private List<Review> reviews;

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Image> getImages() {
        return images;
    }
    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Image getFirstImage() {
        return this.images.get(0);
    }

    public List<Image> getAllImagesWithoutFirst(){
        try {
            return this.images.subList(1, images.size());
        } catch(Exception e) {
            return null;
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book other = (Book) o;
        return id == other.id && releaseDate == other.releaseDate && Objects.equals(title, other.title) && Objects.equals(author, other.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, releaseDate);
    }

}
