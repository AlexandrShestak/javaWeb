package com.shestakam.helpers;

import java.util.List;

/**
 * Created by alexandr on 17.7.15.
 */
public interface GenericDao<Entity> {
    public abstract void  add(Entity entity);
    public abstract Entity get(String id);
    public abstract List<Entity> getAll();
    public abstract void delete(String id);
    public abstract void edit(Entity entity);
}

