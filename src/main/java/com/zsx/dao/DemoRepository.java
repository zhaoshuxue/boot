package com.zsx.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zsx.bean.Demo;

@Repository
public interface DemoRepository extends CrudRepository<Demo, Long> {

}
