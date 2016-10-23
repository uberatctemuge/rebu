package scripts;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import models.UberTrip;

import org.apache.commons.csv.*;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.jadira.usertype.dateandtime.joda.*;

import play.db.jpa.JPA;

/**
 * A one time script to insert data for Uber trips
 * @author temuge
 *
 */
public class SaveUberDataToDb {
	
	public static void run() {
		save("/Users/temuge/uberatc/rawdata/apr14.csv");
	}
	
	private static void save(String filename) {
		System.out.println("Started saving: " + filename );
		EntityManager entityManager = JPA.em();
		try {
			String csv = readFile(filename, Charset.defaultCharset());
			CSVParser parser = CSVParser.parse(csv, CSVFormat.DEFAULT);
			List<CSVRecord> records = parser.getRecords();
			List<UberTrip> trips = new ArrayList<UberTrip>();
			UberTrip trip = null;
			for (int i=10001; i<500001; i++) {
				CSVRecord record = records.get(i);
				if (i%2==1) {
					trip = new UberTrip();
					trip.setPickup_date(parseDate(record.get(0)));
					trip.setPickup_lat(Double.parseDouble(record.get(1)));
					trip.setPickup_lng(Double.parseDouble(record.get(2)));
				} else {
					trip.setDropoff_date(parseDate(record.get(0)));
					trip.setDropoff_lat(Double.parseDouble(record.get(1)));
					trip.setDropoff_lng(Double.parseDouble(record.get(2)));
					trips.add(trip);
					save(trip, entityManager);
					if (i%40==0) {
						entityManager.flush();
						entityManager.clear();
					}
				}
				if (i%1000==0) System.out.println(i);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("done.");
	}
	
	private static void save(UberTrip trip, EntityManager entityManager) {
		trip.save();
	}
	
	private static Timestamp parseDate(String date) {
		DateTimeFormatter f = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
		DateTime dateTime = f.parseDateTime(date);
		return new Timestamp(dateTime.getMillis());
	}
	
	private static String readFile(String path, Charset encoding) 
	throws IOException 
	{
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static void main(String[] args) {
		SaveUberDataToDb.run();
	}
}
