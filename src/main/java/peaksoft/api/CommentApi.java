package peaksoft.api;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @GetMapping("/{id}")
    public List<CommentResponse> getAllCommentsByUserId(@PathVariable Long id) {
        return commentService.getAllComments(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{userId}/{productId}")
    public SimpleResponse saveComment(@PathVariable Long userId,
                                      @PathVariable Long productId,
                                      @RequestBody CommentRequest commentRequest) {
        return commentService.saveComment(userId, productId, commentRequest);
    }

    @PermitAll
    @GetMapping("/{userId}/{productId}")
    public CommentResponse getCommentById(
            @PathVariable Long userId,
            @PathVariable Long productId) {
        return commentService.getCommentById(userId, productId);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse updateCommentById(
            @PathVariable Long id,
            @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(id, commentRequest);
    }
@PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public SimpleResponse deleteCommentById(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }


}
