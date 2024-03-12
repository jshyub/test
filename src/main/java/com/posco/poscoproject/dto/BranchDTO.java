package com.posco.poscoproject.dto;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.enumtype.BranchStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BranchDTO {

    private Long id;

    private String email;

    private String password;

    private String name;

    private String address;

    private String phone;

    private BranchStatus status; // 총 3가지 상태 : Pending, Available, Refused 이 중에서 앞글자만 사용 => P, A, R

    private Authority authority;

    @Builder
    public BranchDTO(Long id, String email, String password, String name, String address, String phone, BranchStatus status, Authority authority) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.authority = authority;
    }

    public Branch toEntity(){
        return Branch.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .address(this.address)
                .phone(this.phone)
                .status(this.status)
                .authority(this.authority)
                .build();
    }
}
