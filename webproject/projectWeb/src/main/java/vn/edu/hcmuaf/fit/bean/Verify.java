package vn.edu.hcmuaf.fit.bean;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.io.Serializable;
import java.sql.Timestamp;

public class Verify implements Serializable {

    @ColumnName("public_id")
    private int  public_id;

    @ColumnName("booking_id")
    private  String booking_id ;

    @ColumnName("date_create")
    private Timestamp date_create;


    public Verify(){}
    public Verify(@ColumnName("public_id") int public_id,@ColumnName("booking_id") String booking_id,@ColumnName("p_key") Timestamp date_create){
        this.public_id = public_id;
        this.booking_id = booking_id;
        this.date_create = date_create;
    }

    public int getPublic_id() {
        return public_id;
    }

    public void setPublic_id(int public_id) {
        this.public_id = public_id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public Timestamp getDate_create() {
        return date_create;
    }

    public void setDate_create(Timestamp date_create) {
        this.date_create = date_create;
    }
}
