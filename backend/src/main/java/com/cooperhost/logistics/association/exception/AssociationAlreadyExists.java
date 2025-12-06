package com.cooperhost.logistics.association.exception;

public class AssociationAlreadyExists extends RuntimeException {
  public AssociationAlreadyExists() {
    super("The association already exists");
  }
}
