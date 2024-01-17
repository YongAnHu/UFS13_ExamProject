package it.hu.ufs13_examproject.data;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import it.hu.ufs13_examproject.model.MenuCategory;
import it.hu.ufs13_examproject.model.Item;
import it.hu.ufs13_examproject.model.Order;
import it.hu.ufs13_examproject.model.Row;
import it.hu.ufs13_examproject.model.Table;

public class Repository {
	private FirebaseFirestore db = FirebaseFirestore.getInstance();

	public void getMenu(String collection, final MenuAsyncResponse callback) {
		String tag = "FS_Fetch_Menu";

		db.collection(collection)
				.addSnapshotListener((value, error) -> {
					if (error != null) {
						Log.w(tag, "Error getting documents.", error);
						callback.processFailed(new Exception());
						return;
					}

					Data.getRepo(collection).clear();

					for (DocumentChange dc : value.getDocumentChanges()) {
						DocumentSnapshot document = dc.getDocument();
						MenuCategory data = document.toObject(MenuCategory.class);

						switch (dc.getType()) {
							case ADDED:
								Log.d(tag, "New Document Menu");
								Data.getRepo(collection).add(data.getName());
								Data.getRepo(collection).addAll(data.getItems());
								break;

							case MODIFIED:
								Log.d(tag, "Update Document Menu");
								break;

							case REMOVED:
								Log.d(tag, "Removed Document Menu");
								break;
						}
					}
					callback.processTerminated(Data.getRepo(collection));
				});
	}

	public void getTables(final TableAsyncResponse callback) {
		String collection = "tables";
		String tag = "FS_Fetch_Table";

		db.collection(collection)
				.addSnapshotListener((value, error) -> {
					if (error != null) {
						Log.w(tag, "Error getting documents.", error);
						callback.processFailed(new Exception());
						return;
					}

					for (DocumentChange dc : value.getDocumentChanges()) {
						DocumentSnapshot document = dc.getDocument();
						Table data = document.toObject(Table.class);
						data.setId(document.getId());

						switch (dc.getType()) {
							case ADDED:
								Log.d(tag, "New Document Table");
								Data.getTables().add(data);
								break;

							case MODIFIED:
								Log.d(tag, "Update Document Table");
								break;

							case REMOVED:
								Log.d(tag, "Removed Document Table");
								Data.getTables().removeIf(table -> table.getId().equals(data.getId()));
								break;
						}
					}
					callback.processTerminated(Data.getTables());
				});
	}

	public void getOrders(String document, final OrderAsyncResponse callback) {
		String tag = "FS_Fetch_Orders";

		db.collection("tables")
				.document(document)
				.get()
				.addOnSuccessListener(documentSnapshot -> {
					Log.d(tag, "New Document Orders");
					Table table = documentSnapshot.toObject(Table.class);
					if (table == null) {
						Log.w(tag, "Tavolo eliminato");
						callback.processFailed(new Exception());
						return;
					}

					Data.getOrdersList().clear();
					Data.getOrdersList().addAll(table.getOrders());
					callback.processTerminated(Data.getOrdersList());
				})
				.addOnFailureListener(e -> {
					Log.w(tag, "Error getting documents.", e);
					callback.processFailed(new Exception());
				});
	}

//	public void addItemToDB(Item item, String collection) {
//		String tag = "FS_Add_Item_DB";
//
//		Map<String, Object> data = new HashMap<>();
//		data.put("id", item.getId());
//		data.put("name", item.getName());
//		data.put("description", item.getDescription());
//		data.put("allergens", item.getAllergens());
//		data.put("image", item.getImage());
//		data.put("price", item.getPrice());
//
//		db.collection(collection)
//				.add(data)
//				.addOnSuccessListener(documentReference -> {
//					Log.d(tag, "DocumentSnapshot added with ID: " + documentReference.getId());
//				})
//				.addOnFailureListener(e -> {
//					Log.w(tag, "Error adding document", e);
//				});
//
//		db.collection(collection)
//				.document(item.getId())
//				.set(data)
//				.addOnSuccessListener(unused -> Log.d(tag, "Document deleted"))
//				.addOnFailureListener(e -> Log.w(tag, "Error removing document", e));
//	}
//
//	public void removeItemFromDB(String id, String collection) {
//		String tag = "FS_Remove_Item_DB";
//
//		db.collection(collection).document(id)
//				.delete()
//				.addOnSuccessListener(unused -> {
//					Log.d(tag, "Document deleted");
//				})
//				.addOnFailureListener(e -> {
//					Log.w(tag, "Error removing document", e);
//				});
//	}

