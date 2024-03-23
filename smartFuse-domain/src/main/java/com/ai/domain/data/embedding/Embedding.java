package com.ai.domain.data.embedding;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Embedding {

    private double[] embedding;

    private String content;

}
