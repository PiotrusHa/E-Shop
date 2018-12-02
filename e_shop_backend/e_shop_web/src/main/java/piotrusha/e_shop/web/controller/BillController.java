package piotrusha.e_shop.web.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotrusha.e_shop.core.model.Bill;
import piotrusha.e_shop.core.model.BillState;
import piotrusha.e_shop.core.model.dto.PurchaseProductDto;
import piotrusha.e_shop.core.service.BillService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "bills", produces = APPLICATION_JSON_UTF8_VALUE)
public class BillController {

    private BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("client/{clientId}")
    List<Bill> getBillsByClientId(@PathVariable BigDecimal clientId) {
        return billService.findBillsByClientId(clientId);
    }

    @GetMapping("client/{clientId}/{state}")
    List<Bill> getBillsByClientIdAndState(@PathVariable BigDecimal clientId, @PathVariable BillState state) {
        return billService.findBillsByClientIdAndState(clientId, state);
    }

    @PostMapping("client/{clientId}/create")
    Bill createBill(@RequestBody List<PurchaseProductDto> purchaseProductDtos, @PathVariable BigDecimal clientId) {
        return billService.createBill(purchaseProductDtos, clientId);
    }

    @PatchMapping("{billId}/pay")
    Bill payBill(@PathVariable BigDecimal billId) {
        return billService.payBill(billId);
    }

    @PatchMapping("{billId}/cancel")
    Bill cancelBill(@PathVariable BigDecimal billId) {
        return billService.cancelBill(billId);
    }

}
