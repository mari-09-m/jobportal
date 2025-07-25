package com.mweb.jobportal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    private String email;

    @NotEmpty
    private String password;

    private boolean isActive;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date registrationDate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userTypeId", referencedColumnName = "userTypeId")
    private UsersType usersTypeId;

    // ✅ ADD THIS: Transient field for form binding
    @Transient
    private Integer userTypeId;

    public Users() {}

    public Users(int userId, String email, String password, boolean isActive, Date registrationDate, UsersType usersTypeId) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.registrationDate = registrationDate;
        this.usersTypeId = usersTypeId;
    }

    // Getter and Setter for UsersType
    public UsersType getUsersTypeId() {
        return usersTypeId;
    }

    public void setUsersTypeId(UsersType usersTypeId) {
        this.usersTypeId = usersTypeId;
    }

    // ✅ Getter and Setter for Integer userTypeId
    public Integer getUserTypeId() {
        if (usersTypeId != null) {
            return usersTypeId.getUserTypeId();
        }
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", registrationDate=" + registrationDate +
                ", usersTypeId=" + usersTypeId +
                ", userTypeId=" + userTypeId +
                '}';
    }
}
