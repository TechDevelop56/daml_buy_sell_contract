package com.daml.hunter.model.hunter;

import com.daml.hunter.model.da.internal.template.Archive;
import com.daml.ledger.javaapi.data.CreateAndExerciseCommand;
import com.daml.ledger.javaapi.data.CreateCommand;
import com.daml.ledger.javaapi.data.CreatedEvent;
import com.daml.ledger.javaapi.data.ExerciseCommand;
import com.daml.ledger.javaapi.data.Identifier;
import com.daml.ledger.javaapi.data.Record;
import com.daml.ledger.javaapi.data.Template;
import com.daml.ledger.javaapi.data.Text;
import com.daml.ledger.javaapi.data.Value;
import java.lang.Deprecated;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class Payment extends Template {
  public static final Identifier TEMPLATE_ID = new Identifier("a40c2278c53fd2feefdf7880ed6ca9d48e711ae33b696355ead3e503dc2a83c3", "Hunter", "Payment");

  public final Agreement_order_details order_details;

  public final Shipment_details shipment_details;

  public final String status;

  public Payment(Agreement_order_details order_details, Shipment_details shipment_details,
      String status) {
    this.order_details = order_details;
    this.shipment_details = shipment_details;
    this.status = status;
  }

  public CreateCommand create() {
    return new CreateCommand(Payment.TEMPLATE_ID, this.toValue());
  }

  public CreateAndExerciseCommand createAndExerciseArchive(Archive arg) {
    Value argValue = arg.toValue();
    return new CreateAndExerciseCommand(Payment.TEMPLATE_ID, this.toValue(), "Archive", argValue);
  }

  public CreateAndExerciseCommand createAndExerciseAccept_payment(Accept_payment arg) {
    Value argValue = arg.toValue();
    return new CreateAndExerciseCommand(Payment.TEMPLATE_ID, this.toValue(), "Accept_payment", argValue);
  }

  public CreateAndExerciseCommand createAndExerciseAccept_payment() {
    return createAndExerciseAccept_payment(new Accept_payment());
  }

  public static CreateCommand create(Agreement_order_details order_details,
      Shipment_details shipment_details, String status) {
    return new Payment(order_details, shipment_details, status).create();
  }

  public static Payment fromValue(Value value$) throws IllegalArgumentException {
    Value recordValue$ = value$;
    Record record$ = recordValue$.asRecord().orElseThrow(() -> new IllegalArgumentException("Contracts must be constructed from Records"));
    List<Record.Field> fields$ = record$.getFields();
    int numberOfFields = fields$.size();
    if (numberOfFields != 3) {
      throw new IllegalArgumentException("Expected 3 arguments, got " + numberOfFields);
    }
    Agreement_order_details order_details = Agreement_order_details.fromValue(fields$.get(0).getValue());
    Shipment_details shipment_details = Shipment_details.fromValue(fields$.get(1).getValue());
    String status = fields$.get(2).getValue().asText().orElseThrow(() -> new IllegalArgumentException("Expected status to be of type com.daml.ledger.javaapi.data.Text")).getValue();
    return new com.daml.hunter.model.hunter.Payment(order_details, shipment_details, status);
  }

  public Record toValue() {
    ArrayList<Record.Field> fields = new ArrayList<Record.Field>(3);
    fields.add(new Record.Field("order_details", this.order_details.toValue()));
    fields.add(new Record.Field("shipment_details", this.shipment_details.toValue()));
    fields.add(new Record.Field("status", new Text(this.status)));
    return new Record(fields);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (!(object instanceof Payment)) {
      return false;
    }
    Payment other = (Payment) object;
    return this.order_details.equals(other.order_details) && this.shipment_details.equals(other.shipment_details) && this.status.equals(other.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.order_details, this.shipment_details, this.status);
  }

  @Override
  public String toString() {
    return String.format("com.daml.hunter.model.hunter.Payment(%s, %s, %s)", this.order_details, this.shipment_details, this.status);
  }

  public static final class ContractId extends com.daml.ledger.javaapi.data.codegen.ContractId<Payment> {
    public ContractId(String contractId) {
      super(contractId);
    }

    public ExerciseCommand exerciseArchive(Archive arg) {
      Value argValue = arg.toValue();
      return new ExerciseCommand(Payment.TEMPLATE_ID, this.contractId, "Archive", argValue);
    }

    public ExerciseCommand exerciseAccept_payment(Accept_payment arg) {
      Value argValue = arg.toValue();
      return new ExerciseCommand(Payment.TEMPLATE_ID, this.contractId, "Accept_payment", argValue);
    }

    public ExerciseCommand exerciseAccept_payment() {
      return exerciseAccept_payment(new Accept_payment());
    }
  }

  public static class Contract implements com.daml.ledger.javaapi.data.Contract {
    public final ContractId id;

    public final Payment data;

    public final Optional<String> agreementText;

    public final Set<String> signatories;

    public final Set<String> observers;

    public Contract(ContractId id, Payment data, Optional<String> agreementText,
        Set<String> signatories, Set<String> observers) {
      this.id = id;
      this.data = data;
      this.agreementText = agreementText;
      this.signatories = signatories;
      this.observers = observers;
    }

    public static Contract fromIdAndRecord(String contractId, Record record$,
        Optional<String> agreementText, Set<String> signatories, Set<String> observers) {
      ContractId id = new ContractId(contractId);
      Payment data = Payment.fromValue(record$);
      return new Contract(id, data, agreementText, signatories, observers);
    }

    @Deprecated
    public static Contract fromIdAndRecord(String contractId, Record record$) {
      ContractId id = new ContractId(contractId);
      Payment data = Payment.fromValue(record$);
      return new Contract(id, data, Optional.empty(), Collections.emptySet(), Collections.emptySet());
    }

    public static Contract fromCreatedEvent(CreatedEvent event) {
      return fromIdAndRecord(event.getContractId(), event.getArguments(), event.getAgreementText(), event.getSignatories(), event.getObservers());
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
        return true;
      }
      if (object == null) {
        return false;
      }
      if (!(object instanceof Contract)) {
        return false;
      }
      Contract other = (Contract) object;
      return this.id.equals(other.id) && this.data.equals(other.data) && this.agreementText.equals(other.agreementText) && this.signatories.equals(other.signatories) && this.observers.equals(other.observers);
    }

    @Override
    public int hashCode() {
      return Objects.hash(this.id, this.data, this.agreementText, this.signatories, this.observers);
    }

    @Override
    public String toString() {
      return String.format("com.daml.hunter.model.hunter.Payment.Contract(%s, %s, %s, %s, %s)", this.id, this.data, this.agreementText, this.signatories, this.observers);
    }
  }
}
