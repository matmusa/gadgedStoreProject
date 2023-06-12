package peaksoft.service;

import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.CommentResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface CommentService {


    List<CommentResponse> getAllComments(Long userId);

    SimpleResponse saveComment(Long userId,Long productId,CommentRequest commentRequest);

    CommentResponse getCommentById(Long userId, Long commentId);

    SimpleResponse updateComment(Long id,CommentRequest commentRequest);

    SimpleResponse deleteComment(Long id);




}
