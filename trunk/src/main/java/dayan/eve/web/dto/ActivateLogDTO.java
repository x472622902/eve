/**
 * *****************************************************
 * Copyright (C) Dayan techology Co.ltd - All Rights Reserved
 *
 * This file is part of Dayan techology Co.ltd property.
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * *****************************************************
 */
package dayan.eve.web.dto;

/**
 *
 * @author zhuangyd
 */
public class ActivateLogDTO {

    private String imei;
    private String osType;
    private String osVer;
    private String product;
    private String romType;
    private Integer distributeId;

    /**
     * iOS 版无法获取imei 号，仅能获取应用安装后分配的唯一随机号
     * @return 
     */
    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }

    public String getOsVer() {
        return osVer;
    }

    public void setOsVer(String osVer) {
        this.osVer = osVer;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRomType() {
        return romType;
    }

    public void setRomType(String romType) {
        this.romType = romType;
    }

    public Integer getDistributeId() {
        return distributeId;
    }

    public void setDistributeId(Integer distributeId) {
        this.distributeId = distributeId;
    }

}
