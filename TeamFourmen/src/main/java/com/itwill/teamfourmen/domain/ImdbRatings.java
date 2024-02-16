package com.itwill.teamfourmen.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "IMDB_RATINGS")
public class ImdbRatings {

    @Id
    @Basic(optional = false)
    @Column(name = "tconst")
    private String imdbId;

    @Basic(optional = false)
    private double averagerating;

    @Basic(optional = false)
    private int numvotes;

}
