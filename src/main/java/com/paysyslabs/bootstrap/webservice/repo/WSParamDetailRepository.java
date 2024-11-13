package com.paysyslabs.bootstrap.webservice.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paysyslabs.bootstrap.webservice.entities.WSParamDetail;

@Repository
public interface WSParamDetailRepository extends CrudRepository<WSParamDetail, Long> {
    
}
