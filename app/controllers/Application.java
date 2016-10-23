package controllers;

import play.*;
import play.mvc.*;
import repo.UberTripsLoader;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
        render();
    }

}