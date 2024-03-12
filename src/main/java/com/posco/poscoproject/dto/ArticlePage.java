package com.posco.poscoproject.dto;

import java.util.List;

public class ArticlePage {
    // 전체 글의 행의 수
    private int total;
    // 현재 페이지 번호
    private int currentPage;
    // 전체 페이지 개수
    private int totalPages;
    // 시작 페이지 번호
    private int startPage;
    // 종료 페이지 번호
    private int endPage;
    // 페이징의 개수
    private int pagingCount;
    // 게시글 데이터
    private List<ItemDTO> content;

    // 생성자
    // size : 한 화면에 보여질 행의 수
    public ArticlePage(int total, int currentPage, int size, int pagingCount, List<ItemDTO> content) {
        this.total = total;
        this.currentPage = currentPage;
        this.content = content;
        this.pagingCount = pagingCount;

        if(total == 0) {
            totalPages = 0;
            startPage = 0;
            endPage = 0;
        } else {
            totalPages = total / size;
            if(total % size > 0) {
                totalPages++;
            }

            startPage = currentPage / pagingCount * pagingCount + 1;
            if(currentPage % pagingCount == 0) {
                startPage -= pagingCount;
            }

            endPage = startPage + pagingCount - 1 ;
            if(endPage > totalPages) {
                endPage = totalPages;
            }
        }
    }

    public int getTotal() {
        return this.total;
    }

    public boolean hasNoArticles() {
        return this.total == 0;
    }

    public boolean hasArticles() {
        return this.total > 0;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public List<ItemDTO> getContent(){
        return this.content;
    }

    public int getStartPage() {
        return this.startPage;
    }

    public int getEndPage() {
        return this.endPage;
    }

}