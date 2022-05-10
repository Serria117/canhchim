package com.canhchim.payload.request;

import com.canhchim.models.PrtPrinterTemp;
import lombok.Data;

import java.io.Serializable;

@Data
public class PrinterTempRequest implements Serializable
{
    private Integer id;
    private String printerTempName;
    private String printerTempDeviceName;
    private Integer printerTempIndex;
    private String printerTempPageSize;
    private Long shopId;

    public PrtPrinterTemp build(Integer id)
    {
        var p = new PrtPrinterTemp();
        if(id != null) p.setId(id);
        p.setPrinterTempName(printerTempName);
        p.setPrinterTempIndex(printerTempIndex);
        p.setPrinterTempDeviceName(printerTempDeviceName);
        p.setPrinterTempIndex(printerTempIndex);
        p.setPrinterTempPageSize(printerTempPageSize);

        return p;
    }
}
