package com.itwill.teamfourmen.dto.person;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

// social links 에서 사용.
@Data
public class ExternalIDsDto {

    private int id;
    @JsonProperty("freebase_mid")
    private String freebaseMid;
    @JsonProperty("freebase_id")
    private String freebaseId;
    @JsonProperty("imdb_id")
    private String imdbId;
    @JsonProperty("tvrage_id")
    private String tvrageId;
    @JsonProperty("wikidata_id")
    private String wikidataId;
    @JsonProperty("facebook_id")
    private String facebookId;
    @JsonProperty("instagram_id")
    private String instagramId;
    @JsonProperty("tiktok_id")
    private String tiktokId;
    @JsonProperty("twitter_id")
    private String twitterId;
    @JsonProperty("youtube_id")
    private String youtubeId;

}
