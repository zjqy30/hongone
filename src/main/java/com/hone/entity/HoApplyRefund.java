package com.hone.entity;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author lijia
 * Date  2019-07-12
 */
@Table(name = "ho_apply_refund")
public class HoApplyRefund extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
        private String offerId;
    private String userId;
    private String reason;
    private String status;
            

    public HoApplyRefund(){
    }

        public void setOfferId (String offerId) {this.offerId = offerId;} 
    public String getOfferId(){ return offerId;} 
    public void setUserId (String userId) {this.userId = userId;} 
    public String getUserId(){ return userId;} 
    public void setReason (String reason) {this.reason = reason;} 
    public String getReason(){ return reason;} 
    public void setStatus (String status) {this.status = status;} 
    public String getStatus(){ return status;} 
            
}