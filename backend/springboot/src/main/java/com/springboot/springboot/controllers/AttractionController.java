package com.springboot.springboot.controllers;

import com.springboot.springboot.model.Attraction;
import com.springboot.springboot.requests.attraction.create.NewAttractionRequest;
import com.springboot.springboot.requests.attraction.update.UpdateAttractionRequest;
import com.springboot.springboot.requests.general.DeleteManyRequest;
import com.springboot.springboot.services.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class AttractionController {
	@Autowired
	AttractionService attractionService;

	@GetMapping("/attraction")
	public ResponseEntity<List<Attraction>> getAllAttractions(@RequestParam(value = "limit", required = false, defaultValue = "0") Integer limit,
														   @RequestParam(value = "offset", required = false) Integer offset) {
		try {
			List<Attraction> attractions = new ArrayList<Attraction>();
			if ( offset != null  && offset > 0 ) {
				attractionService.getAllAttractions(limit,offset).forEach(attractions::add);
			}else {
				attractionService.getAllAttractions().forEach(attractions::add);
			}

			return new ResponseEntity<>(attractions, HttpStatus.OK);
		} catch (Exception e) {
			System.err.println(e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/attraction/{id}")
	public ResponseEntity<?> getAttraction(@PathVariable(required = true) String id) {
		try {
			Attraction attraction = attractionService.getAttraction(id);
			if (attraction == null) return ResponseEntity.notFound().build();
 			return ResponseEntity.ok(attraction);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Attraction not found");
		}
	}

	@PostMapping("/admin/attraction")
	public ResponseEntity<Attraction> createAttraction(@RequestBody NewAttractionRequest newAttractionRequest) {
		try {
			Attraction newAttraction = new Attraction();
			newAttraction.setName(newAttractionRequest.getName());
			newAttraction.setDescription(newAttractionRequest.getDescription());
			newAttraction.setPhoto(newAttractionRequest.getPhoto());
			newAttraction.setRide_capacity(newAttractionRequest.getRide_capacity());

			Attraction createdAttraction = attractionService.createAttraction(newAttraction);
			return new ResponseEntity<>(createdAttraction, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	@PutMapping("/admin/attraction/{id}")
	public ResponseEntity<?> updateAttraction(@PathVariable String id, @RequestBody UpdateAttractionRequest updatedAttraction) {
		try {
			Attraction attraction = attractionService.updateAttraction(id, updatedAttraction);
			if (attraction == null) return ResponseEntity.notFound().build();
			return ResponseEntity.ok(attraction);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Attraction update failed");
		}
	}

	@DeleteMapping("/admin/attraction/{id}")
	public ResponseEntity<?> deleteAttraction(@PathVariable String id) {
		try {
			attractionService.deleteAttraction(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Attraction deletion failed");
		}
	}

	@DeleteMapping("/admin/attraction/deleteMany")
	public ResponseEntity<?> deleteManyAttractions(@RequestBody DeleteManyRequest manyAttractions) {
		try {
			attractionService.deleteManyAttractions(manyAttractions.getIds());
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body("Attractions deletion failed");
		}
	}

}