package com.springboot.springboot.controllers;

import com.springboot.springboot.model.Ticket;
import com.springboot.springboot.model.User;
import com.springboot.springboot.repository.TicketRepository;
import com.springboot.springboot.requests.ticket.create.NewTicketRequest;
import com.springboot.springboot.requests.ticket.update.UpdateTicketRequest;
import com.springboot.springboot.services.TicketService;
import com.springboot.springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class TicketController {
	@Autowired
	TicketService ticketService;

	@Autowired
	UserService userService;

	@GetMapping("/ticket")
	public ResponseEntity<List<Ticket>> getAllTickets(@RequestParam(value = "limit", required = false, defaultValue = "0") Integer limit,
														   @RequestParam(value = "offset", required = false) Integer offset) {
		try {
			List<Ticket> tickets = new ArrayList<Ticket>();
			if ( offset != null  && offset > 0 ) {
				ticketService.getAllTickets(limit,offset).forEach(tickets::add);
			}else {
				ticketService.getAllTickets().forEach(tickets::add);
			}

			return new ResponseEntity<>(tickets, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/ticket/{id}")
	public ResponseEntity<?> getTicket(@PathVariable(required = true) String id) {
		try {
			Ticket ticket = ticketService.getTicket(id);
			if (ticket == null) return ResponseEntity.notFound().build();
 			return ResponseEntity.ok(ticket);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Ticket not found");
		}
	}

	@PostMapping("/admin/ticket")
	public ResponseEntity<?> createTicket(@RequestBody NewTicketRequest newTicketRequest) {
		try {
			Ticket newTicket = new Ticket();
			newTicket.setDate(LocalDate.parse(newTicketRequest.getDate()));
			newTicket.setPersons(newTicketRequest.getPersons());
			newTicket.setUser(userService.token_user());
			newTicket.setCheckin(false);
			Ticket createdTicket = ticketService.createTicket(newTicket);
			return new ResponseEntity<>(createdTicket, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping("/admin/ticket/{id}")
	public ResponseEntity<?> updateTicket(@PathVariable String id, @RequestBody UpdateTicketRequest updatedTicket) {
		try {
			Ticket ticket = ticketService.updateTicket(id, updatedTicket);
			if (ticket == null) return ResponseEntity.notFound().build();
			return ResponseEntity.ok(ticket);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Ticket update failed");
		}
	}

	@DeleteMapping("/admin/ticket/{id}")
	public ResponseEntity<?> deleteTicket(@PathVariable String id) {
		try {
			ticketService.deleteTicket(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Ticket deletion failed");
		}
	}
}