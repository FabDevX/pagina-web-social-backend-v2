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
@SQLDelete(sql = "UPDATE EventInvitation SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Table(name = "event_invitation")
public class EventInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "uuid", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "invited_user_id", referencedColumnName = "uuid", nullable = false)
    private Users invitedUser;

    @ManyToOne
    @JoinColumn(name = "inviter_user_id", referencedColumnName = "uuid", nullable = false)
    private Users inviterUser; // Who sent the invitation

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date invitationDate;

    @Column(length = 20)
    private String status; // PENDING, ACCEPTED, DECLINED, EXPIRED

    @Column
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date responseDate; // When user responded to invitation

    @Column(length = 100)
    private String invitationToken; // Unique token for accessing private event

    @Column(length = 500)
    private String personalMessage; // Optional personal message from inviter

    @Column
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date expirationDate; // When invitation expires

    @Column(nullable = false)
    private Boolean emailSent; // Whether invitation email was sent

    @Column
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date emailSentDate; // When invitation email was sent

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
        this.setInvitationDate(new Date());
        this.setStatus("PENDING");
        this.setEmailSent(false);
        // Generate unique invitation token
        this.setInvitationToken(UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventInvitation that = (EventInvitation) o;
        return deleted == that.deleted && 
               Objects.equals(id, that.id) && 
               Objects.equals(uuid, that.uuid) && 
               Objects.equals(event, that.event) && 
               Objects.equals(invitedUser, that.invitedUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, event, invitedUser, deleted);
    }
}