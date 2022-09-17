package com.ssw.fssw.repository;

import com.ssw.fssw.domain.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommunityApiRepository extends JpaRepository<Community, Long> {


    Page<Community> findByTitleContainsAndContentsContainsAndCategoryContains(String title, String content,String category,Pageable pageable);


}
