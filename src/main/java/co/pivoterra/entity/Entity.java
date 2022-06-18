package co.pivoterra.entity;

public interface Entity<T> {

  T getIdentifier();

  void setIdentifier(T identifier);

}
