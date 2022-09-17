package com.ssw.fssw.controller.coummuity;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommunityForm {

    private String title;

    private String content;

    private String category;

    private int board_num = 1;

    private LocalDateTime localDateTime;
}
