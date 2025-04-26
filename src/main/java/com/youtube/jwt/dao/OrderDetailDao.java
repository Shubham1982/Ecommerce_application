package com.youtube.jwt.dao;

import com.youtube.jwt.entity.OrderDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailDao extends CrudRepository<OrderDetail,Integer> {
}
