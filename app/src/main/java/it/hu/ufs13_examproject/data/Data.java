package it.hu.ufs13_examproject.data;

import java.util.ArrayList;

import it.hu.ufs13_examproject.model.MenuCategory;
import it.hu.ufs13_examproject.model.Order;
import it.hu.ufs13_examproject.model.Table;

public class Data {
	private static ArrayList<Object> menu = new ArrayList<>();
	private static ArrayList<Object> menu_daily = new ArrayList<>();
	private static ArrayList<Table> tables = new ArrayList<>();
	private static ArrayList<Order> orders = new ArrayList<>();
	private static Data instance;

	private Data() {}

	public static Data getInstance() {
		if (instance == null) instance = new Data();

		return instance;
	}

	public static ArrayList<Object> getRepo(String type) {
		if (type.equals("menu_daily")) return menu_daily;

		return menu;
	}

	public static ArrayList<Object> getMenu() {
		return menu;
	}

	public static void setMenu(ArrayList<Object> menu) {
		Data.menu = menu;
	}

	public static ArrayList<Object> getMenu_daily() {
		return menu_daily;
	}

	public static void setMenu_daily(ArrayList<Object> menu_daily) {
		Data.menu_daily = menu_daily;
	}

	public static ArrayList<Table> getTables() {
		return tables;
	}

	public static void setTables(ArrayList<Table> tables) {
		Data.tables = tables;
	}

	public static ArrayList<Order> getOrdersList() {
		return orders;
	}

	public static void setOrdersList(ArrayList<Order> orders) {
		Data.orders = orders;
	}
}
