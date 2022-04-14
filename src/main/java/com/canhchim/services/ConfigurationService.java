package com.canhchim.services;

import com.canhchim.models.CfgConfigPrinterOrder;
import com.canhchim.repositories.ConfigPrinterOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service @AllArgsConstructor
public class ConfigurationService
{
    ConfigPrinterOrderRepository printerConfigRepo;

    public Collection<CfgConfigPrinterOrder> getAllPrinterOrder()
    {
        return printerConfigRepo.findAll();
    }

    public CfgConfigPrinterOrder getPrinterOrder(int id)
    {
        return printerConfigRepo.findById(id).orElse(null);
    }

    public CfgConfigPrinterOrder savePrinterOrder(CfgConfigPrinterOrder prtOrder)
    {
        return printerConfigRepo.save(prtOrder);
    }
}
