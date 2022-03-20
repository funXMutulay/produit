package org.nioun.essentials.produit.controller;

import java.math.BigDecimal;
import java.rmi.ServerException;
import java.util.List;

import org.nioun.essentials.produit.model.Produit;
import org.nioun.essentials.produit.repository.ProduitRepository;
import org.nioun.essentials.produit.service.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ServerWebInputException;

@RestController
public class ProduitController {
	
	Logger logger=LoggerFactory.getLogger(ProduitController.class);
	
	@Autowired
	ProduitRepository repository;
	
	@Autowired
	Environment environment ;

	@Autowired
	ProduitService service; 
	
@GetMapping("/produit/name/{name}/description/{description}")
 public Produit	recupererProduit(@PathVariable ("name") String name , @PathVariable ("description") String description){
//	 Produit produit = new Produit(1000L,name,description,BigDecimal.valueOf(1000));
	Produit produit=repository.findByNameAndDescription(name, description);
	
	logger.info("recupererProduit avec les valeurs name {} & description  {} ", name , description );
	
	if (produit==null) {
		throw new RuntimeException("Unable to find name "+name+"with description"+description);
	}
	
	String port = environment.getProperty("local.server.port");
	 produit.setEnv(port);
	return produit;
 }	
@PostMapping("/produitss")
public List<Produit> getAllProduits(){
	List<Produit> allProduits = service.getAllProduits();
	return allProduits;
	 
}

@PostMapping(path = "/produits")

public ResponseEntity<Produit> create(@RequestBody Produit newProduit) {
Produit produit = service.saveProduit(newProduit);

if (produit==null) {
	throw new RuntimeException("Remplir la fiche Produit  ");
}
return new ResponseEntity<>(produit, HttpStatus.CREATED);

}

@PostMapping(path="/produits",
		consumes = MediaType.APPLICATION_JSON_VALUE, 
		produces = MediaType.APPLICATION_JSON_VALUE)
public String saveProduitToRestDB(@RequestParam("file") MultipartFile file ,@RequestParam("name") String name ,
		@RequestParam("description") String description ,@RequestParam("price") BigDecimal price,
		@RequestParam("env")  String env) {
            
    service.saveProduitToDB(file, name, description, price, env);
    return "index";
}



@GetMapping("/produit/produitId/{produitId}")
public Produit getProduitById(@PathVariable ("produitId") Long produitId) {
	
	Produit produit = service.getProduit(produitId);
	if (produit==null) {
		throw new RuntimeException("Produit introuvable avec cet Id "+produitId);
	}
	return produit;
}
}
