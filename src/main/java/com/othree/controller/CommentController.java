package com.othree.controller;

import com.othree.entity.Comment;
import com.othree.service.CommentService;
import com.othree.service.ImageNFTService;
import com.othree.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private ImageNFTService imageNFTService;

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String comments(Model model) {
        List<Comment> comments = commentService.findAll();
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new Comment()); // For creating a new comment
        model.addAttribute("imageNFTs", imageNFTService.findAll());
        model.addAttribute("accounts", accountService.findAll());
        return "Othree/comments";
    }

    @PostMapping("/create")
    public String createComment(@ModelAttribute("comment") Comment comment) {
        commentService.save(comment);
        return "redirect:/comments";
    }
}
