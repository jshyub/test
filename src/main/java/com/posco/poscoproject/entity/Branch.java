package com.posco.poscoproject.entity;

import com.posco.poscoproject.dto.BranchDTO;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.enumtype.BranchStatus;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Branch extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String phone;

    @OneToMany(mappedBy = "branch")
    private List<OrderBranch> orderBranchList = new ArrayList<>();

    @Column
    @Enumerated(EnumType.STRING)
    private BranchStatus status; // 총 3가지 상태 : Pending, Confirmed, Refused 이 중에서 앞글자만 사용 => P, A, R

    @Column
    @Enumerated(EnumType.STRING)
    private Authority authority; // 권한 : ROLE_USER, ROLE_ADMIN

    @Builder
    public Branch(Long id, String email, String password, String name, String address, String phone, BranchStatus status, Authority authority) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.authority = authority;
    }

    public BranchDTO toDTO(){

        return BranchDTO.builder()
                .id(this.id)
                .email(this.email)
                .password(password)
                .name(this.name)
                .address(this.address)
                .phone(this.phone)
                .status(this.status)
                .authority(this.authority)
                .build();
    }
}
