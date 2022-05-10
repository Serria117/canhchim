package com.canhchim.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "PRT_Printer_temp")
public class PrtPrinterTemp
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "PrinterTempName", nullable = false, length = 100)
    private String printerTempName;

    @Column(name = "PrinterTempDeviceName", nullable = false, length = 100)
    private String printerTempDeviceName;

    @Column(name = "PrinterTempIndex")
    private Integer printerTempIndex;

    @Column(name = "PrinterTempPageSize", length = 100)
    private String printerTempPageSize;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ShopId", nullable = false)
    private ShpShop shop;
}
