package com.daml.hunter.model.hunter;

import com.daml.hunter.model.da.internal.template.Archive;
import com.daml.ledger.javaapi.data.CreateAndExerciseCommand;
import com.daml.ledger.javaapi.data.CreateCommand;
import com.daml.ledger.javaapi.data.CreatedEvent;
import com.daml.ledger.javaapi.data.DamlCollectors;
import com.daml.ledger.javaapi.data.ExerciseCommand;
import com.daml.ledger.javaapi.data.Identifier;
import com.daml.ledger.javaapi.data.Int64;
import com.daml.ledger.javaapi.data.Numeric;
import com.daml.ledger.javaapi.data.Party;
import com.daml.ledger.javaapi.data.Record;
import com.daml.ledger.javaapi.data.Template;
import com.daml.ledger.javaapi.data.Text;
import com.daml.ledger.javaapi.data.Value;
import java.lang.Deprecated;
import java.lang.IllegalArgumentException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class Deal_Initiate extends Template {
  public static final Identifier TEMPLATE_ID = new Identifier("a40c2278c53fd2feefdf7880ed6ca9d48e711ae33b696355ead3e503dc2a83c3", "Hunter", "Deal_Initiate");

  public final Long deal_id;

  public final Long flow_id;

  public final String seller;

  public final String buyer;

  public final BigDecimal price_agreed;

  public final Map<String, List<List<String>>> documents;

  public Deal_Initiate(Long deal_id, Long flow_id, String seller, String buyer,
      BigDecimal price_agreed, Map<String, List<List<String>>> documents) {
    this.deal_id = deal_id;
    this.flow_id = flow_id;
    this.seller = seller;
    this.buyer = buyer;
    this.price_agreed = price_agreed;
    this.documents = documents;
  }

  public CreateCommand create() {
    return new CreateCommand(Deal_Initiate.TEMPLATE_ID, this.toValue());
  }

  public CreateAndExerciseCommand createAndExerciseArchive(Archive arg) {
    Value argValue = arg.toValue();
    return new CreateAndExerciseCommand(Deal_Initiate.TEMPLATE_ID, this.toValue(), "Archive", argValue);
  }

  public CreateAndExerciseCommand createAndExerciseAccept_Deal(Accept_Deal arg) {
    Value argValue = arg.toValue();
    return new CreateAndExerciseCommand(Deal_Initiate.TEMPLATE_ID, this.toValue(), "Accept_Deal", argValue);
  }

  public CreateAndExerciseCommand createAndExerciseAccept_Deal() {
    return createAndExerciseAccept_Deal(new Accept_Deal());
  }

  public static CreateCommand create(Long deal_id, Long flow_id, String seller, String buyer,
      BigDecimal price_agreed, Map<String, List<List<String>>> documents) {
    return new Deal_Initiate(deal_id, flow_id, seller, buyer, price_agreed, documents).create();
  }

  public static Deal_Initiate fromValue(Value value$) throws IllegalArgumentException {
    Value recordValue$ = value$;
    Record record$ = recordValue$.asRecord().orElseThrow(() -> new IllegalArgumentException("Contracts must be constructed from Records"));
    List<Record.Field> fields$ = record$.getFields();
    int numberOfFields = fields$.size();
    if (numberOfFields != 6) {
      throw new IllegalArgumentException("Expected 6 arguments, got " + numberOfFields);
    }
    Long deal_id = fields$.get(0).getValue().asInt64().orElseThrow(() -> new IllegalArgumentException("Expected deal_id to be of type com.daml.ledger.javaapi.data.Int64")).getValue();
    Long flow_id = fields$.get(1).getValue().asInt64().orElseThrow(() -> new IllegalArgumentException("Expected flow_id to be of type com.daml.ledger.javaapi.data.Int64")).getValue();
    String seller = fields$.get(2).getValue().asParty().orElseThrow(() -> new IllegalArgumentException("Expected seller to be of type com.daml.ledger.javaapi.data.Party")).getValue();
    String buyer = fields$.get(3).getValue().asParty().orElseThrow(() -> new IllegalArgumentException("Expected buyer to be of type com.daml.ledger.javaapi.data.Party")).getValue();
    BigDecimal price_agreed = fields$.get(4).getValue().asNumeric().orElseThrow(() -> new IllegalArgumentException("Expected price_agreed to be of type com.daml.ledger.javaapi.data.Numeric")).getValue();
    Map<String, List<List<String>>> documents = fields$.get(5).getValue().asGenMap()
            .map(v$0 -> v$0.toMap(
                v$1 -> v$1.asParty().orElseThrow(() -> new IllegalArgumentException("Expected v$1 to be of type com.daml.ledger.javaapi.data.Party")).getValue(),
                v$1 -> v$1.asList()
            .map(v$2 -> v$2.toList(v$3 ->
                v$3.asList()
            .map(v$4 -> v$4.toList(v$5 ->
                v$5.asText().orElseThrow(() -> new IllegalArgumentException("Expected v$5 to be of type com.daml.ledger.javaapi.data.Text")).getValue()
            ))
            .orElseThrow(() -> new IllegalArgumentException("Expected v$3 to be of type com.daml.ledger.javaapi.data.DamlList"))

            ))
            .orElseThrow(() -> new IllegalArgumentException("Expected v$1 to be of type com.daml.ledger.javaapi.data.DamlList"))

            ))
            .orElseThrow(() -> new IllegalArgumentException("Expected documents to be of type com.daml.ledger.javaapi.data.DamlGenMap"))
                  ;
    return new com.daml.hunter.model.hunter.Deal_Initiate(deal_id, flow_id, seller, buyer, price_agreed, documents);
  }

  public Record toValue() {
    ArrayList<Record.Field> fields = new ArrayList<Record.Field>(6);
    fields.add(new Record.Field("deal_id", new Int64(this.deal_id)));
    fields.add(new Record.Field("flow_id", new Int64(this.flow_id)));
    fields.add(new Record.Field("seller", new Party(this.seller)));
    fields.add(new Record.Field("buyer", new Party(this.buyer)));
    fields.add(new Record.Field("price_agreed", new Numeric(this.price_agreed)));
    fields.add(new Record.Field("documents", this.documents.entrySet().stream().collect(DamlCollectors.toDamlGenMap(v$0 -> new Party(v$0.getKey()), v$0 -> v$0.getValue().stream().collect(DamlCollectors.toDamlList(v$1 -> v$1.stream().collect(DamlCollectors.toDamlList(v$2 -> new Text(v$2)))))))));
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
    if (!(object instanceof Deal_Initiate)) {
      return false;
    }
    Deal_Initiate other = (Deal_Initiate) object;
    return this.deal_id.equals(other.deal_id) && this.flow_id.equals(other.flow_id) && this.seller.equals(other.seller) && this.buyer.equals(other.buyer) && this.price_agreed.equals(other.price_agreed) && this.documents.equals(other.documents);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.deal_id, this.flow_id, this.seller, this.buyer, this.price_agreed, this.documents);
  }

  @Override
  public String toString() {
    return String.format("com.daml.hunter.model.hunter.Deal_Initiate(%s, %s, %s, %s, %s, %s)", this.deal_id, this.flow_id, this.seller, this.buyer, this.price_agreed, this.documents);
  }

  public static final class ContractId extends com.daml.ledger.javaapi.data.codegen.ContractId<Deal_Initiate> {
    public ContractId(String contractId) {
      super(contractId);
    }

    public ExerciseCommand exerciseArchive(Archive arg) {
      Value argValue = arg.toValue();
      return new ExerciseCommand(Deal_Initiate.TEMPLATE_ID, this.contractId, "Archive", argValue);
    }

    public ExerciseCommand exerciseAccept_Deal(Accept_Deal arg) {
      Value argValue = arg.toValue();
      return new ExerciseCommand(Deal_Initiate.TEMPLATE_ID, this.contractId, "Accept_Deal", argValue);
    }

    public ExerciseCommand exerciseAccept_Deal() {
      return exerciseAccept_Deal(new Accept_Deal());
    }
  }

  public static class Contract implements com.daml.ledger.javaapi.data.Contract {
    public final ContractId id;

    public final Deal_Initiate data;

    public final Optional<String> agreementText;

    public final Set<String> signatories;

    public final Set<String> observers;

    public Contract(ContractId id, Deal_Initiate data, Optional<String> agreementText,
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
      Deal_Initiate data = Deal_Initiate.fromValue(record$);
      return new Contract(id, data, agreementText, signatories, observers);
    }

    @Deprecated
    public static Contract fromIdAndRecord(String contractId, Record record$) {
      ContractId id = new ContractId(contractId);
      Deal_Initiate data = Deal_Initiate.fromValue(record$);
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
      return String.format("com.daml.hunter.model.hunter.Deal_Initiate.Contract(%s, %s, %s, %s, %s)", this.id, this.data, this.agreementText, this.signatories, this.observers);
    }
  }
}
