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
@SQLDelete(sql = "UPDATE EventRegistration SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Table(name = "event_registration")
public class EventRegistration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "uuid", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uuid", nullable = false)
    private Users user;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date registrationDate;

    @Column(length = 20)
    private String status; // REGISTERED, CONFIRMED, CANCELLED, ATTENDED, NO_SHOW

    @Column(length = 500)
    private String notes; // Special requirements or notes

    @Column
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date checkInDate; // When user actually attended

    @Column(length = 100)
    private String confirmationCode; // Unique code for event access

    @Column(nullable = false)
    private Boolean emailNotificationSent;

    @Column(nullable = false)
    private Boolean reminderSent;

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
        this.setRegistrationDate(new Date());
        this.setEmailNotificationSent(false);
        this.setReminderSent(false);
        // Generate confirmation code
        this.setConfirmationCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventRegistration that = (EventRegistration) o;
        return deleted == that.deleted && 
               Objects.equals(id, that.id) && 
               Objects.equals(uuid, that.uuid) && 
               Objects.equals(event, that.event) && 
               Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, event, user, deleted);
    }
}