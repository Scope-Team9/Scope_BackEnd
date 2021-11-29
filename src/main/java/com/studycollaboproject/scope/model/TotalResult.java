package com.studycollaboproject.scope.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
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

    public TotalResult(String userType, String memberType){
        this.userType = userType;
        this.memberType = memberType;
        this.result = 0L;
    }

    public void setResult(Long result){
        this.result = result;
    }

}
