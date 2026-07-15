package com.purrnetics.dto;

import java.util.List;

public record CatResponseDto(String catId, String name, String sex, List<InheritedAllelesDto> inheritedAlleles, List<ExpressedTraitDto> expressedTraits) {}
