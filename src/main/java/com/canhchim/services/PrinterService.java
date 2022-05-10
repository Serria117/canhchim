package com.canhchim.services;

import com.canhchim.models.CfgConfigPrinterOrder;
import com.canhchim.models.PrtPrinterTemp;
import com.canhchim.repositories.ConfigPrinterOrderRepository;
import com.canhchim.repositories.PrinterTempRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrinterService
{
    ConfigPrinterOrderRepository printerConfigRepo;
    PrinterTempRepository printerTempRepository;

    public Collection<CfgConfigPrinterOrder> getAllPrinterOrder()
    {
        return printerConfigRepo.findAll();
    }

    public Optional<CfgConfigPrinterOrder> getPrinterOrder(int id)
    {
        return printerConfigRepo.findById(id);
    }

    @Transactional(rollbackOn = IllegalArgumentException.class)
    public void savePrinterOrder(CfgConfigPrinterOrder prtOrder)
    {
        printerConfigRepo.save(prtOrder);
    }

    public Collection<PrtPrinterTemp> getAllPrinterTemplates()
    {
        return printerTempRepository.findAll();
    }

    public Optional<PrtPrinterTemp> getPrinterTemplate(int id)
    {
        return printerTempRepository.findById(id);
    }

    @Transactional(rollbackOn = IllegalArgumentException.class)
    public void saveTemplate(PrtPrinterTemp p)
    {
        printerTempRepository.save(p);
    }
}
