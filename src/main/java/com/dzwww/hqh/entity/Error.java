package com.dzwww.hqh.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

/**
 * 错误信息
 */
public class Error {

    @JacksonXmlText
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
