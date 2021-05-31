package com.daml.hunter.model.ghc.stack.types.callstack;

import com.daml.hunter.model.ghc.stack.types.CallStack;
import com.daml.ledger.javaapi.data.Value;
import com.daml.ledger.javaapi.data.Variant;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.Objects;

public class FreezeCallStack extends CallStack {
  public static final String _packageId = "f3a8894cb1c3e35049399d6a7ae98b6669772e31c932afff68e1b3ac36b8f327";

  public final CallStack callStackValue;

  public FreezeCallStack(CallStack callStackValue) {
    this.callStackValue = callStackValue;
  }

  public Variant toValue() {
    return new Variant("FreezeCallStack", this.callStackValue.toValue());
  }

  public static FreezeCallStack fromValue(Value value$) throws IllegalArgumentException {
    Variant variant$ = value$.asVariant().orElseThrow(() -> new IllegalArgumentException("Expected: Variant. Actual: " + value$.getClass().getName()));
    if (!"FreezeCallStack".equals(variant$.getConstructor())) throw new IllegalArgumentException("Invalid constructor. Expected: FreezeCallStack. Actual: " + variant$.getConstructor());
    Value variantValue$ = variant$.getValue();
    CallStack body = CallStack.fromValue(variantValue$);
    return new FreezeCallStack(body);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null) {
      return false;
    }
    if (!(object instanceof FreezeCallStack)) {
      return false;
    }
    FreezeCallStack other = (FreezeCallStack) object;
    return this.callStackValue.equals(other.callStackValue);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.callStackValue);
  }

  @Override
  public String toString() {
    return String.format("FreezeCallStack(%s)", this.callStackValue);
  }
}
