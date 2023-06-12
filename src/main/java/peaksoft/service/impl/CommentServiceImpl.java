package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.dto.request.CommentRequest;
import peaksoft.dto.response.CommentResponse;
import peaksoft.dto.response.SimpleResponse;
import peaksoft.entity.Comment;
import peaksoft.entity.Product;
import peaksoft.entity.User;
import peaksoft.repository.CommentRepository;
import peaksoft.repository.ProductRepository;
import peaksoft.repository.UserRepository;
import peaksoft.service.CommentService;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    public List<CommentResponse> getAllComments(Long userId) {

        return commentRepository.getAllComments(userId);
    }

    @Override
    public SimpleResponse saveComment(Long userId, Long productId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId).
                orElseThrow(() -> new UsernameNotFoundException("User with id  " + userId + "  doesn't exist "));
        Product product=productRepository.findById(productId)
                .orElseThrow(()->new UsernameNotFoundException("User with id  " + productId + "  doesn't exist "));
        Comment comment = new Comment();
        List<Product>products=new ArrayList<>();
        products.add(product);
        comment.setComment(commentRequest.comment());
        comment.setCreatedDate(ZonedDateTime.now());
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);
        user.setComments(comments);
        comment.setUser(user);
        product.setComment(comment);
        comment.setProducts(products);
        userRepository.save(user);
        commentRepository.save(comment);
        productRepository.save(product);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Comment with id %s successfully added user comment with  id %s ", user.getId(), comment.getId()))
                .build();
    }

    @Override
    public CommentResponse getCommentById(Long userId, Long commentId) {
        return commentRepository.getAllCommentsById(userId,commentId);
    }

    @Override
    public SimpleResponse updateComment(Long id, CommentRequest commentRequest) {
        Comment comment=commentRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("Comment with id  " + id + "  doesn't exist "));
       comment.setComment(commentRequest.comment());
       comment.setCreatedDate(ZonedDateTime.now());
       commentRepository.save(comment);
       return SimpleResponse.builder()
               .httpStatus(HttpStatus.OK)
               .message(String.format("Comment with id %s successfully updated !",id))
               .build();
    }

    @Override
    public SimpleResponse deleteComment(Long id) {
        Comment comment=commentRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("Comment with id  " + id + "  doesn't exist "));
        commentRepository.delete(comment);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Comment with id %s successfully deleted !",id))
                .build();
    }
}
