package com.studycollaboproject.scope.model;

import lombok.Getter;
import javax.persistence.*;

@Getter
@Entity
public class TotalResult {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "totalResult_id")
    private Long id;

    @Column(nullable = false)
    private String userType;

    @Column(nullable = false)
    private String memberType;

    @Column(nullable = false)
    private Long result;

    public TotalResult(){
        this.result = 0L;
    }

    public TotalResult(String s, String s1) {
        this.result = 0L;
        this.userType = s;
        this.memberType = s1;
    }

    public void addrecommended(){
        result += 1L;
    }
}
