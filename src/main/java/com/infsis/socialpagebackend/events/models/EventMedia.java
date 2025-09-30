package com.infsis.socialpagebackend.events.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE EventMedia SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Table(name = "event_media")
public class EventMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "uuid", nullable = false)
    private Event event;

    @Column(nullable = false, length = 150)
    private String fileName;

    @Column(nullable = false, length = 200)
    private String filePath;

    @Column(nullable = false, length = 50)
    private String fileType; // IMAGE, VIDEO, DOCUMENT, AUDIO

    @Column(nullable = false, length = 50)
    private String mimeType; // image/jpeg, video/mp4, etc.

    @Column(nullable = false)
    private Long fileSize; // Size in bytes

    @Column(nullable = false)
    private Integer displayOrder; // Order for display in gallery

    @Column(length = 500)
    private String description;

    @Column(length = 200)
    private String altText; // For accessibility

    @Column(nullable = false)
    private Boolean isPublic; // Whether media is publicly viewable

    @Column(nullable = false)
    private Boolean isCover; // Whether this is the cover image

    @CreatedDate
    @Column(updatable = false)
    private Date createdDate;

    @LastModifiedDate
    @Column(updatable = false)
    private Date lastModifiedDate;

    @Column(nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean deleted;

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
        if (this.isPublic == null) {
            this.setIsPublic(true);
        }
        if (this.isCover == null) {
            this.setIsCover(false);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventMedia that = (EventMedia) o;
        return deleted == that.deleted && 
               Objects.equals(id, that.id) && 
               Objects.equals(uuid, that.uuid) && 
               Objects.equals(event, that.event) && 
               Objects.equals(fileName, that.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, event, fileName, deleted);
    }
}