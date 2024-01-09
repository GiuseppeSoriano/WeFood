package it.unipi.lsmsdb.wefood.controller;

import it.unipi.lsmsdb.wefood.apidto.CommentRequestDTO;
import it.unipi.lsmsdb.wefood.apidto.StarRankingRequestDTO;
import it.unipi.lsmsdb.wefood.service.CommentService;
import it.unipi.lsmsdb.wefood.service.StarRankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/starranking")
public class StarRankingController {
    private final StarRankingService starRankingService;

    public StarRankingController() {
        this.starRankingService = new StarRankingService();
    }

    @PostMapping("/create")
    public ResponseEntity<Boolean> votePost(@RequestBody StarRankingRequestDTO request) {
        return ResponseEntity.ok(starRankingService.votePost(request.getUser(), request.getStarRanking(), request.getPostDTO()));
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteVote(@RequestBody StarRankingRequestDTO request) {
        return ResponseEntity.ok(starRankingService.deleteVote(request.getUser(), request.getStarRanking(), request.getPostDTO()));
    }
}
