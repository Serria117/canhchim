package com.canhchim.services;

import com.canhchim.models.ShpShop;
import com.canhchim.models.ShpTable;
import com.canhchim.models.ShpZone;
import com.canhchim.repositories.ShpShopRepository;
import com.canhchim.repositories.ShpTableRepository;
import com.canhchim.repositories.ShpZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShopService
{
    @Autowired
    ShpShopRepository shopRepo;
    @Autowired
    ShpTableRepository tableRepo;
    @Autowired
    ShpZoneRepository zoneRepo;

    public ShpShop findById(long id)
    {
        return shopRepo.findById(id).orElse(null);
    }

    public List<ShpZone> findAllZone(long shopId)
    {
        return zoneRepo.findAllZone(shopId);
    }


    public void newTable(long shopId, long zoneId, String tableName, int numberOfSeat) throws RuntimeException
    {
        if ( tableRepo.existsByTableNumber(tableName, zoneId) ) {
            throw new RuntimeException("Table number has already exist");
        }
        if ( !zoneRepo.findAllZone(shopId).stream().map(ShpZone::getId).toList().contains(zoneId) ) {
            throw new RuntimeException("The ZoneID does not belong to this shop. Correct your input and try again.");
        }
        ShpTable newTable = new ShpTable();
        newTable.setZone(zoneRepo.findById(zoneId)
                                 .orElseThrow(() -> new RuntimeException("Invalid zone ID")));
        newTable.setTableName(tableName);
        newTable.setNumberOfSeat(numberOfSeat);
        saveTable(newTable);
    }

    public void saveTable(ShpTable table)
    {
        tableRepo.save(table);
    }
    public void saveZone(ShpZone zone) {zoneRepo.save(zone);}

    public Optional<ShpTable> getTableById(Long id)
    {
        return tableRepo.findById(id);
    }
    public Optional<ShpZone> getZoneById(Long id) {return zoneRepo.findById(id);}
    public void newZone(long shopId, String zoneName) throws RuntimeException
    {
        if( existByZoneName(zoneName, shopId) ){
            throw new RuntimeException("Zone name already exist.");
        }
        var newZone = new ShpZone();
        newZone.setZoneName(zoneName);
        newZone.setShop(shopRepo.findById(shopId)
                                .orElseThrow(() -> new RuntimeException("Invalid shop ID")));
        saveZone(newZone);
    }
    public boolean existByZoneName(String zoneName, Long shopId)
    {
        return zoneRepo.existsByZoneName(zoneName, shopId);
    }

}
