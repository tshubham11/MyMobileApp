package com.msp.jio.modelassembler;

import org.springframework.stereotype.Component;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;

import com.msp.jio.controller.JioController;
import com.msp.jio.entity.Jio;


@Component
public class JioModelAssembler implements RepresentationModelAssembler<Jio, EntityModel<Jio>>{
	public EntityModel<Jio> toModel(Jio jio){
		return EntityModel.of(jio, //
			      linkTo(methodOn(JioController.class).viewBalance(jio.getMobileNumber())).withSelfRel(), //
			      linkTo(methodOn(JioController.class).allNumbers()).withRel("viewBalance"));
	}
}