	public void addTableToDB(String table_name) {
		String collection = "tables";
		String tag = "FS_Add_Table";

		String table_id = "Table_" + table_name.replaceAll("\\D+", "");
		Table table = new Table(table_name, 0);

		db.collection(collection)
				.document(table_id)
				.set(table)
				.addOnSuccessListener(unused -> Log.d(tag, "Tavolo aggiunto"))
				.addOnFailureListener(e -> Log.w(tag, "Error adding document", e));
	}

	public void removeTableFromDB(String table_name) {
		String collection = "tables";
		String tag = "FS_Remove_Table_DB";

		String table_id = "Table_" + table_name.replaceAll("\\D+", "");

		db.collection(collection).document(table_id)
				.delete()
				.addOnSuccessListener(unused -> Log.d(tag, "Table deleted"))
				.addOnFailureListener(e -> Log.w(tag, "Error removing document", e));
	}

//	public void removeAllergen(String allergen, String collection, String document) {
//		db.collection(collection).document(document)
//				.update("allergens", FieldValue.arrayRemove(allergen));
//	}
//
//	public void updateItemInDB(Map<String, Object> data, String collection, String document) {
//		db.collection(collection).document(document)
//				.update(data)
//				.addOnCompleteListener(task -> {
//					if (task.isSuccessful()) {
//						Log.d("Update data in DB", "Item update success");
//					} else {
//						Log.w("Update data in DB", "Error getting documents.", task.getException());
//					}
//				});
//	}
//
//	public void updateAllergens(String allergen, String collection, String document) {
//		db.collection(collection).document(document)
//				.update("allergens", FieldValue.arrayUnion(allergen))
//				.addOnCompleteListener(task -> {
//					if (task.isSuccessful()) {
//						Log.d("Update data in DB", "Item update success");
//					} else {
//						Log.w("Update data in DB", "Error getting documents.", task.getException());
//					}
//				});
//	}

	public void updateTable(Table table, String collection) {
		Map<String, Object> data = new HashMap<>();
		data.put("name", table.getName());
		data.put("num", table.getNum());
		data.put("orders", table.getOrders());

		db.collection(collection).document(table.getId())
				.set(data)
				.addOnCompleteListener(task -> {
					if (task.isSuccessful()) {
						Log.d("Update table in DB", "Table update success");
					} else {
						Log.w("Update table in DB", "Error getting documents.", task.getException());
					}
				});
	}

	public void addItemToOrder(Item item, String document) {
		String collection = "tables";
		String tag = "FS_Add_ItemToOrder";

		Order order = new Order();
		order.setTimestamp(new Timestamp(new Date()));
		order.setItems(formatOrderData(new Row(item.getId(), item.getName())));

		db.runTransaction(transaction -> {
			Table table = transaction.get(db.collection(collection).document(document)).toObject(Table.class);

			Data.getOrdersList().clear();
			Data.getOrdersList().addAll(table.getOrders());
			Data.getOrdersList().add(order);

			transaction.update(db.collection(collection).document(document), "orders", Data.getOrdersList());

			return null;
		})
				.addOnSuccessListener(o -> Log.d(tag, "Order inserted successfully"))
				.addOnFailureListener(e -> Log.w(tag, "Error getting documents.", e));
	}

	public void removeItemFromOrder(Order order, String document) {
		String collection = "tables";
		String tag = "FS_Remove_ItemToOrder";

		db.runTransaction(transaction -> {
					Data.getOrdersList().remove(order);

					transaction.update(db.collection(collection).document(document), "orders", Data.getOrdersList());

					return null;
				})
				.addOnSuccessListener(o -> Log.d(tag, "Order inserted successfully"))
				.addOnFailureListener(e -> Log.w(tag, "Error getting documents.", e));
	}

	private ArrayList<Row> formatOrderData(Row row) {
		ArrayList<Row> data = new ArrayList<>();

		data.add(row);

		return data;
	}
}
