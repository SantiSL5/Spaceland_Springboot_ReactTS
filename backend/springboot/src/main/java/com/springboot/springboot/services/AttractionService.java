package com.springboot.springboot.services;

import com.google.gson.Gson;
import com.springboot.springboot.model.Attraction;
import com.springboot.springboot.repository.AttractionRepository;
import com.springboot.springboot.requests.attraction.update.UpdateAttractionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AttractionService {
    @Autowired
    AttractionRepository attractionRepository;

    private static final Logger logger = LoggerFactory.getLogger(Attraction.class);

    private static final Gson gson = new Gson();

    public List<Attraction> getAllAttractions() {
        return attractionRepository.findAll();
    }

    public Page<Attraction> getAllAttractions(Integer limit, Integer offset) {
        Pageable pageable = PageRequest.of(limit,offset);
        return attractionRepository.findAll(pageable);
    }

    public Attraction getAttraction(String id) throws Exception {
        try {
            return getAttraction(Long.parseLong(id));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public Attraction getAttraction(Long id) {
        Optional<Attraction> optional = attractionRepository.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    public Attraction createAttraction(Attraction attraction) {
        attraction.setCreated_at(new Timestamp(System.currentTimeMillis()));
        attraction.setUpdated_at(new Timestamp(System.currentTimeMillis()));
        return attractionRepository.save(attraction);
    }

    public Attraction updateAttraction(String id, UpdateAttractionRequest updatedAttraction) {
        try {
            Attraction attraction = getAttraction(id);
            if (attraction != null) {
                if (updatedAttraction.getName() != null ) {
                    attraction.setName(updatedAttraction.getName());
                }
                if (updatedAttraction.getDescription() != null ) {
                    attraction.setName(updatedAttraction.getDescription());
                }
                if (updatedAttraction.getPhoto() != null) {
                    attraction.setPhoto(updatedAttraction.getPhoto());
                }
                if (updatedAttraction.getRide_capacity() != null) {
                    attraction.setRide_capacity(updatedAttraction.getRide_capacity());
                }
                attraction.setUpdated_at(new Timestamp(System.currentTimeMillis()));
                return attractionRepository.save(attraction);
            }
        } catch (Exception e) {
            logger.error("Error updating attraction: {}", e.getMessage());
        }
        return null;
    }

    public void deleteAttraction(Long id) {
        try {
            attractionRepository.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting attraction: {}", e.getMessage());
        }
    }
    public void deleteAttraction(String id) {
        try {
            Long attractionId = Long.parseLong(id);
            deleteAttraction(attractionId);
        } catch (Exception e) {
            logger.error("Error deleting attraction: {}", e.getMessage());
        }
    }

    public void deleteManyAttractions(ArrayList<Long> ids) {
        try {
            for (Long id : ids) {
                deleteAttraction(id);
            }
        } catch (Exception e) {
            logger.error("Error deleting attraction: {}", e.getMessage());
        }
    }
}

