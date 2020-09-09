package com.yuexing.insurancewechatofficalaccount.wechat.model.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
public class BaseEntityPO implements Serializable {

    private static final String UNKNOWN_USER = "_UNKNOWN";
    private static final long serialVersionUID = 2388213001235406501L;

    private static Logger logger = LoggerFactory.getLogger(BaseEntityPO.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_by", updatable = false)
    private String createBy;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "create_time", updatable = false)
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @PrePersist
    public void prePersist() {
        this.createTime = new Date();
        this.updateTime = new Date();

        if (null == this.createBy) {
            this.createBy = UNKNOWN_USER;
        }

        if (null == this.updateBy) {
            this.updateBy = UNKNOWN_USER;
        }
    }


    @PreUpdate
    public void preUpdate() {
        this.updateTime = new Date();

        if (null == this.updateBy) {
            this.updateBy = UNKNOWN_USER;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
