package com.purrnetics.dto;

import java.util.List;

public record SexLinkedVariantOutComeDto(String sex, List<VariantOutcomeDto> outcomes) {}
