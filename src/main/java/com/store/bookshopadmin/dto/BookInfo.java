package com.store.bookshopadmin.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
public class BookInfo {

    public interface BookListView {};

    public interface BookDetailView extends BookListView {};

    @ApiModelProperty("图书id")
    @JsonView(BookListView.class)
    private Long id;

    @ApiModelProperty("图书名字")
    @JsonView(BookListView.class)
    private String name;

    @JsonView(BookDetailView.class)
    @NotBlank
    private String content;

    @JsonView(BookListView.class)
    private Date publishDate;


}
