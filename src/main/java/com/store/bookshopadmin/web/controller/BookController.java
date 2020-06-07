package com.store.bookshopadmin.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.store.bookshopadmin.dto.BookCondition;
import com.store.bookshopadmin.dto.BookInfo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@RestController
@RequestMapping("/book")
public class BookController {

    private ConcurrentMap<Long, DeferredResult<BookInfo>> map = new ConcurrentHashMap<>();

    //@RequestMapping(method = RequestMethod.GET)
    @GetMapping
    @JsonView(BookInfo.BookListView.class)
    public List<BookInfo> query(@RequestParam(value = "name", defaultValue = "hello world", required = true) String bookName) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication != null) {
            System.out.println(authentication.getPrincipal());
        }

        System.out.println(bookName);
        List<BookInfo> books = new ArrayList<BookInfo>();
        books.add(new BookInfo());
        books.add(new BookInfo());
        books.add(new BookInfo());
        return books;
    }


    //@RequestMapping(value = "/books", method = RequestMethod.GET)
    @GetMapping("/books")
    @JsonView(BookInfo.BookDetailView.class)
    public List<BookInfo> query1(BookCondition bookCondition, @PageableDefault(size = 5) Pageable pageable) {
        System.out.println(bookCondition.getName());
        System.out.println(bookCondition.getCategoryId());

        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getPageNumber());

        System.out.println(pageable.getSort());

        List<BookInfo> books = new ArrayList<BookInfo>();
        books.add(new BookInfo());
        books.add(new BookInfo());
        books.add(new BookInfo());
        return books;
    }

    //@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @GetMapping("/{id:\\d}")
    public BookInfo getInfo(@PathVariable Long id) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        if (authentication != null) {
            System.out.println(authentication.getPrincipal());
        }

        throw new RuntimeException("ex");
//        BookInfo bookInfo = new BookInfo();
//        bookInfo.setName("战争与和平");
//        return bookInfo;
    }

    /**
     * 这里不会缩短请求时间，但是可以加大请求吞吐量
     * @param id
     * @return
     */
    @GetMapping("/callable/{id:\\d}")
    public Callable<BookInfo> getInfoCallable(@PathVariable Long id) {

        long start = new Date().getTime();
        System.out.println(Thread.currentThread().getName() + " start");
        Callable<BookInfo> result = () -> {
            System.out.println(Thread.currentThread().getName() + " thread start");
            Thread.sleep(1000);
            BookInfo bookInfo = new BookInfo();
            bookInfo.setName("战争与和平");
            bookInfo.setPublishDate(new Date());
            System.out.println(Thread.currentThread().getName() + " thread elapsed time: ");
            return bookInfo;
        };

        System.out.println(Thread.currentThread().getName() + "back elapsed time:" + (new Date().getTime() - start));
        return result;
    }

    @GetMapping("/cookie/{id:\\d}")
    public BookInfo getInfoAboutCookie(@ApiParam("图书id") @PathVariable Long id,
                                       @CookieValue String token,
                                       @RequestHeader String auth) {

        System.out.println(token);
        System.out.println(auth);
        BookInfo bookInfo = new BookInfo();
        bookInfo.setName("战争与和平");
        return bookInfo;
    }
    @PostMapping
    public BookInfo create(@Valid @RequestBody BookInfo info, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(objectError -> System.out.println(objectError.getDefaultMessage()));
        }
        System.out.println("id is " + info.getId());
        System.out.println("name is " + info.getName());
        System.out.println("content is " + info.getContent());
        System.out.println("publishDate is " + info.getPublishDate());
        info.setId(1L);
        return info;
    }

    @PutMapping("/{id}")
    @ApiOperation("更新图书信息")
    public BookInfo update(@Valid @RequestBody BookInfo info, BindingResult result) {
        if (result.hasErrors()) {
            result.getAllErrors().stream().forEach(objectError -> System.out.println(objectError.getDefaultMessage()));
        }
        System.out.println("id is " + info.getId());
        System.out.println("name is " + info.getName());
        System.out.println("content is " + info.getContent());
        System.out.println("publishDate is " + info.getPublishDate());
        info.setId(1L);
        return info;
    }

    //@RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        System.out.println(id);
    }

    @GetMapping("/deferred/{id:\\d}")
    public DeferredResult<BookInfo> getDeferredResult(@PathVariable Long id) {

        DeferredResult<BookInfo> result = new DeferredResult<>();
        map.put(id, result);

        return result;
    }

    /**
     * 有监听到消息到时候，上面到result才会真正到返回。
     * 但是在这段时间内，主线程还可以处理其他到请求
     * @param bookInfo
     */
    private void listenMessage(BookInfo bookInfo) {
        map.get(bookInfo.getId()).setResult(bookInfo);
    }


}
