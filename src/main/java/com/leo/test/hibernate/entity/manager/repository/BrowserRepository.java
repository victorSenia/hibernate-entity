package com.leo.test.hibernate.entity.manager.repository;

import com.leo.test.hibernate.entity.manager.model.Browser;

/**
 * Created by Senchenko Viktor on 27.09.2016.
 */
public class BrowserRepository extends Repository<Browser> {
    public BrowserRepository() {
        super(Browser.class);
    }
}