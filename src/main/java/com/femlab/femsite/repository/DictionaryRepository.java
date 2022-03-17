package com.femlab.femsite.repository;

import java.util.List;

import com.femlab.femsite.domain.Dictionary;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DictionaryRepository extends JpaRepository<Dictionary, Integer> {
    List<Dictionary> findByWordLike(String word);
}
