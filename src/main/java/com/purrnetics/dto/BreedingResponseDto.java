package com.purrnetics.dto;

import java.util.List;

public record BreedingResponseDto(String motherId, String fatherId, List<GeneResultDto> genes, List<TraitResultDto> traits) {}
