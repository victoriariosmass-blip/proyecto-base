package com.verdegreen.dto;

/**
 * Formato estándar de respuesta: {status, messege, data}.
 * "messege" va escrito así a propósito, igual que en la consigna del TP2.
 */
public class ApiResponse<T> {

    private int status;
    private String messege;
    private T data;

    public ApiResponse(int status, String messege, T data) {
        this.status = status;
        this.messege = messege;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public String getMessege() {
        return messege;
    }

    public T getData() {
        return data;
    }
}
