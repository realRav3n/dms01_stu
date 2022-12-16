/**
 * @作者 awa
 */
package com.qst.dms.entity;

import java.io.Serializable;
import java.util.Date;

//货运物流信息
public class Log extends DataBase implements Serializable{
    protected int log_id; //数据库中的日志记录id（每次操作都有）
    protected String time; // 日志采集时间
    protected String handle; //填日志的用户信息（名字）
    protected String next_handle; //下一个接收人名字
    protected String next_handle_phone; //下一个接收人的电话

    public int getLog_id() {
        return log_id;
    }
    public String getTime() {
        return time;
    }
    public String getHandle() {
        return handle;
    }
    public String getNext_handle() {
        return next_handle;
    }
    public String getNext_handle_phone() {
        return next_handle_phone;
    }

    public Log() {
    }

    public Log(int log_id,int transport_id, String time, int type, String handle,
                     String next_handler, String next_handler_phone) {
        this.transport_id=transport_id;
        this.transport_type=type;
        this.log_id = log_id;
        this.time = time;
        this.handle = handle;
        this.next_handle = next_handler;
        this.next_handle_phone = next_handler_phone;
    }

    public String toString() {
        return this.getLog_id() + "," + this.getTransport_id() + "," + this.getTime()
                + "," + this.getTransport_type() + "," + this.getHandle()
                + "," + this.getNext_handle() + "," + this.getNext_handle_phone();
    }

}
