package com.kdt.firststep.community.service;

import com.kdt.firststep.community.dto.CommentDTO;

public interface TipPostCommentService {
    void postComment(Integer postId, CommentDTO commentDTO);
    void updateComment(Integer postId, Integer commentId, CommentDTO commentDTO);
    void deleteComment(Integer commentId, Integer postId, CommentDTO commentDTO);

}
