package com.example.system.service.impl;

import com.example.system.domain.Menu;
import com.ocean.common.utils.ValidationUtil;
import com.example.system.repository.MenuRepository;
import com.example.system.service.MenuService;
import com.example.system.service.dto.MenuDTO;
import com.example.system.service.dto.MenuQueryCriteria;
import com.example.system.service.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.ocean.common.utils.PageUtil;
import com.ocean.common.utils.QueryHelp;

/**
* @author suj
* @date 2022-03-28
*/
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Object queryAll(MenuQueryCriteria criteria, Pageable pageable){
        Page<Menu> page = menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(menuMapper::toDto));
    }

    @Override
    public Object queryAll(MenuQueryCriteria criteria){
        return menuMapper.toDto(menuRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    public MenuDTO findById(Long id) {
        Optional<Menu> menu = menuRepository.findById(id);
        ValidationUtil.isNull(menu,"Menu","id",id);
        return menuMapper.toDto(menu.get());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuDTO create(Menu resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return menuMapper.toDto(menuRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Menu resources) {
        Optional<Menu> optionalMenu = menuRepository.findById(resources.getId());
        ValidationUtil.isNull( optionalMenu,"Menu","id",resources.getId());
        Menu menu = optionalMenu.get();
        menu.copy(resources);
        menuRepository.save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        menuRepository.deleteById(id);
    }
}