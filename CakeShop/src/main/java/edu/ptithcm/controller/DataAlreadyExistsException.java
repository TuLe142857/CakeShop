package edu.ptithcm.controller;


/**
 * Xử lý các lỗi trùng dữ liệu khi thêm vào database
 * @author Le Ngoc Tu
 */
public class DataAlreadyExistsException extends Exception{
    public DataAlreadyExistsException(String message){
        super(message);
    }
}