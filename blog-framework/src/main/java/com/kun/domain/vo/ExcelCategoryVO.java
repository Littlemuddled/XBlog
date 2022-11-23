package com.kun.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kun
 * @since 2022-11-19 14:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelCategoryVO {

    @ExcelProperty("分类名")
    private String name;
    @ExcelProperty("描述")
    private String description;
    @ExcelProperty("状态 0:正常 1:禁用")
    private String status;
}
