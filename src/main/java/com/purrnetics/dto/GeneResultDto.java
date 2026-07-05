package com.purrnetics.dto;

import java.util.List;

public record GeneResultDto(String geneName, List<AlleleOutcomeDto> alleleOutcomes) {}
