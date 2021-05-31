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

public final class Invoice extends Template {
  public static final Identifier TEMPLATE_ID = new Identifier("a40c2278c53fd2feefdf7880ed6ca9d48e711ae33b696355ead3e503dc2a83c3", "Hunter", "Invoice");

  public final Payment invoice;

  public final String status;

  public Invoice(Payment invoice, String status) {
    this.invoice = invoice;
    this.status = status;
  }

  public CreateCommand create() {
    return new CreateCommand(Invoice.TEMPLATE_ID, this.toValue());
  }

  public CreateAndExerciseCommand createAndExerciseArchive(Archive arg) {
    Value argValue = arg.toValue();
    return new CreateAndExerciseCommand(Invoice.TEMPLATE_ID, this.toValue(), "Archive", argValue);
  }

  public static CreateCommand create(Payment invoice, String status) {
    return new Invoice(invoice, status).create();
  }

  public static Invoice fromValue(Value value$) throws IllegalArgumentException {
    Value recordValue$ = value$;
    Record record$ = recordValue$.asRecord().orElseThrow(() -> new IllegalArgumentException("Contracts must be constructed from Records"));
    List<Record.Field> fields$ = record$.getFields();
    int numberOfFields = fields$.size();
    if (numberOfFields != 2) {
      throw new IllegalArgumentException("Expected 2 arguments, got " + numberOfFields);
    }
    Payment invoice = Payment.fromValue(fields$.get(0).getValue());
    String status = fields$.get(1).getValue().asText().orElseThrow(() -> new IllegalArgumentException("Expected status to be of type com.daml.ledger.javaapi.data.Text")).getValue();
    return new com.daml.hunter.model.hunter.Invoice(invoice, status);
  }

  public Record toValue() {
    ArrayList<Record.Field> fields = new ArrayList<Record.Field>(2);
    fields.add(new Record.Field("invoice", this.invoice.toValue()));
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
    if (!(object instanceof Invoice)) {
      return false;
    }
    Invoice other = (Invoice) object;
    return this.invoice.equals(other.invoice) && this.status.equals(other.status);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.invoice, this.status);
  }

  @Override
  public String toString() {
    return String.format("com.daml.hunter.model.hunter.Invoice(%s, %s)", this.invoice, this.status);
  }

  public static final class ContractId extends com.daml.ledger.javaapi.data.codegen.ContractId<Invoice> {
    public ContractId(String contractId) {
      super(contractId);
    }

    public ExerciseCommand exerciseArchive(Archive arg) {
      Value argValue = arg.toValue();
      return new ExerciseCommand(Invoice.TEMPLATE_ID, this.contractId, "Archive", argValue);
    }
  }

  public static class Contract implements com.daml.ledger.javaapi.data.Contract {
    public final ContractId id;

    public final Invoice data;

    public final Optional<String> agreementText;

    public final Set<String> signatories;

    public final Set<String> observers;

    public Contract(ContractId id, Invoice data, Optional<String> agreementText,
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
      Invoice data = Invoice.fromValue(record$);
      return new Contract(id, data, agreementText, signatories, observers);
    }

    @Deprecated
    public static Contract fromIdAndRecord(String contractId, Record record$) {
      ContractId id = new ContractId(contractId);
      Invoice data = Invoice.fromValue(record$);
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
      return String.format("com.daml.hunter.model.hunter.Invoice.Contract(%s, %s, %s, %s, %s)", this.id, this.data, this.agreementText, this.signatories, this.observers);
    }
  }
}
