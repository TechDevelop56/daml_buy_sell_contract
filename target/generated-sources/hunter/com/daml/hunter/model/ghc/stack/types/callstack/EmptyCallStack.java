package com.daml.hunter.model.ghc.stack.types.callstack;

import com.daml.hunter.model.ghc.stack.types.CallStack;
import com.daml.ledger.javaapi.data.Unit;
import com.daml.ledger.javaapi.data.Value;
import com.daml.ledger.javaapi.data.Variant;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

public class EmptyCallStack extends CallStack {
  public static final String _packageId = "f3a8894cb1c3e35049399d6a7ae98b6669772e31c932afff68e1b3ac36b8f327";

  public final Unit unitValue;

  public EmptyCallStack(Unit unitValue) {
    this.unitValue = unitValue;
  }

  public Variant toValue() {
    return new Variant("EmptyCallStack", Unit.getInstance());
  }

  public static EmptyCallStack fromValue(Value value$) throws IllegalArgumentException {
    Variant variant$ = value$.asVariant().orElseThrow(() -> new IllegalArgumentException("Expected: Variant. Actual: " + value$.getClass().getName()));
    if (!"EmptyCallStack".equals(variant$.getConstructor())) throw new IllegalArgumentException("Invalid constructor. Expected: EmptyCallStack. Actual: " + variant$.getConstructor());
    Value variantValue$ = variant$.getValue();
    Unit body = variantValue$.asUnit().orElseThrow(() -> new IllegalArgumentException("Expected body to be of type com.daml.ledger.javaapi.data.Unit"));
    return new EmptyCallStack(body);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (!(object instanceof EmptyCallStack)) {
      return false;
    }
    EmptyCallStack other = (EmptyCallStack) object;
    return this.unitValue.equals(other.unitValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.unitValue);
  }

  @Override
  public String toString() {
    return String.format("EmptyCallStack(%s)", this.unitValue);
  }
}
