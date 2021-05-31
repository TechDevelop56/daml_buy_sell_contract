package com.daml.hunter.model.hunter;

import com.daml.ledger.javaapi.data.Record;
import com.daml.ledger.javaapi.data.Text;
import com.daml.ledger.javaapi.data.Value;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Shipment_details {
  public static final String _packageId = "a40c2278c53fd2feefdf7880ed6ca9d48e711ae33b696355ead3e503dc2a83c3";

  public final String deliveryLocation;

  public final String delivery_starting_date;

  public final String delivery_end_date;

  public Shipment_details(String deliveryLocation, String delivery_starting_date,
      String delivery_end_date) {
    this.deliveryLocation = deliveryLocation;
    this.delivery_starting_date = delivery_starting_date;
    this.delivery_end_date = delivery_end_date;
  }

  public static Shipment_details fromValue(Value value$) throws IllegalArgumentException {
    Value recordValue$ = value$;
    Record record$ = recordValue$.asRecord().orElseThrow(() -> new IllegalArgumentException("Contracts must be constructed from Records"));
    List<Record.Field> fields$ = record$.getFields();
    int numberOfFields = fields$.size();
    if (numberOfFields != 3) {
      throw new IllegalArgumentException("Expected 3 arguments, got " + numberOfFields);
    }
    String deliveryLocation = fields$.get(0).getValue().asText().orElseThrow(() -> new IllegalArgumentException("Expected deliveryLocation to be of type com.daml.ledger.javaapi.data.Text")).getValue();
    String delivery_starting_date = fields$.get(1).getValue().asText().orElseThrow(() -> new IllegalArgumentException("Expected delivery_starting_date to be of type com.daml.ledger.javaapi.data.Text")).getValue();
    String delivery_end_date = fields$.get(2).getValue().asText().orElseThrow(() -> new IllegalArgumentException("Expected delivery_end_date to be of type com.daml.ledger.javaapi.data.Text")).getValue();
    return new com.daml.hunter.model.hunter.Shipment_details(deliveryLocation, delivery_starting_date, delivery_end_date);
  }

  public Record toValue() {
    ArrayList<Record.Field> fields = new ArrayList<Record.Field>(3);
    fields.add(new Record.Field("deliveryLocation", new Text(this.deliveryLocation)));
    fields.add(new Record.Field("delivery_starting_date", new Text(this.delivery_starting_date)));
    fields.add(new Record.Field("delivery_end_date", new Text(this.delivery_end_date)));
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
    if (!(object instanceof Shipment_details)) {
      return false;
    }
    Shipment_details other = (Shipment_details) object;
    return this.deliveryLocation.equals(other.deliveryLocation) && this.delivery_starting_date.equals(other.delivery_starting_date) && this.delivery_end_date.equals(other.delivery_end_date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.deliveryLocation, this.delivery_starting_date, this.delivery_end_date);
  }

  @Override
  public String toString() {
    return String.format("com.daml.hunter.model.hunter.Shipment_details(%s, %s, %s)", this.deliveryLocation, this.delivery_starting_date, this.delivery_end_date);
  }
}
