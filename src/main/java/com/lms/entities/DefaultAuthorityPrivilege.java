package com.lms.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Data
@Table(name = "default_authority_privilege")
public class DefaultAuthorityPrivilege extends BaseEntity {


    @OneToOne
    private Authority authority;

    @OneToMany(mappedBy = "defaultAuthorityPrivilege")
    private List<Privilege> privileges;


}
