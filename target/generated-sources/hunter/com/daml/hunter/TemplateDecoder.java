package com.daml.hunter;

import com.daml.hunter.model.hunter.Agreement_order_details;
import com.daml.hunter.model.hunter.Deal_Initiate;
import com.daml.hunter.model.hunter.Invoice;
import com.daml.hunter.model.hunter.Order;
import com.daml.hunter.model.hunter.Payment;
import com.daml.hunter.model.hunter.Shipment;
import com.daml.ledger.javaapi.data.Contract;
import com.daml.ledger.javaapi.data.CreatedEvent;
import com.daml.ledger.javaapi.data.Identifier;
import java.lang.IllegalArgumentException;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

public class TemplateDecoder {
  private static HashMap<Identifier, Function<CreatedEvent, Contract>> decoders;

  static {
    decoders = new HashMap<Identifier, Function<CreatedEvent, Contract>>();
    decoders.put(Deal_Initiate.TEMPLATE_ID, Deal_Initiate.Contract::fromCreatedEvent);
    decoders.put(Shipment.TEMPLATE_ID, Shipment.Contract::fromCreatedEvent);
    decoders.put(Agreement_order_details.TEMPLATE_ID, Agreement_order_details.Contract::fromCreatedEvent);
    decoders.put(Invoice.TEMPLATE_ID, Invoice.Contract::fromCreatedEvent);
    decoders.put(Payment.TEMPLATE_ID, Payment.Contract::fromCreatedEvent);
    decoders.put(Order.TEMPLATE_ID, Order.Contract::fromCreatedEvent);
  }

  public static Contract fromCreatedEvent(CreatedEvent event) throws IllegalArgumentException {
    Identifier templateId = event.getTemplateId();
    Function<CreatedEvent, Contract> decoderFunc = getDecoder(templateId).orElseThrow(() -> new IllegalArgumentException("No template found for identifier " + templateId));
    return decoderFunc.apply(event);
  }

  public static Optional<Function<CreatedEvent, Contract>> getDecoder(Identifier templateId) {
    return Optional.ofNullable(decoders.get(templateId));
  }
}
