package com.othree.entity;



import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "Comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "nftId")
    private ImageNFT imageNFT;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdate = new Date();

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Account account;

    // Getters and Setters
}
