package com.othree.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ImageNFT")
public class ImageNFT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NftID") // specify the exact column name
    private Integer nftId;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Price", nullable = false)
    private float price;
    
    @Column(name = "key_wallet", nullable = false)
    private String keyWallet;

    @Lob
    @Column(name = "Image")
    private String image; // Use @Lob for binary data

    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "Username", referencedColumnName = "Username", insertable = false, updatable = false)
    private Account account;
}
