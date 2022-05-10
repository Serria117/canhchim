package com.canhchim.payload.request;

import com.canhchim.models.CfgConfigPrinterOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConfigPrinterRequest implements Serializable
{
    private Integer id;
    private Integer fontColor = 0;
    private String fontName;
    private Integer fontSize = 0;
    private String fontStyle = null;
    private Integer locationX;
    private Integer locationY;
    private Integer locationType;
    private Integer statusEdit;
    private String fontInStringFormat;
    private String dataToPrint;
    private Integer type;

    public CfgConfigPrinterOrder build(Integer id)
    {
        var prt = new CfgConfigPrinterOrder();
        if ( id != null ) {prt.setId(id);}
        prt.setFontColor(fontColor);
        prt.setFontName(fontName);
        prt.setFontSize(fontSize);
        prt.setFontStyle(fontStyle);
        prt.setLocationX(locationX);
        prt.setLocationY(locationY);
        prt.setLocationType(locationType);
        prt.setStatusEdit(statusEdit);
        prt.setFontInStringFormat(fontInStringFormat);
        prt.setDataToPrint(dataToPrint);
        prt.setType(type);
        return prt;
    }
}
