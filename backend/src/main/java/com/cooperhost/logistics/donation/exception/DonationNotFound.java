package com.cooperhost.logistics.donation.exception;

public class DonationNotFound extends RuntimeException {
  public DonationNotFound() {
    super("The donation is not found");
  }
}
