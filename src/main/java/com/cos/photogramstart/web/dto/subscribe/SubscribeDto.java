package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class SubscribeDto {
    private int id;
    private String username;
    private String profileImageUrl;
    private Integer subscribeState;
    private Integer equalUserState;

    public SubscribeDto() {
    }

    public SubscribeDto(int id, String username, String profileImageUrl, Integer subscribeState, Integer equalUserState) {
        this.id = id;
        this.username = username;
        this.profileImageUrl = profileImageUrl;
        this.subscribeState = subscribeState;
        this.equalUserState = equalUserState;
    }
}
