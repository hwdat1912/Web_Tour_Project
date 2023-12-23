package vn.edu.hcmuaf.fit.bean;


import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.io.Serializable;
import java.sql.Timestamp;

public class PublicKey implements Serializable {
    @ColumnName("public_id")
    private int  public_id;
    @ColumnName("User_id")
    private  String userId ;
    @ColumnName("p_key")
    private String p_key;
    @ColumnName("date_create")
    private Timestamp date_create;
    @ColumnName("date_report")
    private Timestamp date_report;
    @ColumnName("status")
    private int status;

    public PublicKey(@ColumnName("public_id") int public_id,@ColumnName("userId") String userId,@ColumnName("p_key") String p_key,@ColumnName("date_create") Timestamp date_create,@ColumnName("date_report") Timestamp date_report,@ColumnName("status") int status) {
        this.public_id = public_id;
        this.userId = userId;
        this.p_key = p_key;
        this.date_create = date_create;
        this.date_report= date_report;
        this.status = status;
    }

    public int getPublic_id() {
        return public_id;
    }

    public void setPublic_id(int public_id) {
        this.public_id = public_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getP_key() {
        return p_key;
    }

    public void setP_key(String p_key) {
        this.p_key = p_key;
    }

    public Timestamp getDate_create() {
        return date_create;
    }

    public void setDate_create(Timestamp date_create) {
        this.date_create = date_create;
    }

    public Timestamp getDate_report() {
        return date_report;
    }

    public void setDate_report(Timestamp date_report) {
        this.date_report = date_report;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PublicKey{" +
                "public_id=" + public_id +
                ", userId='" + userId + '\'' +
                ", p_key='" + p_key + '\'' +
                ", date_create=" + date_create +
                ", date_report=" + date_report +
                ", status=" + status +
                '}';
    }
}
