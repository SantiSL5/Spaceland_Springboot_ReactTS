package com.springboot.springboot.services;

import com.google.gson.Gson;
import com.springboot.springboot.model.Ticket;
import com.springboot.springboot.repository.TicketRepository;
import com.springboot.springboot.requests.ticket.update.UpdateTicketRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;

    private static final Logger logger = LoggerFactory.getLogger(Ticket.class);

    private static final Gson gson = new Gson();

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Page<Ticket> getAllTickets(Integer limit, Integer offset) {
        Pageable pageable = PageRequest.of(limit,offset);
        return ticketRepository.findAll(pageable);
    }

    public Ticket getTicket(String id) throws Exception {
        try {
            return getTicket(Long.parseLong(id));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Ticket getTicket(Long id) {
        Optional<Ticket> optional = ticketRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    public Ticket createTicket(Ticket ticket) {
        ticket.setCreated_at(new Timestamp(System.currentTimeMillis()));
        ticket.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(String id, UpdateTicketRequest updatedTicket) {
        try {
            Ticket ticket = getTicket(id);
            if (ticket != null) {
                if (updatedTicket.getDate() != null ) {
                    ticket.setDate(LocalDate.parse(updatedTicket.getDate()));
                }

                if (updatedTicket.getPersons() != null) {
                    ticket.setPersons(updatedTicket.getPersons());
                }

                if (updatedTicket.getPersons() != null) {
                    ticket.setPersons(updatedTicket.getPersons());
                }

                ticket.setUpdated_at(new Timestamp(System.currentTimeMillis()));
                return ticketRepository.save(ticket);
            }
        } catch (Exception e) {
            logger.error("Error updating ticket: {}", e.getMessage());
        }
        return null;
    }

    public void deleteTicket(Long id) {
        try {
            ticketRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting ticket: {}", e.getMessage());
        }
    }
    public void deleteTicket(String id) {
        try {
            Long ticketId = Long.parseLong(id);
            deleteTicket(ticketId);
        } catch (Exception e) {
            logger.error("Error deleting ticket: {}", e.getMessage());
        }
    }

    public void deleteManyTickets(ArrayList<Long> ids) {
        try {
            for (Long id : ids) {
                deleteTicket(id);
            }
        } catch (Exception e) {
            logger.error("Error deleting ticket: {}", e.getMessage());
        }
    }
}
