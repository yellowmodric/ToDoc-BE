package com.solinone.todoc.content.infrastructure;

import com.solinone.todoc.content.domain.Content;
import com.solinone.todoc.content.dto.response.MyLatestContentResponse;
import com.solinone.todoc.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContentRepository extends JpaRepository<Content, Long> {

    int countByBoardBoardId(Long boardId);
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

    @Query(value = """
    select c.*
    from contents c
    join boards b on c.board_id = b.board_id
    where b.place_id = :placeId
    order by RANDOM()
    limit 2
    """, nativeQuery = true)
    List<Content> findRandomContents(
            @Param("placeId") Long placeId
    );

    List<Content> findByBoard_Place_PlaceIdAndUser_UserIdOrderByCreatedAtDesc(
            Long placeId,
            Long userId
    );

    List<Content> findAllByBoardBoardId(Long boardId);

    //마이페이지
    @EntityGraph(attributePaths = {"font"})
    List<Content> findByUser_UserIdOrderByCreatedAtDesc(Long userId);

}
