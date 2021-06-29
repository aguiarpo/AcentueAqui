package com.pac.acentueaqui.repository;

import com.pac.acentueaqui.models.questions.Accentuation;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AccentuatuionRepository extends PagingAndSortingRepository<Accentuation, Long> {
    Accentuation findByName(String name);
    List<Accentuation> findAll();
    Accentuation findByCode(Long code);
}
