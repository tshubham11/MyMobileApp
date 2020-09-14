
 
package com.msp.jio.controller;



import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Collection;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.ResponseEntity;

import com.msp.jio.entity.Jio;
import com.msp.jio.modelassembler.JioModelAssembler;
import com.msp.jio.repository.JioRepository;
import com.msp.jio.exceptions.InvalidNumberException;




@RestController
public class JioController {
final String regex = "[0-9]+";
final Pattern p = Pattern.compile(regex);
@Autowired
private JioRepository jioRepository;
private JioModelAssembler jioAssembler;
public JioController(JioRepository jioRepository, JioModelAssembler jioAssembler) {
	this.jioAssembler=jioAssembler;
	this.jioRepository=jioRepository;
}
/**
 * This method recharges the phone number of the user
 * @param mobileNumber: The mobile number of the user
 * @param amount: The topUp amount
 * @return a Response Entity with the id, mobilenumber, updated balance and a link 
 * to self and a link to viewBalance
 * @throws InvalidNumberExcpetion
 */
@PutMapping("/topUp/{mobileNumber}")
public ResponseEntity<?> topUp(@PathVariable String mobileNumber, @RequestParam Double amount){
	try{
		if(mobileNumber.length()!=10 || !p.matcher(mobileNumber).matches()) {
			throw new InvalidNumberException();
		}
		Jio	tempJio=jioRepository.findByMobileNumber(mobileNumber);
		Jio rechargedJio= tempJio.topUp(amount);
		jioRepository.delete(tempJio);
		EntityModel<Jio> entityModel=jioAssembler.toModel(jioRepository.save(rechargedJio));
		return ResponseEntity //
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
			.body(entityModel);
	}
	catch (InvalidNumberException e) {
		System.out.println("Invalid Number");
		return null;
	}
}
/**
 * This method creates a new entity of the user that after the person has input their
 * desired number. 
 * @param mobileNumber: The mobile number of the new user
 * @return  a Response Entity with the id, mobilenumber, balance a link to self
 * and a link to viewBalance of all numbers
 * @throws InvalidNumberExcpetion
 */
@PostMapping("/addNewUser/{newMobileNumber}")
public ResponseEntity<?> addNewUser(@PathVariable String newMobileNumber){
	try{
		if(newMobileNumber.length()!=10 || !p.matcher(newMobileNumber).matches()) {
			throw new InvalidNumberException();
		}
		Jio newJio=new Jio(newMobileNumber);
		EntityModel<Jio> entityModel=jioAssembler.toModel(jioRepository.save(newJio));
		return ResponseEntity //
			.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
			.body(entityModel);
	}
	catch(InvalidNumberException e) {
		System.out.println("Invalid Number");
		return null;
	}
	}
/**
 * This method displays the entity of the user mapping them by their phone number. 
 * @param mobileNumber: The mobile number of the user
 * @return  a Response Entity with the id, mobilenumber, balance a link to self
 * and a link to viewBalance of all numbers
 * @throws InvalidNumberExcpetion
 */
@GetMapping("/viewBalance/{mobileNumber}")
public EntityModel<Jio> viewBalance(@PathVariable String mobileNumber) {
	try{
		if(mobileNumber.length()!=10 || !p.matcher(mobileNumber).matches()) {
			throw new InvalidNumberException();
		}  
		Jio jio = jioRepository.findByMobileNumber(mobileNumber);
		return jioAssembler.toModel(jio);
	}
	catch (InvalidNumberException e) {
		System.out.println("Invalid Number");
		return null;
	}

}
/**
 * This method displays a collection of all entities of the user
 * @return  a collection of Response Entities with the id, mobilenumber, balance and
 * a link to self and a link to viewBalance of all numbers
 * @throws InvalidNumberExcpetion
 */
@GetMapping("/viewBalance")
public CollectionModel<EntityModel<Jio>> allNumbers(){
	Collection<EntityModel<Jio>> jiousers=jioRepository.findAll().stream().map//
			(jio->jioAssembler.toModel(jio)).collect(Collectors.toList());
	return CollectionModel.of(jiousers, //
			linkTo(methodOn(JioController.class).allNumbers()).withSelfRel());
}
}
