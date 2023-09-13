package com.lingxi.websocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreUmpireMessage {

    private Integer id;

    private Integer umpireId;

    private Integer teamProjectId;

    private Integer projectId;

    private String name;

    private String score;

    private String members;
}