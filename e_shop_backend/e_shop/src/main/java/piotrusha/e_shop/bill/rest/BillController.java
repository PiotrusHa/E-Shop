package piotrusha.e_shop.bill.rest;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import piotrusha.e_shop.base.rest.ResponseEntityCreator;
import piotrusha.e_shop.base.rest.ResponseErrorMapper;
import piotrusha.e_shop.bill.domain.BillFacade;
import piotrusha.e_shop.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto;

import java.math.BigDecimal;

@RestController
@RequestMapping(value = "bills", produces = APPLICATION_JSON_UTF8_VALUE)
class BillController {

    private final BillFacade billFacade;
    private final ResponseErrorMapper responseErrorMapper;

    @Autowired
    BillController(BillFacade billFacade, ResponseErrorMapper responseErrorMapper) {
        this.billFacade = billFacade;
        this.responseErrorMapper = responseErrorMapper;
    }

    @GetMapping
    ResponseEntity<?> findBillsByClient(@RequestParam BigDecimal clientId, @RequestParam(required = false) String state) {
        if (state == null) {
            return ResponseEntityCreator.ok(billFacade.findBillsByClientId(clientId));
        }
        return billFacade.findBillsByClientIdAndBillState(clientId, state)
                         .map(ResponseEntityCreator::ok)
                         .getOrElseGet(responseErrorMapper::map);
    }

    @GetMapping("{billId}")
    ResponseEntity<?> findBillById(@PathVariable BigDecimal billId) {
        return billFacade.findBillByBillId(billId)
                         .map(ResponseEntityCreator::ok)
                         .getOrElseGet(responseErrorMapper::map);
    }

    @PostMapping
    ResponseEntity<?> createBill(@RequestBody CreateBillDto createBillDto) {
        return billFacade.createBill(createBillDto)
                         .map(ResponseEntityCreator::created)
                         .getOrElseGet(responseErrorMapper::map);
    }

    @PostMapping("{billId}/pay")
    ResponseEntity<?> payBill(@PathVariable BigDecimal billId) {
        BillActionDto dto = new BillActionDto(billId);
        return billFacade.payBill(dto)
                         .map(response -> ResponseEntityCreator.noContent())
                         .getOrElseGet(responseErrorMapper::map);
    }

    @PostMapping("{billId}/cancel")
    ResponseEntity<?> cancelBill(@PathVariable BigDecimal billId) {
        BillActionDto dto = new BillActionDto(billId);
        return billFacade.cancelBill(dto)
                         .map(response -> ResponseEntityCreator.noContent())
                         .getOrElseGet(responseErrorMapper::map);
    }

}
