package com.daml.hunter.model.da.random;

import com.daml.ledger.javaapi.data.Value;
import com.daml.ledger.javaapi.data.Variant;
import java.lang.String;

public abstract class Minstd {
  public static final String _packageId = "d8c2ae4afe16f0192cfcaa00ee16e0cf68c22020aa81e1ad55f739f5c42516fd";

  public Minstd() {
  }

  public abstract Value toValue();

  public static Minstd fromValue(Value value$) {
    Variant variant$ = value$.asVariant().orElseThrow(() -> new IllegalArgumentException("Expected Variant to build an instance of the Variant com.daml.hunter.model.da.random.Minstd"));
    if ("Minstd".equals(variant$.getConstructor())) {
      return com.daml.hunter.model.da.random.minstd.Minstd.fromValue(variant$);
    }
    throw new IllegalArgumentException("Found unknown constructor variant$.getConstructor() for variant com.daml.hunter.model.da.random.Minstd, expected one of [Minstd]");
  }
}
