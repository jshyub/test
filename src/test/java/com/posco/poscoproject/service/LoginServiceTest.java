package com.posco.poscoproject.service;

import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.repository.BranchRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
class LoginServiceTest {
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private BranchRepository branchRepository;


    @Test
    @DisplayName("인코딩 테스트")
    public void enPpasswd(){

        String enpd = passwordEncoder.encode("branch2");
        System.out.println("passwd : " + enpd);

        Branch branch = branchRepository.findByEmail("kokeettea2@kokeetea.com")
                .orElseThrow(EntityNotFoundException::new);

        System.out.println("id : "+branch.getEmail().equals("kokeettea2@kokeetea.com"));
        String branPWD = branch.getPassword();
//        System.out.println("pwd : "+passwordEncoder.matches("branch2", enpd));

        boolean flag = passwordEncoder.matches(enpd, branPWD);
        System.out.println("passwd branch : "+branch.getPassword());
        System.out.println("test : "+passwordEncoder.matches(enpd, branPWD));
    }

}