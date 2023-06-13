package peaksoft.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.config.JwtService;
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
    private final JwtService jwtService;


    @Override
    public List<CommentResponse> getAllComments() {
        User user = jwtService.getAuthentication();
        return commentRepository.getAllComments(user.getId());
    }


    @Override
    public SimpleResponse saveComment(Long productId, CommentRequest commentRequest) {
        User user = jwtService.getAuthentication();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new UsernameNotFoundException("Product with id " + productId + " doesn't exist"));
        Comment comment = new Comment();
        comment.setComment(commentRequest.comment());
        comment.setCreatedDate(ZonedDateTime.now());
        comment.setUser(user);

        List<Product> products = comment.getProducts();
        if (products == null) {
            products = new ArrayList<>();
            comment.setProducts(products);
        }
        products.add(product);

        product.setComment(comment);

        userRepository.save(user);
        commentRepository.save(comment);
        productRepository.save(product);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Comment with id %s successfully added user comment with id %s", comment.getId(), user.getId()))
                .build();
    }


    @Override
    public SimpleResponse deleteComment(Long commentId) {
        User user = jwtService.getAuthentication();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("Comment with id " + commentId + " doesn't exist"));


        if (comment.getUser() == null || !comment.getUser().equals(user)) {
            throw new BadCredentialsException("You are not authorized to delete this comment");
        }
        List<Comment> comments = user.getComments();
        comments.remove(comment);
        user.setComments(comments);
        Product product = comment.getProducts().get(0);
        product.setComment(null);
        commentRepository.delete(comment);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Comment successfully deleted")
                .build();
    }


    @Override
    public SimpleResponse updateComment(Long id, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Comment with id  " + id + "  doesn't exist "));
        User user = jwtService.getAuthentication();
        for (Comment c : user.getComments()
        ) {
            if (c.getId().equals(comment.getId())) {
                comment.setComment(commentRequest.comment());
                comment.setCreatedDate(ZonedDateTime.now());
                commentRepository.save(comment);
                return SimpleResponse.builder()
                        .httpStatus(HttpStatus.OK)
                        .message(String.format("Comment with id %s successfully updated !", id))
                        .build();
            }

        }
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("Comment with id %s not found !", id))
                .build();
    }


    @Override
    public List<CommentResponse> getAllCommentsById(Long id) {
        User user = jwtService.getAuthentication();
        return commentRepository.getAllComments(user.getId());

    }
}


