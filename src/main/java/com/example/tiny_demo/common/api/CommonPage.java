package com.example.tiny_demo.common.api;

import com.github.pagehelper.PageInfo;
import lombok.Getter;

import java.util.List;

/**
 * 分页数据封装类
 */
@Getter
public class CommonPage<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalPage;
    private Long total;
    private List<T> list;

    public CommonPage(Integer pageNum, Integer pageSize, Integer totalPage, Long total, List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.total = total;
        this.list = list;
    }

    /**
     * 将PageInfo转化为通用分页结果
     * @param pageInfo
     * @param <T>
     * @return
     */
    public static <T> CommonPage<T> restPage(PageInfo<T> pageInfo) {
        return new CommonPage<>(pageInfo.getPageNum(), pageInfo.getPageSize(), pageInfo.getPages(), pageInfo.getTotal(), pageInfo.getList());
    }
}
