package com.team.ShopSystem.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xby
 * @since 2023-04-05
 */
@TableName("ss_transfer_records")
@ApiModel(value = "TransferRecords对象", description = "")
@Getter
@Setter
public class TransferRecords {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String transferName;

    private Float amount;

    private String receiveName;

    private Integer deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TransferRecords(String transferName, Float amount, String receiveName) {
        this.transferName = transferName;
        this.amount = amount;
        this.receiveName = receiveName;
    }
}
