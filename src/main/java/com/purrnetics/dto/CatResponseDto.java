package com.purrnetics.dto;

import java.util.List;

public record CatResponseDto(String name, String sex, List<ExpressedTraitDto> traits) {}
