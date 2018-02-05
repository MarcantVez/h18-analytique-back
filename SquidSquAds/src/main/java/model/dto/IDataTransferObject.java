package model.dto;

public interface IDataTransferObject<T, E> {
    T fromClass(E classObj);
}
