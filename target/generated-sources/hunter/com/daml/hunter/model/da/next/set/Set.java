package com.daml.hunter.model.da.next.set;

import com.daml.ledger.javaapi.data.DamlCollectors;
import com.daml.ledger.javaapi.data.Record;
import com.daml.ledger.javaapi.data.Unit;
import com.daml.ledger.javaapi.data.Value;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class Set<a> {
  public static final String _packageId = "d8c2ae4afe16f0192cfcaa00ee16e0cf68c22020aa81e1ad55f739f5c42516fd";

  public final Map<String, Unit> textMap;

  public Set(Map<String, Unit> textMap) {
    this.textMap = textMap;
  }

  public static <a> Set<a> fromValue(Value value$) throws IllegalArgumentException {
    Value recordValue$ = value$;
    Record record$ = recordValue$.asRecord().orElseThrow(() -> new IllegalArgumentException("Contracts must be constructed from Records"));
    List<Record.Field> fields$ = record$.getFields();
    int numberOfFields = fields$.size();
    if (numberOfFields != 1) {
      throw new IllegalArgumentException("Expected 1 arguments, got " + numberOfFields);
    }
    Map<String, Unit> textMap = fields$.get(0).getValue().asTextMap()
            .map(v$0 -> v$0.toMap(v$1 ->
                v$1.asUnit().orElseThrow(() -> new IllegalArgumentException("Expected v$1 to be of type com.daml.ledger.javaapi.data.Unit"))
            ))
            .orElseThrow(() -> new IllegalArgumentException("Expected textMap to be of type com.daml.ledger.javaapi.data.DamlTextMap"))
                  ;
    return new com.daml.hunter.model.da.next.set.Set<a>(textMap);
  }

  public Record toValue() {
    ArrayList<Record.Field> fields = new ArrayList<Record.Field>(1);
    fields.add(new Record.Field("textMap", this.textMap.entrySet().stream().collect(DamlCollectors.toDamlTextMap(Map.Entry::getKey, v$0 -> Unit.getInstance())) ));
    return new Record(fields);
  }

  public static <a> Set<a> fromValue(Value value$, Function<Value, a> fromValuea) throws
      IllegalArgumentException {
    Value recordValue$ = value$;
    Record record$ = recordValue$.asRecord().orElseThrow(() -> new IllegalArgumentException("Contracts must be constructed from Records"));
    List<Record.Field> fields$ = record$.getFields();
    int numberOfFields = fields$.size();
    if (numberOfFields != 1) {
      throw new IllegalArgumentException("Expected 1 arguments, got " + numberOfFields);
    }
    Map<String, Unit> textMap = fields$.get(0).getValue().asTextMap()
            .map(v$0 -> v$0.toMap(v$1 ->
                v$1.asUnit().orElseThrow(() -> new IllegalArgumentException("Expected v$1 to be of type com.daml.ledger.javaapi.data.Unit"))
            ))
            .orElseThrow(() -> new IllegalArgumentException("Expected textMap to be of type com.daml.ledger.javaapi.data.DamlTextMap"))
                  ;
    return new com.daml.hunter.model.da.next.set.Set<a>(textMap);
  }

  public Record toValue(Function<a, Value> toValuea) {
    ArrayList<Record.Field> fields = new ArrayList<Record.Field>(1);
    fields.add(new Record.Field("textMap", this.textMap.entrySet().stream().collect(DamlCollectors.toDamlTextMap(Map.Entry::getKey, v$0 -> Unit.getInstance())) ));
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
    if (!(object instanceof Set<?>)) {
      return false;
    }
    Set<?> other = (Set<?>) object;
    return this.textMap.equals(other.textMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.textMap);
  }

  @Override
  public String toString() {
    return String.format("com.daml.hunter.model.da.next.set.Set(%s)", this.textMap);
  }
}
