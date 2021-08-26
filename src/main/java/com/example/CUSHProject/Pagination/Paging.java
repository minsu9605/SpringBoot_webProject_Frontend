package com.example.CUSHProject.Pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class Paging {
    private int recordPerPage = 10;                //초기값으로 목록개수를 10으로 셋팅
    private int bottomSize = 10;            //초기값으로 페이지범위를 10으로 셋팅
    private int curPage;
    private double listCnt;
    private int totalPageSize;
    private int startPage;
    private int endPage;


    public void pageInfo(int curPage, double listCnt) {

        this.curPage = curPage;
        this.listCnt = listCnt;

        //전체 페이지수
        this.totalPageSize = (int) Math.ceil(listCnt / recordPerPage);

        //시작 페이지
        this.startPage = ((curPage - 1)/bottomSize) * bottomSize + 1;

        //끝 페이지
        this.endPage = startPage + bottomSize -1;

        if (this.endPage > this.totalPageSize) {

            this.endPage = this.totalPageSize;

        }

    }

}

