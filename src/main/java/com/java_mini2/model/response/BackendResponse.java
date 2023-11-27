package com.java_mini2.model.response;

import com.java_mini2.model.entity.User;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BackendResponse {
    private List<User> data;
    private PageInfo pageInfo;
}
