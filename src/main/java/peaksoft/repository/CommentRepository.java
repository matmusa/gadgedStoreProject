package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import peaksoft.dto.response.CommentResponse;
import peaksoft.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("select new peaksoft.dto.response.CommentResponse(" +
            "c.id,c.comment,c.createdDate)from Comment  c join c.user cu where cu.id=:userId")
    List<CommentResponse> getAllComments(@Param("userId") Long userId);

    @Query("select new peaksoft.dto.response.CommentResponse(" +
            "c.id,c.comment,c.createdDate)from Comment  c join c.user cu where cu.id=:userId and c.id=:commentId")
    List<CommentResponse> getAllCommentsById(@Param("commentId") Long commentId);


}
