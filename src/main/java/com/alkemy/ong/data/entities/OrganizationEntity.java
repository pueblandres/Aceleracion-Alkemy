package com.alkemy.ong.data.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "organizations")
@SQLDelete(sql = "UPDATE organizations SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String image;
    private String address;
    private Integer phone;
    private String email;
    private String welcomeText;
    private String aboutUsText;
    private String facebook;
    private String linkedin;
    private String instagram;

    @OneToMany(mappedBy = "organization")
    private List<SlidesEntity> slides;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted = Boolean.FALSE;
}
