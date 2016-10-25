package controllers;

import play.*;
import play.mvc.*;
import repo.UberTripsLoader;

import java.util.*;

import models.*;
/**
 * Controller for home page
 * @author temuge
 *
 */
public class Home extends Controller {

	/**
	 * A controller endpoint for home page
	 */
    public static void home() {
        render();
    }

}