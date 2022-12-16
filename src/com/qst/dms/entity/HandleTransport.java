package com.qst.dms.entity;

import java.io.Serializable;

public class HandleTransport extends Transport implements Serializable {
    public HandleTransport(int transport_id, String destination, String recipient,
                           String recipient_phone, String now_handle, String now_handler_phone) {
        this.transport_id = transport_id;
        this.destination = destination;
        this.recipient = recipient;
        this.recipient_phone = recipient_phone;
        this.now_handle = now_handle;
        this.now_handle_phone = now_handler_phone;
    }

    public String toString() {
        return this.getTransport_id() + "," + this.getDestination() + "," + this.getRecipient()
                + "," + this.getRecipient_phone() + "," + this.getNow_handle() + "," + this.getNow_handle_phone();
    }
}
