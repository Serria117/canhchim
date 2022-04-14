package com.canhchim.models.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ShopInComeReportDTO implements Serializable
{
    String shopName;
    Long totalIncome;
    LocalDateTime orderTime;
}
