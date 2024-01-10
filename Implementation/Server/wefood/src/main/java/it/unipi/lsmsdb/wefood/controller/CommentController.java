package it.unipi.lsmsdb.wefood.controller;

import it.unipi.lsmsdb.wefood.apidto.CommentRequestDTO;
import it.unipi.lsmsdb.wefood.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comment")
public class CommentController {
    
    private final CommentService commentService;

    public CommentController() {
        this.commentService = new CommentService();
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> commentPost(@RequestBody CommentRequestDTO request) {
        return ResponseEntity.ok(commentService.commentPost(request.getUser(), request.getComment(), request.getPostDTO()));
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateComment(@RequestBody CommentRequestDTO request) {
        return ResponseEntity.ok(commentService.updateComment(request.getUser(), request.getComment(), request.getPostDTO()));
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteComment(@RequestBody CommentRequestDTO request) {
        return ResponseEntity.ok(commentService.updateComment(request.getUser(), request.getComment(), request.getPostDTO()));
    }
    
}
