package com.shestakam.db;

import java.util.List;

/**
 * Created by alexandr on 17.7.15.
 */
public interface GenericDao<Entity> {
     String add(Entity entity);
     Entity get(String id);
     List<Entity> getAll();
     void delete(String id);
     void update(Entity entity);
}

