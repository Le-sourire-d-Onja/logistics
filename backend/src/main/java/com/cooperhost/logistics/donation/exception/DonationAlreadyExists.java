package com.cooperhost.logistics.donation.exception;

public class DonationAlreadyExists extends RuntimeException {
  public DonationAlreadyExists() {
    super("The donation already exists");
  }
}
