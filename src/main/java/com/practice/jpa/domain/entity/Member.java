package com.practice.jpa.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "members")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Length(max = 20)
    private String name;

    @NotNull
    @Email
    private String email;

    @NotNull
    private Boolean deleted = Boolean.FALSE;

    @Builder
    public Member(final String name, final String email) {
        this.name = name;
        this.email = email;
    }

    public void toggleDeleted() {
        this.deleted = !this.deleted;
    }

    public void updateName(final String newName) {
        this.name = newName;
    }
}
