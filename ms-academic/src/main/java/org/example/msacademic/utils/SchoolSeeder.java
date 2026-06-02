package org.example.msacademic.utils;

import org.example.msacademic.modules.ies.domain.Ies;
import org.example.msacademic.modules.ies.infra.persistence.IesRepository;
import org.example.msacademic.modules.schools.domain.School;
import org.example.msacademic.modules.schools.infra.persistence.SchoolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SchoolSeeder implements CommandLineRunner {
    private final SchoolRepository schoolRepository;
    private final IesRepository iesRepository;

    public SchoolSeeder(SchoolRepository schoolRepository, IesRepository iesRepository) {
        this.schoolRepository = schoolRepository;
        this.iesRepository = iesRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Ies ucsal = iesRepository.findByName("UCSAL")
                .orElseGet(() -> iesRepository.save(new Ies(
                        "UCSAL",
                        "Av. Pinto de Águiar, 999, Pituaçu, Salvador - BA",
                        "(71)9 9999-9999"
                )));

        List<String> nomesEscolas = List.of(
                "Escola de Educação, Cultura e Humanidades",
                "Escola de Ciências Sociais e Aplicadas",
                "Escola de Engenharias e Ciências Tecnológicas",
                "Escola de Ciências Naturais e da Saúde"
        );

        for (String nome : nomesEscolas) {
            if (!schoolRepository.existsByName(nome)) {
                schoolRepository.save(new School(nome, "A Definir", ucsal));
            }
        }
    }
}
