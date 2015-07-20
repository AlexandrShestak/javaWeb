package com.shestakam.helpers;

import java.util.List;

/**
 * Created by alexandr on 17.7.15.
 */
public interface GenericDao<Entity> {
     void  add(Entity entity);
     Entity get(String id);
     List<Entity> getAll();
     void delete(String id);
     void edit(Entity entity);
}

