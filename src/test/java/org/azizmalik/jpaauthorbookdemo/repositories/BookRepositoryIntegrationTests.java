package org.azizmalik.jpaauthorbookdemo.repositories;

import org.azizmalik.jpaauthorbookdemo.TestDataUtil;
import org.azizmalik.jpaauthorbookdemo.domain.Author;
import org.azizmalik.jpaauthorbookdemo.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext( classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTests {
    private iBookRep underTest;

    @Autowired
    public BookRepositoryIntegrationTests(iBookRep underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);
        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();

        Book bookA = TestDataUtil.createTestBookA(author);
        underTest.save(bookA);

        Book bookB = TestDataUtil.createTestBookB(author);
        underTest.save(bookB);

        Book bookC = TestDataUtil.createTestBookC(author);
        underTest.save(bookC);

        Iterable<Book> result = underTest.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
    }
    @Test
    public void testThatBookCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthorA();

        Book book = TestDataUtil.createTestBookA(author);

        underTest.save(book);

        book.setTitle("UPDATED");
        underTest.save(book);

        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);

    }
    @Test
    public void testThatBookCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();

        Book book = TestDataUtil.createTestBookA(author);
        underTest.save(book);

        underTest.deleteById(book.getIsbn());

        Optional<Book> result = underTest.findById(book.getIsbn());
        assertThat(result).isEmpty();
    }
}
