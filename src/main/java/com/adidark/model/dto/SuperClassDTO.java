package com.adidark.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuperClassDTO<T> {
    private int totalPage;
    private int currentPage;
    private String searchValue;
    private List<T> items;
}
