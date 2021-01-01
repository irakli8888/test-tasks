package com.example.task_4.service;

import com.example.task_4.model.Book;
import com.example.task_4.model.Genre;
import com.example.task_4.repository.AuthorRepository;
import com.example.task_4.repository.BookRepository;
import com.example.task_4.repository.GenreRepository;
import com.example.task_4.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final PersonRepository personRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;

    @Transactional
    public List<Book> findAll(){
        return (List<Book>) bookRepository.findAll();
    }


    @Transactional
    public List <Book> getBooksByGenre(@PathVariable String genre) {
        if (bookRepository.existsByGenresName(genre)) {
            return bookRepository.findByGenresName(genre);
        }
        return null;
    }

    //выводим книги по автору(любой комбинацие фио)
    @Transactional
    public String getBooksByAuthor(String author) {
        String values[] = author.split(" ");
        ArrayList<Book> books = new ArrayList<>();
        books.addAll(findAll());
        for (int i = 0; i < values.length; i++) {
            if (bookRepository.existsByAuthorFirstName(values[i]) || bookRepository.existsByAuthorLastName(values[i])
                    || bookRepository.existsByAuthorMiddleName(values[i])) {
                books.stream().filter(a -> a.getAuthor().getFirstName().equals(values[i]) ||
                        a.getAuthor().getMiddleName().equals(values[i]) ||
                        a.getAuthor().getLastName().equals(values[i])).collect(Collectors.toList());
                return books.toString();
            }
            return "Not Found";
        }
        return null;
    }

    @Transactional
    public void add(Book book) {
        genreRepository.saveAll(book.getGenres());
        authorRepository.save(book.getAuthor());
        bookRepository.save(book);
        personRepository.saveAll(book.getPersons());
    }

    @Transactional
    public  List<Book> updateBookForGenre(Book book ) {
        try {
            if (bookRepository.existsById(book.getId())) {
                List<Genre> genres = bookRepository.findById(book.getId()).get().getGenres();
                for (int i = 0; i < genres.size(); i++) {//если в бд нет жанров, которые передали в книге то сохраняем их
                    if (!genres.contains(book.getGenres())) {
                        genreRepository.save(book.getGenres().get(i));
                    }
                    bookRepository.save(book);
                }
            }
            return findAll();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Transactional
    public ResponseEntity deleteById(Long id) {
        if(bookRepository.existsById(id) && bookRepository.findById(id).stream().allMatch(a->a.getPersons().isEmpty())){
            bookRepository.deleteById(id);
            return new ResponseEntity(HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);//если нет, то NOT_MODIFIED
    }
}
