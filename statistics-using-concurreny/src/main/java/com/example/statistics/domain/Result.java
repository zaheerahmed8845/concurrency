package com.example.statistics.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result {
    private int count;
    private float mean;
    private float variance;
}
