package com.csp.formwork.database.tblbean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class TblTask {
    @Id
    private String id;

    @Generated(hash = 162217633)
    public TblTask(String id) {
        this.id = id;
    }

    @Generated(hash = 1404881509)
    public TblTask() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
