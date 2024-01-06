package com.ai.domain.data.embedding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Embedding {

    private List<BigDecimal> embedding;

    private String content;


}
