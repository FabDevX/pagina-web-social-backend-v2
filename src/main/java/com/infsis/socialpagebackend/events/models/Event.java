package com.infsis.socialpagebackend.events.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.infsis.socialpagebackend.authentication.models.Users;
import com.infsis.socialpagebackend.institutions.models.Institution;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Event SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(updatable = false, nullable = false, unique = true, length = 36)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "uuid", nullable = false)
    private Institution institution;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uuid", nullable = false)
    private Users creator;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "uuid", nullable = false)
    private EventCategory category;

    @Column(length = 500)
    private String location; // Simple text field for location

    @Column(nullable = false, length = 200)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date startDate;

    @Column
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private Date endDate;

    @Column(nullable = false)
    private Integer maxCapacity;

    @Column(nullable = false)
    private Boolean isPublic;

    @Column(nullable = false)
    private Boolean requiresRegistration;

    @Column(length = 20)
    private String status; // DRAFT, PUBLISHED, CANCELLED, COMPLETED

    @Column(length = 100)
    private String coverImagePath;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventRegistration> registrations = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventMedia> media = new ArrayList<>();

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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return deleted == event.deleted && 
               Objects.equals(id, event.id) && 
               Objects.equals(uuid, event.uuid) && 
               Objects.equals(title, event.title) && 
               Objects.equals(startDate, event.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uuid, title, startDate, deleted);
    }
}