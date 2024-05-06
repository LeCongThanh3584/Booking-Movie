package com.example.demo.DTOs.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PaginationResponse<T> {

    private Long totalElement;

    private int totalPage;

    private int pageNumber;

    private int pageSize;

    private List<T> data;

}
