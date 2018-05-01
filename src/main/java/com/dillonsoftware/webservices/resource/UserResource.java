package com.dillonsoftware.webservices.resource;

import com.dillonsoftware.webservices.bean.Error;
import com.dillonsoftware.webservices.bean.User;
import com.dillonsoftware.webservices.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

@Service
@Path("/users")
public class UserResource {

	private final UserService userService;

	@Autowired
	public UserResource(
			final UserService userService) {
		this.userService = userService;
	}


	@GET
	@Produces("application/json;charset=utf-8")
	public Response getUsers(@RequestParam(value="q", required=false) String query) {
		List<User> users = null;
		if(query==null)
			users = userService.listUsers();
		else
			users = userService.listUsers(query);
		
		// sort users
		Collections.sort(users, (User user1, User user2) -> user1.getName().compareTo(user2.getName()));
		
		return Response.status(Response.Status.OK).entity(users).build();
	}
	
	@POST
	@Produces("application/json;charset=utf-8")
	public Response addUser(@RequestBody User user) {
		Integer id = userService.addUser(user);
		return Response.status(Response.Status.OK).entity(id).build();
	}

	@Path("/{userId}")
	@DELETE
	@Produces("application/json;charset=utf-8")
	public Response deleteUser(@PathVariable("userId") Integer userId) {
		
		final User user = userService.getUser(userId);

		if (null == user) {
			final Error error = new Error();
			error.setMessage("User not found.");
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		} else {
			userService.deleteUser(userId);
			return Response.status(Response.Status.OK).entity(null).build();
		}
	}
	
	@Path("/{userId}")
	@GET
	@Produces("application/json;charset=utf-8")
	public Response getUser(@PathParam("userId") final Integer userId) {
		final User user = userService.getUser(userId);

		if (null == user) {
			final Error error = new Error();
			error.setMessage("User not found.");
			return Response.status(Response.Status.NOT_FOUND).entity(error).build();
		} else {
			return Response.status(Response.Status.OK).entity(user).build();
		}
	}

}
