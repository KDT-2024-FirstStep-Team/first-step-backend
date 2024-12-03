package com.kdt.firststep.counselor.dto.request;

import lombok.Getter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
public class ReviewUpdateRequestDto {
    private Integer rating;
    private String content;
}
