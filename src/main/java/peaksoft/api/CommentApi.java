package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.CommentResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("comments")
@RequiredArgsConstructor
public class CommentApi {

    private final CommentService commentService;

    @PermitAll
    @GetMapping()
    public List<CommentResponse> getAllCommentsByUserId() {
        return commentService.getAllComments();
    }

    @PermitAll
    @PostMapping("/{productId}")
    public SimpleResponse saveComment(
            @PathVariable Long productId,
            @RequestBody CommentRequest commentRequest) {
        return commentService.saveComment(productId, commentRequest);
    }



    @PermitAll
    @PutMapping("/{id}")
    public SimpleResponse updateCommentById(
            @PathVariable Long id,
            @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(id, commentRequest);
    }

    @PermitAll
    @DeleteMapping("/{id}")
    public SimpleResponse deleteCommentById(
            @PathVariable Long id) {
        return commentService.deleteComment(id);
    }


}
