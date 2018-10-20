package piotrek.e_shop.model.builder;

import piotrek.e_shop.model.Bill;
import piotrek.e_shop.model.BillState;
import piotrek.e_shop.model.PurchaseProduct;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class BillBuilder {

    private Bill bill;

    public BillBuilder() {
        bill = new Bill();
    }

    public BillBuilder purchaseProducts(List<PurchaseProduct> purchaseProducts) {
        bill.setPurchaseProducts(purchaseProducts);
        return this;
    }

    public BillBuilder id(BigDecimal id) {
        bill.setId(id);
        return this;
    }

    public BillBuilder clientId(BigDecimal clientId) {
        bill.setClientId(clientId);
        return this;
    }

    public BillBuilder priceSum(BigDecimal priceSum) {
        bill.setPriceSum(priceSum);
        return this;
    }

    public BillBuilder purchaseDate(Date purchaseDate) {
        bill.setPurchaseDate(purchaseDate);
        return this;
    }

    public BillBuilder paymentDate(Date paymentDate) {
        bill.setPaymentDate(paymentDate);
        return this;
    }

    public BillBuilder paymentExpirationDate(Date paymentExpirationDate) {
        bill.setPaymentExpirationDate(paymentExpirationDate);
        return this;
    }

    public BillBuilder state(BillState state) {
        bill.setState(state);
        return this;
    }

    public Bill build() {
        return bill;
    }

}
