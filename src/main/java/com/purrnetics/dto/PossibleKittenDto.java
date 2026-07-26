package com.purrnetics.dto;

import java.util.List;

public record PossibleKittenDto(String sex, List<ExpressedTraitDto> expressedTraits, Double probability) {}
