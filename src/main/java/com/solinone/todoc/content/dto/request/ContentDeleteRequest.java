package com.solinone.todoc.content.dto.request;


import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ContentDeleteRequest {
    private List<Long> contentIds;
}
