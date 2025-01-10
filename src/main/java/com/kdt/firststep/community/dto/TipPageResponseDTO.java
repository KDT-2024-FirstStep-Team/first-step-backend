package com.kdt.firststep.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipPageResponseDTO {
    private List<TipPostListDTO> content;
    private int currentPage;
    private int totalPages;
    private long totalElements;

}
