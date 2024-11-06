package com.kdt.firststep.community.service;

import com.kdt.firststep.community.dto.CommentDTO;

public interface TipPostCommentService {
    void postComment(int postId, CommentDTO commentDTO);
}
