package com.posco.poscoproject.service;

import com.posco.poscoproject.dto.BranchDTO;
import com.posco.poscoproject.entity.Branch;
import com.posco.poscoproject.enumtype.Authority;
import com.posco.poscoproject.enumtype.BranchStatus;
import com.posco.poscoproject.repository.BranchRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BranchService {

    private final BranchRepository branchRepository;
    private final MailService mailService;

    private final PasswordEncoder passwordEncoder;

    // 지점 목록
    public List<BranchDTO> getBranchList() {
        List<BranchDTO> list = new ArrayList<>();
        branchRepository.findAll().forEach(branch -> {
            list.add(branch.toDTO());
        });

        return list;
    }

    // 지점 요청 시 insert => 테이블에 데이터가 insert 되었으나 로그인은 불가능
    public void insertBranch(BranchDTO branchDTO) {
        Branch branch = branchDTO.toEntity();
        branch.setPassword(passwordEncoder.encode(branch.getPassword()));
        branch.setStatus(BranchStatus.Pending);
        branch.setAuthority(Authority.USER);

        branchRepository.save(branch);
    }

    public void permitBranch(Long id) {
        Branch branch = branchRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        try {
            mailService.sendSimpleMessage(branch.getEmail(), String.valueOf(branch.getEmail()), branch.getPassword());
            branch.setStatus(BranchStatus.Confirmed);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Branch findBranchByEmail(String email) {
//        System.out.println("principal : "+email);

        Optional<Branch> branch = branchRepository.findByEmail(email);

        return branch.orElse(null);
    }

    @Transactional
    public BranchDTO getPost(Long id) {
        Optional<Branch> branchWrapper = branchRepository.findById(id);
        Branch branch = branchWrapper.get();

        BranchDTO branchDTO = BranchDTO.builder()
                .id(branch.getId())
                .email(branch.getEmail())
                .password(branch.getPassword())
                .name(branch.getName())
                .address(branch.getAddress())
                .phone(branch.getPhone())
                .status(branch.getStatus())
                .authority(branch.getAuthority())
                .build();

        return branchDTO;
    }

    @Transactional
    public Long savePost(BranchDTO branchDTO) {
        return branchRepository.save(branchDTO.toEntity()).getId();
    }

//    public List<Branch> topTenList(){
//        return branchRepository.topTenBranchList();
//    }

    /******* 본사 *******/
    public Optional<Long> dailySales() {
        Optional<Long> daily = Optional.ofNullable(branchRepository.getDailySalesAll(Date.valueOf(LocalDate.now())));

        return daily;
    }

    public Long weekSales() {
        return branchRepository.getWeekSalesAll();
    }

    public double compareLastWeek(double week) {
        double lastWeek = branchRepository.getLastWeekSalesAll();

        return ((week - lastWeek) / lastWeek) * 100;
    }

    public Long monthSales() {
        return branchRepository.getMonthSalesAll();
    }

    public double compareLastMonth(double month) {
        double lastMonth = branchRepository.getLastMonthSalesAll();

        return ((month - lastMonth) / lastMonth) * 100;
    }

    public int getBranchCount() {
        return branchRepository.getBranchCount();
}

    // 페이징
    public Page<Branch> branchList(Pageable pageable) {
        return branchRepository.findAll(pageable);
    }

    /******* 지점 *******/
    public Optional<Long> dailySalesById(Long id) {
        Optional<Long> daily = Optional.ofNullable(branchRepository.getDailySalesById(Date.valueOf(LocalDate.now()), id));

        return daily;
    }

    public Long weekSalesById(Long id) {
        return branchRepository.getWeekSalesById(id);
    }

    public double compareLastWeekById(double week, Long id) {
        double lastWeek = branchRepository.getLastWeekSalesById(id);

        return ((week - lastWeek) / lastWeek) * 100;
    }

    public Long monthSalesById(Long id) {
        return branchRepository.getMonthSalesById(id);
    }

    public double compareLastMonthById(double month, Long id) {
        double lastMonth = branchRepository.getLastMonthSalesById(id);

        return ((month - lastMonth) / lastMonth) * 100;
    }

    public int orderCountById(Long id) {
        return branchRepository.getOrderCountById(id);
    }

    // branch 에 username 을 받아오며 username.getId로 brachId를 가져옴
    public Long findBranchIdByUsername(String username) {
        Branch branch = findBranchByEmail(username);
        System.out.println("------------------" + branch);
        if (branch != null) {
            return branch.getId(); // Branch 객체에서 ID(즉, branchId)를 반환
        }
        return null; // 사용자를 찾을 수 없는 경우 null 반환
    }

    public String findBranchNameByUsername(String username) {
        Branch branch = findBranchByEmail(username);
        System.out.println("------------------" + branch);
        if (branch != null) {
            return branch.getName(); // Branch 객체에서 ID(즉, branchName)를 반환
        }
        return null; // 사용자를 찾을 수 없는 경우 null 반환
    }

}
