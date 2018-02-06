package lebed.ecommerce.controller.rest;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

interface CoreController {
    default Link createHateoasLink(Long id) {
        return linkTo(getClass()).slash(id).withSelfRel();
    }
}
