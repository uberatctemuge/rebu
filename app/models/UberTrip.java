package models;
import org.jadira.usertype.dateandtime.joda.*;
import org.jadira.usertype.spi.*;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Query;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import play.db.jpa.JPA;
import play.db.jpa.Model;
import util.GoogleMapUtil;
/**
 * A model represents a single uber trip with 
 * 1 pickup location/time and 1 dropoff location/time
 * @author temuge
 *
 */
@Entity
@Table(name="uber_trips")
public class UberTrip extends Model{
	
	public Double pickup_lat;
	public Double pickup_lng;
	public Double dropoff_lat;
	public Double dropoff_lng;
	public Timestamp pickup_date;
	public Timestamp dropoff_date;
	
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("pickup_lat", pickup_lat);
		json.addProperty("pickup_lng", pickup_lng);
		json.addProperty("dropoff_lat", dropoff_lat);
		json.addProperty("dropoff_lng", dropoff_lng);
		return json;
	}
	
	public Double getPickup_lat() {
		return pickup_lat;
	}
	public void setPickup_lat(Double pickup_lat) {
		this.pickup_lat = pickup_lat;
	}
	public Double getPickup_lng() {
		return pickup_lng;
	}
	public void setPickup_lng(Double pickup_lng) {
		this.pickup_lng = pickup_lng;
	}
	public Double getDropoff_lat() {
		return dropoff_lat;
	}
	public void setDropoff_lat(Double dropoff_lat) {
		this.dropoff_lat = dropoff_lat;
	}
	public Double getDropoff_lng() {
		return dropoff_lng;
	}
	public void setDropoff_lng(Double dropof_lng) {
		this.dropoff_lng = dropof_lng;
	}
	public Timestamp getPickup_date() {
		return pickup_date;
	}
	public void setPickup_date(Timestamp pickup_date) {
		this.pickup_date = pickup_date;
	}
	public Timestamp getDropoff_date() {
		return dropoff_date;
	}
	public void setDropoff_date(Timestamp dropoff_date) {
		this.dropoff_date = dropoff_date;
	}
}
