package com.daml.hunter.model.ghc.stack.types;

import com.daml.ledger.javaapi.data.Value;
import com.daml.ledger.javaapi.data.Variant;
import java.lang.String;

public abstract class CallStack {
  public static final String _packageId = "f3a8894cb1c3e35049399d6a7ae98b6669772e31c932afff68e1b3ac36b8f327";

  public CallStack() {
  }

  public abstract Value toValue();

  public static CallStack fromValue(Value value$) {
    Variant variant$ = value$.asVariant().orElseThrow(() -> new IllegalArgumentException("Expected Variant to build an instance of the Variant com.daml.hunter.model.ghc.stack.types.CallStack"));
    if ("EmptyCallStack".equals(variant$.getConstructor())) {
      return com.daml.hunter.model.ghc.stack.types.callstack.EmptyCallStack.fromValue(variant$);
    }
    if ("PushCallStack".equals(variant$.getConstructor())) {
      return com.daml.hunter.model.ghc.stack.types.callstack.PushCallStack.fromValue(variant$);
    }
    if ("FreezeCallStack".equals(variant$.getConstructor())) {
      return com.daml.hunter.model.ghc.stack.types.callstack.FreezeCallStack.fromValue(variant$);
    }
    throw new IllegalArgumentException("Found unknown constructor variant$.getConstructor() for variant com.daml.hunter.model.ghc.stack.types.CallStack, expected one of [EmptyCallStack, PushCallStack, FreezeCallStack]");
  }
}
