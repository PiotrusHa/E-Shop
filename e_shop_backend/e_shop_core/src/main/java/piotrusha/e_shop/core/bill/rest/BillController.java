package piotrusha.e_shop.core.bill.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import piotrusha.e_shop.core.bill.domain.BillFacade;
import piotrusha.e_shop.core.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.core.bill.domain.dto.BillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto.CreateBillRecordDto;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping(value = "bills", produces = APPLICATION_JSON_UTF8_VALUE)
class BillController {

    private final BillFacade billFacade;

    @Autowired
    BillController(BillFacade billFacade) {
        this.billFacade = billFacade;
    }

    @GetMapping("{billId}")
    BillDto findBillById(@PathVariable BigDecimal billId) {
        return billFacade.findBillByBillId(billId);
    }

    @GetMapping("client/{clientId}")
    List<BillDto> findBillsByClientId(@PathVariable BigDecimal clientId) {
        return billFacade.findBillsByClientId(clientId);
    }

    @GetMapping("client/{clientId}/{state}")
    List<BillDto> findBillsByClientIdAndState(@PathVariable BigDecimal clientId, @PathVariable String state) {
        return billFacade.findBillsByClientIdAndBillState(clientId, state);
    }

    @PostMapping("client/{clientId}/create")
    BillDto createBill(@RequestBody List<CreateBillRecordDto> createBillRecordDtos, @PathVariable BigDecimal clientId) {
        CreateBillDto createBillDto = new CreateBillDto(clientId, createBillRecordDtos);
        return billFacade.createBill(createBillDto);
    }

    @PatchMapping("{billId}/pay")
    void payBill(@PathVariable BigDecimal billId) {
        BillActionDto dto = new BillActionDto(billId);
        billFacade.payBill(dto);
    }

    @PatchMapping("{billId}/cancel")
    void cancelBill(@PathVariable BigDecimal billId) {
        BillActionDto dto = new BillActionDto(billId);
        billFacade.cancelBill(dto);
    }

}
