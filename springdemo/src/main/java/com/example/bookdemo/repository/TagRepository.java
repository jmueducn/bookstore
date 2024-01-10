package com.example.bookdemo.repository;

import com.example.bookdemo.entity.Tag;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TagRepository  extends Neo4jRepository<Tag, Long> {
    Tag findByName(String name);

    @Query("MATCH (tag:Tag)-[:SUBCATEGORY*0..2]-(relatedTag:Tag) " +
            "WHERE tag.name = $tagName  " +
            "RETURN DISTINCT relatedTag")
    List<Tag> findRelatedTags(@Param("tagName") String tagName);
}
