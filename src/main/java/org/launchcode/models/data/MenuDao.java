package org.launchcode.models.data;

import org.launchcode.models.Menu;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Rhodondron on 4/25/2017. ***copy-pasta 4/26/2017
 */

@Repository
@Transactional
public interface MenuDao extends CrudRepository<Menu, Integer>{
}
