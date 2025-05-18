package com.aseds.channelsmicroservice.Mappers;

import com.aseds.channelsmicroservice.models.dto.VideoDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class EmbeddedMapper {
    @JsonProperty("videoDTOList")
    private List<VideoDTO> videoDTOList;
}
