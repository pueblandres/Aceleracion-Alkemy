package com.alkemy.ong.data.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "testimonials")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE testimonial SET is_deleted = true, updated_at=now() WHERE id=?")
@Where(clause = "is_deleted=false")
public class TestimonialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "image", nullable = false)
    private String image;
    @Column(name = "content", nullable = false)
    private String content;
    @CreationTimestamp
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;
    @Column(name = "isDeleted")
    private boolean isDeleted = false;

    @Override
    public boolean equals(Object obj) {
        TestimonialEntity entity = (TestimonialEntity) obj;
        return Objects.equals(this.id, entity.getId());
    }
}
