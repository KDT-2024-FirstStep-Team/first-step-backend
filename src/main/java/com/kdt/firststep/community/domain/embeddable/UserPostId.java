package com.kdt.firststep.community.domain.embeddable;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class UserPostId implements Serializable {
    private Integer userId;
    private Integer postId;
}
