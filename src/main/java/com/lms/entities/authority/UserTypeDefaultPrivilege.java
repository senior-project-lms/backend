package com.lms.entities.authority;

import com.lms.entities.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

/**
 * Created by umit.kas on 05.12.2017.
 */
@Entity
@Table(schema = "user_type_default_settings")
@Data
public class UserTypeDefaultPrivilege extends BaseEntity {

    @OneToOne
    private UserType userType;

    @ManyToMany
    private List<Privilege> privileges;

}
