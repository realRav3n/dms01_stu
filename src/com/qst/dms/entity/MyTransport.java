package com.qst.dms.entity;

import java.io.Serializable;

public class MyTransport extends Transport implements Serializable {
    public MyTransport(int transport_id, String recipient, String transport_name,
                       int transport_type, String now_handle, String now_handler_phone) {
        this.transport_id = transport_id;
        this.recipient = recipient;
        this.transport_name = transport_name;
        this.transport_type = transport_type;
        this.now_handle = now_handle;
        this.now_handle_phone = now_handler_phone;
    }

    public String toString() {
        return this.getTransport_id() + "," + this.getRecipient() + "," + this.getTransport_name()
                + "," + this.getTransport_type() + "," + this.getNow_handle() + "," + this.getNow_handle_phone();
    }
}
