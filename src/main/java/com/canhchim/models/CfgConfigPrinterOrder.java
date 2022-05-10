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
@Table(name = "CFG_Config_printer_order")
public class CfgConfigPrinterOrder
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "font_color", nullable = false)
    private Integer fontColor;

    @Column(name = "font_name", nullable = false, length = 20)
    private String fontName;

    @Column(name = "font_size", nullable = false)
    private Integer fontSize;

    @Column(name = "font_style", nullable = false, length = 20)
    private String fontStyle;

    @Column(name = "location_X", nullable = false)
    private Integer locationX;

    @Column(name = "location_Y", nullable = false)
    private Integer locationY;

    @Column(name = "location_type", nullable = false)
    private Integer locationType;

    @Column(name = "status_edit", nullable = false)
    private Integer statusEdit;

    @Column(name = "font_in_string_format", nullable = false, length = 60)
    private String fontInStringFormat;

    @Column(name = "type")
    private Integer type;

    @Lob
    @Column(name = "data_to_print", nullable = false)
    private String dataToPrint;
}
