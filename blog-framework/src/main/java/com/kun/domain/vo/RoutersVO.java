package com.kun.domain.vo;

import com.kun.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author kun
 * @since 2022-11-22 16:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutersVO {

    private List<MenuVO> menus;
}
