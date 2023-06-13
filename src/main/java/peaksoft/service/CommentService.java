package peaksoft.service;

import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.CommentResponse;
import peaksoft.dto.response.SimpleResponse;

import java.util.List;

public interface CommentService {


    List<CommentResponse> getAllComments();

    SimpleResponse saveComment(Long productId,CommentRequest commentRequest);

    SimpleResponse updateComment(Long commentId,CommentRequest commentRequest);

    SimpleResponse deleteComment(Long id);
    List<CommentResponse> getAllCommentsById(Long id);




}
