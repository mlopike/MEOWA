package com.bsac.meowa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsDataResponse {

    private String status;
    private List<Article> results;
    @JsonProperty("nextPage")
    private String nextPage;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Article {
        @JsonProperty("article_id")
        private String articleId;
        private String title;
        private String description;
        private String content;
        @JsonProperty("pubDate")
        private String pubDate;
        @JsonProperty("image_url")
        private String imageUrl;
        @JsonProperty("source_id")
        private String sourceId;
        @JsonProperty("source_url")
        private String sourceUrl;
        @JsonProperty("source_icon")
        private String sourceIcon;
        private List<String> country;
        private String link;
        private List<String> category;
    }
}