package com.kdt.firststep.community.service;

import com.kdt.firststep.community.dto.CommentDTO;

public interface LifePostCommentService {
    void postComment(Integer postId, CommentDTO commentDTO);
    void updateComment(Integer commentId, Integer postId, CommentDTO commentDTO);
    void deleteComment(Integer commentId, Integer postId);
} 