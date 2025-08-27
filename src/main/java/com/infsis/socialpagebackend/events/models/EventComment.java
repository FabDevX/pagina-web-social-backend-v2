package com.infsis.socialpagebackend.events.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infsis.socialpagebackend.authentication.models.Users;
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
@SQLDelete(sql = "UPDATE EventComment SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Table(name = "event_comment")
public class EventComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "uuid", nullable = false)
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "uuid", nullable = false)
    private Users user;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date commentDate;

    @Column(nullable = false)
    private boolean moderated;

    @Column(length = 20)
    private String status; // PENDING, APPROVED, REJECTED, HIDDEN

    @Column(length = 500)
    private String moderatorNotes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moderator_id", referencedColumnName = "uuid")
    private Users moderator;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date moderatedDate;

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
        this.setCommentDate(new Date());
        this.setModerated(false);
        this.setStatus("PENDING");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventComment that = (EventComment) o;
        return deleted == that.deleted && 
               moderated == that.moderated && 
               Objects.equals(id, that.id) && 
               Objects.equals(uuid, that.uuid) && 
               Objects.equals(event, that.event) && 
               Objects.equals(user, that.user) && 
               Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, event, user, content, deleted, moderated);
    }
}