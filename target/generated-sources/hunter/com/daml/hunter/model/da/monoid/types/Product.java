package com.daml.hunter.model.da.monoid.types;

import com.daml.ledger.javaapi.data.Record;
import com.daml.ledger.javaapi.data.Value;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class Product<a> {
  public static final String _packageId = "6c2c0667393c5f92f1885163068cd31800d2264eb088eb6fc740e11241b2bf06";

  public final a unpack;

  public Product(a unpack) {
    this.unpack = unpack;
  }

  public static <a> Product<a> fromValue(Value value$, Function<Value, a> fromValuea) throws
      IllegalArgumentException {
    Value recordValue$ = value$;
    Record record$ = recordValue$.asRecord().orElseThrow(() -> new IllegalArgumentException("Contracts must be constructed from Records"));
    List<Record.Field> fields$ = record$.getFields();
    int numberOfFields = fields$.size();
    if (numberOfFields != 1) {
      throw new IllegalArgumentException("Expected 1 arguments, got " + numberOfFields);
    }
    a unpack = fromValuea.apply(fields$.get(0).getValue());
    return new com.daml.hunter.model.da.monoid.types.Product<a>(unpack);
  }

  public Record toValue(Function<a, Value> toValuea) {
    ArrayList<Record.Field> fields = new ArrayList<Record.Field>(1);
    fields.add(new Record.Field("unpack", toValuea.apply(this.unpack)));
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
    if (!(object instanceof Product<?>)) {
      return false;
    }
    Product<?> other = (Product<?>) object;
    return this.unpack.equals(other.unpack);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.unpack);
  }

  @Override
  public String toString() {
    return String.format("com.daml.hunter.model.da.monoid.types.Product(%s)", this.unpack);
  }
}
