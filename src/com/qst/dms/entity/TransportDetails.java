/**
 * @作者 awa
 */
package com.qst.dms.entity;

import java.io.Serializable;
import java.util.Date;

//货运物流信息
public class TransportDetails extends Log implements Serializable{
    public TransportDetails() {
    }
    //log视图展示内容
    public TransportDetails(int transport_id, String time, int transport_type,
               String next_handler, String next_handler_phone) {
        this.transport_id=transport_id;
        this.time = time;
        this.transport_type=transport_type;
        this.next_handle = next_handler;
        this.next_handle_phone = next_handler_phone;
    }

    public String toString() {
        return this.getTransport_id() + "," + this.getTime() + "," + this.getTransport_type()
                + "," + this.getNext_handle() + "," + this.getNext_handle_phone();
    }

}
