package com.techlab.spring.exception;

public class ProductoDuplicadoException extends RuntimeException {
  public ProductoDuplicadoException(String message) {
    super(message);
  }
}
