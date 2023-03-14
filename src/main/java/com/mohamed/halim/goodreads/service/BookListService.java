package com.mohamed.halim.goodreads.service;

import com.mohamed.halim.goodreads.model.BookList;
import com.mohamed.halim.goodreads.model.dto.ListDto;
import com.mohamed.halim.goodreads.model.joins.ProfileBookList;
import com.mohamed.halim.goodreads.repository.BookListRepository;
import com.mohamed.halim.goodreads.repository.ListBookRepository;
import com.mohamed.halim.goodreads.repository.ProfileBookListRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.mohamed.halim.goodreads.config.ConfigProperties.PAGE_SIZE;

@Service
@AllArgsConstructor
public class BookListService {
    private BookListRepository bookListRepository;
    private ProfileBookListRepository profileBookListRepository;
    private ListBookRepository listBookRepository;

    public Flux<ListDto> getProfileLists(String username, int page) {
        Flux<BookList> bookListFlux = profileBookListRepository.findByUserId(username, PageRequest.of(page, PAGE_SIZE))
                .flatMap(profileBookList -> bookListRepository.findById(profileBookList.getListId()));
        Flux<Long> bookCount = bookListFlux.flatMap(bookList -> listBookRepository.findByListId(bookList.getId()).count());
        return bookListFlux
                .map(ListDto::fromList)
                .zipWith(bookCount)
                .map(tuple -> {
                    tuple.getT1().setBookCount(tuple.getT2().intValue());
                    return tuple.getT1();
                })
        ;
    }

    public Mono<ProfileBookList> addProfileList(String username, ProfileBookList profileBookList) {
        profileBookList.setUserId(username);
        return profileBookListRepository.save(profileBookList);
    }

    public Mono<Void> deleteProfileLists(String username) {
        return profileBookListRepository.deleteAllByUserId(username);
    }

    public Mono<Void> deleteProfileList(ProfileBookList profileBookList) {
        return bookListRepository.deleteById(profileBookList.getId());
    }
}
