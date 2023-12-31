package org.azizmalik.jpaauthorbookdemo.repositories;

import org.azizmalik.jpaauthorbookdemo.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface iBookRep extends CrudRepository<Book, String> {
}
