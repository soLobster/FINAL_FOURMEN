package com.itwill.teamfourmen.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
@Builder
@Table(name = "follows")
@SequenceGenerator(name = "follows_id_seq", sequenceName = "follows_id_seq", allocationSize = 1)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "follows_id_seq")
    @Column(name = "follows_id")
    private Long followsId;

    @Basic(optional = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_user")
    @JsonIgnoreProperties("followings")
    private Member fromUser;

    @Basic(optional = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_user")
    @JsonIgnoreProperties("followers")
    private Member toUser;

    @CreatedDate
    private LocalDateTime createdTime;

}
