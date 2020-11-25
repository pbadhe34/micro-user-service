package com.services;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.ws.rs.core.GenericEntity;

@Path("/users")
public class UserService {
	private static Map<String, User> userDB;

	private static AtomicInteger idCounter;

	static {
		System.out.println("The initilaization for userDB");
		// customerDB = new ConcurrentHashMap<Customer_ID, Customer>();
		userDB = new ConcurrentHashMap<String, User>();
		idCounter = new AtomicInteger();
		User user1 = new User();
		user1.setUserName("DON");
		user1.setUserPass("MushKill");
		user1.setUserMail("narang@malaysia.net");

		int idCount = idCounter.incrementAndGet();

		// add User objects to map;
		user1.setUserId("" + idCount);
		userDB.put(user1.getUserId(), user1);

		User user2 = new User();

		idCount = idCounter.incrementAndGet();

		user2.setUserName("Kishor");
		user2.setUserPass("mohanErE2");
		user2.setUserMail("kish.killer@gmail.net");

		// int idCount2 = idCounter.incrementAndGet();

		user2.setUserId("" + idCount);
		// add User objects to map;

		userDB.put(user2.getUserId(), user2);

	}

	public UserService() {
		System.out.println("UserService()");

	}

	@GET
	@Produces({ "application/json", "application/xml" })
	// public Map getAllCustomers()
	/*
	 * The returned map doesnot work with array data filtering on client side.And
	 * also with AngularJS resource we have to add isArray:false congiguration for
	 * query method
	 */
	public Response getAllCustomers() {
		System.out.println("getAllCustomers from json service");
		int size = userDB.size();
		System.out.println("The number of initial customer entries are " + size);
		System.out.println("The initial customer entries are " + userDB);

		List<User> userList = new ArrayList<User>(userDB.values());
		System.out.println("The usr list is  " + userList);

		GenericEntity entity = new GenericEntity<List<User>>(userList) {};
		return Response.ok(entity).build();

	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json" })
	public User getUser(@PathParam("id") String id) {
		System.out.println("getUser for json " + id);
		int size = userDB.size();
		System.out.println("The User entries are " + size);
		User customer = userDB.get(id);
		if (customer == null) {
			// throw new WebApplicationException(Response.Status.NOT_FOUND);
			System.out.println("The User NOT identified as  " + customer);

			return null;
		}
		System.out.println("\nUserResource.getCustomer() in json for " + customer.getUserId());

		System.out.println("The User identified as  " + customer.getUserName());

		return customer;
	}

	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces({ "application/json" })
	// public Response createCustomerInJSON(Customer customer)
	// //Here the angularJS on client side expects the same object as json in the
	// response output
	public User createUserInJSON(User customer) {
		System.out.println("UserResource.createUser() post from json " + customer);
		// customer.setId(""+cunter.incrementAndGet());
		// customer.setId(""+(++counter));
		int idCount = idCounter.incrementAndGet();

		String cust_id = "" + idCount;
		customer.setUserId(cust_id);
		userDB.put(cust_id, customer);

		User custObj = userDB.get(cust_id);
		System.out.println("The ciustomer in map is  " + custObj);
		System.out.println("Created customer " + cust_id);
		String result = "Added customer " + cust_id;
		int size = userDB.size();
		System.out.println("The customer entries in poste are " + size);

		// return Response.status(201).entity(cust_id).build();
		return customer;

	}

	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces({ "application/json" })
	// public Response updateCustomerInJSON(@PathParam("id") String id,Customer
	// customer)
	// Here the angularJS on client side expects the same object as json in the
	// respoinse output

	public User updateUserInJSON(@PathParam("id") String id, User customer) {
		System.out.println("CustomerResource.updateUser() put in json " + customer);

		String cust_id = customer.getUserId();
		System.out.println("Updating the customer with id = " + id);
		User custObj = userDB.get(cust_id);
		System.out.println("The User in map is  " + custObj);
		if (custObj == null)
			return null;

		userDB.put(cust_id, customer);

		System.out.println("Updated customer " + cust_id);
		String result = "Updated customer " + cust_id;
		int size = userDB.size();
		System.out.println("The customer entries in update are " + size);

		// return Response.status(201).entity(result).build();
		// Here the angularJS on client side expects the same object as json in the
		// respoinse output
		return customer;

	}

	@DELETE
	@Path("/{id}")
	// Here the angularJS on client side expects the same object as json in the
	// respoinse output
	// public Response removeCustomerInJSON(@PathParam("id") String id)

	public User removeCustomerInJSON(@PathParam("id") String id) {
		System.out.println("CustomerResource.removeCustomerInJSON()  in json for " + id);

		User custObj = userDB.get(id);
		System.out.println("The customer in map is  " + custObj);
		if (custObj == null)
			return null;

		userDB.remove(id);

		String result = "Removed customer " + id;
		int size = userDB.size();
		System.out.println("The customer entries after  delete  are " + size);
		// return Response.status(201).entity(result).build();

		return custObj;

	}

	@GET
	@Path("{id}")
	@Produces("text/plain")
	public String getUserString(@PathParam("id") String id) {
		System.out.println("\nCustomerResource.getCustomerString() in plain text for  " + id);
		User cust = getUser(id);
		System.out.println("The user in plain text is  " + cust);
		if (cust == null)
			return null;
		else
			return cust.toString();
	}
}
