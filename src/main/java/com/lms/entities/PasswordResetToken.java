package com.lms.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/*
When a password reset is triggered
the token is gonna created and the link that containing this token will be emailed to the user.
 */

@Entity
@Data
public class PasswordResetToken extends BaseEntity {
    private static final int EXPIRATION = 60 * 24; //1day for the end


    @Column(nullable = false, unique = true)
    private String token;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    private User user;

    @Column(nullable = false)
    private Date expiresAt;


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((expiresAt == null) ? 0 : expiresAt.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PasswordResetToken other = (PasswordResetToken) obj;
        if (expiresAt == null) {
            if (other.expiresAt != null) {
                return false;
            }
        } else if (!expiresAt.equals(other.expiresAt)) {
            return false;
        }
        if (token == null) {
            if (other.token != null) {
                return false;
            }
        } else if (!token.equals(other.token)) {
            return false;
        }
        if (user == null) {
            if (other.user != null) {
                return false;
            }
        } else if (!user.equals(other.user)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Token [String=").append(token).append("]").append("[Expires").append(expiresAt).append("]");
        return builder.toString();
    }

    public void generateExpiredDate(final int expiryTimeInMinutes) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(new Date().getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        expiresAt = new Date(cal.getTime().getTime());
    }


}

