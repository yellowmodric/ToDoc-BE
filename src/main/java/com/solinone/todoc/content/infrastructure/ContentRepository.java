package com.solinone.todoc.content.infrastructure;

import com.solinone.todoc.content.domain.Content;
import com.solinone.todoc.content.dto.response.MyLatestContentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    @Query("""
    select new com.solinone.todoc.content.dto.response.MyLatestContentResponse(
        c.contentId,
        b.place.placeId,
        c.content,
        c.createdAt
        )
    from Content c
    join c.board b
    where c.user.userId = :userId
        and b.place.placeId in :placeIds
        and c.createdAt = (
            select max(c2.createdAt)
            from Content c2
            join c2.board b2
            where c2.user.userId = :userId
                and b2.place.placeId = b.place.placeId
            )
    """)
    List<MyLatestContentResponse> findLatestUserAndPlace(
            @Param("userId") Long userId,
            @Param("placeIds") List<Long> placeIds
    );
}
