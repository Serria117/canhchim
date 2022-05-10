package com.canhchim.controllers.configuration;

import com.canhchim.models.CfgConfigPrinterOrder;
import com.canhchim.payload.request.ConfigPrinterRequest;
import com.canhchim.payload.request.PrinterTempRequest;
import com.canhchim.payload.response.Content;
import com.canhchim.payload.response.ErrorResponse;
import com.canhchim.payload.response.ResponseObject;
import com.canhchim.services.PrinterService;
import com.canhchim.services.ShopService;
import com.canhchim.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/config-printer")
@AllArgsConstructor
public class PrinterConfigController
{

    PrinterService configService;
    UserService userService;
    ShopService shopService;

    @GetMapping("get-all")
    public ResponseEntity<?> getAllPrinterOrder()
    {
        var result = configService.getAllPrinterOrder();
        return result.isEmpty()
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
        return result.isPresent()
               ? ResponseEntity.ok().body(
                new ResponseObject(200, "OK", new Content(result)))
               : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                       new ResponseObject(404, "NOT FOUND", new Content("Not found")
                       ));
    }

    @PostMapping("insert")
    public ResponseEntity<?> insertPrinterOrder(
            @RequestBody @Valid ConfigPrinterRequest cfgPrinter,
            Errors err)
    {
        HttpStatus status;
        if ( err.hasErrors() ) {
            Map<String, String> errCollection = new HashMap<>();
            err.getFieldErrors().forEach(e -> {
                errCollection.put(e.getField(), e.getDefaultMessage());
            });
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ErrorResponse(
                            LocalDateTime.now(),
                            status,
                            status.value(),
                            errCollection
                    )
            );
        }
        try {
            Integer id = cfgPrinter.getId();
            var prtOrder = new CfgConfigPrinterOrder();
            if ( id != null && id > 0 ) {
                if ( configService.getPrinterOrder(id).isPresent() ) {
                    prtOrder = cfgPrinter.build(id);
                }
                else {
                    throw new RuntimeException("Invalid ID. The ID you provided did not match any record in the database");
                }
            }
            else {
                prtOrder = cfgPrinter.build(id);
            }
            configService.savePrinterOrder(prtOrder);
            status = HttpStatus.OK;
            return ResponseEntity.ok().body(
                    new ResponseObject(
                            status.value(),
                            status.getReasonPhrase(),
                            new Content(prtOrder))
            );
        }
        catch ( RuntimeException e ) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(
                            status.value(),
                            status.getReasonPhrase(),
                            new Content(e.getMessage()))
            );
        }
    }

    @GetMapping("template/get")
    public ResponseEntity<?> getPrinterTemplate(@RequestParam("id") int id)
    {
        var template = configService.getPrinterTemplate(id);
        return template.isPresent()
               ? ResponseEntity.ok().body(new ResponseObject(
                200, "OK", new Content(template.get())))
               : ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                       404, "NOT FOUND", new Content("No template found")
               ));
    }

    @GetMapping("template/update")
    public ResponseEntity<?> updatePrinterTemplate(@RequestBody PrinterTempRequest request, Errors err)
    {
        if ( err.hasErrors() ) {
            Map<String, String> errCollection = new HashMap<>();
            err.getFieldErrors().forEach(e -> {
                errCollection.put(e.getField(), e.getDefaultMessage());
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(new ResponseObject(400, "BAD REQUEST",
                                                          new Content(errCollection)));
        }
        var id = request.getId();
        if ( id != null ) {
            var foundTemp = configService.getPrinterTemplate(id).orElse(null);
            if ( foundTemp != null ) {
                foundTemp.setPrinterTempPageSize(request.getPrinterTempPageSize());
                foundTemp.setPrinterTempIndex(request.getPrinterTempIndex());
                foundTemp.setPrinterTempDeviceName(request.getPrinterTempDeviceName());
                foundTemp.setShop(shopService.findById(request.getShopId()));
                configService.saveTemplate(foundTemp);
                return ResponseEntity.ok().body(new ResponseObject(
                        200, "OK", new Content("Template saved.")
                ));
            } else {
                return ResponseEntity.status(400).body(new ResponseObject(
                        400, "BAD REQUEST", new Content("invalid template ID")
                ));
            }
        }
        else {
            var newTemp = request.build(null);
            newTemp.setShop(shopService.findById(request.getShopId()));
            configService.saveTemplate(newTemp);
            return ResponseEntity.ok().body(new ResponseObject(
                    200, "OK", new Content("new template created.")
            ));
        }
    }
}
