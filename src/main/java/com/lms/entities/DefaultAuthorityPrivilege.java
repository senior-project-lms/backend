package com.lms.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "default_authority_privileges")
public class DefaultAuthorityPrivilege extends BaseEntity {


    @OneToOne
    private Authority authority;

    @ManyToMany
//    @JoinTable(
//            name = "default_authority_privileges",
//            joinColumns = @JoinColumn(name = "default_authority_privilege_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private List<Privilege> privileges;


}
