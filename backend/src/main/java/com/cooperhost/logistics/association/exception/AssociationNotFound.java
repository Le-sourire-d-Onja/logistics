package com.cooperhost.logistics.association.exception;

public class AssociationNotFound extends RuntimeException {
  public AssociationNotFound() {
    super("The association is not found");
  }
}
