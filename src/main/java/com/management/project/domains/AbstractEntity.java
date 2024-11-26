package com.management.project.domains;

import com.management.project.commons.CommonUtil;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;

@MappedSuperclass
public abstract class AbstractEntity {
   @CreatedBy
   @Column(name = "created_by", nullable = false, length = 50, updatable = false)
   private String createBy;
   @LastModifiedBy
   @Column(name = "update_by", length = 50)
   private String updateBy;
   @CreatedDate
   @Column(name = "create_at", updatable = false, columnDefinition = "TIMESTAMP")
   private Instant createAt = Instant.now();
   @LastModifiedDate
   @Column(name = "update_at", updatable = false, columnDefinition = "TIMESTAMP")
   private Instant updateAt = Instant.now();

   @PrePersist
   public void prePersist() {
      this.createAt = Instant.now();
      this.createBy = CommonUtil.getCurrentUser();
   }

   @PreUpdate
   public void preUpdate() {
      this.updateAt = Instant.now();
      this.updateBy = CommonUtil.getCurrentUser();

   }
}
