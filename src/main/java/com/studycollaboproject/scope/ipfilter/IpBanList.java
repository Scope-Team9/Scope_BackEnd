package com.studycollaboproject.scope.ipfilter;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor

public class IpBanList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ip_Id")
    private Long id;

    @Column
    private String ip;

    public IpBanList(String ip) {
        this.ip = ip;
    }

}
