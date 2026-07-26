package com.purrnetics.config;

import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.purrnetics.factory.GeneFactory;
import com.purrnetics.factory.PresetCatFactory;
import com.purrnetics.mapper.BreedingMapper;
import com.purrnetics.mapper.CatMapper;
import com.purrnetics.mapper.PossibleKittenMapper;

@Configuration
public class AppConfig {
    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public GeneFactory geneFactory() {
        return new GeneFactory();
    }

    @Bean
    public PresetCatFactory presetCatFactory(GeneFactory geneFactory) {
        return new PresetCatFactory(geneFactory);
    }

    @Bean
    public BreedingMapper breedingMapper() {
        return new BreedingMapper();
    }

    @Bean
    public CatMapper catMapper() {
        return new CatMapper();
    }

    @Bean
    public PossibleKittenMapper possibleKittenMapper() {
        return new PossibleKittenMapper();
    }
}
