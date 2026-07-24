package com.purrnetics.dto;

import java.util.List;

public record BreedingResponseDto(String motherName, String fatherName, List<GeneResultDto> genes, List<TraitResultDto> traits, List<SexLinkedTraitResultDto> sexLinkedTraits) {}
