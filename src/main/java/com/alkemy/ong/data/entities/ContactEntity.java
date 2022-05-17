package com.alkemy.ong.data.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "contacts")
@SQLDelete(sql = "UPDATE contacts SET is_deleted = true, updated_at = now() WHERE id=?")
@Where(clause = "is_deleted = false")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String message;
    @Column(name="is_deleted", nullable = false)
    @Builder.Default
    private boolean isDeleted = Boolean.FALSE;
    @CreationTimestamp
    @Column(name="created_at", columnDefinition = "TIMESTAMP", nullable = false)
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name="updated_at", columnDefinition = "TIMESTAMP", nullable = false)
    @DateTimeFormat(pattern = "yyyy/MM/dd hh:mm:ss")
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object obj){
        ContactEntity entity = (ContactEntity) obj;
        return this.id==entity.getId();
    }
}
