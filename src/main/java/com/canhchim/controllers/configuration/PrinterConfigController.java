package com.canhchim.controllers.configuration;

import com.canhchim.payload.response.Content;
import com.canhchim.payload.response.ResponseObject;
import com.canhchim.services.ConfigurationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config-printer")
@AllArgsConstructor
public class PrinterConfigController
{
    ConfigurationService configService;

    @GetMapping("get-all")
    public ResponseEntity<?> getAllPrinterOrder()
    {
        var result = configService.getAllPrinterOrder();
        return result.size() == 0
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(404, "NOT FOUND", new Content("No config template found")))
                : ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "OK", new Content(result))
        );
    }

    @GetMapping("get-byId")
    public ResponseEntity<?> getPrinterOrder(@RequestParam(name = "id") int id)
    {
        var result = configService.getPrinterOrder(id);
        return result != null
                ? ResponseEntity.ok().body(
                new ResponseObject(200, "OK", new Content(result)))
                :ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(404, "NOT FOUND", new Content("Not found")
        ));
    }
}
