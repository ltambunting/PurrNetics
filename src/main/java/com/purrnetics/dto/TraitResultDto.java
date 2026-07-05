package com.purrnetics.dto;

import java.util.List;

public record TraitResultDto(String traitName, List<VariantOutcomeDto> variants) {}
