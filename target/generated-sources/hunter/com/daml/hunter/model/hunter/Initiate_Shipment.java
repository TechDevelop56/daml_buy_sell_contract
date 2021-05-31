package com.daml.hunter.model.hunter;

import com.daml.ledger.javaapi.data.Record;
import com.daml.ledger.javaapi.data.Value;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Initiate_Shipment {
  public static final String _packageId = "a40c2278c53fd2feefdf7880ed6ca9d48e711ae33b696355ead3e503dc2a83c3";

  public final Shipment_details shipment_details;

  public Initiate_Shipment(Shipment_details shipment_details) {
    this.shipment_details = shipment_details;
  }

  public static Initiate_Shipment fromValue(Value value$) throws IllegalArgumentException {
    Value recordValue$ = value$;
    Record record$ = recordValue$.asRecord().orElseThrow(() -> new IllegalArgumentException("Contracts must be constructed from Records"));
    List<Record.Field> fields$ = record$.getFields();
    int numberOfFields = fields$.size();
    if (numberOfFields != 1) {
      throw new IllegalArgumentException("Expected 1 arguments, got " + numberOfFields);
    }
    Shipment_details shipment_details = Shipment_details.fromValue(fields$.get(0).getValue());
    return new com.daml.hunter.model.hunter.Initiate_Shipment(shipment_details);
  }

  public Record toValue() {
    ArrayList<Record.Field> fields = new ArrayList<Record.Field>(1);
    fields.add(new Record.Field("shipment_details", this.shipment_details.toValue()));
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
    if (!(object instanceof Initiate_Shipment)) {
      return false;
    }
    Initiate_Shipment other = (Initiate_Shipment) object;
    return this.shipment_details.equals(other.shipment_details);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.shipment_details);
  }

  @Override
  public String toString() {
    return String.format("com.daml.hunter.model.hunter.Initiate_Shipment(%s)", this.shipment_details);
  }
}
