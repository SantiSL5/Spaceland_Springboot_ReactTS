package com.springboot.springboot.resources;

import java.util.List;

public class ResponseResource {
    String statusText;

    public ResponseResource(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
}
