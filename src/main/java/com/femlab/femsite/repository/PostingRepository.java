package com.femlab.femsite.repository;

import java.util.List;

import com.femlab.femsite.domain.Postings;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostingRepository extends JpaRepository<Postings, Integer> {
    Postings findById(int posId);
    List<Postings> findByTitleLike(String word);
}