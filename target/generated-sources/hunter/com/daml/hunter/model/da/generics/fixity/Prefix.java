package com.daml.hunter.model.da.generics.fixity;

import com.daml.hunter.model.da.generics.Fixity;
import com.daml.ledger.javaapi.data.Unit;
import com.daml.ledger.javaapi.data.Value;
import com.daml.ledger.javaapi.data.Variant;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

public class Prefix extends Fixity {
  public static final String _packageId = "d8c2ae4afe16f0192cfcaa00ee16e0cf68c22020aa81e1ad55f739f5c42516fd";

  public final Unit unitValue;

  public Prefix(Unit unitValue) {
    this.unitValue = unitValue;
  }

  public Variant toValue() {
    return new Variant("Prefix", Unit.getInstance());
  }

  public static Prefix fromValue(Value value$) throws IllegalArgumentException {
    Variant variant$ = value$.asVariant().orElseThrow(() -> new IllegalArgumentException("Expected: Variant. Actual: " + value$.getClass().getName()));
    if (!"Prefix".equals(variant$.getConstructor())) throw new IllegalArgumentException("Invalid constructor. Expected: Prefix. Actual: " + variant$.getConstructor());
    Value variantValue$ = variant$.getValue();
    Unit body = variantValue$.asUnit().orElseThrow(() -> new IllegalArgumentException("Expected body to be of type com.daml.ledger.javaapi.data.Unit"));
    return new Prefix(body);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (!(object instanceof Prefix)) {
      return false;
    }
    Prefix other = (Prefix) object;
    return this.unitValue.equals(other.unitValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.unitValue);
  }

  @Override
  public String toString() {
    return String.format("Prefix(%s)", this.unitValue);
  }
}
