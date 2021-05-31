package com.daml.hunter.model.hunter;

import com.daml.ledger.javaapi.data.DamlCollectors;
import com.daml.ledger.javaapi.data.Party;
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

public class Upload_documents {
  public static final String _packageId = "a40c2278c53fd2feefdf7880ed6ca9d48e711ae33b696355ead3e503dc2a83c3";

  public final String uploader;

  public final List<String> document_name;

  public Upload_documents(String uploader, List<String> document_name) {
    this.uploader = uploader;
    this.document_name = document_name;
  }

  public static Upload_documents fromValue(Value value$) throws IllegalArgumentException {
    Value recordValue$ = value$;
    Record record$ = recordValue$.asRecord().orElseThrow(() -> new IllegalArgumentException("Contracts must be constructed from Records"));
    List<Record.Field> fields$ = record$.getFields();
    int numberOfFields = fields$.size();
    if (numberOfFields != 2) {
      throw new IllegalArgumentException("Expected 2 arguments, got " + numberOfFields);
    }
    String uploader = fields$.get(0).getValue().asParty().orElseThrow(() -> new IllegalArgumentException("Expected uploader to be of type com.daml.ledger.javaapi.data.Party")).getValue();
    List<String> document_name = fields$.get(1).getValue().asList()
            .map(v$0 -> v$0.toList(v$1 ->
                v$1.asText().orElseThrow(() -> new IllegalArgumentException("Expected v$1 to be of type com.daml.ledger.javaapi.data.Text")).getValue()
            ))
            .orElseThrow(() -> new IllegalArgumentException("Expected document_name to be of type com.daml.ledger.javaapi.data.DamlList"))
        ;
    return new com.daml.hunter.model.hunter.Upload_documents(uploader, document_name);
  }

  public Record toValue() {
    ArrayList<Record.Field> fields = new ArrayList<Record.Field>(2);
    fields.add(new Record.Field("uploader", new Party(this.uploader)));
    fields.add(new Record.Field("document_name", this.document_name.stream().collect(DamlCollectors.toDamlList(v$0 -> new Text(v$0)))));
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
    if (!(object instanceof Upload_documents)) {
      return false;
    }
    Upload_documents other = (Upload_documents) object;
    return this.uploader.equals(other.uploader) && this.document_name.equals(other.document_name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.uploader, this.document_name);
  }

  @Override
  public String toString() {
    return String.format("com.daml.hunter.model.hunter.Upload_documents(%s, %s)", this.uploader, this.document_name);
  }
}
